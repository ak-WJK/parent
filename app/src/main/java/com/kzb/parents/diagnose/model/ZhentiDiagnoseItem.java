package com.kzb.parents.diagnose.model;

import java.io.Serializable;

/**
 * Created by wanghaofei on 17/6/6.
 */

public class ZhentiDiagnoseItem implements Serializable {
    private String exam_id;
    private String kemu_name;

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public String getKemu_name() {
        return kemu_name;
    }

    public void setKemu_name(String kemu_name) {
        this.kemu_name = kemu_name;
    }
}
