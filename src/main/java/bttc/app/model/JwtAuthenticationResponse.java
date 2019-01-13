package bttc.app.model;

public class JwtAuthenticationResponse {

    public   String accessToken;
    public User accountInfo;

    public JwtAuthenticationResponse(String accessToken,User accountInfo) {
        this.accessToken = accessToken;
        this.accountInfo = accountInfo;
    }
}
