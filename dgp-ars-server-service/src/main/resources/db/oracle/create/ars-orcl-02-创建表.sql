-- 建表语句

-- 审查意见表
create table ARS_REVIEW_OPINION
(
	ID NUMBER(19) not null
		primary key,
	CREATETIME TIMESTAMP(6),
	IMPORTANTTAG CHAR(1 char),
	QUESTIONDESC VARCHAR2(4000 char),
	QUESTIONPOSITION VARCHAR2(4000 char),
	REVIEWPOINTID NUMBER(19),
	REVIEWTASKID NUMBER(19)
);

commit;
