-- 创建添加唯一索引的存储过程
DROP PROCEDURE IF EXISTS ifaas_basicinfo.add_unique_index;
DELIMITER $$

CREATE PROCEDURE `add_unique_index`(IN dbname varchar(100), IN tablename varchar(100), IN indexname varchar(100), IN columnname varchar(100))
BEGIN

	set @exist := (SELECT count(*) FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = dbname AND TABLE_NAME = tablename AND INDEX_NAME = indexname);
	set @sqlstmt := CONCAT('ALTER TABLE ', dbname, '.', tablename, ' ADD UNIQUE INDEX ', indexname, ' (', columnname, ')');
	
	if @exist > 0 then
	    set @dropstmt := CONCAT('ALTER TABLE ', dbname, '.', tablename, ' DROP INDEX ', indexname);
	    prepare dropstmt from @dropstmt;
	    execute dropstmt;
    end if;
	
	prepare stmt from @sqlstmt;
	execute stmt;
	
END $$
DELIMITER ;