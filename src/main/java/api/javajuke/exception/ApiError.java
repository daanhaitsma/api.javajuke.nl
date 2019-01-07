package api.javajuke.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiError {

    private HttpStatus status;
    private Date timestamp;
    private String message;
    private String details;

    private ApiError() {
        timestamp = new Date();
    }

    public ApiError(HttpStatus status, String message, String details) {
        this();
        this.status = status;
        this.message = message;
        this.details = details;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
