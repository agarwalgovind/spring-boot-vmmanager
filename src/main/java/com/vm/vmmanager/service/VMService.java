package com.vm.vmmanager.service;

import com.vm.vmmanager.dto.VMDto;
import com.vm.vmmanager.model.Role;
import com.vm.vmmanager.model.UserDao;
import com.vm.vmmanager.model.VMDao;
import com.vm.vmmanager.model.comparator.VMMemoryComparator;
import com.vm.vmmanager.repository.UserRepository;
import com.vm.vmmanager.repository.VMRepository;
import com.vm.vmmanager.security.userdetails.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VMService {

    @Autowired
    private VMRepository vmRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> provisionVM(final VMDto vmDto, final String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDao authenticatedUser = userRepository.findByUsername(userDetails.getUsername()).get();
        if (!authenticatedUser.getRole().getName().equals(Role.MASTER) && !authenticatedUser.getUsername().equals(userName)) {
            log.error("User {} not allowed to access this resource", userName);
            return ResponseEntity.badRequest().body("User not allowed to access this resource");
        }
        Optional<UserDao> userDao = userRepository.findByUsername(userName);
        if (userDao.isPresent()) {
            VMDao vmDao = new VMDao(vmDto.getOsName(), vmDto.getRamSizeInGB(), vmDto.getHardDiskInGB(), vmDto.getCpuCores(), userDao.get());
            vmRepository.save(vmDao);
            log.info("VM Provisioned Successfully for the user - " + userDao.get().getUsername());
            return ResponseEntity.ok("VM Provisioned Successfully for the user - " + userDao.get().getUsername());
        }
        log.error("User - {} is not present in the DB. VM Provisioning failed !!!", userName);
        return ResponseEntity.badRequest().body(String.format("UserName - %s is not present in the DB. VM Provisioning failed !!!", userName));
    }

    public ResponseEntity<?> fetchVM(final Optional<Integer> topVMCount, final String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserDao authenticatedUser = userRepository.findByUsername(userDetails.getUsername()).get();
        if (!authenticatedUser.getRole().getName().equals(Role.MASTER) && !authenticatedUser.getUsername().equals(userName)) {
            log.error("User {} not allowed to access this resource", userName);
            return ResponseEntity.badRequest().body("User not allowed to access this resource");
        }
        Optional<UserDao> userDao = userRepository.findByUsername(userName);
        if (userDao.isPresent()) {
            List<VMDao> vmDaoList = vmRepository.findByUserDao(userDao.get());
            if (topVMCount.isPresent()) {
                int size = topVMCount.get() > vmDaoList.size() ? vmDaoList.size() : topVMCount.get();
                Collections.sort(vmDaoList, new VMMemoryComparator());
                return ResponseEntity.ok(vmDaoList.subList(0, size));
            }
            return ResponseEntity.ok(vmDaoList);
        }
        log.error("User - {} is not present in the DB. No VM's are present.", userName);
        return ResponseEntity.badRequest().body(String.format("UserName - %s is not present in the DB. No VM's are present.", userName));
    }

    public ResponseEntity<?> fetchAllVM(final Optional<Integer> topVMCount) {
        List<VMDao> vmDaoList = vmRepository.findAll();
        if (topVMCount.isPresent()) {
            int size = topVMCount.get() > vmDaoList.size() ? vmDaoList.size() : topVMCount.get();
            Collections.sort(vmDaoList, new VMMemoryComparator());
            return ResponseEntity.ok(vmDaoList.subList(0, size));
        }
        return ResponseEntity.ok(vmDaoList);
    }

}
