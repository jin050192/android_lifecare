package com.example.lifecare.VO;

public class ReservationVO {

    private String appoint_num;
    private String reservation_date;
    private String doctor_id;
    private String customer_id;

    // 닥터정보
    private String doctor_name;
    private String doctor_major;

    public ReservationVO(String doctor_major, String doctor_name, String reservation_date, String appoint_num) {
        this.doctor_major = doctor_major;
        this.doctor_name = doctor_name;
        this.reservation_date = reservation_date;
        this.appoint_num = appoint_num;
    }

    public String getAppoint_num() {
        return appoint_num;
    }

    public void setAppoint_num(String appoint_num) {
        this.appoint_num = appoint_num;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_major() {
        return doctor_major;
    }

    public void setDoctor_major(String doctor_major) {
        this.doctor_major = doctor_major;
    }
}
