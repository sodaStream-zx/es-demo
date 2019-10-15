package zxx.pri.core.entity;

import java.io.Serializable;

public class ResDto implements Serializable {
    private Integer status;
    private String message;

    public ResDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ResDto{status=" + this.status + ", message='" + this.message + '\'' + '}';
    }
}
