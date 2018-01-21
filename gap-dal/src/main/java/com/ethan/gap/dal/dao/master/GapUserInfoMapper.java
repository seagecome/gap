package com.ethan.gap.dal.dao.master;

import com.ethan.gap.dal.model.GapUserInfo;

public interface GapUserInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    int insert(GapUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    int insertSelective(GapUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    GapUserInfo selectByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    int updateByPrimaryKeySelective(GapUserInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gap_user_info
     *
     * @mbggenerated Sat Jan 20 20:00:17 CST 2018
     */
    int updateByPrimaryKey(GapUserInfo record);
}