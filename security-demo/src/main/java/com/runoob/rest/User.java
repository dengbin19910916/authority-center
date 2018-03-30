package com.runoob.rest;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @author DENGBIN
 * @since 2018-3-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {

    interface UserSimpleView {}

    interface UserDetailView extends UserSimpleView {}

    @JsonView(UserSimpleView.class)
    private Long id;

    @JsonView(UserSimpleView.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;

    @Past(message = "生日必须是过去的时间")
    @JsonView(UserSimpleView.class)
    private Date birthday;
}
