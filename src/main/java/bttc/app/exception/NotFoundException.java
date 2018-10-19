package bttc.app.exception;

public class NotFoundException extends RuntimeException {
    private final AppException error;

    public NotFoundException(AppException error) {
        this.error = error;
    }

    public AppException getError() {
        return error;
    }

}
