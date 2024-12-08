package jeevankumar.spring_boot_security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jeevankumar.spring_boot_security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest  request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

       String jwt = getJwtFromRequest(request);
       if (jwt != null && jwtTokenProvider.validateToken(jwt, customUserDetailsService.loadUserByUsername(jwtTokenProvider.getUsernameFromToken(jwt)))){
           String username = jwtTokenProvider.getUsernameFromToken(jwt);
          UserDetails userDetails =  customUserDetailsService.loadUserByUsername(username);
           UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authenticationToken);

       }
       filterChain.doFilter(request,response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null &&bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;


    }
}
