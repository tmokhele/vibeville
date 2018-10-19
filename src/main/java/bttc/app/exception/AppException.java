package bttc.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AppException  extends RuntimeException{
    private final String message;

    public AppException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }


}