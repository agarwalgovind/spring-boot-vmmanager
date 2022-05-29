package com.vm.vmmanager.repository;

import com.vm.vmmanager.model.UserDao;
import com.vm.vmmanager.model.VMDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VMRepository extends JpaRepository<VMDao, Long> {

    List<VMDao> findByUserDao(UserDao userDao);

}
