package com.alibaba.csp.sentinel.starter.zookeeper;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
public class AuthorityRuleConverterTest {

    public static void main(String[]arg) throws Exception{
        System.setProperty("file.encoding","utf-8");
        String source="[{\"app\":\"sentinel-demo-starter-zookeeper-provider\",\"gmtModified\":1681393024159,\"id\":13,\"ip\":\"10.10.0.14\",\"port\":8720,\"rule\":{\"limitApp\":\"aa33\",\"resource\":\"sayhello\",\"strategy\":0}},{\"app\":\"sentinel-demo-starter-zookeeper-provider\",\"gmtModified\":1681388441941,\"id\":14,\"ip\":\"127.0.0.1\",\"port\":8000,\"rule\":{\"limitApp\":\"333\",\"resource\":\"sayhell\",\"strategy\":1}}]";
        List<AuthorityRule> authorityRuleList = JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {});
        List<HashMap<String,Object>> authorityRuleMapList =JSON.parseObject(source, new TypeReference<List<HashMap<String,Object>>>() {});
        List<Object> jsonList = Lists.newArrayList();
        for(HashMap<String,Object> map: authorityRuleMapList){
            jsonList.add(map.get("rule"));
        }
        String jsonListStr = JSONObject.toJSONString(jsonList);
        authorityRuleList = JSON.parseObject(jsonListStr, new TypeReference<List<AuthorityRule>>() {});
        System.out.println(JSONObject.toJSONString(authorityRuleList));
    }
}
