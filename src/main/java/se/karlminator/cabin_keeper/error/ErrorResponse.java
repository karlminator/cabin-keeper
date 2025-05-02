package se.karlminator.cabin_keeper.error;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String msg;
    private String path;

    public ErrorResponse(){}

    public ErrorResponse(Integer status, String error, String msg, String path){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.msg = msg;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public String getPath() {
        return path;
    }
}
