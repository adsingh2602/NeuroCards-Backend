package com.project.NeuroCards.config;

import com.project.NeuroCards.entity.User;
import com.project.NeuroCards.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository repo;

    public JwtFilter(JwtUtil jwtUtil, UserRepository repo) {
        this.jwtUtil = jwtUtil;
        this.repo = repo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String auth = req.getHeader("Authorization");

        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);

            try {
                String email = jwtUtil.extractEmail(token);
                User user = repo.findByEmail(email).orElseThrow();

                req.setAttribute("user", user);

            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(req, res);
    }
}