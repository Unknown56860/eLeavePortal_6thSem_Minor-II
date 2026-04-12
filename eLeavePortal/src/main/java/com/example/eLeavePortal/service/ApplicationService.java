package com.example.eLeavePortal.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.eLeavePortal.dto.ApplicationDto;
import com.example.eLeavePortal.entity.ApplicationEntity;
import com.example.eLeavePortal.entity.ApprovalRecordEntity;
import com.example.eLeavePortal.entity.StudentEntity;
import com.example.eLeavePortal.entity.UserEntity;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;
import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;
import com.example.eLeavePortal.repository.ApplicationRepository;
import com.example.eLeavePortal.repository.ApprovalRecordRepository;
import com.example.eLeavePortal.repository.StudentRepository;
import com.example.eLeavePortal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ApprovalRecordRepository approvalRepository;

    private final StorageService storageService;
    private final AiService aiService;

    public Long saveApplication(String username, ApplicationDto dto, String action) {
        ApplicationEntity entity;

        UserEntity user = userRepository.findByUserName(username).orElseThrow();
        validateUserActive(user);

        if (dto.getId() != null) {
            entity = applicationRepository.findById(dto.getId()).orElseThrow();
            validateStudentOwner(entity, username);
            validateEditable(entity);
        } else {
            entity = new ApplicationEntity();
            StudentEntity student = studentRepository.findByUser_UserName(username).orElseThrow();
            entity.setStudent(student);
            entity.setCurrentStatus(ApplicationStatusEnums.DRAFTED);
        }

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setExplanation(dto.getExplanation());

        entity.setLastUpdateDate(LocalDateTime.now());
        entity.setUpdatedBy(userRepository.findByUserName(username).get());

        if ("apply".equals(action)) {
            entity.setCurrentStatus(ApplicationStatusEnums.APPLIED);
        } else {
            entity.setCurrentStatus(ApplicationStatusEnums.DRAFTED);
        }

        MultipartFile file = dto.getAttachmentFile();
        if (file != null && !file.isEmpty()) {
            if (entity.getAttachmentUrl() != null) {
                try {
                    storageService.delete(entity.getAttachmentUrl());
                } catch (IOException ignored) {}
            }
            String url = storageService.saveFile(file);
            entity.setAttachmentUrl(url);
        }

        return applicationRepository.save(entity).getApplicationId();
    }

    public ApplicationDto getApplication(Long id, String username) {

        ApplicationEntity entity = applicationRepository.findById(id).orElseThrow();
        validateAccess(entity, username);

        return mapToDto(entity);
    }

    public List<ApplicationDto> getPermittedApplications(String username) {
        UserEntity user = userRepository.findByUserName(username).orElseThrow();

        validateUserActive(user);

        if (user.getRole() == RolesEnum.STUDENT) {
            return applicationRepository.findByStudent_User_UserName(username)
                    .stream().map(this::mapToDto).toList();
        }
        return applicationRepository.findAll().stream()
            .filter(app -> app.getCurrentStatus() != ApplicationStatusEnums.DRAFTED)
            .filter(app -> isVisibleToRole(app, user.getRole()))
            .map(this::mapToDto).toList();
    }

    public List<ApplicationDto> getStudentApplications(String studentUsername, String requester) {
        validateStudentAccess(studentUsername, requester);
        return applicationRepository.findByStudent_User_UserName(studentUsername)
                .stream().map(this::mapToDto).toList();
    }

    public void updateStatus(Long id, ApplicationStatusEnums status, String remark, String username) {
        ApplicationEntity app = applicationRepository.findById(id).orElseThrow();
        UserEntity user = userRepository.findByUserName(username).orElseThrow();

        validateUserActive(user);
        validateStatusTransition(app, status, user.getRole());

        app.setCurrentStatus(status);
        app.setUpdatedBy(user);

        ApprovalRecordEntity record = new ApprovalRecordEntity();

        record.setApplication(app);
        record.setUser(user);
        record.setRole(user.getRole());
        record.setRemark(remark);
        record.setApprovalStatus(status);
        record.setApprovalDate(LocalDate.now());

        approvalRepository.save(record);
    }

    public List<ApprovalRecordEntity> getApprovalHistory(Long appId, String username) {
        ApplicationEntity entity = applicationRepository.findById(appId).orElseThrow();
        validateAccess(entity, username);
        return approvalRepository.findByApplication_ApplicationIdOrderByApprovalDateAsc(appId);
    }

    public void delete(Long id) {
        applicationRepository.deleteById(id);
    }

    private void validateUserActive(UserEntity user) {
        if (user.getProfileStatus() != ProfileStatusEnums.ACTIVE) {
            throw new AccessDeniedException("Inactive profile");
        }
    }

    private void validateEditable(ApplicationEntity entity) {

        if (entity.getCurrentStatus() == ApplicationStatusEnums.DRAFTED)
            return;

        if (entity.getCurrentStatus() == ApplicationStatusEnums.ACTION_REQUIRED)
            return;

        throw new IllegalStateException("Application not editable");
    }

    private void validateStudentOwner(ApplicationEntity entity, String username) {
        if (!entity.getStudent().getUser().getUserName().equals(username)) {
            throw new AccessDeniedException("Not owner");
        }
    }

    private void validateStudentAccess(String student, String requester) {
        if (student.equals(requester)) return;
        UserEntity user = userRepository.findByUserName(requester).orElseThrow();
        if (user.getRole() != RolesEnum.STUDENT) return;
        throw new AccessDeniedException("Not allowed");
    }

    private void validateAccess(ApplicationEntity entity, String username) {
        if (entity.getStudent().getUser().getUserName().equals(username)) return;
        UserEntity user = userRepository.findByUserName(username).orElseThrow();
        if (user.getRole() != RolesEnum.STUDENT) return;
        throw new AccessDeniedException("Not allowed");
    }

    private void validateStatusTransition(ApplicationEntity app, ApplicationStatusEnums next, RolesEnum role) {
        ApplicationStatusEnums curr = app.getCurrentStatus();

        switch (role) {
            case STUDENT -> {
                if ((curr == ApplicationStatusEnums.APPLIED &&
                    next == ApplicationStatusEnums.CANCELLED) ||
                    (curr == ApplicationStatusEnums.DRAFTED &&
                    next == ApplicationStatusEnums.APPLIED)) return;
                throw new AccessDeniedException("Students cannot modify");
            }
            case CT -> {
                if (curr == ApplicationStatusEnums.APPLIED &&
                    (next == ApplicationStatusEnums.CT_APPROVED ||
                    next == ApplicationStatusEnums.REJECTED ||
                    next == ApplicationStatusEnums.ACTION_REQUIRED)) return;
            }
            case HOD -> {
                if (curr == ApplicationStatusEnums.CT_APPROVED &&
                    (next == ApplicationStatusEnums.HOD_APPROVED ||
                    next == ApplicationStatusEnums.REJECTED ||
                    next == ApplicationStatusEnums.ACTION_REQUIRED)) return;
            }
            case WARDEN -> {
                if (curr == ApplicationStatusEnums.HOD_APPROVED &&
                    (next == ApplicationStatusEnums.WARDEN_APPROVED ||
                    next == ApplicationStatusEnums.REJECTED ||
                    next == ApplicationStatusEnums.ACTION_REQUIRED)) return;
            }
            case HOSTEL_CONTROLLER -> {
                if (curr == ApplicationStatusEnums.WARDEN_APPROVED &&
                    (next == ApplicationStatusEnums.HC_APPROVED ||
                    next == ApplicationStatusEnums.REJECTED)) return;
            }
            case ADMIN -> {
                if (curr != ApplicationStatusEnums.DRAFTED &&
                    curr != ApplicationStatusEnums.CANCELLED) return;
            }
        }
        throw new AccessDeniedException("Invalid transition");
    }

    private boolean isVisibleToRole(ApplicationEntity app, RolesEnum role) {
        return switch (role) {
            case CT -> app.getCurrentStatus() == ApplicationStatusEnums.APPLIED;
            case HOD -> app.getCurrentStatus() == ApplicationStatusEnums.CT_APPROVED;
            case WARDEN -> app.getCurrentStatus() == ApplicationStatusEnums.HOD_APPROVED;
            case HOSTEL_CONTROLLER -> app.getCurrentStatus() == ApplicationStatusEnums.WARDEN_APPROVED;
            case ADMIN -> app.getCurrentStatus() != ApplicationStatusEnums.DRAFTED;
            default -> false;
        };
    }

    private ApplicationDto mapToDto(ApplicationEntity e) {
        ApplicationDto dto = new ApplicationDto();

        dto.setId(e.getApplicationId());

        dto.setUsername(e.getStudent().getUser().getUserName());
        dto.setStudentName(e.getStudent().getUser().getUserContact().getName());
        dto.setStudentRoll(e.getStudent().getAcademicProfile().getRollNumber());
        dto.setStudentPfp(e.getStudent().getUser().getUserContact().getPfpUrl());

        dto.setStartDate(e.getStartDate());
        dto.setEndDate(e.getEndDate());

        dto.setReason(e.getReason());
        dto.setExplanation(e.getExplanation());

        dto.setAttachmentUrl(e.getAttachmentUrl());

        dto.setLastUpdate(e.getLastUpdateDate());
        dto.setUpdatedBy(e.getUpdatedBy());

        dto.setCurrentStatus(e.getCurrentStatus());

        return dto;
    }

    public String enhanceText(String reason, String explanation) {
        return aiService.enhance(reason, explanation);
    }
}
