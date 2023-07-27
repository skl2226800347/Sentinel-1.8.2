package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ParamFlowRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int countByExample(ParamFlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByExample(ParamFlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insert(ParamFlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insertSelective(ParamFlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    List<ParamFlowRule> selectByExample(ParamFlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    ParamFlowRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExampleSelective(@Param("record") ParamFlowRule record, @Param("example") ParamFlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExample(@Param("record") ParamFlowRule record, @Param("example") ParamFlowRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKeySelective(ParamFlowRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table param_flow_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKey(ParamFlowRule record);
}