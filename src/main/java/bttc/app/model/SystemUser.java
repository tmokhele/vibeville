package bttc.app.model;

public class SystemUser {
    private String uid;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String contactNumber;
    private String role;
    private boolean isInfo;
    private boolean isTwitter;
    private boolean isFaceBook;
    private boolean isEvents;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isInfo() {
        return isInfo;
    }

    public void setInfo(boolean info) {
        isInfo = info;
    }

    public boolean isTwitter() {
        return isTwitter;
    }

    public void setTwitter(boolean twitter) {
        isTwitter = twitter;
    }

    public boolean isFaceBook() {
        return isFaceBook;
    }

    public void setFaceBook(boolean faceBook) {
        isFaceBook = faceBook;
    }

    public boolean isEvents() {
        return isEvents;
    }

    public void setEvents(boolean events) {
        isEvents = events;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
