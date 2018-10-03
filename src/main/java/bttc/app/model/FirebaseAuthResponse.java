package bttc.app.model;

public class FirebaseAuthResponse {

    private String kind;
    private String localId;
    private String displayName;
    private String idToken;
    private boolean registered;
    private  String refreshToken;
    private String expiresIn;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdtoken(String idToken) {
        this.idToken = idToken;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshtoken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresin(String expiresIn) {
        this.expiresIn = expiresIn;
    }
}
