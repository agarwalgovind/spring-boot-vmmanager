package com.vm.vmmanager.service;

import com.vm.vmmanager.dto.UserDto;
import com.vm.vmmanager.dto.UserResponseDto;
import com.vm.vmmanager.model.Role;
import com.vm.vmmanager.model.RoleDao;
import com.vm.vmmanager.model.UserDao;
import com.vm.vmmanager.repository.RoleRepository;
import com.vm.vmmanager.repository.UserRepository;
import com.vm.vmmanager.security.jwt.JwtUtils;
import com.vm.vmmanager.security.userdetails.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public ResponseEntity<?> registerUser(final UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        if (userRepository.existsByMobileNumber(userDto.getMobileNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Mobile Number is already in use!");
        }

        UserDao user = new UserDao(userDto.getUsername(), encoder.encode(userDto.getPassword()),
                userDto.getEmail(), userDto.getMobileNumber());

        String role = userDto.getRole();
        RoleDao userRole;

        if (role == null) {
            userRole = roleRepository.findByName(Role.NONMASTER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        }
        else {
            switch (role.toLowerCase(Locale.ROOT)) {
                case "master":
                    userRole = roleRepository.findByName(Role.MASTER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    break;
                default:
                    userRole = roleRepository.findByName(Role.NONMASTER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            }
        }
        user.setRole(userRole);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<?> authenticateUser(final String username, final String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponseDto(jwt, userDetails.getUsername(), userDetails.getEmail()));
    }

    public ResponseEntity<?> deleteUser(final String userName) {
        Optional<UserDao> userDao = userRepository.findByUsername(userName);
        if (userDao.isPresent()) {
            userRepository.delete(userDao.get());
            log.warn("User {} deleted successfully", userDao.get().getUsername());
            return ResponseEntity.ok("User and all the created VM's deleted successfully");
        }
        return ResponseEntity.ok("User could not be found in the DB");
    }


}
