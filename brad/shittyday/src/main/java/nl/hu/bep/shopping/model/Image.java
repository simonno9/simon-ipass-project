package nl.hu.bep.shopping.model;

import java.util.List;

public class Image {
    private int id;
    private List<String> imageLinks;

    public Image() {
    }

    public Image(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }
}
