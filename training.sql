/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : training

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 14/07/2022 10:10:21
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for judge_scores
-- ----------------------------
DROP TABLE IF EXISTS `judge_scores`;
CREATE TABLE `judge_scores`  (
  `judge_scores_id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `player_id` int NOT NULL COMMENT '被评分的参赛选手id',
  `judger_id` int NOT NULL COMMENT '评委id',
  `matchpk_id` tinyint NOT NULL COMMENT '比赛对战id',
  `judge_scores_score` float(4, 1) NOT NULL COMMENT '评委所打分数',
  PRIMARY KEY (`judge_scores_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of judge_scores
-- ----------------------------
INSERT INTO `judge_scores` VALUES (10, 148, 190, 2, 90.3);
INSERT INTO `judge_scores` VALUES (11, 154, 190, 2, 77.0);
INSERT INTO `judge_scores` VALUES (12, 146, 209, 2, 85.3);
INSERT INTO `judge_scores` VALUES (13, 97, 209, 2, 97.9);
INSERT INTO `judge_scores` VALUES (14, 159, 188, 1, 76.0);
INSERT INTO `judge_scores` VALUES (15, 155, 188, 1, 87.0);
INSERT INTO `judge_scores` VALUES (16, 146, 188, 2, 76.0);
INSERT INTO `judge_scores` VALUES (17, 97, 188, 2, 83.5);
INSERT INTO `judge_scores` VALUES (18, 148, 192, 1, 67.9);
INSERT INTO `judge_scores` VALUES (19, 147, 192, 1, 77.8);
INSERT INTO `judge_scores` VALUES (20, 148, 188, 1, 84.6);
INSERT INTO `judge_scores` VALUES (21, 147, 188, 1, 93.8);

-- ----------------------------
-- Table structure for matchpk
-- ----------------------------
DROP TABLE IF EXISTS `matchpk`;
CREATE TABLE `matchpk`  (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `match_id` int NOT NULL COMMENT '比赛id',
  `player_a` int NOT NULL COMMENT '参赛选手a的id外键',
  `player_music_a` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参赛选手a的歌曲',
  `player_b` int NOT NULL COMMENT '参赛选手a的id外键',
  `player_music_b` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参赛选手a的歌曲',
  `matchPK_status` int NOT NULL COMMENT '比赛状态（1：开启，0：关闭，2：暂停中）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of matchpk
-- ----------------------------
INSERT INTO `matchpk` VALUES (8, 1, 159, '好想抱住你', 155, '夏日环游', 1);
INSERT INTO `matchpk` VALUES (10, 2, 146, '未来之声', 97, '明天会更好', 0);
INSERT INTO `matchpk` VALUES (11, 1, 148, '泡沫', 147, '光年之外', 0);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `scores_id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `player_id` int NOT NULL COMMENT '参赛选手外键',
  `matchpk_id` tinyint NOT NULL COMMENT '比赛对战id',
  `scores_final` float(10, 1) NOT NULL COMMENT '综合分数',
  `vote_id` int NOT NULL COMMENT '投票得分外键id',
  PRIMARY KEY (`scores_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES (3, 97, 2, 140.7, 32);
INSERT INTO `scores` VALUES (4, 146, 2, 130.7, 31);
INSERT INTO `scores` VALUES (5, 159, 1, 126.0, 27);
INSERT INTO `scores` VALUES (6, 155, 1, 137.0, 28);
INSERT INTO `scores` VALUES (9, 148, 1, 176.2, 33);
INSERT INTO `scores` VALUES (10, 147, 1, 85.8, 34);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `user_password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户密码',
  `user_real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别（1：男，0：女）',
  `user_telephone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `user_scale` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限控制（audience:观众，admin：管理员，singer：歌手）',
  `user_status` tinyint(1) NOT NULL COMMENT '状态位（1：有效，0无效）',
  `matchPK_id` tinyint NULL DEFAULT NULL COMMENT '比赛对战外键',
  `player_promotion` tinyint NULL DEFAULT NULL COMMENT '1：已晋级；0：未晋级',
  `player_music` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选手作品',
  `user_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 210 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (96, '623938768@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '严文', 1, '13802054478', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (97, '167293009@qq.com', '$2a$10$.pth7CLUYKTycFzcYB5m6OKbqmC31yqfTuebt4nMns6RsV9akzBvq', '江星', 0, '13402116315', 'singer', 0, 1, NULL, '伤不起', NULL);
INSERT INTO `users` VALUES (98, '695036527@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '戈善会', NULL, '15508093615', 'audience', 2, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (99, '901328083@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '谭瑶璐', 0, '13508678331', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (100, '550349650@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '訾翔', 1, '13305305699', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (101, '517243637@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '宗丽佳', 0, '13200487874', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (102, '214773640@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '政彬朋', 1, '13705285989', 'audience', 0, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (103, '928966336@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '狐昭宜', 0, '13501201402', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (104, '659018825@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '隗海宏', 0, '15908332530', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (105, '137403758@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '濮山心', 1, '13607056765', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (106, '747350629@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '凤萍融', 1, '13808575849', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (107, '552103946@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '赏环', 0, '13206577031', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (108, '535905188@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '常菊', 0, '15601803339', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (109, '143568214@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '厉茜舒', 0, '13407361508', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (110, '908544023@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '帅敬林', 0, '13902835333', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (111, '301267202@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '容会', 1, '13800309100', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (112, '810215920@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '萧晓', 1, '13100848203', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (113, '90889035@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '伍波茂', 0, '15704626298', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (114, '515342992@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '东军若', 1, '15503156599', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (115, '93510497@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '戴茂家', 1, '15603820754', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (116, '468184291@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '濮萍玲', 1, '13004140144', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (117, '968772490@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '南飞', 0, '15900915062', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (118, '921339000@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '杨谦', 1, '13904981156', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (119, '905408723@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '梁河', 1, '15205211666', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (120, '207179006@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '莘航德', NULL, '13507930816', 'audience', 2, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (121, '145797407@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '乐豪义', 1, '13900510345', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (122, '941863532@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '南清', 1, '15000464033', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (123, '491189315@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '五媛兰', 1, '13702031581', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (124, '943958624@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '林博东', 0, '15808160470', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (125, '671724702@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '聂凡岚', 1, '13101510259', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (126, '994660637@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '庚康', 0, '15504994405', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (127, '581549010@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '朱祥', 1, '15004056490', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (128, '647957479@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '钮祥', 1, '13607922473', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (129, '445316453@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '尤强', 1, '15807077819', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (130, '555700939@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '冀朗', 1, '15808812431', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (131, '300556782@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '索坚建', 1, '13205440866', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (132, '752899929@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '宫毅冠', 1, '13603627254', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (133, '639664772@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '焦清', 1, '13104900871', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (134, '114706826@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '濮宜姬', 1, '15008186968', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (135, '107325535@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '阴影叶', 0, '13702681032', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (136, '903115764@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '东君', 0, '15804598676', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (137, '488649189@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '晋芳', 0, '13505782498', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (138, '959187006@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '广红荣', 0, '15005478404', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (139, '366199843@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '杜飞友', 0, '15000122432', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (140, '478981595@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '段姣', 1, '15105071923', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (141, '956424212@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '夹岚仪', 0, '13706852877', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (142, '386891486@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '颜彬永', 0, '13004350198', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (143, '530974501@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '詹晨', 1, '15802676902', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (144, '828486741@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '咎兴民', 1, '13903686673', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (145, '26079566@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '官富', 1, '15901961855', 'audience', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (146, '675768081@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '辕岩安', 0, '13206047677', 'singer', 1, 1, NULL, '未来之声', NULL);
INSERT INTO `users` VALUES (147, '946107574@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '狐华影', 1, '15303590262', 'singer', 1, 1, NULL, '光年之外', NULL);
INSERT INTO `users` VALUES (148, '136361791@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '费斌', 0, '15704456980', 'singer', 1, 1, NULL, '泡沫', NULL);
INSERT INTO `users` VALUES (149, '302893583@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '别武', 1, '13000918921', 'singer', 1, 1, NULL, '等你下课', NULL);
INSERT INTO `users` VALUES (150, '958413723@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '阮欢', 1, '15701598398', 'singer', 1, 1, NULL, '孤勇者', NULL);
INSERT INTO `users` VALUES (151, '803218512@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '禹奇', 0, '13005376346', 'singer', 1, 1, NULL, '我是如此相信', NULL);
INSERT INTO `users` VALUES (152, '31282605@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '山健力', 1, '15102095436', 'singer', 1, 1, NULL, '好想抱住你', NULL);
INSERT INTO `users` VALUES (153, '146452493@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '南羽', 1, '15201456778', 'singer', 1, 1, NULL, '篝火旁', NULL);
INSERT INTO `users` VALUES (154, '137113801@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '养瑶', 0, '15906904510', 'singer', 1, 1, NULL, '起风了', NULL);
INSERT INTO `users` VALUES (155, '209853500@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '车娥', 0, '13308876562', 'singer', 1, 1, NULL, '夏日环游', NULL);
INSERT INTO `users` VALUES (156, '590305699@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '呼枫', 0, '13703560662', 'singer', 1, 1, NULL, '我愿意平凡的陪在你身旁', NULL);
INSERT INTO `users` VALUES (157, '241174001@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '张元', 0, '15508835464', 'singer', 1, 1, NULL, '好喜欢你', NULL);
INSERT INTO `users` VALUES (158, '125052084@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '富江', 1, '15502180088', 'singer', 1, 1, NULL, '完美降落', NULL);
INSERT INTO `users` VALUES (159, '268947064@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '尚眉', 1, '13804415641', 'singer', 1, 1, NULL, '星辰大海', NULL);
INSERT INTO `users` VALUES (160, '366525114@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '元若', 0, '15001796324', 'singer', 1, 1, NULL, '白月光与朱砂痣', NULL);
INSERT INTO `users` VALUES (161, '200370787@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '巩彬', 1, '13005426722', 'singer', 1, 1, NULL, '醒不来的梦', NULL);
INSERT INTO `users` VALUES (162, '208065171@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '滕海', 1, '13707983509', 'singer', 1, 1, NULL, '用力活着', NULL);
INSERT INTO `users` VALUES (163, '147369056@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '生艺莲', 1, '15800983792', 'singer', 1, 1, NULL, '迷失幻境', NULL);
INSERT INTO `users` VALUES (164, '902189678@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '李泽梁', 0, '15305526518', 'singer', 1, 1, NULL, 'Love is gone', NULL);
INSERT INTO `users` VALUES (165, '117001352@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '干薇', 1, '13105980445', 'singer', 1, 1, NULL, '执迷不悟', NULL);
INSERT INTO `users` VALUES (166, '450463354@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '马霭宜', 0, '13704044280', 'singer', 1, 1, NULL, '夜空中最亮的星', NULL);
INSERT INTO `users` VALUES (167, '115998472@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '谈黛筠', 0, '15104994800', 'singer', 1, 1, NULL, '和你一样', NULL);
INSERT INTO `users` VALUES (168, '547158831@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '马莺', 0, '15100274873', 'singer', 1, 1, NULL, '孤勇者', NULL);
INSERT INTO `users` VALUES (169, '573145922@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '谯荷卿', 0, '15208532476', 'singer', 1, 1, NULL, '好汉哥', NULL);
INSERT INTO `users` VALUES (170, '228791873@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '饶生鸣', 0, '15504826049', 'singer', 1, 1, NULL, '青花瓷', NULL);
INSERT INTO `users` VALUES (171, '434768272@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '侯娟黛', 1, '13800805119', 'singer', 1, 1, NULL, '自由飞翔', NULL);
INSERT INTO `users` VALUES (172, '964738917@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '燕德', 0, '15204057392', 'singer', 1, 1, NULL, '从前的少年', NULL);
INSERT INTO `users` VALUES (173, '554151786@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '戚克启', 1, '13908306849', 'singer', 1, 1, NULL, '辞九门回忆', NULL);
INSERT INTO `users` VALUES (174, '641973163@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '加全朋', 1, '15205495717', 'singer', 1, 1, NULL, '后来遇见他', NULL);
INSERT INTO `users` VALUES (175, '588052110@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '佴冰融', 1, '13702927901', 'singer', 1, 1, NULL, '世界那么大还是遇见你', NULL);
INSERT INTO `users` VALUES (176, '475625853@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '滕姣', 0, '13404218703', 'singer', 1, 1, NULL, '我的滑板鞋', NULL);
INSERT INTO `users` VALUES (177, '770427242@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '印洁霞', 0, '15805871617', 'singer', 1, 1, NULL, '把孤独当做晚餐', NULL);
INSERT INTO `users` VALUES (178, '214429999@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '郗爽', 0, '13200446560', 'singer', 1, 1, NULL, '一路逆风', NULL);
INSERT INTO `users` VALUES (179, '312636351@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '郁会', 0, '13106326315', 'singer', 1, 1, NULL, '努力生活', NULL);
INSERT INTO `users` VALUES (180, '381514836@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '籍峰', 1, '13108300603', 'singer', 1, 1, NULL, '坠落星空', NULL);
INSERT INTO `users` VALUES (181, '290027448@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '辕莎 ', 1, '15505006516', 'singer', 1, 1, NULL, '黎明前的黑暗', NULL);
INSERT INTO `users` VALUES (182, '438460944@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '阎媛薇', 0, '15800686090', 'singer', 1, 1, NULL, '百花香', NULL);
INSERT INTO `users` VALUES (183, '856292519@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '却林', 0, '13601056031', 'singer', 1, 1, NULL, '璀璨烟火', NULL);
INSERT INTO `users` VALUES (184, '422616726@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '庞松旭', 1, '13103266834', 'singer', 1, 1, NULL, '桥边姑娘', NULL);
INSERT INTO `users` VALUES (185, '69589441@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '卓纯融', 1, '15800204550', 'singer', 1, 1, NULL, '青石巷', NULL);
INSERT INTO `users` VALUES (186, '958822501@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '丁露慧', 0, '13408475013', 'judges', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (187, '120446722@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '谈义', 0, '13304375828', 'judges', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (188, '167074974@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '左艳娥', 1, '13600295720', 'judges', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (189, '340368076@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '云静', 0, '15207376081', 'judges', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (190, '738142971@qq.com', '$2a$10$CNuEgJqAX/ClfcOgzo0AWOm4DWq0Qkvu/jSJmxNVYcn/nn0Y.aHIC', '廉生致', 0, '13400434163', 'judges', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (191, 'root', '$2a$10$skGcJ3AP7GKG1VK252rgVuD4PQ655iNIB4/AC9xM1Ku6DOjuOONbe', '魏天', 0, '13806205656', 'admin', 1, NULL, NULL, NULL, '3358d3e6-6fcf-4bf5-b460-2ac8edfdfdcedefaultImg.jpeg');
INSERT INTO `users` VALUES (192, 'admin', '$2a$10$e1xMeq5OETAgcyhyTuanqezhjImVsLVr0Epjf0HzxpE6ZYAb8iIGa', '张三', 1, '15223638640', 'admin', 1, NULL, NULL, NULL, 'aafeb7fb-0603-47cb-b53d-2b9f1e7ec655logo.png');
INSERT INTO `users` VALUES (196, '2998448518@qq.com', '$2a$10$M4AmOgD/uIrC09/CwKvU7usY6Vv063u/EwYi8XZidEnqxat7Bqicm', '盾山', 1, NULL, 'admin', 1, 1, NULL, NULL, NULL);
INSERT INTO `users` VALUES (200, '123456', '$2a$10$EWT8vP0mgWcJPsUsYlVMU.4prLTnY7SE6IKODNbGKbR7aT6O8Y96K', '三生三世', 0, '13402116315', 'admin', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (201, 'dj', '$2a$10$NRbr.hNIC1jrYbilwjIoCeEkePQJI4jugrsvPX.k3tWxT3EQOwqi.', '邓进', 1, '15223636894', 'audience', 1, 1, NULL, '沙漠风暴', NULL);
INSERT INTO `users` VALUES (207, '1#', '$2a$10$NNkEAuaNmgtI/7Futq/AaugBRNtyj5M2/fixOEGBXLwigHTr0bYbO', 'qqq', 0, 'qq', 'admin', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (208, '666666@qq.com', '$2a$10$t2kHXVI63hm/oHPD4VUtQeZJXl3M02fyIQZtUNWUyFNl1B92d7rbC', '帝皇侠', 1, '13402116315', 'singer', 1, NULL, NULL, NULL, NULL);
INSERT INTO `users` VALUES (209, 'danger', '$2a$10$7UREpm/qTBKBnIsyqTf2puaSR3qGVJcKlRVb0Ese0d5Jru57FiVqe', '炎龙铠甲', 1, '13402116315', 'judges', 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote`  (
  `vote_id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `player_id` int NOT NULL COMMENT '参赛选手外键',
  `matchpk_id` tinyint NOT NULL COMMENT '比赛对战id外键',
  `vote_numbers` int NULL DEFAULT NULL COMMENT '得票数目',
  PRIMARY KEY (`vote_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vote
-- ----------------------------
INSERT INTO `vote` VALUES (27, 159, 1, 2);
INSERT INTO `vote` VALUES (28, 155, 1, 1);
INSERT INTO `vote` VALUES (31, 146, 2, 1);
INSERT INTO `vote` VALUES (32, 97, 2, 1);
INSERT INTO `vote` VALUES (33, 148, 1, 1);
INSERT INTO `vote` VALUES (34, 147, 1, 0);

-- ----------------------------
-- Table structure for vote_audi
-- ----------------------------
DROP TABLE IF EXISTS `vote_audi`;
CREATE TABLE `vote_audi`  (
  `vote_aud_id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `player_id` int NOT NULL COMMENT '选手id',
  `audi_id` int NOT NULL COMMENT '投票人id',
  `matchpk_id` tinyint NOT NULL COMMENT '比赛对战id',
  PRIMARY KEY (`vote_aud_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vote_audi
-- ----------------------------
INSERT INTO `vote_audi` VALUES (47, 159, 104, 1);
INSERT INTO `vote_audi` VALUES (48, 146, 201, 2);
INSERT INTO `vote_audi` VALUES (49, 97, 107, 2);
INSERT INTO `vote_audi` VALUES (50, 155, 192, 1);
INSERT INTO `vote_audi` VALUES (51, 148, 192, 1);
INSERT INTO `vote_audi` VALUES (52, 159, 191, 1);

SET FOREIGN_KEY_CHECKS = 1;
