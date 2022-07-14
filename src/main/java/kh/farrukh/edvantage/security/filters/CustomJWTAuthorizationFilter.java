package kh.farrukh.edvantage.security.filters;

import com.auth0.jwt.interfaces.DecodedJWT;
import kh.farrukh.edvantage.security.utils.JWTUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static kh.farrukh.edvantage.utils.constants.ApiEndpoints.ENDPOINT_LOGIN;

public class CustomJWTAuthorizationFilter extends OncePerRequestFilter {

    // TODO: 7/14/22 handle ide warnings
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().equals(ENDPOINT_LOGIN)
            // TODO: 7/14/22 add other ignored endpoints

//                ||
//                request.getServletPath().equals(ENDPOINT_REFRESH_TOKEN) ||
//
//                (request.getServletPath().contains(ENDPOINT_IMAGE) && (request.getMethod().equals(HttpMethod.GET.name()) || request.getMethod().equals(HttpMethod.POST.name()))) ||
//
//                (request.getMethod().equals(HttpMethod.GET.name()) && request.getServletPath().contains(ENDPOINT_LANGUAGE) && (request.getParameter("state") == null))
        ) {

            filterChain.doFilter(request, response);
        } else {
            try {
                DecodedJWT decodedJWT = JWTUtils.decodeJWT(request.getHeader(HttpHeaders.AUTHORIZATION));
                if (decodedJWT != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            JWTUtils.getAuthenticationFromDecodedJWT(decodedJWT);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            } catch (Exception exception) {
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
        }
    }
}
