package com.kp.prosenjit.kect.Model;

import java.util.ArrayList;

public class ActivityModel {


    String activity_id,activity_title,activity_created_by,activity_created_on;
    ArrayList<String> activity_image_list;

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getActivity_title() {
        return activity_title;
    }

    public void setActivity_title(String activity_title) {
        this.activity_title = activity_title;
    }

    public String getActivity_created_by() {
        return activity_created_by;
    }

    public void setActivity_created_by(String activity_created_by) {
        this.activity_created_by = activity_created_by;
    }

    public String getActivity_created_on() {
        return activity_created_on;
    }

    public void setActivity_created_on(String activity_created_on) {
        this.activity_created_on = activity_created_on;
    }

    public ArrayList<String> getActivity_image_list() {
        return activity_image_list;
    }

    public void setActivity_image_list(ArrayList<String> activity_image_list) {
        this.activity_image_list = activity_image_list;
    }
}
