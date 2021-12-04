SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for iot_radar_properties
-- ----------------------------
DROP TABLE IF EXISTS `iot_radar_properties`;
CREATE TABLE `iot_radar_properties`  (
                                         `id` bigint(20) NOT NULL COMMENT '主键id',
                                         `stcd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站号',
                                         `device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备id',
                                         `tm` datetime(0) NULL DEFAULT NULL COMMENT '时间',
                                         `uz` double NULL DEFAULT NULL COMMENT '雷达水位',
                                         `uq` double NULL DEFAULT NULL COMMENT '流量',
                                         `uv` double NULL DEFAULT NULL COMMENT '流速',
                                         `ua` double NULL DEFAULT NULL COMMENT '面积',
                                         `u_left_water_side` double NULL DEFAULT NULL COMMENT '左水边',
                                         `u_right_water_side` double NULL DEFAULT NULL COMMENT '右水边',
                                         `voltage` double NULL DEFAULT NULL COMMENT '电压',
                                         `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '雷达水位流速数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iot_radar_properties_tv
-- ----------------------------
DROP TABLE IF EXISTS `iot_radar_properties_tv`;
CREATE TABLE `iot_radar_properties_tv`  (
                                            `id` bigint(20) NOT NULL COMMENT '主键id',
                                            `pid` bigint(20) NULL DEFAULT NULL COMMENT '主表id',
                                            `stcd` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站号',
                                            `device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备id',
                                            `tv` double NULL DEFAULT NULL COMMENT '1#分流速',
                                            `csq` double NULL DEFAULT NULL COMMENT '1#信号',
                                            `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '雷达水位分流速数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for iot_station
-- ----------------------------
DROP TABLE IF EXISTS `iot_station`;
CREATE TABLE `iot_station`  (
                                `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
                                `sttp_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点类型id',
                                `device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备id',
                                `stnm` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点名称',
                                `stcd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点代码',
                                `lgtd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '经度',
                                `lttd` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '纬度',
                                `stlc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站址',
                                `state` tinyint(2) NULL DEFAULT 0 COMMENT '站点状态',
                                `server_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '服务器ID',
                                `session_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会话ID',
                                `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '设备地址',
                                `online_time` timestamp(0) NULL DEFAULT NULL COMMENT '设备上线时间',
                                `offline_time` timestamp(0) NULL DEFAULT NULL COMMENT '设备下线时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站点表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of iot_station
-- ----------------------------
INSERT INTO `iot_station` VALUES ('1', '1', '11', '测试站点', 'LL008', '108.425030', '32.101950', NULL, -1, '', '', '/127.0.0.1:52615', '2021-10-29 15:42:36', '2021-10-29 15:45:16');

-- ----------------------------
-- Table structure for iot_station_type
-- ----------------------------
DROP TABLE IF EXISTS `iot_station_type`;
CREATE TABLE `iot_station_type`  (
                                     `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
                                     `sttp_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
                                     `sttp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类代码',
                                     `sttp_icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
                                     `deleted` bit(1) NULL DEFAULT b'0' COMMENT '是否删除',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '站点类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of iot_station_type
-- ----------------------------
INSERT INTO `iot_station_type` VALUES ('1', '水文站', 'ZQ', NULL, b'0');
INSERT INTO `iot_station_type` VALUES ('2', '雨量站', 'PP', NULL, b'0');
INSERT INTO `iot_station_type` VALUES ('3', '水位站', 'ZZ', NULL, b'0');
INSERT INTO `iot_station_type` VALUES ('4', '流量站', 'QS', NULL, b'0');

SET FOREIGN_KEY_CHECKS = 1;
