package ru.softage.task.api;

public class Code {

    /**
     * Ok result
     */
    public static final int OK = 0;

    /**
     * Missed required fields in request
     */
    public static final int FORMAT_ERROR = 10;

    /**
     * Some error while handling request
     */
    public static final int PROCESSING_ERROR = 20;

    /**
     * Unexpected internal error
     */
    public static final int INTERNAL_ERROR = 99;
}
