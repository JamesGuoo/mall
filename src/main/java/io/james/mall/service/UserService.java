package io.james.mall.service;

import io.james.mall.web.dto.UserUpdateDTO;

public interface UserService {

    /**
     * User Register Interface
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     *
     * @param loginName
     * @param password
     * @return
     */
    String login(String loginName, String password);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用户信息修改
     * @param userUpdateDTO
     * @param userId
     * @return
     */
    Boolean updateUserInfo(UserUpdateDTO userUpdateDTO, Long userId);

}
