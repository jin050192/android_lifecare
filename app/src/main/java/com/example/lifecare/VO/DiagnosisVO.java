package com.example.lifecare.VO;

public class DiagnosisVO {

    private String diagnosis_num;
    private String diagnosis_time;
    private String customer_id;
    private String doctor_id;
    private String doctor_name;
    private String doctor_major;
    private String disease_name;
    private String drug;
    private String customer_amount;
    private int customer_payment;
    public boolean expanded = false;
    public boolean parent = false;

    // flag when item swiped
    public boolean swiped = false;

    public DiagnosisVO(String diagnosis_num, String diagnosis_time, String doctor_name, String doctor_major, String disease_name, String drug) {
        this.diagnosis_num = diagnosis_num;
        this.diagnosis_time = diagnosis_time;
        this.doctor_name = doctor_name;
        this.doctor_major = doctor_major;
        this.disease_name = disease_name;
        this.drug = drug;
    }

    public DiagnosisVO(String diagnosis_num, String diagnosis_time, String doctor_major, String customer_amount) {
        this.diagnosis_num = diagnosis_num;
        this.diagnosis_time = diagnosis_time;
        this.doctor_major = doctor_major;
        this.customer_amount = customer_amount;
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

    public String getDiagnosis_num() {
        return diagnosis_num;
    }

    public void setDiagnosis_num(String diagnosis_num) {
        this.diagnosis_num = diagnosis_num;
    }

    public String getDiagnosis_time() {
        return diagnosis_time;
    }

    public void setDiagnosis_time(String diagnosis_time) {
        this.diagnosis_time = diagnosis_time;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getCustomer_amount() {
        return customer_amount;
    }

    public void setCustomer_amount(String customer_amount) {
        this.customer_amount = customer_amount;
    }

    public int getCustomer_payment() {
        return customer_payment;
    }

    public void setCustomer_payment(int customer_payment) {
        this.customer_payment = customer_payment;
    }
}
