package bttc.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event  implements Serializable{
    private String id;
    private String name;
    private String location;
    private String category;
    private Date releaseDate;
    private String description;
    private int unitPrice;
    private int quantityInStock;
    private String vipamount;
    private String generalamount;
    private String earlyamount;
    private int rating;
    private String imageUrl;
    List<Review> reviews = new ArrayList<>();
    List<Performance> performances = new ArrayList<>();
    private int reviewsCount;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getVipamount() {
        return vipamount;
    }

    public void setVipamount(String vipamount) {
        this.vipamount = vipamount;
    }

    public String getGeneralamount() {
        return generalamount;
    }

    public void setGeneralamount(String generalamount) {
        this.generalamount = generalamount;
    }

    public String getEarlyamount() {
        return earlyamount;
    }

    public void setEarlyamount(String earlyamount) {
        this.earlyamount = earlyamount;
    }
}
