package com.alibaba.csp.sentinel.extension.common.utils;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FlowRuleParseUtil {

    public static final List<FlowRule> parse(String source){
        List<HashMap<String,Object>> flowRuleMapList = JSON.parseObject(source, new TypeReference<List<HashMap<String,Object>>>() {});
        List<Object> jsonList = new ArrayList<>();
        for(HashMap<String,Object> map: flowRuleMapList){
            jsonList.add(map.get("rule"));
        }
        String jsonListStr = JSONObject.toJSONString(jsonList);
        List<FlowRule> flowRuleList = JSON.parseObject(jsonListStr, new TypeReference<List<FlowRule>>() {});
        return flowRuleList;
    }
}
