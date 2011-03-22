drop table if exists periodlog;

create table periodlog (
        id integer primary key autoincrement,
        ts_date varchar(10) not null,
        is_period integer,
        took_pill integer,
        strength integer,
        notes varchar(250)        
);

create unique index idx_periodlog_ts_date on periodlog ( ts_date );
