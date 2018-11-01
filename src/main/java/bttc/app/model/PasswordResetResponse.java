package bttc.app.model;

import java.io.Serializable;

public class PasswordResetResponse implements Serializable {
    private String kind;
    private String email;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
