package bttc.app.model;

import java.io.Serializable;
import java.util.List;

public class AccountInformation implements Serializable {

    private String kind;
    private List<AccountUser> users;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<AccountUser> getUsers() {
        return users;
    }

    public void setUsers(List<AccountUser> users) {
        this.users = users;
    }
}
