package com.vm.vmmanager.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class VMDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String osName;

    @NotNull
    @Max(128)
    @Min(1)
    private Integer ramSizeInGB;

    @NotNull
    @Max(4096)
    @Min(128)
    private Integer hardDiskInGB;

    @NotNull
    @Max(64)
    @Min(1)
    private Integer cpuCores;


}
