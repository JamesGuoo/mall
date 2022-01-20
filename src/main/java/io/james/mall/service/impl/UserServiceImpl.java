package io.james.mall.service.impl;

import io.james.mall.domain.UserDO;
import io.james.mall.domain.UserDORepo;
import io.james.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    private UserDORepo userDORepo;

    @Autowired
    public UserServiceImpl(UserDORepo userDORepo) {
        this.userDORepo = userDORepo;
    }

    @Override
    public String register(String loginName, String password) {
        if (userDORepo.findUserDOByLoginName(loginName) != null) {
            return "SAME_LOGIN_NAME_EXIST";
        }

        UserDO userDO = new UserDO();
        userDO.setLoginName(loginName);
        userDO.setNickName(loginName);
        userDO.setPasswordMd5(password);
        if (userDORepo.save(userDO) != null) {
            return "SUCCESS";
        }

        return "DB_ERROR";
    }
}
