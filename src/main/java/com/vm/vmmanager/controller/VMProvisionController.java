package com.vm.vmmanager.controller;


import com.vm.vmmanager.dto.VMDto;
import com.vm.vmmanager.service.VMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vmmanager")
public class VMProvisionController {

    @Autowired
    private VMService vmService;

    @GetMapping(value = "/user/{userName}/vm", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVMs(@RequestParam(name = "topVMCount", required = false) final Integer topVMCount,
                                    @PathVariable(name = "userName") final String userName) {
        if (topVMCount != null && topVMCount > 0) {
            return vmService.fetchVM(Optional.of(topVMCount), userName);
        }
        return vmService.fetchVM(Optional.empty(), userName);
    }

    @GetMapping(value = "/user/all/vm", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllVMs(@RequestParam(name = "topVMCount", required = false) final Integer topVMCount) {
        if (topVMCount != null && topVMCount > 0) {
            return vmService.fetchAllVM(Optional.of(topVMCount));
        }
        return vmService.fetchAllVM(Optional.empty());
    }

    @PostMapping(value = "/user/{userName}/vm")
    public ResponseEntity<?> provisionVM(@Valid @RequestBody final VMDto vmDto,
                                         @PathVariable(name = "userName") final String userName) {
        return vmService.provisionVM(vmDto, userName);
    }




}
