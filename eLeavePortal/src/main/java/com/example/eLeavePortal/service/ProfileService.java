package com.example.eLeavePortal.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import com.example.eLeavePortal.entity.*;
import com.example.eLeavePortal.repository.*;
import com.example.eLeavePortal.security.CustomUserDetails;
import com.example.eLeavePortal.dto.ProfileDto;
import com.example.eLeavePortal.enums.ProfileStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserContactRepository contactRepository;

    private final StudentRepository studentRepository;
    private final ClassTeacherRepository ctRepository;
    private final HodRepository hodRepository;
    private final WardenRepository wardenRepository;
    private final HostelControllerRepository hcRepository;

    private final BatchRepository batchRepository;
    private final BranchRepository branchRepository;
    private final SectionRepository sectionRepository;
    private final HostelRepository hostelRepository;

    private final StorageService storageService;

    public ProfileDto getProfile(String username) {

        UserEntity user = userRepository.findByUserName(username).orElseThrow();

        ProfileDto dto = new ProfileDto();

        dto.setUsername(username);
        dto.setRole(user.getRole());
        dto.setStatus(user.getProfileStatus());

        contactRepository.findByUser(user).ifPresent(c -> {
            dto.setName(c.getName());
            dto.setMobile(c.getMobile());
            dto.setWhatsapp(c.getWhatsapp());
            dto.setEmail(c.getEmail());
            dto.setPfpUrl(c.getPfpUrl());
        });

        if (user.getRole() == RolesEnum.STUDENT) {

            studentRepository.findByUser(user).ifPresent(st -> {

                if (st.getPersonalProfile() != null) {
                    dto.setHomeAddress(
                        st.getPersonalProfile().getHomeAddress());
                }

                if (st.getAcademicProfile() != null) {

                    var a = st.getAcademicProfile();

                    dto.setRegNumber(a.getRegNumber());
                    dto.setRollNumber(a.getRollNumber());
                    dto.setCollegeMail(a.getCollegeMail());
                    dto.setBatchId(a.getBatch().getBatchId());
                    dto.setBranchId(a.getBranch().getBranchId());
                    dto.setSectionId(a.getSection().getId());
                    dto.setHostelId(a.getHostel().getHostelId());
                    dto.setHostelRoom(a.getHostelRoom());
                }

                if (st.getFacultyMapping() != null) {

                    var f = st.getFacultyMapping();

                    dto.setClassTeacherId(f.getClassTeacher().getClassTeacherId());
                    dto.setHodId(f.getHod().getHodId());
                    dto.setWardenId(f.getWarden().getWardenId());
                    dto.setHostelControllerId(f.getHostelController().getHcId());
                }
            });
        }

        if (user.getRole() == RolesEnum.CT) {
            ctRepository.findByUser(user).ifPresent(ct -> {
                if (ct.getBatch() != null) dto.setBatchId(ct.getBatch().getBatchId());
                if (ct.getBranch() != null) dto.setBranchId(ct.getBranch().getBranchId());
                if (ct.getSection() != null) dto.setSectionId(ct.getSection().getId());
            });
        }

        if (user.getRole() == RolesEnum.HOD) {
            hodRepository.findByUser(user).ifPresent(hod -> {
                if (hod.getBatch() != null) dto.setBatchId(hod.getBatch().getBatchId());
                if (hod.getBranch() != null) dto.setBranchId(hod.getBranch().getBranchId());
            });
        }

        
        if (user.getRole() == RolesEnum.WARDEN) {
            wardenRepository.findByUser(user).ifPresent(w -> {
                if (w.getHostel() != null) dto.setHostelId(w.getHostel().getHostelId());
                if (w.getHostelController() != null)
                    dto.setHostelControllerId(w.getHostelController().getHcId());
            });
        }

        if (user.getRole() == RolesEnum.HOSTEL_CONTROLLER) {
            hcRepository.findByUser(user).ifPresent(hc -> {

                dto.setHostelControllerId(hc.getHcId());

                List<WardenEntity> wardens =
                        wardenRepository.findByHostelController(hc);

                List<Long> hostelIds = wardens.stream()
                        .map(w -> w.getHostel().getHostelId())
                        .toList();

                dto.setHostelIds(hostelIds); // add List<Long> in DTO
            });
        }

        return dto;
    }

    public void saveProfile(String username, ProfileDto dto) {

        UserEntity user = userRepository.findByUserName(username).orElseThrow();

        saveContact(user, dto);

        switch (user.getRole()) {
            case STUDENT -> saveStudent(user, dto);
            case CT -> saveCT(user, dto);
            case HOD -> saveHod(user, dto);
            case WARDEN -> saveWarden(user, dto);
            case HOSTEL_CONTROLLER -> saveHostelController(user);

            default -> {}
        }

        if(user.getRole() != RolesEnum.ADMIN)
            user.setProfileStatus(ProfileStatusEnums.PENDING);
        else
            user.setProfileStatus(ProfileStatusEnums.ACTIVE);
    }

    public void updateProfileStatus(String username, ProfileStatusEnums status) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails actingUser = (CustomUserDetails) auth.getPrincipal();

        UserEntity targetUser = userRepository.findByUserName(username).orElseThrow();

        RolesEnum actorRole = actingUser.getRole();
        RolesEnum targetRole = targetUser.getRole();

        boolean isSelf = actingUser.getUsername().equals(username);

        boolean allowed = switch (actorRole) {
            case ADMIN -> 
                !isSelf && targetRole != RolesEnum.ADMIN;
            case CT, HOD, WARDEN, HOSTEL_CONTROLLER ->
                !isSelf && targetRole == RolesEnum.STUDENT && targetRole != RolesEnum.ADMIN;
            default -> false;
        };

        if(!allowed) {
            throw new AccessDeniedException("Not allowed to modify this profile!");
        }

        targetUser.setProfileStatus(status);

    }

    public List<ProfileDto> getAllFaculty(String username){

        List<UserEntity> users =
            userRepository.findByRoleIn(List.of(
                RolesEnum.CT,
                RolesEnum.HOD,
                RolesEnum.WARDEN,
                RolesEnum.HOSTEL_CONTROLLER
            ));

        return users.stream()
                .map(u -> getProfile(u.getUserName()))
                .toList();
    }

    public List<ProfileDto> getAllStudents(String username){

        UserEntity current = userRepository
                .findByUserName(username)
                .orElseThrow();

        if(current.getRole() == RolesEnum.STUDENT){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<UserEntity> students =
                userRepository.findByRole(RolesEnum.STUDENT);

        return students.stream()
                .map(u -> getProfile(u.getUserName()))
                .filter(dto -> canViewStudent(current, dto))
                .toList();
    }

    private void saveContact(UserEntity user, ProfileDto dto) {

        UserContactEntity contact = Optional.ofNullable(user.getUserContact())
                                        .orElseGet(UserContactEntity::new);

        contact.setUser(user);
        user.setUserContact(contact);

        if (dto.getName() != null) contact.setName(dto.getName());
        if (dto.getMobile() != null) contact.setMobile(dto.getMobile());
        if (dto.getWhatsapp() != null) contact.setWhatsapp(dto.getWhatsapp());
        if (dto.getEmail() != null) contact.setEmail(dto.getEmail());

        System.out.println("BBB");
        if (dto.getPfpImage() != null && !dto.getPfpImage().isEmpty()) {
            if (contact.getPfpUrl() != null) {
                try {
                    storageService.delete(contact.getPfpUrl());
                } catch (IOException ignored) {}
            }
        System.out.println("CCC");

            String url = storageService.savePfp(dto.getPfpImage());
            contact.setPfpUrl(url);
        }
        
        System.out.println("DDD");

        userRepository.save(user);
    }

    private void saveStudent(UserEntity user, ProfileDto dto) {

        StudentEntity student =
            studentRepository.findByUser(user)
                .orElseGet(() -> {
                    StudentEntity s = new StudentEntity();
                    s.setUser(user);
                    return studentRepository.save(s);
                });

        if (dto.getHomeAddress() != null) {

            StudentPersonalProfileEntity personal =
                Optional.ofNullable(student.getPersonalProfile())
                    .orElseGet(StudentPersonalProfileEntity::new);

            personal.setStudent(student);
            student.setPersonalProfile(personal);

            personal.setHomeAddress(dto.getHomeAddress());
        }

        if (dto.getRegNumber() != null) {

            StudentAcademicProfileEntity academic =
                Optional.ofNullable(student.getAcademicProfile())
                    .orElseGet(StudentAcademicProfileEntity::new);

            academic.setStudent(student);
            student.setAcademicProfile(academic);

            if (dto.getRegNumber() != null)
                academic.setRegNumber(dto.getRegNumber());

            if (dto.getRollNumber() != null)
                academic.setRollNumber(dto.getRollNumber());

            if (dto.getCollegeMail() != null)
                academic.setCollegeMail(dto.getCollegeMail());

            if (dto.getBatchId() != null)
                academic.setBatch(batchRepository.findById(dto.getBatchId()).orElseThrow());

            if (dto.getBranchId() != null)
                academic.setBranch(branchRepository.findById(dto.getBranchId()).orElseThrow());

            if (dto.getSectionId() != null)
                academic.setSection(sectionRepository.findById(dto.getSectionId()).orElseThrow());

            if (dto.getHostelId() != null)
                academic.setHostel(hostelRepository.findById(dto.getHostelId()).orElseThrow());

            if (dto.getHostelRoom() != null)
                academic.setHostelRoom(dto.getHostelRoom());
        }

        if (dto.getClassTeacherId() != null) {

            StudentFacultyMappingEntity mapping =
                Optional.ofNullable(student.getFacultyMapping())
                    .orElseGet(StudentFacultyMappingEntity::new);

            mapping.setStudent(student);
            student.setFacultyMapping(mapping);

            if (dto.getClassTeacherId() != null)
                mapping.setClassTeacher(
                    ctRepository.findById(dto.getClassTeacherId()).orElseThrow());

            if (dto.getHodId() != null)
                mapping.setHod(
                    hodRepository.findById(dto.getHodId()).orElseThrow());

            if (dto.getWardenId() != null)
                mapping.setWarden(
                    wardenRepository.findById(dto.getWardenId()).orElseThrow());

            if (dto.getHostelControllerId() != null)
                mapping.setHostelController(
                    hcRepository.findById(dto.getHostelControllerId()).orElseThrow());
        }

        studentRepository.save(student);
    }

    private void saveCT(UserEntity user, ProfileDto dto) {

        ClassTeacherEntity ct =
            ctRepository.findByUser(user)
                .orElse(new ClassTeacherEntity());

        ct.setUser(user);

        if (dto.getBatchId() != null)
            ct.setBatch(batchRepository.findById(dto.getBatchId()).orElseThrow());

        if (dto.getBranchId() != null)
            ct.setBranch(branchRepository.findById(dto.getBranchId()).orElseThrow());

        if (dto.getSectionId() != null)
            ct.setSection(sectionRepository.findById(dto.getSectionId()).orElseThrow());

        ctRepository.save(ct);
    }

    private void saveHod(UserEntity user, ProfileDto dto) {

        HodEntity hod =
            hodRepository.findByUser(user)
                .orElse(new HodEntity());

        hod.setUser(user);

        if (dto.getBatchId() != null)
            hod.setBatch(batchRepository.findById(dto.getBatchId()).orElseThrow());

        if (dto.getBranchId() != null)
            hod.setBranch(branchRepository.findById(dto.getBranchId()).orElseThrow());

        hodRepository.save(hod);
    }

    private void saveWarden(UserEntity user, ProfileDto dto) {

        WardenEntity warden =
            wardenRepository.findByUser(user)
                .orElse(new WardenEntity());

        warden.setUser(user);

        if (dto.getHostelId() != null)
            warden.setHostel(
                hostelRepository.findById(dto.getHostelId()).orElseThrow());
            warden.setHostelController(
                hcRepository.findById(dto.getHostelControllerId()).orElseThrow());

        wardenRepository.save(warden);
    }

    private void saveHostelController(UserEntity user) {

        HostelControllerEntity hc =
            hcRepository.findByUser(user)
                .orElse(new HostelControllerEntity());

        hc.setUser(user);

        hcRepository.save(hc);
    }

    private boolean canViewStudent(UserEntity viewer, ProfileDto student){

        switch (viewer.getRole()) {

            case ADMIN:
                return true;

            case CT:
                var ct = ctRepository.findByUser(viewer).orElseThrow();
                return Objects.equals(student.getBatchId(), ct.getBatch().getBatchId()) &&
                    Objects.equals(student.getBranchId(), ct.getBranch().getBranchId()) &&
                    Objects.equals(student.getSectionId(), ct.getSection().getId());

            case HOD:
                var hod = hodRepository.findByUser(viewer).orElseThrow();
                return Objects.equals(student.getBatchId(), hod.getBatch().getBatchId()) &&
                    Objects.equals(student.getBranchId(), hod.getBranch().getBranchId());

            case WARDEN:
                var w = wardenRepository.findByUser(viewer).orElseThrow();
                return Objects.equals(student.getHostelId(), w.getHostel().getHostelId());

            case HOSTEL_CONTROLLER:
                var hc = hcRepository.findByUser(viewer).orElseThrow();

                List<Long> hostelIds =
                        wardenRepository.findByHostelController(hc)
                        .stream()
                        .map(x -> x.getHostel().getHostelId())
                        .toList();

                return hostelIds.contains(student.getHostelId());

            default:
                return false;
        }
    }
}
