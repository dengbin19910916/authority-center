package com.runoob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author DENGBIN
 * @since 2018-4-1
 */
@Slf4j
@Service
public class MeicloudUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("用户登录名：" + username);
        return new User(username, "123456",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
