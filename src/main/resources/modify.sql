ALTER TABLE `fd_hospital_dept`
  ADD COLUMN `icon`  varchar(255) NULL COMMENT '小图标';
ALTER TABLE `fd_hospital_dept`
  ADD COLUMN `pid`  int(10) NULL COMMENT '父id';

-- ALTER TABLE `fd_doctor_opinion`
--   ADD COLUMN `file_create_time`  bigint(13) NULL COMMENT '附件创建时间';
-- ALTER TABLE `fd_doctor_opinion`
--   ADD COLUMN `file_to_imgs`  text NULL COMMENT '附件转图片地址集合';

ALTER TABLE `fd_member`
  ADD COLUMN `hx_password`  varchar(36) NULL COMMENT '环信登录密码';
ALTER TABLE `fd_member`
  ADD COLUMN `hx_status`  tinyint(1) NULL DEFAULT 0 COMMENT '环信是否注册，1：已注册；0：未注册';
ALTER TABLE `fd_member`
  ADD COLUMN `head_image`  varchar(256) NULL COMMENT '用户头像（为空时再取pic）' AFTER `pic`;
ALTER TABLE `fd_member`
  DROP INDEX `username` ,
  ADD UNIQUE INDEX `username` (`username`, `is_admin`) USING BTREE ;

ALTER TABLE `fd_member_appointment`
  ADD COLUMN `sourse`  varchar(6) NULL DEFAULT 'AS01' COMMENT '来源{AS}';
ALTER TABLE `fd_member_appointment`
  ADD COLUMN `appointment_no`  varchar(64) NULL COMMENT '预约订单号';
ALTER TABLE `fd_member_appointment`
  ADD COLUMN `amount`  bigint NULL COMMENT '订单金额';
ALTER TABLE `fd_member_appointment`
  ADD COLUMN `appoint_address`  varchar(200) NULL COMMENT '预约门诊地址';

ALTER TABLE `fd_member_doctor_sh`
  ADD COLUMN `hospital_name`  varchar(100) NULL COMMENT '医院名称',
  ADD COLUMN `department_name`  varchar(100) NULL COMMENT '科室名称',
  ADD COLUMN `audit_type`  int(1) NOT NULL DEFAULT 2 COMMENT '审核类型1注册审核 2编辑审核',
  ADD COLUMN `email`  varchar(50) NULL COMMENT '邮箱';

ALTER TABLE `fd_member_doctor_sh`
  DROP PRIMARY KEY,
  ADD PRIMARY KEY (`id`, `audit_type`);

ALTER TABLE `fd_member_doctor`
  ADD COLUMN `accept_appointment`  tinyint NULL DEFAULT 1 COMMENT '是否接受预约1是 0否',
  ADD COLUMN `accept_consultation`  tinyint NULL DEFAULT 1 COMMENT '是否接受咨询1是 0否',
  ADD COLUMN `department_name`  varchar(100) NULL COMMENT '科室名称';
ALTER TABLE `fd_member_doctor`
  ADD COLUMN `is_best`  tinyint NULL DEFAULT 0 COMMENT '是否著名专家1是 0否' AFTER `sort`;


ALTER TABLE `fd_doctor_group`
  ADD COLUMN `is_best`  tinyint NULL DEFAULT 0 COMMENT '是否优秀团队1是 0否',
  ADD COLUMN `seq`  int NULL DEFAULT 0 COMMENT '排序（越小越靠前）';

ALTER TABLE fd_customer MODIFY `real_name` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE fd_member_doctor_sh MODIFY `speciality` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '擅长';
ALTER TABLE fd_member_doctor_sh MODIFY `introduce` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '自我介绍';
ALTER TABLE fd_member_doctor MODIFY `speciality` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '擅长';
ALTER TABLE fd_member_doctor MODIFY `introduce` VARCHAR(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '自我介绍';
ALTER TABLE fd_member_appointment MODIFY `appoint_message` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '预约留言';
ALTER TABLE fd_member_appointment MODIFY `refuse_reason` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '拒绝理由';


CREATE TABLE `fd_feedback` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `contact_way` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '正文',
  `create_by` int(10) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_by` int(10) DEFAULT NULL COMMENT '修改人',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 1 是 0 否',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='意见反馈信息表';

CREATE TABLE `fd_member_consultation_order` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) DEFAULT NULL COMMENT '病人id',
  `doctor_id` int(10) DEFAULT NULL COMMENT '医生id',
	`order_no` varchar(50) DEFAULT NULL COMMENT '订单号',
	`amount` bigint(20) DEFAULT NULL COMMENT '订单金额',
  `create_by` int(10) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 1 是 0 否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='咨询订单表';

CREATE TABLE `fd_member_consultation_expire` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) DEFAULT NULL COMMENT '病人id',
  `doctor_id` int(10) DEFAULT NULL COMMENT '医生id',
  `create_by` int(10) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 1 是 0 否',
  `expire_date` datetime DEFAULT NULL COMMENT '到期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='咨询有效期表';

DROP TABLE IF EXISTS `fd_balance_log`;
CREATE TABLE `fd_balance_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(11) DEFAULT '0',
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `balance_no` varchar(64) DEFAULT NULL COMMENT '余额流水号',
  `ref_id` varchar(64) DEFAULT NULL COMMENT '业务id',
  `ref_type` varchar(10) NOT NULL COMMENT '业务类型',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  `amount` float(10,2) NOT NULL COMMENT '金额',
  `amount_log` float(10,2) NOT NULL COMMENT '钱包期末余额',
  `note` text COMMENT '备注',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否删除1:是 0:否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额流水表';

CREATE TABLE `fd_member_appointment_comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctor_id` int(10) DEFAULT NULL COMMENT '医生id',
  `appointment_id` int(10) DEFAULT NULL COMMENT '预约订单id',
  `user_id` int(10) DEFAULT NULL COMMENT '评论人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '评论时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 1 是 0 否',
  `effect` float DEFAULT NULL COMMENT '治疗效果',
  `attitude` float DEFAULT NULL COMMENT '服务态度',
  `disease` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '看什么病',
  `objective` varchar(6) DEFAULT NULL COMMENT '看病目的{OT}',
  `objective_other` varchar(100) DEFAULT NULL COMMENT '看病目的_其他',
  `therapy` varchar(6) DEFAULT NULL COMMENT '治疗方式{TW}',
  `therapy_other` varchar(100) DEFAULT NULL COMMENT '治疗方式_其他',
  `comment` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评价内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预约评价表';

CREATE TABLE `fd_member_consultation_friend` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) DEFAULT NULL COMMENT '病人id',
  `doctor_id` int(10) DEFAULT NULL COMMENT '医生id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 0 否 1 病人删除 2医生删除 3 互删',
  `last_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '最后聊天记录',
  `last_time` datetime DEFAULT NULL COMMENT '最后消息时间',
  `sender_type` int(1) DEFAULT NULL COMMENT '发送方类型1患者 2医生',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='咨询好友表';

CREATE TABLE `fd_member_consultation_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`mtype` varchar(18) DEFAULT NULL COMMENT '消息类型：text、image、audio',
  `from_user_id` int(10) DEFAULT NULL COMMENT '发送人',
  `to_user_id` int(10) DEFAULT NULL COMMENT '接收人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` varchar(2) DEFAULT '0' COMMENT '是否删除 0 否 1 是',
	`content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '聊天记录',
	`sender_type` int(1) DEFAULT NULL COMMENT '发送方类型1患者 2医生',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='咨询聊天记录表';

CREATE TABLE `fd_doctor_close_time` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `doctor_id` int(10) DEFAULT NULL COMMENT '医生id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  `close_date` date DEFAULT NULL COMMENT '停诊日期',
	`time` int(2) DEFAULT NULL COMMENT '0不限 1 上午 2 下午 3 夜班',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='停诊发布记录表';

CREATE TABLE `fd_account` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `bank_account` varchar(18) DEFAULT NULL COMMENT '开户名',
  `bank_phone` varchar(18) DEFAULT NULL COMMENT '开户预留手机号',
  `bank_id_no` varchar(18) DEFAULT NULL COMMENT '开户身份证号',
  `bank_code` varchar(10) DEFAULT NULL COMMENT '银行编号',
  `bank_name` varchar(36) DEFAULT NULL COMMENT '开户行名称',
  `bank_card` varchar(36) DEFAULT NULL COMMENT '银行卡号',
  `alipay` varchar(100) DEFAULT NULL COMMENT '支付宝账号',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 PACK_KEYS=1 ROW_FORMAT=DYNAMIC COMMENT='用户账号信息表';

CREATE TABLE `fd_cash_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) DEFAULT '0' COMMENT '是否删除1:是 0:否',
  `create_time` bigint(20) DEFAULT NULL COMMENT '提现时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `handle_status` varchar(4) DEFAULT 'HS01' COMMENT '处理状态{HS}',
  `handle_login_id` varchar(36) DEFAULT NULL COMMENT '处理人',
  `handle_remark` varchar(512) DEFAULT NULL COMMENT '处理结果',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `user_id` int(11) NOT NULL COMMENT '提现用户id',
  `amount` int(11) NOT NULL COMMENT '提现金额',
  `ref_type` varchar(10) NOT NULL COMMENT '业务类型',
  `content` varchar(512) DEFAULT NULL COMMENT '申请备注',
  `bank_account` varchar(18) DEFAULT NULL COMMENT '开户名',
  `bank_phone` varchar(18) DEFAULT NULL COMMENT '开户预留手机号',
  `bank_id_no` varchar(18) DEFAULT NULL COMMENT '开户身份证号',
  `bank_name` varchar(36) DEFAULT NULL COMMENT '开户行名称',
  `bank_card` varchar(36) DEFAULT NULL COMMENT '银行卡号',
  `alipay` varchar(100) DEFAULT NULL COMMENT '支付宝账号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='提现申请记录表';

DROP TABLE IF EXISTS `fd_message`;
CREATE TABLE `fd_message` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '正文',
  `create_by` int(10) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_by` int(10) DEFAULT NULL COMMENT '修改人',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `isdeleted` tinyint(1) DEFAULT '0' COMMENT '是否删除1:是 0:否',
  `status` varchar(4) DEFAULT 'ST01' COMMENT '业务状态{ST}',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `btype` varchar(6) DEFAULT NULL COMMENT '消息业务类型',
  `mtype` varchar(6) DEFAULT NULL COMMENT '消息类型{MT}',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '是否已读1:是 0:否',
  `url` varchar(100) DEFAULT NULL COMMENT '正文链接',
  `start_date` date DEFAULT NULL COMMENT '开始时间',
  `end_date` date DEFAULT NULL COMMENT '结束时间',
  `consumer_type` int(1) DEFAULT '0' COMMENT '消费方类型0不限 1患者方 2医生方',
  `is_pushed` tinyint(4) DEFAULT '0' COMMENT '是否已推送0未推送 1已推送',
  `push_content` varchar(300) DEFAULT NULL COMMENT '推送正文',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='消息表';

CREATE TABLE `fd_message_read_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否删除1:是 0:否',
	`user_id` int(10) DEFAULT NULL COMMENT '用户id',
	`message_id` int(10) DEFAULT NULL COMMENT '消息Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='消息查看记录表';

CREATE TABLE `fd_patient` (
  `user_id` int(20) NOT NULL COMMENT '用户ID',
  `create_time` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否删除1:是 0:否',
	`real_name` varchar(50) DEFAULT NULL COMMENT '姓名',
	`sex` int(1) DEFAULT '1' COMMENT '1男2女 0未知',
	`birthday` bigint(13) DEFAULT NULL COMMENT '出生日期',
	`relation` varchar(6) DEFAULT NULL COMMENT '与患者关系{PR}',
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='患者信息表';

DROP TABLE IF EXISTS `fd_payment_log`;
CREATE TABLE `fd_payment_log` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT 'id',
  `payment_id` varchar(50) DEFAULT NULL COMMENT '支付id',
  `type` varchar(16) DEFAULT NULL COMMENT '支付类型（微信/支付宝）',
  `status` varchar(16) DEFAULT NULL COMMENT '支付状态（0 待支付 1 支付中 2 完成支付 3 退款 9 取消）',
  `total_fee` bigint(11) DEFAULT NULL COMMENT '总金额',
  `ref_id` varchar(64) DEFAULT NULL COMMENT '第三方支付订单号',
  `refund_no` varchar(64) DEFAULT NULL COMMENT '退款单号',
  `ref_refund_no` varchar(64) DEFAULT NULL COMMENT '第三方退款单号',
  `refund_fee` bigint(20) DEFAULT NULL COMMENT '退款金额',
  `refund_date` datetime DEFAULT NULL COMMENT '退款日期',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易日志表';


