package nl.rls.util;

import java.util.Date;

public class Response<T> {
    private final T data;
    private final Object error;
    private final int status;
    private final Date timestamp;

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
