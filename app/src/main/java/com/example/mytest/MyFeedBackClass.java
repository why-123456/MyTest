package com.example.mytest;

public class MyFeedBackClass {
    private String phoneNum;
    private String email;
    private String remark;
    private String picObsUrl;

    private String equipmentVersion;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicObsUrl() {
        return picObsUrl;
    }

    public void setPicObsUrl(String picObsUrl) {
        this.picObsUrl = picObsUrl;
    }

    public String getEquipmentVersion() {
        return equipmentVersion;
    }

    public void setEquipmentVersion(String equipmentVersion) {
        this.equipmentVersion = equipmentVersion;
    }

    @Override
    public String toString() {
        return "MyFeedBackClass{" +
                "phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                ", picObsUrl='" + picObsUrl + '\'' +
                ", equipmentVersion='" + equipmentVersion + '\'' +
                '}';
    }
}
