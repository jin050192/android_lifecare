package com.example.lifecare.VO;

import java.sql.Timestamp;

/**
 * Created by psn on 2018-01-18.
 */

public class UserVO {
    private UserVO(){}
    private static UserVO userVO= new UserVO();
    public static UserVO getInstance(){
        return userVO;
    }

    private String id="";
    private String customer_echeck="";
    private String enabled="";
    private String Customer_fingerprint;
    private String CUSTOMER_ID;

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getCustomer_fingerprint() {
        return Customer_fingerprint;
    }

    public void setCustomer_fingerprint(String customer_fingerprint) {
        Customer_fingerprint = customer_fingerprint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCustomer_echeck() {
        return customer_echeck;
    }

    public void setCustomer_echeck(String customer_echeck) {
        this.customer_echeck = customer_echeck;
    }
}
