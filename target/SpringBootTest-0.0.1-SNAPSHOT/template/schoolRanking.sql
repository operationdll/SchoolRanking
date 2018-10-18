DROP TABLE IF EXISTS `schools`;

CREATE TABLE IF NOT EXISTS `schools` (
   `id` int NOT NULL AUTO_INCREMENT Primary key comment '主键',
   `ranking` int NOT NULL comment '排名',
   `name` VARCHAR(50) NOT NULL comment '学校名称',
   `country` VARCHAR(50) NOT NULL comment '国家',
   `year` int NOT NULL comment '年份',
    `type` VARCHAR(50) NOT NULL comment '类别'
) character set = utf8;

ALTER TABLE schools COMMENT '学校排名表';

INSERT INTO schools(ranking,name,country,year,type) VALUES(1,'测试学校','中国',2000,'ranking');