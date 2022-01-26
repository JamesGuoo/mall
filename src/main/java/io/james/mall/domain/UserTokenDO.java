package io.james.mall.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserTokenDO {

    @Id
    private Long userId;

    private String token;

    private Date updateTime;

    private Date expireTime;
}
