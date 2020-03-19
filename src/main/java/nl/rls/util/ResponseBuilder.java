package nl.rls.util;

public class ResponseBuilder<T> {
    private T data;
    private Object error;
    private int status;

    public ResponseBuilder(int status) {
        this.status = status;
    }

    public static <T> ResponseBuilder<T> ok() {
        return new ResponseBuilder<>(200);
    }

    public ResponseBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseBuilder<T> error(Object error) {
        this.error = error;
        return this;
    }

    public <D> Response<D> build() {
        return new Response<>(status, (D) data, error);
    }
}
