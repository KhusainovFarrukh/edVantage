package kh.farrukh.edvantage.security.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder
public class AuthResponse {

    private List<String> authorities;
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpires;
    private String refreshTokenExpires;
}