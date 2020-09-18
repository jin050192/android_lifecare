package com.example.lifecare.VO;

import java.sql.Timestamp;

/**
 * Created by psn on 2018-01-18.
 */

public class MemberVO {
    // member_tbl
    private String member_id;
    private String member_pwd;
    private String member_name;
    private String member_tel;
    private String member_email;
    private String member_birth;
    private String member_sex;
    private String member_address;
    private int member_point;
    private int member_step;
    private int member_cumPoint;
    private String member_img;

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    private Timestamp member_reg_date;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_pwd() {
        return member_pwd;
    }

    public void setMember_pwd(String member_pwd) {
        this.member_pwd = member_pwd;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_tel() {
        return member_tel;
    }

    public void setMember_tel(String member_tel) {
        this.member_tel = member_tel;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_birth() {
        return member_birth;
    }

    public void setMember_birth(String member_birth) {
        this.member_birth = member_birth;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_address() {
        return member_address;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

    public int getMember_point() {
        return member_point;
    }

    public void setMember_point(int member_point) {
        this.member_point = member_point;
    }

    public int getMember_step() {
        return member_step;
    }

    public void setMember_step(int member_step) {
        this.member_step = member_step;
    }

    public int getMember_cumPoint() {
        return member_cumPoint;
    }

    public void setMember_cumPoint(int member_cumPoint) {
        this.member_cumPoint = member_cumPoint;
    }

    public Timestamp getMember_reg_date() {
        return member_reg_date;
    }

    public void setMember_reg_date(Timestamp member_reg_date) {
        this.member_reg_date = member_reg_date;
    }

}
