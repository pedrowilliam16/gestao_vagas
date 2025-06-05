package br.com.pw.gestao.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pw.gestao.modules.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

    @Autowired
    private JWTCompanyProvider jwtProvider;

    @Override
    protected void doFilterInternal(
    HttpServletRequest request, 
    HttpServletResponse response, 
    FilterChain filterChain) throws ServletException, IOException {
            if (request.getRequestURI().startsWith("/company")) {
                String header = request.getHeader("Authorization");
                if (header!=null) {
                    var token = this.jwtProvider.validateToken(header);

                    if (token==null) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
                    
                    request.setAttribute("company_id", token.getSubject());
                    UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(token, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(auth);
            }
            }
        
            
                filterChain.doFilter(request, response);
            }

    }
    
