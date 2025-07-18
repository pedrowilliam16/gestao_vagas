package br.com.pw.gestao.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    private final JWTCompanyProvider jwtProvider;

    public SecurityCompanyFilter (
        JWTCompanyProvider jwtProvider
    ) {
        this.jwtProvider=jwtProvider;
    }

    @Override
    protected void doFilterInternal(
    HttpServletRequest request, 
    HttpServletResponse response, 
    FilterChain filterChain) throws ServletException, IOException {
            if (request.getRequestURI().startsWith("/company")) {
                String header = request.getHeader("Authorization");
                if (header != null && header.startsWith("Bearer ")) {
                    String tokenString = header.substring(7);
                    var token = this.jwtProvider.validateToken(tokenString);

                    if (token==null) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return;
                    }
    
                    request.setAttribute("company_id", token.getSubject());
                    var roles = token.getClaim("roles").asList(Object.class);

                    var grants = roles.stream()
                        .map(role-> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                        .toList()
                    ;

                    UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(token.getSubject(), null, grants);

                    SecurityContextHolder.getContext().setAuthentication(auth);
            }
            } else {
                    SecurityContextHolder.clearContext();
            }

        
            
                filterChain.doFilter(request, response);
            }

    }
    
