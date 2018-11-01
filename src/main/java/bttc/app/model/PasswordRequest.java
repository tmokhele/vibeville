package bttc.app.model;

import java.io.Serializable;

public class PasswordRequest implements Serializable {
    private String idToken;
    private String password;
    private boolean returnSecureToken;

    public PasswordRequest(String idToken, String password) {
        this.idToken = idToken;
        this.password = password;
        this.returnSecureToken = true;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isReturnSecureToken() {
        return returnSecureToken;
    }

    public void setReturnSecureToken(boolean returnSecureToken) {
        this.returnSecureToken = returnSecureToken;
    }
}
