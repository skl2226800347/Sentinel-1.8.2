package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.constants.CommonConstant;
import com.alibaba.csp.sentinel.dashboard.biz.entity.User;
import com.alibaba.csp.sentinel.dashboard.biz.entity.UserExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.UserMapperExt;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserManager {
    @Autowired
    private UserMapperExt userMapperExt;

    public User getUserByUserName(String userName){
        if(StringUtil.isEmpty(userName)){
            throw  new DashboardBizException("userName is null");
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        List<User> userList = userMapperExt.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }
        return userList.get(CommonConstant.ZERO);
    }
}
