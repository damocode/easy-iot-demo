SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for iot_station
-- ----------------------------
DROP TABLE IF EXISTS `iot_station`;
CREATE TABLE `iot_station`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键id',
  `sttp_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '站点类型id',
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
INSERT INTO `iot_station` VALUES ('11', '2', '站点001', '0020', '108.650000', '33.650000', NULL, -1, '', '', '/127.0.0.1:60348', '2021-10-07 18:09:11', '2021-10-07 18:12:07');

SET FOREIGN_KEY_CHECKS = 1;
