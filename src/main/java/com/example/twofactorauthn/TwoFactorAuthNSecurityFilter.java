package com.example.twofactorauthn;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.user.User;

/**
 * 2要素認証済みのユーザー、もしくは2要素認証をOFFにしているユーザーを通すフィルター。
 *
 */
public class TwoFactorAuthNSecurityFilter implements Filter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        final User user = User.get(req);

        if (user.isTwoFactorAuthentication() == false || isTwoFactorAuthenticated(req)) {
            chain.doFilter(request, response);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/two_factor_authn");
    }

    /**
     * 2要素認証済みかどうかを判定する。
     * 
     * @param req
     * @return trueなら2要素認証済み
     */
    private boolean isTwoFactorAuthenticated(final HttpServletRequest req) {
        final HttpSession session = req.getSession();
        final Object attribute = session.getAttribute("twoFactorAuthenticated");
        return attribute != null;
    }
}
