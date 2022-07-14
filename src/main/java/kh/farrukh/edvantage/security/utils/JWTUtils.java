package kh.farrukh.edvantage.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static kh.farrukh.edvantage.utils.constants.JWTKeys.*;

public class JWTUtils {
    // Used to format the date in the response.
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
    private static final DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    // Creating an algorithm object which will be used to sign the token.
    public static final Algorithm algorithm = Algorithm.HMAC256(getJwtSecret().getBytes());
    // Value of the expiration time for the access token and refresh token.
    public static final int accessValidMillis = 30 * 60 * 1000;
    public static final int refreshValidMillis = 3 * 24 * 60 * 60 * 1000;

    /**
     * It creates two tokens, one for access and one for refresh, and returns them in a RefreshTokenResponse
     *
     * @param user The user object that is passed in from the controller.
     * @return RefreshTokenResponse
     */
    public static AuthResponse generateTokens(UserDetails user) {
        long currentMillis = System.currentTimeMillis();
        Date accessExpireDate = new Date(currentMillis + accessValidMillis);
        Date refreshExpireDate = new Date(currentMillis + refreshValidMillis);

        // Getting the roles of the user and converting them to a list of strings.
        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(accessExpireDate)
                .withClaim(KEY_AUTHORITIES, authorities)
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(refreshExpireDate)
                .withClaim(KEY_AUTHORITIES, authorities)
                .sign(algorithm);

        return new AuthResponse(
                authorities,
                accessToken,
                refreshToken,
                formatter.format(accessExpireDate),
                formatter.format(refreshExpireDate)
        );
    }

    /**
     * If the request has an Authorization header with a Bearer token, then verify the token and return the decoded JWT
     *
     * @param refreshTokenHeader The refresh token in request header
     * @return A DecodedJWT object
     */
    public static DecodedJWT decodeJWT(String refreshTokenHeader) {
        if (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer ")) {
            String token = refreshTokenHeader.substring("Bearer ".length());
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            return jwtVerifier.verify(token);
        } else {
            return null;
        }
    }

    /**
     * It takes a decoded JWT and returns a UsernamePasswordAuthenticationToken with the username and roles from the JWT
     *
     * @param decodedJWT The decoded JWT.
     * @return A UsernamePasswordAuthenticationToken object
     */
    public static UsernamePasswordAuthenticationToken getAuthenticationFromDecodedJWT(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        List<SimpleGrantedAuthority> authorities = decodedJWT.getClaim(KEY_AUTHORITIES).asList(String.class)
                .stream().map(SimpleGrantedAuthority::new).toList();

        return new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
    }

    /**
     * It takes a map of data and an HttpServletResponse object, and writes the data to the response as JSON
     *
     * @param authResponse This is the AuthResponse that you want to send back to the client.
     * @param response     The HttpServletResponse object.
     */
    public static void sendTokenInResponse(AuthResponse authResponse, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> data = new ObjectMapper().convertValue(authResponse,
                new TypeReference<>() {
                }
        );
        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }

    /**
     * It sets the access token and refresh token in the response header
     *
     * @param authResponse The AuthResponse that contains the access token and refresh token.
     * @param response     The response object of the request.
     */
    public static void sendTokenInHeader(AuthResponse authResponse, HttpServletResponse response) {
        response.setHeader(KEY_AUTHORITIES, String.join(",", authResponse.getAuthorities()));
        response.setHeader(KEY_ACCESS_TOKEN, authResponse.getAccessToken());
        response.setHeader(KEY_REFRESH_TOKEN, authResponse.getRefreshToken());
        response.setHeader(KEY_ACCESS_TOKEN_EXPIRES, authResponse.getAccessTokenExpires());
        response.setHeader(KEY_REFRESH_TOKEN_EXPIRES, authResponse.getRefreshTokenExpires());
    }

    private static String getJwtSecret() {
        String jwtSecret = System.getenv("MY_JWT_SECRET");
        if (jwtSecret == null) {
            return "test-secret";
        }
        return jwtSecret;
    }
}