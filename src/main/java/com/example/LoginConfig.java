package com.example;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.lib.LoginFilter;
import com.example.lib.SecurityFilter;
import com.example.lib.UserPrincipalBindFilter;

@WebListener
public class LoginConfig implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {

        final ServletContext sc = sce.getServletContext();

        final FilterRegistration.Dynamic loginFilter = sc.addFilter("loginFilter",
                LoginFilter.class);
        loginFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/login");
        loginFilter.setInitParameter(LoginFilter.LOGIN_ACTION_CLASS_NAME_KEY,
                "com.example.UsernameAndPasswordLoginAction");
        loginFilter.setInitParameter(LoginFilter.LOGIN_SUCCESS_PATH_KEY, "/");
        loginFilter.setInitParameter(LoginFilter.LOGIN_FAILURE_PATH_KEY, "/login");

        final FilterRegistration.Dynamic userPrincipalBindFilter = sc
                .addFilter("userPrincipalBindFilter", UserPrincipalBindFilter.class);
        userPrincipalBindFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false,
                "/*");

        final FilterRegistration.Dynamic securityFilter = sc.addFilter("securityFilter",
                SecurityFilter.class);
        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        securityFilter.setInitParameter(SecurityFilter.LOGIN_PATH_KEY, "/login");

        final FilterRegistration.Dynamic twoFactorAuthenticator = sc
                .addFilter("twoFactorAuthenticator", TwoFactorAuthenticator.class);
        twoFactorAuthenticator.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false,
                "/two_factor_authentication");
        twoFactorAuthenticator.setInitParameter(TwoFactorAuthenticator.SUCCESS_PATH_KEY, "/");
        twoFactorAuthenticator.setInitParameter(TwoFactorAuthenticator.FAILURE_PATH_KEY,
                "/two_factor_authentication");

        final FilterRegistration.Dynamic twoFactorAuthenticationSecurityFilter = sc.addFilter(
                "twoFactorAuthenticationSecurityFilter",
                TwoFactorAuthenticationSecurityFilter.class);
        twoFactorAuthenticationSecurityFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false,
                "/*");
        twoFactorAuthenticationSecurityFilter.setInitParameter(
                TwoFactorAuthenticationSecurityFilter.TWO_FACTOR_AUTHENTICATION_PATH_KEY,
                "/two_factor_authentication");

    }
}
