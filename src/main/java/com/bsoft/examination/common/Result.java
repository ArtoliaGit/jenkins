package com.bsoft.examination.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description 返回json
 * @createTime 2019年03月24日 14:31:00
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 2455156933806338812L;

    private Integer code;

    private String message;

    private T data;

    private long total;

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
