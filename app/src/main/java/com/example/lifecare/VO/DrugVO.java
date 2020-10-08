package com.example.lifecare.VO;

public class DrugVO {
    public DrugVO(String drug_number, String drug_name, String drug_enptname, String drug_productimage, String drug_frontShape, String drug_color) {
        this.drug_number = drug_number;
        this.drug_name = drug_name;
        this.drug_enptname = drug_enptname;
        this.drug_productimage = drug_productimage;
        this.drug_frontShape = drug_frontShape;
        this.drug_color = drug_color;
    }

    private DrugVO(){}

    private static DrugVO DrugVO= new DrugVO();

    public DrugVO(String drug_image, String drug_name, String drug_emtp, String drug_division, String drug_lisenceDate, String drug_category, String drug_formulation, String drug_color, String drug_num, String drug_storage, String drug_additives, String drug_effect, String drug_precautions) {
    }

    public static DrugVO getInstance(){
        return DrugVO;
    }


    private String drug_number;                 // 품목일련번호
    private String drug_name;                   // 약품명
    private String drug_enptname;               // 업체명
    private String drug_shape;                  // 약품 모양 컬럼
    private String drug_productimage;           // 약품 이미지
    private String drug_frontShape;             // 약품 표시앞
    private String drug_formulation;            // 제형
    private String drug_color;                  // 약품 색깔
    private float drug_size_l;                  // 약품 장축
    private float drug_size_s;                  // 약품 단축
    private float drug_size_w;                  // 약품  컬럼 컬럼
    private String drug_category;               // 약품 분류명
    private String drug_division;               // 약품의 전문 or 비전문 기입 컬럼
    private String drug_license_date;           // 약품 허가일
    private String drug_effect;                 // 약품 효능 및 효과
    private String drug_storage;                // 약품 저장방법
    private String drug_expiration_date;        // 약품  유효기간
    private String drug_dosage;                 // 약품 복용량
    private String drug_additives;              // 약품 첨가제명
    private String drug_precautions;            // 약품 주의사항


    public String getDrug_number() {
        return drug_number;
    }

    public void setDrug_number(String drug_number) {
        this.drug_number = drug_number;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDrug_enptname() {
        return drug_enptname;
    }

    public void setDrug_enptname(String drug_enptname) {
        this.drug_enptname = drug_enptname;
    }

    public String getDrug_shape() {
        return drug_shape;
    }

    public void setDrug_shape(String drug_shape) {
        this.drug_shape = drug_shape;
    }

    public String getDrug_productimage() {
        return drug_productimage;
    }

    public void setDrug_productimage(String drug_productimage) {
        this.drug_productimage = drug_productimage;
    }

    public String getDrug_frontShape() {
        return drug_frontShape;
    }

    public void setDrug_frontShape(String drug_frontShape) {
        this.drug_frontShape = drug_frontShape;
    }

    public String getDrug_formulation() {
        return drug_formulation;
    }

    public void setDrug_formulation(String drug_formulation) {
        this.drug_formulation = drug_formulation;
    }

    public String getDrug_color() {
        return drug_color;
    }

    public void setDrug_color(String drug_color) {
        this.drug_color = drug_color;
    }

    public float getDrug_size_l() {
        return drug_size_l;
    }

    public void setDrug_size_l(float drug_size_l) {
        this.drug_size_l = drug_size_l;
    }

    public float getDrug_size_s() {
        return drug_size_s;
    }

    public void setDrug_size_s(float drug_size_s) {
        this.drug_size_s = drug_size_s;
    }

    public float getDrug_size_w() {
        return drug_size_w;
    }

    public void setDrug_size_w(float drug_size_w) {
        this.drug_size_w = drug_size_w;
    }

    public String getDrug_category() {
        return drug_category;
    }

    public void setDrug_category(String drug_category) {
        this.drug_category = drug_category;
    }

    public String getDrug_division() {
        return drug_division;
    }

    public void setDrug_division(String drug_division) {
        this.drug_division = drug_division;
    }

    public String getDrug_license_date() {
        return drug_license_date;
    }

    public void setDrug_license_date(String drug_license_date) {
        this.drug_license_date = drug_license_date;
    }

    public String getDrug_effect() {
        return drug_effect;
    }

    public void setDrug_effect(String drug_effect) {
        this.drug_effect = drug_effect;
    }

    public String getDrug_storage() {
        return drug_storage;
    }

    public void setDrug_storage(String drug_storage) {
        this.drug_storage = drug_storage;
    }

    public String getDrug_expiration_date() {
        return drug_expiration_date;
    }

    public void setDrug_expiration_date(String drug_expiration_date) {
        this.drug_expiration_date = drug_expiration_date;
    }

    public String getDrug_dosage() {
        return drug_dosage;
    }

    public void setDrug_dosage(String drug_dosage) {
        this.drug_dosage = drug_dosage;
    }

    public String getDrug_additives() {
        return drug_additives;
    }

    public void setDrug_additives(String drug_additives) {
        this.drug_additives = drug_additives;
    }

    public String getDrug_precautions() {
        return drug_precautions;
    }

    public void setDrug_precautions(String drug_precautions) {
        this.drug_precautions = drug_precautions;
    }
}