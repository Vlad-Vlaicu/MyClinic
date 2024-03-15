package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfo, Long> {
}