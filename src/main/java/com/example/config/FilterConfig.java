package com.example.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.example.login.LoginFilter;
import com.example.login.SecurityFilter;
import com.example.login.UserPrincipalBindFilter;
import com.example.twofactorauthn.TwoFactorAuthNSecurityFilter;
import com.example.twofactorauthn.TwoFactorAuthenticator;

@WebListener
public class FilterConfig implements ServletContextListener {

    @Override
    public void contextInitialized(final ServletContextEvent sce) {

        final ServletContext sc = sce.getServletContext();

        final FilterRegistration.Dynamic loginFilter = sc
                .addFilter(LoginFilter.class.getSimpleName(), LoginFilter.class);
        loginFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/login");

        final FilterRegistration.Dynamic userPrincipalBindFilter = sc.addFilter(
                UserPrincipalBindFilter.class.getSimpleName(), UserPrincipalBindFilter.class);
        userPrincipalBindFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false,
                "/*");

        final FilterRegistration.Dynamic securityFilter = sc
                .addFilter(SecurityFilter.class.getSimpleName(), SecurityFilter.class);
        securityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

        final FilterRegistration.Dynamic twoFactorAuthenticator = sc.addFilter(
                TwoFactorAuthenticator.class.getSimpleName(), TwoFactorAuthenticator.class);
        twoFactorAuthenticator.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false,
                "/two_factor_authn");

        final FilterRegistration.Dynamic twoFactorAuthNSecurityFilter = sc.addFilter(
                TwoFactorAuthNSecurityFilter.class.getSimpleName(),
                TwoFactorAuthNSecurityFilter.class);
        twoFactorAuthNSecurityFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),
                false, "/*");
    }
}
