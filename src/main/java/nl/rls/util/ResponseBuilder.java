package nl.rls.util;

import org.springframework.http.HttpStatus;

public class ResponseBuilder<T> {
    private T data;
    private Object error;
    private int status;

    public ResponseBuilder(int status) {
        this.status = status;
    }

    public static <T> ResponseBuilder<T> ok() {
        return new ResponseBuilder<>(HttpStatus.OK.value());
    }

    public static <T> ResponseBuilder<T> created() {
        return new ResponseBuilder<>(HttpStatus.CREATED.value());
    }

    public static <T> ResponseBuilder<T> accepted() {
        return new ResponseBuilder<>(HttpStatus.ACCEPTED.value());
    }

    public static <T> ResponseBuilder<T> badRequest() {
        return new ResponseBuilder<>(HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ResponseBuilder<T> unauthorized() {
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED.value());
    }

    public static <T> ResponseBuilder<T> forbidden() {
        return new ResponseBuilder<>(HttpStatus.FORBIDDEN.value());
    }

    public static <T> ResponseBuilder<T> notFound() {
        return new ResponseBuilder<>(HttpStatus.NOT_FOUND.value());
    }

    public static <T> ResponseBuilder<T> status(int status) {
        return new ResponseBuilder<>(status);
    }

    public static <T> ResponseBuilder<T> status(HttpStatus status) {
        return new ResponseBuilder<>(status.value());
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
