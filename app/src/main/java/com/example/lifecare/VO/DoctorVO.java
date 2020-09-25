package com.example.lifecare.VO;

public class DoctorVO {
    private String doctor_id;
    private String doctor_name;
    private String doctor_major;
    private String doctor_position;

    public DoctorVO() {}

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getDoctor_position() {
        return doctor_position;
    }

    public void setDoctor_position(String doctor_position) {
        this.doctor_position = doctor_position;
    }

    public DoctorVO(String doctor_id, String doctor_name, String doctor_major, String doctor_position) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_major = doctor_major;
        this.doctor_position = doctor_position;
    }
}
