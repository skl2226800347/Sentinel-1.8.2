/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.controller.v2;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.auth.SimpleWebAuthServiceImpl;
import com.alibaba.csp.sentinel.dashboard.biz.entity.User;
import com.alibaba.csp.sentinel.dashboard.biz.service.UserService;
import com.alibaba.csp.sentinel.dashboard.config.DashboardConfig;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author shikelei
 * @since 1.6.0
 */
@RestController
@RequestMapping("/v2/auth")
public class AuthV2Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthV2Controller.class);

    /*@Value("${auth.username:sentinel}")
    private String authUsername;

    @Value("${auth.password:sentinel}")
    private String authPassword;*/

    @Autowired
    private AuthService<HttpServletRequest> authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<AuthService.AuthUser> login(HttpServletRequest request, String username, String password) {
        /*if (StringUtils.isNotBlank(DashboardConfig.getAuthUsername())) {
            authUsername = DashboardConfig.getAuthUsername();
        }

        if (StringUtils.isNotBlank(DashboardConfig.getAuthPassword())) {
            authPassword = DashboardConfig.getAuthPassword();
        }*/
        if(StringUtil.isEmpty(username)){
            return Result.ofFail(ResultCodeEnum.USER_NAME_IS_NULL);
        }
        if(StringUtil.isEmpty(password)){
            return Result.ofFail(ResultCodeEnum.PASSWORD_IS_NULL) ;
        }
        User user  = userService.getUserByUserName(username);
        if(user == null){
            return Result.ofFail(ResultCodeEnum.USER_NOT_EXISTS);
        }
        if(!username.equals(user.getUserName()) || !password.equals(user.getPassword())){
            return Result.ofFail(ResultCodeEnum.USER_PASSWORD_IS_ERROR);
        }
        /*
         * If auth.username or auth.password is blank(set in application.properties or VM arguments),
         * auth will pass, as the front side validate the input which can't be blank,
         * so user can input any username or password(both are not blank) to login in that case.
         */
        /*if (StringUtils.isNotBlank(authUsername) && !authUsername.equals(username)
                || StringUtils.isNotBlank(authPassword) && !authPassword.equals(password)) {
            LOGGER.error("Login failed: Invalid username or password, username=" + username);
            return Result.ofFail(-1, "Invalid username or password");
        }*/

        AuthService.AuthUser authUser = new SimpleWebAuthServiceImpl.SimpleWebAuthUserImpl(user.getUserName());
        request.getSession().setAttribute(SimpleWebAuthServiceImpl.WEB_SESSION_KEY, authUser);
        return Result.ofSuccess(authUser);
    }

    @PostMapping(value = "/logout")
    public Result<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return Result.ofSuccess(null);
    }

    @PostMapping(value = "/check")
    public Result<?> check(HttpServletRequest request) {
        AuthService.AuthUser authUser = authService.getAuthUser(request);
        if (authUser == null) {
            return Result.ofFail(-1, "Not logged in");
        }
        return Result.ofSuccess(authUser);
    }
}
