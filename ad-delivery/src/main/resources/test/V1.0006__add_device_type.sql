-- 字典表
CREATE TABLE if not exists ifaas_basicinfo.t_dictionary  (
  `dict_name` varchar(255)  NOT NULL COMMENT '字典名称',
  `dict_value` int(11) NOT NULL COMMENT '字典值，唯一且固定不变',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)
  ENGINE=InnoDB DEFAULT CHARSET=utf8
  COMMENT = '字典表';


-- 字典数据表
CREATE TABLE if not exists ifaas_basicinfo.t_dictionary_data (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '字典数据Id，自增长',
  `dict_value` INT(11) NOT NULL COMMENT 't_dictionary表中的值',
  `dict_data_name` VARCHAR(255) NOT NULL COMMENT '字典数据名字',
  `dict_data_value` VARCHAR(255) NOT NULL COMMENT '字典数据值',
  `fixed`  INT(11) NOT NULL COMMENT '是否固定 0-默认为不固定，1-固定；固定了就不允许修改',
  `parent_id`  BIGINT(20)  DEFAULT NULL COMMENT '父id',
  `created` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8
COMMENT = '字典数据表';


-- 摄像头表新增字段 2018.08.10
CALL ifaas_basicinfo.addFieldIfNotExists('ifaas_basicinfo', 't_camera_info', 'device_type', 'VARCHAR(255)', 'DEFAULT NULL COMMENT "设备类型，取自字典表"');
CALL ifaas_basicinfo.addFieldIfNotExists('ifaas_basicinfo', 't_camera_info', 'creator', 'bigint(20)', 'DEFAULT NULL COMMENT "创建人"');
CALL ifaas_basicinfo.addFieldIfNotExists('ifaas_basicinfo', 't_camera_info', 'creator_name', 'varchar(255)', 'DEFAULT NULL COMMENT "创建人名称"');

-- 添加字典表唯一索引
CALL ifaas_basicinfo.add_unique_index ( 'ifaas_basicinfo', 't_dictionary', 'uk_dict_value', 'dict_value' );


-- 向上递归 查询区域存储过程
DELIMITER $$
DROP FUNCTION IF EXISTS ifaas_basicinfo.queryParentArea; $$

CREATE FUNCTION queryParentArea(areaId INT)
  RETURNS VARCHAR(4000)
  BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp='$';
    SET sTempChd = CAST(areaId AS CHAR);
    SET sTemp = CONCAT(sTemp,',',sTempChd);

    SELECT parent_id INTO sTempChd FROM t_area WHERE id = sTempChd;
    WHILE sTempChd <> 0 DO
      SET sTemp = CONCAT(sTemp,',',sTempChd);
      SELECT parent_id INTO sTempChd FROM t_area WHERE id = sTempChd;
    END WHILE;
    RETURN sTemp;
  END
$$
DELIMITER ;


-- 向下递归 查询区域存储过程，慎用
DELIMITER $$
DROP FUNCTION IF EXISTS ifaas_basicinfo.queryChildrenArea $$

CREATE FUNCTION queryChildrenArea(areaId INT)
  RETURNS VARCHAR(4000)
  BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempChd VARCHAR(4000);

    SET sTemp='$';
    SET sTempChd = CAST(areaId AS CHAR);

    WHILE sTempChd IS NOT NULL DO
      SET sTemp= CONCAT(sTemp,',',sTempChd);
      SELECT GROUP_CONCAT(id) INTO sTempChd FROM t_area WHERE FIND_IN_SET(parent_id,sTempChd)>0;
    END WHILE;
    RETURN sTemp;
  END
$$
DELIMITER ;

