package io.james.mall.service;

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
}
