package bttc.app.exception;

import java.io.IOException;

public class NotFoundException extends RuntimeException {
    AppException error;

    public AppException getError() {
        return error;
    }

    public void setError(AppException error) {
        this.error = error;
    }
}
