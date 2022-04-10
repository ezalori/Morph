create table morph.extract_table (
  id int primary key auto_increment
  ,source_instance int not null
  ,source_database varchar(255) not null
  ,source_table varchar(255) not null
  ,target_instance int not null
  ,target_database varchar(255) not null
  ,target_table varchar(255) not null
  ,column_list text not null
  ,created_at datetime not null
  ,updated_at timestamp not null default current_timestamp on update current_timestamp
);
