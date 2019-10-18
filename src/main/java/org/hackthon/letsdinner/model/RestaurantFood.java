package org.hackthon.letsdinner.model;

public class RestaurantFood {
    private String imgUrl;//图片链接
    private String foodName;//食物名称

    public RestaurantFood() {
    }

    public RestaurantFood(String imgUrl, String foodName) {
        this.imgUrl = imgUrl;
        this.foodName = foodName;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
