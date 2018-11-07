package bttc.app.model;

public class JwtAuthenticationResponse {

    public   String accessToken;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
