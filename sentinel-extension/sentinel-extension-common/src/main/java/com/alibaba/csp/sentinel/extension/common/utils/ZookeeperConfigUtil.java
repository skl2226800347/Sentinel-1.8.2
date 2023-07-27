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
package com.alibaba.csp.sentinel.extension.common.utils;

import com.alibaba.csp.sentinel.util.StringUtil;

public class ZookeeperConfigUtil {

    public static final String BACKSLASH = "/";
    public static final int RETRY_TIMES = 3;
    public static final int SLEEP_TIME = 1000;
    public static final int CONNECTED_TIME = 5000;

    public static String getDataId(String appName,String dataIdPrefix){
        StringBuilder dataId = new StringBuilder();
        dataId.append(dataIdPrefix).append("-").append(appName);
        return dataId.toString();
    }

    public static String getPath(String groupId,String dataId) {
        if (StringUtil.isBlank(groupId)) {
            throw new IllegalArgumentException("grouId not null");
        }
        if (StringUtil.isBlank(dataId)) {
            throw new IllegalArgumentException("dataId not null");
        }

        StringBuilder zkPath = new StringBuilder();
        if (groupId.startsWith(BACKSLASH)){
            zkPath.append(groupId);
        } else {
            zkPath.append(BACKSLASH).append(groupId);
        }
        if (dataId.startsWith(BACKSLASH)) {
            zkPath.append(dataId);
        } else {
            zkPath.append(BACKSLASH)
                    .append(dataId);
        }
        return zkPath.toString();
    }

    public static final String getPath(String ...paths){
        if (paths == null) {
            return null;
        }
        StringBuilder zkPath = new StringBuilder();
        for (String path : paths) {
            if (path.startsWith(BACKSLASH)){
                zkPath.append(path);
            } else {
                zkPath.append(BACKSLASH).append(path);
            }
        }
        return zkPath.toString();
    }
}