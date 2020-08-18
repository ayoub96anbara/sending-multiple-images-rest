package com.anbara.sending_multiple_images_rest.web;


import java.util.List;

public class MyResponse {
    private List<String> path;

    public MyResponse(List<String> path) {
        this.path = path;
    }

    public MyResponse() {
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }
}
