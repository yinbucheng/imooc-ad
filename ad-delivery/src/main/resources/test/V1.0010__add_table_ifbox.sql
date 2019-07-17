-- 新增代理链路配置(IFBOX、缓存服务器)表 2018.10.16

-- 1. t_trans_proxy_info 代理设备表(代理链路配置 如IFBOX、缓存服务器)
CREATE TABLE if not exists ifaas_basicinfo.t_trans_proxy_info (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `updated` datetime DEFAULT NULL COMMENT '更新时间',
  `capability` int(11) NOT NULL COMMENT '（暂未使用）',
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `port` int(11) DEFAULT NULL COMMENT '登录端口',
  `station_id` bigint(20) DEFAULT NULL COMMENT '位置ID',
  `status` int(11) NOT NULL COMMENT '设备状态 0-在线 1-不在',
  `type` int(11) NOT NULL COMMENT '设备类型 0 Face500(IFBOX)、1 英飞拓数据库、2 海康NVR、3 同步服务器、4 图片缓存服务器（TCP方式）',
  `trans_type` int(11) NOT NULL COMMENT '传输连接类型，设备类型为Face500时：0 引擎主动连Face500(IFBOX)、1 Face500(IFBOX)主动连引擎',
  `uri` varchar(255) DEFAULT NULL COMMENT '设备的IP地址',
  `username` varchar(255) DEFAULT NULL COMMENT '登录用户名',
  `ext1` varchar(255) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(255) DEFAULT NULL COMMENT '扩展字段2',
  `ext3` varchar(255) DEFAULT NULL COMMENT '扩展字段3',
  `is_feature` int(11) DEFAULT NULL COMMENT '1 传特征值、0 不传',
  `promote_state` int(11) DEFAULT NULL COMMENT '设备类型为IFBOX的升级状态 0 正常、-1 待升级 、1 升级成功 、-2 升级失败',
  `snap_count` int(11) DEFAULT NULL COMMENT '抓拍人脸数',
  `camera_count` int(11) DEFAULT NULL COMMENT '摄像机数',
  `netmask` varchar(255) DEFAULT NULL COMMENT '子网掩码 如255.255.255.255',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  `facility_no` varchar(255) DEFAULT NULL COMMENT '设备编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = 't_trans_proxy_info 代理设备表(代理链路配置 如IFBOX、缓存服务器)';

--2. t_trans_proxy_topology  代理设备和IPC的拓扑关系表
CREATE TABLE if not exists ifaas_basicinfo.t_trans_proxy_topology (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_id` bigint(20) NOT NULL COMMENT '摄像机id',
  `source_type` int(11) NOT NULL COMMENT '类型（暂未使用）',
  `source_channel` int(20) DEFAULT NULL COMMENT '设备的通道 当设备为海康NVR时：摄像头在NVR中的通道号',
  `proxy_id` bigint(20) NOT NULL COMMENT '代理设备的ID',
  `ext1` varchar(255) DEFAULT NULL COMMENT '扩展字段1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = 't_trans_proxy_info 代理设备和IPC的拓扑关系表';
