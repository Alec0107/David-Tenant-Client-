package com.example.dt.AUTH.Models;

import java.io.Serializable;

public class Product implements Serializable {

    private String name;
    private String description;
    private String price;
    private String image_url;

    public Product(String name, String description, String price, String image_url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }

}
