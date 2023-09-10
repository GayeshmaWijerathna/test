package lk.ijse.api.filter;


import lk.ijse.api.util.ResponseUtil;

import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = "/*",filterName = "A")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Auth Filter Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        System.out.println("Auth Filter Do Filter Invoked");

         String auth = req.getHeader("Auth");


        if (auth != null && auth.equals("user=admin,pass=admin")) {
            System.out.println("Athul una");
            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            System.out.println("Eliyata awa");
            res.addHeader("Content-Type", "application/json");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonObject jsonObject = ResponseUtil.genJson("Auth-Error", "You are not Authenticated to use this Service.!");
            res.getWriter().print(jsonObject);
        }

    }

    @Override
    public void destroy() {

    }
}
