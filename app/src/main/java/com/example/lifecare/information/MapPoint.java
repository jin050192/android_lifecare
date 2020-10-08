package com.example.lifecare.information;

public class MapPoint {
    private String yadmNm;
    private double xPos;
    private double yPos;

    public MapPoint(){
        super();
    }
    public MapPoint(String yadmNm, double xPos , double yPos){
        this.yadmNm = yadmNm;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public String getYadmNm() {
        return yadmNm;
    }
    public void setYadmNm(String yadmNm){
        this.yadmNm= yadmNm;
    }
    public double getXPos() {
        return xPos;
    }
    public void setXPos(double XPos){
        this.xPos = xPos;
    }
    public double getYPos() {
        return yPos;
    }
    public void setyPos(double YPos){
        this.yPos = yPos;
    }

    @Override
    public String toString() {
        return "MapPoint{" +
                "='yadmNm" + yadmNm + '\'' +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
