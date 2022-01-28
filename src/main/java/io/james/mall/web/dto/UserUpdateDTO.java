package io.james.mall.web.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

    private String nickName;

    private String passwordMd5;

    private String introduceSign;
}
