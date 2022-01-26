package io.james.mall.web;

import io.james.mall.common.Constants;
import io.james.mall.common.ServiceResultEnum;
import io.james.mall.config.annotation.TokenToUser;
import io.james.mall.domain.UserDO;
import io.james.mall.service.UserService;
import io.james.mall.util.PhoneUtil;
import io.james.mall.web.dto.UserLoginDTO;
import io.james.mall.web.dto.UserRegisterDTO;
import io.james.mall.web.vo.Result;
import io.james.mall.web.vo.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private PhoneUtil phoneUtil;
    private ResultGenerator generator;
    private UserService userService;

    @Autowired
    public UserAPI(PhoneUtil phoneUtil, ResultGenerator generator, UserService userService) {
        this.phoneUtil = phoneUtil;
        this.generator = generator;
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public Result register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        if (phoneUtil.isNotPhone(userRegisterDTO.getLoginName())) {
            return generator.genFailResult("LOGIN_NAME_IS_NOT_PHONE");
        }

        String registerResult = userService.register(userRegisterDTO.getLoginName(), userRegisterDTO.getPassword());

        log.info("register api, loginName={}, registerResult={}", userRegisterDTO.getLoginName(), registerResult);

        if ("SUCCESS".equals(registerResult)) {
            return generator.genSuccessResult();
        }

        return generator.genFailResult(registerResult);
    }

    @PostMapping("/user/login")
    public Result<String> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        if (phoneUtil.isNotPhone(userLoginDTO.getLoginName())) {
            return generator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        String loginResult = userService.login(userLoginDTO.getLoginName(), userLoginDTO.getPassword());

        log.info("login api, loginName={}, loginResult={}", userLoginDTO.getLoginName(), loginResult);

        //登录成功
        if (StringUtils.isNotBlank(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = generator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return generator.genFailResult(loginResult);
    }

    @PostMapping("/user/logout")
    @Transactional
    public Result<String> logout(@TokenToUser UserDO userDO) {
        Boolean logoutResult = userService.logout(userDO.getUserId());

        log.info("logout api, loginName={}", userDO.getUserId());

        //登出成功
        if (logoutResult) {
            return generator.genSuccessResult();
        }
        //登出失败
        return generator.genFailResult("LOGOUT_ERROR");
    }

}
