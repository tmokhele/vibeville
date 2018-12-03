package bttc.app.model;


import java.io.Serializable;

public class PasswordConfirmation implements Serializable {
    private String oobCode;
    private String newPassword;
    private String apiKey;

    public PasswordConfirmation() {
    }

    public PasswordConfirmation(String oobCode, String newPassword) {
        this.oobCode = oobCode;
        this.newPassword = newPassword;
    }

    public String getOobCode() {
        return oobCode;
    }

    public void setOobCode(String oobCode) {
        this.oobCode = oobCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
