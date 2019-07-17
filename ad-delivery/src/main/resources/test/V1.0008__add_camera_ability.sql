-- 设备表新增字段 2018.10.12
CALL ifaas_basicinfo.addFieldIfNotExists('ifaas_basicinfo', 't_camera_info', 'ability', 'text', 'DEFAULT NULL COMMENT "能力"');
