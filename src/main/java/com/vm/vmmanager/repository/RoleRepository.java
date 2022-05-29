package com.vm.vmmanager.repository;

import com.vm.vmmanager.model.Role;
import com.vm.vmmanager.model.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Long> {

    Optional<RoleDao> findByName(Role name);

}
