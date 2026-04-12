package com.example.eLeavePortal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.eLeavePortal.dto.AdminDashboardDto;
import com.example.eLeavePortal.dto.AdminUserDto;
import com.example.eLeavePortal.dto.ApplicationTrendDto;
import com.example.eLeavePortal.dto.AuthUserDto;
import com.example.eLeavePortal.entity.BatchEntity;
import com.example.eLeavePortal.entity.BranchEntity;
import com.example.eLeavePortal.entity.HostelEntity;
import com.example.eLeavePortal.entity.SectionEntity;
import com.example.eLeavePortal.enums.ApplicationStatusEnums;
import com.example.eLeavePortal.enums.RolesEnum;
import com.example.eLeavePortal.repository.ApplicationRepository;
import com.example.eLeavePortal.repository.BatchRepository;
import com.example.eLeavePortal.repository.BranchRepository;
import com.example.eLeavePortal.repository.HostelRepository;
import com.example.eLeavePortal.repository.SectionRepository;
import com.example.eLeavePortal.repository.StudentRepository;
import com.example.eLeavePortal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;

    private final BatchRepository batchRepository;
    private final BranchRepository branchRepository;
    private final SectionRepository sectionRepository;
    private final HostelRepository hostelRepository;

    private final AuthService authService;

    // ---- Dashboard ----
    public AdminDashboardDto getDashboard() {
        return AdminDashboardDto.builder()
                .totalStudents(studentRepository.count())
                .totalCT(userRepository.countByRole(RolesEnum.CT))
                .totalHOD(userRepository.countByRole(RolesEnum.HOD))
                .totalWarden(userRepository.countByRole(RolesEnum.WARDEN))
                .totalHC(userRepository.countByRole(RolesEnum.HOSTEL_CONTROLLER))
                .totalApplications(
                        applicationRepository.countByCurrentStatusNotIn(
                                List.of(ApplicationStatusEnums.DRAFTED, ApplicationStatusEnums.CANCELLED)
                        )
                )
                .build();
    }

    public List<ApplicationTrendDto> getApplicationTrend() {
        return applicationRepository.getApplicationTrend(
                List.of(ApplicationStatusEnums.DRAFTED, ApplicationStatusEnums.CANCELLED)
        );
    }

    // ---- Generic fetch ----
    public List<BatchEntity> getBatches() { return batchRepository.findAll(); }
    public List<BranchEntity> getBranches() { return branchRepository.findAll(); }
    public List<SectionEntity> getSections() { return sectionRepository.findAll(); }
    public List<HostelEntity> getHostels() { return hostelRepository.findAll(); }

    // ---- Add ----
    public void addBatch(String value) {
        BatchEntity e = new BatchEntity();
        e.setBatch(value);
        batchRepository.save(e);
    }

    public void addBranch(String value) {
        BranchEntity e = new BranchEntity();
        e.setBranch(value);
        branchRepository.save(e);
    }

    public void addSection(String value) {
        SectionEntity e = new SectionEntity();
        e.setSection(value);
        sectionRepository.save(e);
    }

    public void addHostel(String value) {
        HostelEntity e = new HostelEntity();
        e.setName(value);
        hostelRepository.save(e);
    }

    // ---- Delete ----
    public void deleteBatch(Long id) { batchRepository.deleteById(id); }
    public void deleteBranch(Long id) { branchRepository.deleteById(id); }
    public void deleteSection(Long id) { sectionRepository.deleteById(id); }
    public void deleteHostel(Long id) { hostelRepository.deleteById(id); }

    // ---- Admin ----
    public void createAdmin(AuthUserDto dto) {
        authService.registerAdmin(dto);
    }

    public List<AdminUserDto> getAdmins() {
        return userRepository.findByRole(RolesEnum.ADMIN)
                .stream()
                .map(u -> new AdminUserDto(u.getUserId(), u.getUserName()))
                .toList();
    }

    public void deleteAdmin(Long id) {
        userRepository.deleteById(id);
    }
}
