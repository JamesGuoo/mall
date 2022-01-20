package io.james.mall.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserDORepo extends CrudRepository<UserDO, Long> {

    UserDO findUserDOByLoginName(String loginName);

    UserDO save(UserDO userDO);
}
