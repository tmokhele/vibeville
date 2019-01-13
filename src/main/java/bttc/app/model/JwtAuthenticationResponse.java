package bttc.app.model;

public class JwtAuthenticationResponse {

    public   String accessToken;
    public SystemUser accountInfo;

    public JwtAuthenticationResponse(String accessToken,SystemUser accountInfo) {
        this.accessToken = accessToken;
        this.accountInfo = accountInfo;
    }
}
