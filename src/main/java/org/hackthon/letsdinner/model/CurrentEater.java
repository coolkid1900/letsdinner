package org.hackthon.letsdinner.model;

import java.util.ArrayList;

public class CurrentEater {
    private int id;//取餐号
    private ArrayList<RestaurantFood> selectRestaurantFood;//用餐者所选食物

    public CurrentEater(int id, ArrayList<RestaurantFood> selectRestaurantFood) {
        this.id = id;
        this.selectRestaurantFood = selectRestaurantFood;
    }

    public CurrentEater() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<RestaurantFood> getSelectRestaurantFood() {
        return selectRestaurantFood;
    }
    public void setSelectRestaurantFood(ArrayList<RestaurantFood> selectRestaurantFood) {
        this.selectRestaurantFood = selectRestaurantFood;
    }
}
