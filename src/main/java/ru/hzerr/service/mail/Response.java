package ru.hzerr.service.mail;

public class Response<T> {

    private int code;
    private boolean success;
    private T response;

    private Response(T response) {
        this.setCode(200);
        this.setSuccess(true);
        this.setResponse(response);
    }

    private Response(int code) {
        this.setCode(code);
        this.setSuccess(false);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public static <T>
    Response<T> from(T response) {
        return new Response<>(response);
    }

    public static <T>
    Response<T> from(int code) {
        return new Response<>(code);
    }
}
