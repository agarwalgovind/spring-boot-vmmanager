package com.vm.vmmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vm")
public class VMDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @Setter
    private String osName;

    @Getter
    @Setter
    private Integer ramSizeInGB;

    @Getter
    @Setter
    private Integer hardDiskInGB;

    @Getter
    @Setter
    private Integer cpuCores;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @Getter
    @Setter
    private UserDao userDao;

    public VMDao() {}

    public VMDao(String osName, Integer ramSizeInGB, Integer hardDiskInGB, Integer cpuCores, UserDao userDao) {
        this.osName = osName;
        this.ramSizeInGB = ramSizeInGB;
        this.hardDiskInGB = hardDiskInGB;
        this.cpuCores = cpuCores;
        this.userDao = userDao;
    }
}
