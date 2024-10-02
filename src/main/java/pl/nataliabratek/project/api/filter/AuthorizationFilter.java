package pl.nataliabratek.project.api.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.nataliabratek.project.domain.service.TokenService;

import java.io.IOException;
@AllArgsConstructor
@Component
@Order(1)
public class AuthorizationFilter implements Filter {
    private TokenService tokenService;
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        String endpoint = req.getRequestURI();

        if (endpoint.equals("/api/v1/login") || endpoint.startsWith("/api/v1/users/confirm/")){
            chain.doFilter(request, response);
            return;
        }
        String token = req.getHeader("Authorization");


        HttpServletResponse res = (HttpServletResponse) response;
        if (!tokenService.checkToken(token)){
            res.setStatus(401);
        }
        else{
            chain.doFilter(request, response);
        }
    }
}
