package io.james.mall.service;

public interface UserService {

    /**
     * User Register Interface
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);
}
