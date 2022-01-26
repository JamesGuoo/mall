package io.james.mall.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserDORepo extends CrudRepository<UserDO, Long> {

    UserDO findUserDOByLoginName(String loginName);

    UserDO findUserDOByUserId(Long userId);

    UserDO save(UserDO userDO);

    UserDO findUserDOByLoginNameAndPasswordMd5(String loginName, String passwordMd5);
}
