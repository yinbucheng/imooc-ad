-- 区域表新增字段 2018.10.11
CALL ifaas_basicinfo.addFieldIfNotExists('ifaas_basicinfo', 't_area', 'area_status', 'int(11)', 'NOT NULL DEFAULT 1 COMMENT "区域状态：1-有效 0-无效"');
