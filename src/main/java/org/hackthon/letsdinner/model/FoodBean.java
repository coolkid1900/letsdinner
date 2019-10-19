package org.hackthon.letsdinner.model;

import java.math.BigDecimal;

// 菜品类
public class FoodBean {
    public int id;  // 菜品ID
    public String name;  // 菜品名称
    public BigDecimal price;  // 菜品价格
    public String image;  // 菜品图片链接

    public FoodBean() {
    }

    public FoodBean(String image, String name) {
        this.name = name;
        this.image = image;
    }

    public FoodBean(String name, BigDecimal price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
