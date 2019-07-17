-- 新增t_area 区域表
CREATE TABLE if not exists ifaas_basicinfo.t_area (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL COMMENT '区域名称（社区/小区/单元/房间名称）',
`parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父节点ID，当为根节点时该值为0',
`type` int(11) DEFAULT NULL COMMENT '类型',
`longitude`  varchar(255) DEFAULT NULL COMMENT '经度',
`latitude`  varchar(255)  DEFAULT NULL COMMENT '纬度',
`sort_num` bigint(20)  DEFAULT 0 COMMENT '排序',
`created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
`updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) 
)
ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = 't_area 区域表';


-- 新增t_region 国标地区表
CREATE TABLE if not exists ifaas_basicinfo.t_region(
`id` bigint(20) NOT NULL COMMENT '地区id',
`name` varchar(255) DEFAULT NULL COMMENT '地区名称',
`parent_id` bigint(20) DEFAULT NULL COMMENT '上级id',
`region_type` int(11) DEFAULT NULL COMMENT '1省份；2城市；3区县；4街道',
`sort_num` bigint(20) DEFAULT 0 COMMENT '排序',
`created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
`updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) 
)
ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = 't_region 国标地区表';

-- 新增摄像头信息表
CREATE TABLE if not exists ifaas_basicinfo.t_camera_info(
`id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
`ip` varchar(255)  DEFAULT NULL COMMENT '存放摄像头IP',
`port` int(11) NOT NULL DEFAULT 0 COMMENT '存放摄像头端口',
`login_user` varchar(255)  DEFAULT NULL COMMENT '登录摄像机的用户名',
`password` varchar(255)  DEFAULT NULL COMMENT '登录摄像机的密码',
`area_id` bigint(20)  NOT NULL DEFAULT 0 COMMENT '摄像所属区域ID',
`capture_type` int(11) NOT NULL DEFAULT 0 COMMENT '抓拍类型',
`manufacturer` varchar(255)  DEFAULT NULL COMMENT '摄像机所属的厂商',
`camera_code` varchar(255)  DEFAULT NULL COMMENT '摄像机的camear code',
`rtsp` varchar(255)  DEFAULT NULL COMMENT 'rtsp流地址',
`config` varchar(255)  DEFAULT NULL COMMENT '配置参数',
`created` datetime NOT NULL COMMENT '创建时间',
`updated` datetime NOT NULL COMMENT '更新时间',
`geo_string` varchar(255) DEFAULT NULL COMMENT '经纬度',
`geometry`  geometry DEFAULT NULL,
`is_enable` int(11) NOT NULL DEFAULT 1 COMMENT '是否启用：1-启用 0-停用',
`status` int(11) NOT NULL DEFAULT 1 COMMENT '摄像头状态：1-有效 0-无效',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = '摄像头信息表';


-- 新增通用树表
CREATE TABLE if not exists ifaas_basicinfo.t_general_tree(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`name`  varchar(255) NOT NULL COMMENT '区域名称（社区/小区/单元/房间名称）',
`parent_id`  bigint(20) NOT NULL DEFAULT 0 COMMENT '父节点ID，当为根节点时该值为0',
`type`  int(11) NOT NULL DEFAULT 0 COMMENT '树类型，具体值根据实际情况约定',
`long_field1`  bigint(20) DEFAULT NULL COMMENT '保留long类型字段1',
`long_field2`  bigint(20)  DEFAULT NULL COMMENT '保留long类型字段2',
`string_field1`  varchar(1024)  DEFAULT NULL COMMENT '保留string类型字段1',
`string_field2`  varchar(1024)  DEFAULT NULL COMMENT '保留string类型字段2',
`sort_num` bigint(20)  DEFAULT 0 COMMENT '排序',
`created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
`updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`) 
)
ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = 't_area 通用树表';