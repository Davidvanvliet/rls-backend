package nl.rls.util;

import java.util.Date;

public class Response<T> {
    private T data;
    private Object error;
    private int status;
    private Date timestamp;

    protected Response(int status, T data, Object error) {
        this.status = status;
        this.data = data;
        this.error = error;
        this.timestamp = new Date();
    }

    public T getData() {
        return data;
    }

    public Object getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}