--删除序列
drop SEQUENCE if exists SEQ_ARS_HIBERNATE;

-- 删除表：包含表数据和表结构
drop table ARS_REVIEW_OPINION;

COMMIT;