package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.entity.User;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService implements UserService {

    @Override
    public User getUserByUserName(String userName) {
        User user =userManager.getUserByUserName(userName);
        return user;
    }
}
