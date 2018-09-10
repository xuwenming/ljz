package com.mobian.pageModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenming on 2017/12/27.
 */
public class PushMessage {
    private Integer id;
    private String mtype;
    private String content;
    private Integer status;
    private Map<String, String> extras = new HashMap<String, String>();

    public PushMessage() {}

    public PushMessage(String mtype, String content) {
        this.mtype = mtype;
        this.content = content;
        extras.put("mtype", mtype);
    }

    public PushMessage(String mtype, String content, Integer status) {
        this.mtype = mtype;
        this.content = content;
        this.status = status;
        extras.put("mtype", mtype);
        extras.put("status", status + "");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        extras.put("id", id + "");
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, String> extras) {
        this.extras = extras;
    }
}
