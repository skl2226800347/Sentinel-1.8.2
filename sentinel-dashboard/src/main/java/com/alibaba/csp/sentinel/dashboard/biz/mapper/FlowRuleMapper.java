package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FlowRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int countByExample(FlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByExample(FlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insert(FlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insertSelective(FlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    List<FlowRule> selectByExample(FlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    FlowRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExampleSelective(@Param("record") FlowRule record, @Param("example") FlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExample(@Param("record") FlowRule record, @Param("example") FlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKeySelective(FlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKey(FlowRule record);
}