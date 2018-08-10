package com.school.web.vo.response;

/**
 * @author jame
 */
public class DataResponse<T> extends Response {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public DataResponse writeSuccess(T data) {
        super.writeSuccess();
        this.data = data;
        return this;
    }

    public DataResponse writeSuccess(String msg, T data) {
        super.writeSuccess(msg);
        this.data = data;
        return this;
    }

    public DataResponse writeFailure(String msg) {
        super.writeFailure(msg);
        this.data = null;
        return this;
    }

}
