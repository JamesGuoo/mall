package io.james.mall.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserTokenDORepo extends CrudRepository<UserTokenDO, Long> {

    UserTokenDO findUserTokenDOByUserId(Long userId);

    UserTokenDO findUserTokenDOByToken(String token);

    UserTokenDO save(UserTokenDO userTokenDO);

    void deleteUserTokenDOByUserId(Long userId);

}
