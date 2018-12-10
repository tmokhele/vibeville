package bttc.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Performance implements Serializable {

    private long id;
    private String name;
    List<Review> reviews = new ArrayList<>();
    private String performanceTime;
    private String imageUrl;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPerformanceTime() {
        return performanceTime;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setPerformanceTime(String performanceTime) {
        this.performanceTime = performanceTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
