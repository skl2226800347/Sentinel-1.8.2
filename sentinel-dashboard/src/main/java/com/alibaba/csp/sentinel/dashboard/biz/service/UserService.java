package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.entity.User;

public interface UserService {
    User getUserByUserName(String userName);
}
