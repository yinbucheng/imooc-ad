-- 添加索引
DROP PROCEDURE IF EXISTS ifaas_basicinfo.add_index;
DELIMITER $$
CREATE  PROCEDURE `add_index`(IN dbname varchar(100), IN tablename varchar(100), IN indexname varchar(100), IN columnname varchar(100))
BEGIN

	set @exist := (SELECT count(*) FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = dbname AND TABLE_NAME = tablename AND INDEX_NAME = indexname);
	set @sqlstmt := CONCAT('ALTER TABLE ', dbname, '.', tablename, ' ADD INDEX ', indexname, ' (', columnname, ')');
	
	if @exist > 0 then
	    set @dropstmt := CONCAT('ALTER TABLE ', dbname, '.', tablename, ' DROP INDEX ', indexname);
	    prepare dropstmt from @dropstmt;
	    execute dropstmt;
    end if;
	
	prepare stmt from @sqlstmt;
	execute stmt;
	
END $$
DELIMITER ;


-- 添加字段
DROP PROCEDURE IF EXISTS ifaas_basicinfo.addFieldIfNotExists; 
DELIMITER $$
CREATE  PROCEDURE `addFieldIfNotExists`(
   IN database_name_IN VARCHAR(100)
    ,IN table_name_IN VARCHAR(100)
    , IN field_name_IN VARCHAR(100)
    , IN field_definition_IN VARCHAR(100)
    , IN field_Default_IN VARCHAR(100) charset 'utf8'
)
BEGIN

    SET @isFieldThere = isFieldExisting(database_name_IN,table_name_IN, field_name_IN);
    IF (@isFieldThere = 0) THEN

        SET @ddl = CONCAT('ALTER TABLE ', database_name_IN);
		SET @ddl = CONCAT(@ddl,'.',table_name_IN);
        SET @ddl = CONCAT(@ddl, ' ', 'ADD COLUMN') ;
        SET @ddl = CONCAT(@ddl, ' ', field_name_IN);
        SET @ddl = CONCAT(@ddl, ' ', field_definition_IN);
        SET @ddl = CONCAT(@ddl, ' ', field_Default_IN);


        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

    END IF;

END $$
DELIMITER ;


-- 添加
DROP PROCEDURE IF EXISTS ifaas_basicinfo.addSequence; 
DELIMITER $$
CREATE  PROCEDURE `addSequence`(In tableName VARCHAR(255))
begin
     
    set @count = (select count(1) from intellif_face.t_tables);
      set @start = 1;
         lab: while @start <= @count/2 do
        set @faceName = concat(tableName,@start); 
         set @face_table = concat('intellif_face.',@faceName);
       SET @isFieldThere = isFieldExisting('intellif_face',@faceName, 'sequence');
       IF (@isFieldThere = 0) THEN    
        
        #判断不存在sequence才添加

    set @csql = concat("alter table ",@face_table,
   " change id id BIGINT(64);");
   set @csql1 = concat("alter table ",@face_table,
   " drop primary key;");
   set @csql2 = concat("alter table ",@face_table,
    " AUTO_INCREMENT=1;");
   set @csql3 = concat("alter table ",@face_table,
   " add column sequence BIGINT(64) not null auto_increment primary key;");



PREPARE create_stmt from @csql;  
  EXECUTE create_stmt;  
DEALLOCATE PREPARE create_stmt;
PREPARE create_stmt1 from @csql1;  
  EXECUTE create_stmt1;  
DEALLOCATE PREPARE create_stmt1;
  PREPARE create_stmt2 from @csql2;  
  EXECUTE create_stmt2;  
DEALLOCATE PREPARE create_stmt2;
 PREPARE create_stmt3 from @csql3;  
  EXECUTE create_stmt3;  
DEALLOCATE PREPARE create_stmt3;
        END IF;
 set @start = @start +1;
          end while lab;
     
 
END $$
DELIMITER ;


-- 删除索引
DROP PROCEDURE IF EXISTS ifaas_basicinfo.delete_index; 
DELIMITER $$
CREATE  PROCEDURE `delete_index`(IN dbname varchar(100), IN tablename varchar(100), IN indexname varchar(100))
BEGIN
	
	set @exist := (SELECT count(*) FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_SCHEMA = dbname AND TABLE_NAME = tablename AND INDEX_NAME = indexname);
	set @sqlstmt := if(@exist = 0, 'SELECT ''INFO: Index not exists.''', CONCAT('ALTER TABLE ', dbname, '.', tablename, ' DROP INDEX ', indexname));
	prepare stmt from @sqlstmt;
	execute stmt;
	
END $$
DELIMITER ;

-- 删除字段
DROP PROCEDURE IF EXISTS ifaas_basicinfo.deleteFieldIfExists; 
DELIMITER $$
CREATE  PROCEDURE `deleteFieldIfExists`(
    IN database_name_IN VARCHAR(100)
    ,IN table_name_IN VARCHAR(100)
    , IN field_name_IN VARCHAR(100)
)
BEGIN

    -- http://javajon.blogspot.com/2012/10/mysql-alter-table-add-column-if-not.html

    SET @isFieldThere = isFieldExisting(database_name_IN,table_name_IN, field_name_IN);
    IF (@isFieldThere != 0) THEN

        SET @ddl = CONCAT('ALTER TABLE ', table_name_IN);
        SET @ddl = CONCAT(@ddl, ' ', 'DROP COLUMN') ;
        SET @ddl = CONCAT(@ddl, ' ', field_name_IN);

        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

    END IF;
	
END $$
DELIMITER ;

-- 重命名表
DROP PROCEDURE IF EXISTS ifaas_basicinfo.rename_table; 
DELIMITER $$
CREATE  PROCEDURE `rename_table`(IN talbe_name varchar(100), IN new_name varchar(100))
BEGIN

	-- rename a table safely
	IF NOT EXISTS( (SELECT * FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE()
	        AND TABLE_NAME= talbe_name) ) THEN
	        
	        SET @ddl = CONCAT('RENAME TABLE ', talbe_name);
	        SET @ddl = CONCAT(@ddl, ' ', 'TO') ;
	        SET @ddl = CONCAT(@ddl, ' ',  new_name) ;
	       
	     	PREPARE stmt FROM @ddl;
	        EXECUTE stmt;
	        DEALLOCATE PREPARE stmt;
	END IF;

END $$
DELIMITER ;


-- 更新字段
DROP PROCEDURE IF EXISTS ifaas_basicinfo.updateFieldAttribute; 
DELIMITER $$
CREATE  PROCEDURE `updateFieldAttribute`(IN dbname varchar(100), IN tablename varchar(100), IN fieldname varchar(100),IN attparam varchar(100) charset 'utf8')
BEGIN
	
	 SET @isFieldThere = isFieldExisting(dbname,tablename, fieldname);
    IF (@isFieldThere != 0) THEN

        SET @ddl = CONCAT('ALTER TABLE ', dbname);
		    SET @ddl = CONCAT(@ddl,'.',tablename);
        SET @ddl = CONCAT(@ddl, ' ', 'MODIFY') ;
        SET @ddl = CONCAT(@ddl, ' ', fieldname);
        SET @ddl = CONCAT(@ddl, ' ', attparam);

        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

    END IF;
	
END $$
DELIMITER ;

-- 更新字段值
DROP PROCEDURE IF EXISTS ifaas_basicinfo.updateFieldValues; 
DELIMITER $$
CREATE PROCEDURE `updateFieldValues`(IN dbname varchar(100), IN tablename varchar(100), IN fieldname varchar(100),IN fieldvalue varchar(100) charset 'utf8',IN whereparam varchar(100) charset 'utf8')
BEGIN
	
	 SET @isFieldThere = isFieldExisting(dbname,tablename, fieldname);
    IF (@isFieldThere != 0) THEN

        SET @ddl = CONCAT('UPDATE ', dbname);
		    SET @ddl = CONCAT(@ddl,'.',tablename);
        SET @ddl = CONCAT(@ddl, ' ', 'SET') ;
        SET @ddl = CONCAT(@ddl, ' ', fieldname);
        SET @ddl = CONCAT(@ddl, ' ', '= ');
        SET @ddl = CONCAT(@ddl, ' ', fieldvalue);
        SET @ddl = CONCAT(@ddl, ' ', whereparam);

        PREPARE stmt FROM @ddl;
        EXECUTE stmt;
        DEALLOCATE PREPARE stmt;

    END IF;
	
END $$
DELIMITER ;

-- 判断字段是否存在
DELIMITER $$

DROP FUNCTION IF EXISTS isFieldExisting $$

CREATE FUNCTION isFieldExisting (table_SCHEMA_IN VARCHAR(100),table_name_IN VARCHAR(100),field_name_IN VARCHAR(100)) 
RETURNS INT
RETURN (
    SELECT COUNT(COLUMN_NAME) 
    FROM INFORMATION_SCHEMA.columns 
    WHERE TABLE_SCHEMA = table_SCHEMA_IN 
    AND TABLE_NAME = table_name_IN 
    AND COLUMN_NAME = field_name_IN
)$$

DELIMITER ;
