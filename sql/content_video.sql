/*
 Navicat Premium Dump SQL

 Source Server         : 本地的MySQL
 Source Server Type    : MySQL
 Source Server Version : 80400 (8.4.0)
 Source Host           : localhost:3306
 Source Schema         : project_search

 Target Server Type    : MySQL
 Target Server Version : 80400 (8.4.0)
 File Encoding         : 65001

 Date: 05/04/2025 22:54:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content_video
-- ----------------------------
DROP TABLE IF EXISTS `content_video`;
CREATE TABLE `content_video`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'admin',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'admin',
  `is_deleted` tinyint UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '视频表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_video
-- ----------------------------
INSERT INTO `content_video` VALUES (1, '异星虫群片段', 'Video/异星虫群片段.mp4', '2025-04-05 22:44:06', '2025-04-05 22:44:06', 'admin', 'admin', 0);

SET FOREIGN_KEY_CHECKS = 1;
