package br.com.pw.gestao.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pw.gestao.modules.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {
    
    private final JWTCandidateProvider jwtCandidateProvider;

    public SecurityCandidateFilter (
        JWTCandidateProvider jwtCandidateProvider
    ){
        this.jwtCandidateProvider=jwtCandidateProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
     HttpServletResponse response, 
     FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/candidate")) {
            String header = request.getHeader("Authorization");
            if (header!=null) {
                var token = this.jwtCandidateProvider.execute(header);
                if (token==null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
           }
           request.setAttribute("candidate_id", token.getSubject());
           var roles = token.getClaim("roles").asList(Object.class);

           var grants = roles.stream()
            .map(role-> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
            .toList()
           ;

           UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);

           SecurityContextHolder.getContext().setAuthentication(auth);
        }
        }

        
        filterChain.doFilter(request, response);
    }
    
}
