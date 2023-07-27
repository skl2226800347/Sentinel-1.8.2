package com.alibaba.csp.sentinel.extension.common.utils;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class AuthorityRuleParseUtil {

    public static final List<AuthorityRule> parse(String source){
        List<HashMap<String,Object>> authorityRuleMapList =JSON.parseObject(source, new TypeReference<List<HashMap<String,Object>>>() {});
        List<Object> jsonList = new ArrayList<>();
        for(HashMap<String,Object> map: authorityRuleMapList){
            jsonList.add(map.get("rule"));
        }
        String jsonListStr = JSONObject.toJSONString(jsonList);
        List<AuthorityRule> authorityRuleList = JSON.parseObject(jsonListStr, new TypeReference<List<AuthorityRule>>() {});
        return authorityRuleList;
    }
}
