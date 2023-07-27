package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthorityRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int countByExample(AuthorityRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByExample(AuthorityRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insert(AuthorityRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int insertSelective(AuthorityRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    List<AuthorityRule> selectByExample(AuthorityRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    AuthorityRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExampleSelective(@Param("record") AuthorityRule record, @Param("example") AuthorityRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByExample(@Param("record") AuthorityRule record, @Param("example") AuthorityRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKeySelective(AuthorityRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table authority_rule
     *
     * @mbggenerated Sat Apr 15 17:37:58 CST 2023
     */
    int updateByPrimaryKey(AuthorityRule record);
}