#!/bin/bash
set -e
mysql -uroot -p123123 -h127.0.0.1 <<SQL
use MyDb;
drop table if exists t_user;
create table t_user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(32) NOT NULL UNIQUE,
    sex VARCHAR(4) DEFAULT '男'
);
desc t_user;
SQL
echo "✅ t_user约束表创建完成"
