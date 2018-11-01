package bttc.app.model;

import java.io.Serializable;

public class PasswordReset implements Serializable {
    public  String  requestType;
    public String email;
    public PasswordReset(String email) {
        this.email = email;
        this.requestType="PASSWORD_RESET";
    }


}
