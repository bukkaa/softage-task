package ru.softage.task.crud;

public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException(int code, String description) {
        super(description);
        this.code = code;
    }

    public ServiceException(int code, String description, Throwable cause) {
        super(description, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{code=" + code +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
