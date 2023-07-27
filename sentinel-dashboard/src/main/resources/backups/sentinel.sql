
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `app_info` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'app内容(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_app`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for authority_rule
-- ----------------------------
DROP TABLE IF EXISTS `authority_rule`;
CREATE TABLE `authority_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `limit_app` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `strategy` int(11) NOT NULL COMMENT '授权类型 0：白名单 1：黑名单',
  `rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规则(json)',
  `authority_rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权规则原始报文(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权规则表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for degrade_rule
-- ----------------------------
DROP TABLE IF EXISTS `degrade_rule`;
CREATE TABLE `degrade_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `limit_app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '针对来源',
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `grade` int(255) NOT NULL COMMENT '熔断策略 0： 慢调用比例  1：异常比例 2：异常数',
  `count` double NOT NULL COMMENT '最大RT/比例阈值/异常数',
  `slow_ratio_threshold` double DEFAULT NULL COMMENT '比例阈值',
  `time_window` int(11) NOT NULL COMMENT '熔断时长',
  `min_request_amount` int(11) NOT NULL COMMENT '最小请求数',
  `stat_interval_ms` int(11) NOT NULL COMMENT '统计时长',
  `degrade_rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '熔断规则原始报文(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '熔断规则表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for flow_rule
-- ----------------------------
DROP TABLE IF EXISTS `flow_rule`;
CREATE TABLE `flow_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `limit_app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '针对来源',
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名',
  `grade` int(255) NOT NULL COMMENT '阈值类型 0：并发线程数  1：QPS ',
  `count` double NOT NULL COMMENT '均摊阈值',
  `strategy` int(255) NOT NULL DEFAULT 0 COMMENT '策略',
  `ref_resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ref resource',
  `control_behavior` int(255) NOT NULL DEFAULT 0 COMMENT '0. default, 1. warm up, 2. rate limiter',
  `warm_up_period_sec` int(255) DEFAULT NULL,
  `max_queueing_time_ms` int(11) DEFAULT NULL COMMENT 'max queueing time in rate limiter behavior',
  `clusterMode` tinyint(255) NOT NULL DEFAULT 0 COMMENT '是否集群 0: 否 1：是',
  `cluster_config` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '集群配置数据(json格式)',
  `flow_rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流控规则原始报文(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '流量控制规则表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for param_flow_rule
-- ----------------------------
DROP TABLE IF EXISTS `param_flow_rule`;
CREATE TABLE `param_flow_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规则(json)',
  `param_flow_rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '热点规则原始报文(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_app`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '热点规则表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for system_rule
-- ----------------------------
DROP TABLE IF EXISTS `system_rule`;
CREATE TABLE `system_rule`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `app` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `highest_system_load` double NOT NULL DEFAULT -1 COMMENT '阈值',
  `avg_rt` bigint(20) NOT NULL DEFAULT -1 COMMENT '阈值(RT)',
  `max_thread` bigint(20) NOT NULL DEFAULT -1 COMMENT '阈值(线程数)',
  `qps` double NOT NULL DEFAULT -1 COMMENT '阈值(qps)',
  `highest_cpu_usage` double NOT NULL DEFAULT -1 COMMENT '阈值(CPU 使用率 )',
  `system_rule` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统保护规则原始报文(json)',
  `oper_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `creator` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx`(`app`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统保护规则表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
