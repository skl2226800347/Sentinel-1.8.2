package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DegradeRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int countByExample(DegradeRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByExample(DegradeRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insert(DegradeRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insertSelective(DegradeRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    List<DegradeRule> selectByExample(DegradeRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    DegradeRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExampleSelective(@Param("record") DegradeRule record, @Param("example") DegradeRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExample(@Param("record") DegradeRule record, @Param("example") DegradeRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKeySelective(DegradeRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table degrade_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKey(DegradeRule record);
}