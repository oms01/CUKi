package project.univAlarm.common.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.univAlarm.common.security.jwt.dto.CustomUserDetails;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/v1/auth/") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) { //토큰 필요 X
            filterChain.doFilter(request, response);
            return;
        }


        String authorization = request.getHeader("Authorization");

        if (isHeaderInvalid(authorization)) {
            throw new BadCredentialsException("No JWT token found in request headers.");
        }

        String token = authorization.split(" ")[1];
        if (isTokenExpired(token)) {
            throw new BadCredentialsException("JWT token has expired.");
        }

        Long userId = jwtUtil.getUserId(token);
        String role = jwtUtil.getRole(token);

        //userEntity를 생성하여 값 set
        JWTPayload user = new JWTPayload();
        user.setUserId(userId);
        user.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private boolean isHeaderInvalid(String authorization) {
        return authorization == null || !authorization.startsWith("Bearer ");
    }

    private boolean isTokenExpired(String token) {
        return jwtUtil.isExpired(token);
    }
}