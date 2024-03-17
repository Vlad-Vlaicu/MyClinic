package com.pweb.MyClinic.helper;

import com.pweb.MyClinic.model.PersonalData;
import com.pweb.MyClinic.model.User;
import com.pweb.MyClinic.model.db.ClientInfo;
import com.pweb.model.generated.RegisterRequest;

import static com.pweb.MyClinic.helper.TimeHelper.TIME_FORMATTER;
import static com.pweb.MyClinic.model.Role.USER;
import static java.time.LocalDateTime.now;

public class UserHelper {

    public static User generateUser(RegisterRequest request) {
        var user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(USER);
        return user;
    }

    public static ClientInfo generateClientInfo(Integer userId, RegisterRequest request) {
        var clientInfo = new ClientInfo();
        clientInfo.setCreationDate(now().format(TIME_FORMATTER));
        clientInfo.setUserId(userId);
        clientInfo.setPersonalData(generatePersonalData(request));
        return clientInfo;
    }

    private static PersonalData generatePersonalData(RegisterRequest request) {
        var personalData = new PersonalData();
        personalData.setName(request.getName());
        personalData.setEmail(request.getEmail());
        personalData.setPhoneNumber(request.getPhoneNumber());
        personalData.setCnp(request.getCnp());
        personalData.setBirthday(request.getBirthday());
        return personalData;
    }
}