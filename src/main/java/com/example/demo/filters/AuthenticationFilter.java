package com.example.demo.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;
    private HashSet<String> openServlets;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");

        // Initialize the set of open servlets
        openServlets = new HashSet<>();
        openServlets.add("/demo/saveServlet");
        openServlets.add("/demo/viewByIDServlet");
        openServlets.add("/demo/loginServlet");
        openServlets.add("/demo/viewServlet");
        openServlets.add("/demo/putServlet");
        openServlets.add("/demo/deleteServlet");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);

        if (session == null && !openServlets.contains(uri)) {
            this.context.log("<<< Unauthorized access request");
            PrintWriter out = res.getWriter();
            out.println("No access!!!");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // Close any resources here
    }
}

