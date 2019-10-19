package org.hackthon.letsdinner.model;

public class RestaurantFood {
    private String image;//图片链接
    private String name;//食物名称
    private int count;//菜品数量

    public RestaurantFood() {
    }

    public RestaurantFood(String image, String name, int count) {
        this.image = image;
        this.name = name;
        this.count = count;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
