-- 审查意见表
create table ARS_REVIEW_OPINION
(
	ID numeric(19) not null
		primary key,
	CREATETIME TIMESTAMP(6),
	IMPORTANTTAG CHAR(1),
	QUESTIONDESC VARCHAR(4000),
	QUESTIONPOSITION VARCHAR(4000),
	REVIEWPOINTID numeric(19),
	REVIEWTASKID numeric(19)
);




