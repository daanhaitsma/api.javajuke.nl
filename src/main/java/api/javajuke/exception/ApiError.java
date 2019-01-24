package api.javajuke.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiError {

    private HttpStatus status;
    private int code;
    private Date timestamp;
    private String message;

    private ApiError() {
        timestamp = new Date();
    }

    public ApiError(HttpStatus status, String message, int code) {
        this();
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
