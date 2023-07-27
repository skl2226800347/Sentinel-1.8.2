package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int countByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insert(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insertSelective(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    List<SystemRule> selectByExample(SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    SystemRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExampleSelective(@Param("record") SystemRule record, @Param("example") SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExample(@Param("record") SystemRule record, @Param("example") SystemRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKeySelective(SystemRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKey(SystemRule record);
}