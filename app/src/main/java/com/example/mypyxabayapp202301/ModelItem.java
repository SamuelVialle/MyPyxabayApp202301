package com.example.mypyxabayapp202301;

public class ModelItem {

    private String imageUrl;
    private String creator;
    private int likes;

    public ModelItem() {
    }

    public ModelItem(String imageUrl, String creator, int likes) {
        this.imageUrl = imageUrl;
        this.creator = creator;
        this.likes = likes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreator() {
        return creator;
    }

    public int getLikes() {
        return likes;
    }
}
