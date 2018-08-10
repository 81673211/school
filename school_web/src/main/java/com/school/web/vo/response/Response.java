package com.school.web.vo.response;

/**
 * @author jame
 */
public class Response {
    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Response() {
    }

    public Response writeSuccess() {
        this.status = 200;
        this.msg = "success";
        return this;
    }

    public Response writeSuccess(String msg) {
        this.status = 200;
        this.msg = msg;
        return this;
    }

    public Response writeFailure(String msg) {
        this.status = 500;
        this.msg = msg;
        return this;
    }
}
