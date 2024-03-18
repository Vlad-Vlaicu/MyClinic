package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.ClientInfo;
import com.pweb.MyClinic.repository.ClientInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientInfoService {

    private final ClientInfoRepository repository;

    public void saveClientInfo (ClientInfo clientInfo){
        repository.save(clientInfo);
    }

    public ClientInfo getAccountInfo (Integer userId){
        return repository.getClientInfoByUserId(userId);
    }
}