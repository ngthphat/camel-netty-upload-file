package vn.ngthphat.camel.dom;

import java.io.Serializable;

/**
 * @author ngthphat
 */
public class ResponseWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private ResponseStatus status;

    private Object message;

    private Object data;

    public ResponseWrapper() {

    }

    public ResponseWrapper(ResponseStatus status) {
        this.status = status;
    }

    public ResponseWrapper(ResponseStatus status, Object message) {
        this.status = status;
        this.message = message;
    }

    public ResponseWrapper(ResponseStatus status, Object message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public ResponseWrapper setStatus(final ResponseStatus status) {
        this.status = status;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public ResponseWrapper setMessage(final Object message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseWrapper setData(final Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "status=" + status +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
