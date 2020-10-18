package com.example.lifecare.VO;

public class FoodVO {
    String date;
    String foodName;
    String kcal;
    String carbo;
    String protein;
    String fat;
    int foodNum;
    int foodImg;

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getCarbo() {
        return carbo;
    }

    public void setCarbo(String carbo) {
        this.carbo = carbo;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    @Override
    public String toString() {
        return "FoodVO{" +
                "date='" + date + '\'' +
                ", foodName='" + foodName + '\'' +
                ", kcal='" + kcal + '\'' +
                ", carbo='" + carbo + '\'' +
                ", protein='" + protein + '\'' +
                '}';
    }
}
