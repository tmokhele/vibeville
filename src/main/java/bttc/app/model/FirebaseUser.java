package bttc.app.model;

public class FirebaseUser extends FirebaseAuthResponse {

    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
