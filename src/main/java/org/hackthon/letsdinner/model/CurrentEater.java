package org.hackthon.letsdinner.model;

import java.util.ArrayList;
import java.io.Serializable;

public class CurrentEater implements Serializable {
    private int id;//取餐号
    private ArrayList<RestaurantFood> menu;//用餐者所选食物

    public CurrentEater(int id, ArrayList<RestaurantFood> menu) {
        this.id = id;
        this.menu = menu;
    }

    public CurrentEater() {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<RestaurantFood> getMenu() {
        return menu;
    }
    public void setMenu(ArrayList<RestaurantFood> menu) {
        this.menu = menu;
    }
}
