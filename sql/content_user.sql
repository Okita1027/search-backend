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

 Date: 05/04/2025 22:52:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content_user
-- ----------------------------
DROP TABLE IF EXISTS `content_user`;
CREATE TABLE `content_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `avatar_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像链接',
  `edit_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户编辑时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of content_user
-- ----------------------------
INSERT INTO `content_user` VALUES (1, 'Okita1027', '123456', 'Okita1027', '我是冲田总司', 'https://s2.loli.net/2024/12/12/WBMR4F59Y8fQw2a.jpg', '2025-04-05 00:00:00', '2024-12-10 13:05:47', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (2, 'Saber', '123456', 'Saber', '我是阿尔托莉雅', 'https://s2.loli.net/2024/12/12/UfwYhI3li6Bdp5j.jpg', '2025-04-05 00:00:00', '2024-12-10 13:05:53', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (3, '呆毛', '123456', '呆毛', '呆毛王', 'https://s2.loli.net/2024/12/12/UfwYhI3li6Bdp5j.jpg', '2025-04-05 00:00:00', '2024-12-12 22:26:02', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (4, '柴郡', '123456', '柴郡', '柴郡柴郡柴？', 'https://s2.loli.net/2024/12/12/v76PocqJRMbn3Cj.jpg', '2025-04-05 00:00:00', '2024-12-12 22:30:08', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (5, 'DVA', '123456', 'DVA', '守望先锋机甲战士', 'https://s2.loli.net/2024/12/12/V5n8I9WYMfT17Rh.jpg', '2025-04-05 00:00:00', '2024-12-14 19:52:07', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (6, '梅林', '123456', '梅林', '花之魔术师', 'https://s2.loli.net/2024/12/12/9zLtMuOReAB3VZy.jpg', '2025-04-05 00:00:00', '2024-12-14 19:52:36', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (7, 'God\'s eye', '123456', 'God\'s eye', '宇宙景象：上帝之眼', 'https://s2.loli.net/2024/05/16/fgIWieopP2u1bm3.jpg', '2025-04-05 00:00:00', '2025-03-26 13:09:27', '2025-04-05 22:46:27', 0);
INSERT INTO `content_user` VALUES (9, '镜花水月', '123456', '镜花水月', '蓝染的斩魄刀', 'https://s2.loli.net/2024/05/16/fgIWieopP2u1bm3.jpg', '2025-04-05 00:00:00', '2024-12-16 12:14:59', '2025-04-05 22:46:27', 0);

SET FOREIGN_KEY_CHECKS = 1;
