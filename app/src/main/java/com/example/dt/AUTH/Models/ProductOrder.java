package com.example.dt.AUTH.Models;

public class ProductOrder {

    private String brandName;
    private String description;
    private String price;
    private String image_url;
    private String size;
    private int qty;

    public ProductOrder(String brandName, String description, String price, String image_url, String size, int qty) {
        this.brandName = brandName;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.size = size;
        this.qty = qty;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "ProductOrder{" +
                "brandName='" + brandName + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + image_url + '\'' +
                ", size='" + size + '\'' +
                ", qty=" + qty +
                '}';
    }
}
