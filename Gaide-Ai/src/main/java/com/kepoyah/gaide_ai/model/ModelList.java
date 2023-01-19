package com.kepoyah.gaide_ai.model;

public class ModelList {
    private String id;
    private String judul;
    private String content;
    private String images;

    public ModelList() {
        this.id = id;
        this.judul = judul;
        this.content = content;
        this.images = images;
    }

    public String getId() {
        return  id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}

