package bttc.app.model;

public class UserDelete {

    public UserDelete() {
    }

    public UserDelete(String idToken) {
        this.idToken = idToken;
    }

    private String idToken;
    private String kind;

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
