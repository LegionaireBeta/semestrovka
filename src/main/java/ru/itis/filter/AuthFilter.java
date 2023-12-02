package ru.itis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Boolean isAutenticated = false;
        Boolean sessionExist = session != null ;
        Boolean isLoginPage = request.getRequestURI().equals("/signIn");
        Boolean isSignUpPage = request.getRequestURI().equals("/signUp");


        if (sessionExist) {
            isAutenticated = (Boolean) session.getAttribute("autenticated");
            if(isAutenticated == null) {
                isAutenticated = false;
            }
        }


        if(isAutenticated && !isLoginPage || !isAutenticated && (isLoginPage || isSignUpPage)) {

            filterChain.doFilter(request, response);
        } else if (isAutenticated && (isLoginPage || isSignUpPage)) {

            response.sendRedirect("/home");
        } else {

            response.sendRedirect("/signIn");
        }
    }

    public void destroyAuthentication(HttpSession session){
        if(session != null){
            session.invalidate();
        }
    }

    @Override
    public void destroy() {

    }
}
