package io.james.mall.web;

import io.james.mall.service.UserService;
import io.james.mall.util.PhoneUtil;
import io.james.mall.web.dto.UserRegisterDTO;
import io.james.mall.web.vo.Result;
import io.james.mall.web.vo.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        log.info("register api, loginName={},registerResult={}", userRegisterDTO.getLoginName(), registerResult);

        if ("SUCCESS".equals(registerResult)) {
            return generator.genSuccessResult();
        }

        return generator.genFailResult(registerResult);
    }
}
