package com.ethan.gap.dal.model;

public class GapUserInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gap_user_info.user_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gap_user_info.user_name
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gap_user_info.cert_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    private String certId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gap_user_info.user_mobile
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    private String userMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column gap_user_info.user_address
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    private String userAddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gap_user_info.user_id
     *
     * @return the value of gap_user_info.user_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gap_user_info.user_id
     *
     * @param userId the value for gap_user_info.user_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gap_user_info.user_name
     *
     * @return the value of gap_user_info.user_name
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gap_user_info.user_name
     *
     * @param userName the value for gap_user_info.user_name
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gap_user_info.cert_id
     *
     * @return the value of gap_user_info.cert_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public String getCertId() {
        return certId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gap_user_info.cert_id
     *
     * @param certId the value for gap_user_info.cert_id
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public void setCertId(String certId) {
        this.certId = certId == null ? null : certId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gap_user_info.user_mobile
     *
     * @return the value of gap_user_info.user_mobile
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public String getUserMobile() {
        return userMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gap_user_info.user_mobile
     *
     * @param userMobile the value for gap_user_info.user_mobile
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile == null ? null : userMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column gap_user_info.user_address
     *
     * @return the value of gap_user_info.user_address
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column gap_user_info.user_address
     *
     * @param userAddress the value for gap_user_info.user_address
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress == null ? null : userAddress.trim();
    }
}