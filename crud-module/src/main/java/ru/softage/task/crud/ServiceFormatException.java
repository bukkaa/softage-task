package ru.softage.task.crud;

import ru.softage.task.api.Code;

public class ServiceFormatException extends ServiceException {

    public ServiceFormatException(String description) {
        super(Code.FORMAT_ERROR, description);
    }

    public ServiceFormatException(String description, Throwable cause) {
        super(Code.FORMAT_ERROR, description, cause);
    }
}
