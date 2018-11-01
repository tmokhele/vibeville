package bttc.app.model;

public class JwtAuthenticationResponse {

    public   String accessToken;
    public static final String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
