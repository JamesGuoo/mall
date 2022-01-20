package io.james.mall.web.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterDTO {

    @NotBlank
    private String loginName;

    @NotBlank
    private String password;
}
