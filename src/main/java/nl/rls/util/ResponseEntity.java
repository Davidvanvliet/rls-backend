package nl.rls.util;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public class ResponseEntity<T> extends HttpEntity<T> {
    private final Object status;

    public ResponseEntity(HttpStatus status) {
        this((T) null, null, (HttpStatus) status);
    }

    public ResponseEntity(@Nullable T body, HttpStatus status) {
        this(body, null, (HttpStatus) status);
    }

    public ResponseEntity(MultiValueMap<String, String> headers, HttpStatus status) {
        this((T) null, headers, (HttpStatus) status);
    }

    public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers);
        Assert.notNull(status, "HttpStatus must not be null");
        this.status = status;
    }

    private ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers, Object status) {
        super(body, headers);
        Assert.notNull(status, "HttpStatus must not be null");
        this.status = status;
    }

    public static ResponseEntity.BodyBuilder status(HttpStatus status) {
        Assert.notNull(status, "HttpStatus must not be null");
        return new ResponseEntity.DefaultBuilder(status);
    }

    public static ResponseEntity.BodyBuilder status(int status) {
        return new ResponseEntity.DefaultBuilder(status);
    }

    public static <T> ResponseEntity<T> of(Optional<T> body) {
        Assert.notNull(body, "Body must not be null");
        return (ResponseEntity) body.map(ResponseEntity::ok).orElse(notFound().build());
    }

    public static ResponseEntity.BodyBuilder ok() {
        return status(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        ResponseEntity.BodyBuilder builder = ok();
        return builder.body(body);
    }

    public static ResponseEntity.BodyBuilder created(URI location) {
        ResponseEntity.BodyBuilder builder = status(HttpStatus.CREATED);
        return (ResponseEntity.BodyBuilder) builder.location(location);
    }

    public static ResponseEntity.BodyBuilder accepted() {
        return status(HttpStatus.ACCEPTED);
    }

    public static ResponseEntity.HeadersBuilder<?> noContent() {
        return status(HttpStatus.NO_CONTENT);
    }

    public static ResponseEntity.BodyBuilder badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity.BodyBuilder notFound() {
        return status(HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity.BodyBuilder unprocessableEntity() {
        return status(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public HttpStatus getStatusCode() {
        return this.status instanceof HttpStatus ? (HttpStatus) this.status : HttpStatus.valueOf((Integer) this.status);
    }

    public int getStatusCodeValue() {
        return this.status instanceof HttpStatus ? ((HttpStatus) this.status).value() : (Integer) this.status;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!super.equals(other)) {
            return false;
        } else {
            ResponseEntity<?> otherEntity = (ResponseEntity) other;
            return ObjectUtils.nullSafeEquals(this.status, otherEntity.status);
        }
    }

    public int hashCode() {
        return super.hashCode() * 29 + ObjectUtils.nullSafeHashCode(this.status);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(this.status.toString());
        if (this.status instanceof HttpStatus) {
            builder.append(' ');
            builder.append(((HttpStatus) this.status).getReasonPhrase());
        }

        builder.append(',');
        T body = this.getBody();
        HttpHeaders headers = this.getHeaders();
        if (body != null) {
            builder.append(body);
            builder.append(',');
        }

        builder.append(headers);
        builder.append('>');
        return builder.toString();
    }

    public interface BodyBuilder extends ResponseEntity.HeadersBuilder<ResponseEntity.BodyBuilder> {
        ResponseEntity.BodyBuilder contentLength(long var1);

        ResponseEntity.BodyBuilder contentType(MediaType var1);

        <T> ResponseEntity<T> body(@Nullable T var1);
    }

    public interface HeadersBuilder<B extends ResponseEntity.HeadersBuilder<B>> {
        B header(String var1, String... var2);

        B headers(@Nullable HttpHeaders var1);

        B allow(HttpMethod... var1);

        B eTag(String var1);

        B lastModified(ZonedDateTime var1);

        B lastModified(Instant var1);

        B lastModified(long var1);

        B location(URI var1);

        B cacheControl(CacheControl var1);

        B varyBy(String... var1);

        <T> ResponseEntity<T> build();
    }

    private static class DefaultBuilder implements ResponseEntity.BodyBuilder {
        private final Object statusCode;
        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBuilder(Object statusCode) {
            this.statusCode = statusCode;
        }

        public ResponseEntity.BodyBuilder header(String headerName, String... headerValues) {
            String[] var3 = headerValues;
            int var4 = headerValues.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String headerValue = var3[var5];
                this.headers.add(headerName, headerValue);
            }

            return this;
        }

        public ResponseEntity.BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }

            return this;
        }

        public ResponseEntity.BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet(Arrays.asList(allowedMethods)));
            return this;
        }

        public ResponseEntity.BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        public ResponseEntity.BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        public ResponseEntity.BodyBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }

            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }

            this.headers.setETag(etag);
            return this;
        }

        public ResponseEntity.BodyBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }

        public ResponseEntity.BodyBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }

        public ResponseEntity.BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        public ResponseEntity.BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        public ResponseEntity.BodyBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }

        public ResponseEntity.BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        public <T> ResponseEntity<T> build() {
            return (ResponseEntity<T>) this.body((Object) null);
        }

        public <T> ResponseEntity<T> body(@Nullable T body) {
            return new ResponseEntity(body, this.headers, this.statusCode);
        }
    }
}
