package com.example.rsatest.info;

public class LoginInfo {
    private String equipmentName;
    private Integer loginType;
    private String meid;
    private Integer osType;
    private String password;
    private String phoneNum;
    private String smsCode;
    private Integer userNum;
    private String version;

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid;
    }

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "equipmentName='" + equipmentName + '\'' +
                ", loginType=" + loginType +
                ", meid='" + meid + '\'' +
                ", osType=" + osType +
                ", password='" + password + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", userNum=" + userNum +
                ", version='" + version + '\'' +
                '}';
    }
}
