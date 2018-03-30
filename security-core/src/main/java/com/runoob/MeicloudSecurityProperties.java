package com.runoob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author DENGBIN
 * @since 2018-3-28
 */
@Data
@ConfigurationProperties(prefix = "meicloud.security")
public class MeicloudSecurityProperties {

    private Browser browser;

    @Data
    @ConfigurationProperties(prefix = "meicloud.security.browser")
    public static class Browser {
        /**
         * Browser login page.
         */
        private String loginPage = "/login";
        /**
         * Specifies the URL to validate the credentials.
         */
        private String loginProcessingUrl = "/login";
        /**
         * 注册URL
         */
        private String signUpUrl;
        /**
         * 登录类型
         */
        private String loginType;
    }
}
