package bttc.app.model;

public class ApiResponse {
    private Boolean success;
    private String message;
    private Object created;

    public ApiResponse(Boolean success, String message,Object created) {
        this.success = success;
        this.message = message;
        this.created = created;
    }

    public Object getCreated() {
        return created;
    }

    public void setCreated(Object created) {
        this.created = created;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
