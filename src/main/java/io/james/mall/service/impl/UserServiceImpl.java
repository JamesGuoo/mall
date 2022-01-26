package io.james.mall.service.impl;

import io.james.mall.domain.UserDO;
import io.james.mall.domain.UserDORepo;
import io.james.mall.domain.UserTokenDO;
import io.james.mall.domain.UserTokenDORepo;
import io.james.mall.service.UserService;
import io.james.mall.util.PhoneUtil;
import io.james.mall.util.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserServiceImpl implements UserService {

    private UserDORepo userDORepo;
    private UserTokenDORepo userTokenDORepo;
    private PhoneUtil phoneUtil;
    private SystemUtil systemUtil;

//    @Autowired
//    public UserServiceImpl(UserDORepo userDORepo) {
//        this.userDORepo = userDORepo;
//    }

    @Autowired
    public UserServiceImpl(UserDORepo userDORepo, UserTokenDORepo userTokenDORepo, PhoneUtil phoneUtil, SystemUtil systemUtil) {
        this.userDORepo = userDORepo;
        this.userTokenDORepo = userTokenDORepo;
        this.phoneUtil = phoneUtil;
        this.systemUtil = systemUtil;
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

    @Override
    public String login(String loginName, String password) {
        UserDO userDO = userDORepo.findUserDOByLoginNameAndPasswordMd5(loginName, password);
        if (userDO != null) {
//            if (userDO.getLockedFlag() == 1) {
//                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
//            }

            //登录后执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", userDO.getUserId());
            UserTokenDO userTokenDO = userTokenDORepo.findUserTokenDOByUserId(userDO.getUserId());
            //当前时间
            Date now = new Date();
            //未来的过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000); //过期时间48小时
            if (userTokenDO == null) {
                userTokenDO = new UserTokenDO();
                userTokenDO.setUserId(userDO.getUserId());
                userTokenDO.setToken(token);
                userTokenDO.setUpdateTime(now);
                userTokenDO.setExpireTime(expireTime);
                //新增一条token数据
                if (userTokenDORepo.save(userTokenDO) != null) {
                    return token;
                }
            } else {
                userTokenDO.setToken(token);
                userTokenDO.setUpdateTime(now);
                userTokenDO.setExpireTime(expireTime);
                //更新
                if (userTokenDORepo.save(userTokenDO) != null) {
                    return token;
                }
            }
        }

        return "LOGIN_ERROR";
    }

    public String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + phoneUtil.genRandomNum(4);
        return systemUtil.genToken(src);
    }

    @Override
    public Boolean logout(Long userId) {
        try {
            userTokenDORepo.deleteUserTokenDOByUserId(userId);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
