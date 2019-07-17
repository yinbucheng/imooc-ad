-- 更新摄像头类型默认值

call ifaas_basicinfo.updateFieldAttribute('ifaas_basicinfo','t_camera_info','device_type','varchar(255) DEFAULT "1" COMMENT "设备类型 1-人脸抓拍摄像机 2-人体抓拍摄像机"');


call ifaas_basicinfo.updateFieldValues('ifaas_basicinfo','t_camera_info','device_type','1','where device_type is null');