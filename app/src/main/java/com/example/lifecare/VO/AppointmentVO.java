package com.example.lifecare.VO;

public class AppointmentVO {
    private String appoint_num;
    private String doctor_id;
    private String appoint_date; // 예약선택에서 string 값이라.....
    private String appoint_time;
    private int appoint_enable;

    public String getAppoint_num() {
        return appoint_num;
    }

    public void setAppoint_num(String appoint_num) {
        this.appoint_num = appoint_num;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getAppoint_date() {
        return appoint_date;
    }

    public void setAppoint_date(String appoint_date) {
        this.appoint_date = appoint_date;
    }

    public String getAppoint_time() {
        return appoint_time;
    }

    public void setAppoint_time(String appoint_time) {
        this.appoint_time = appoint_time;
    }

    public int getAppoint_enable() {
        return appoint_enable;
    }

    public void setAppoint_enable(int appoint_enable) {
        this.appoint_enable = appoint_enable;
    }

    public AppointmentVO(String appoint_num, String doctor_id, String appoint_date) {
        this.appoint_num = appoint_num;
        this.doctor_id = doctor_id;
        this.appoint_date = appoint_date;
    }
}
