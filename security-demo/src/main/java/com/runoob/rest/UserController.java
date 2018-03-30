package com.runoob.rest;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author DENGBIN
 * @since 2018-3-25
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setId(1L);
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@PathVariable Long id, @RequestBody @Valid User user, BindingResult errors) {
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        System.out.println(userRepository.findOne(id));

        return user;
    }

    @JsonView(User.UserSimpleView.class)
    @GetMapping
    public Collection<User> query(UserQueryCondition condition, @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());

        return userRepository.findAll();
    }

    @JsonView(User.UserDetailView.class)
    @GetMapping("/{id:\\d+}")
    public User getInfo(@PathVariable Long id) {
        return userRepository.findOne(id).orElse(null);
    }

    @Data
    private static class UserQueryCondition {

        private String username;

        private int age;

        private int ageTo;

        private String xxx;
    }
}
