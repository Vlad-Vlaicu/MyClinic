package com.pweb.MyClinic.service.company;

import com.pweb.MyClinic.model.db.EmployeeInfo;
import com.pweb.MyClinic.repository.EmployeeInfoRepository;
import com.pweb.MyClinic.service.error.ServiceError;
import com.pweb.MyClinic.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeInfoService {

    private final EmployeeInfoRepository repository;

    public void saveEmployeeInfo(EmployeeInfo employeeInfo) {
        try {
            repository.save(employeeInfo);

        } catch (Exception e) {
            throw new ServiceException(ServiceError.CREATE_EMPLOYEE_FAILED);
        }

    }
}