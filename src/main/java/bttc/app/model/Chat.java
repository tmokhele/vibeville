package bttc.app.model;

public class Chat {

    private User from;
    private String content;
    private Token action;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Token getAction() {
        return action;
    }

    public void setAction(Token action) {
        this.action = action;
    }
}
