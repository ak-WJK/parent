package com.kzb.parents.diagnose.model;

import java.io.Serializable;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class DiagnoseItem implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
