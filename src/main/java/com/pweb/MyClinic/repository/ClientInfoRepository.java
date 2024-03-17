package com.pweb.MyClinic.repository;

import com.pweb.MyClinic.model.db.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {

    ClientInfo getClientInfoByUserId(Integer userId);
}