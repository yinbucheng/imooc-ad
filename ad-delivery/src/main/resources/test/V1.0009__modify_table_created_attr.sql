-- 修改字段属性 2018.10.15
call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_area','created', 'datetime NOT NULL COMMENT "创建时间"');

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_camera_info', 'created', 'datetime NOT NULL COMMENT "创建时间"');

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_region', 'created', 'datetime NOT NULL COMMENT "创建时间"');

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_general_tree', 'created', 'datetime NOT NULL COMMENT "创建时间"');

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_dictionary', 'created', 'datetime NOT NULL COMMENT "创建时间"');

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo', 't_dictionary_data', 'created', 'datetime NOT NULL COMMENT "创建时间"');


