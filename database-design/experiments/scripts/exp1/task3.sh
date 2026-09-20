#!/bin/bash
set -e
mysql -uroot -p123123 -h127.0.0.1 <<SQL
use MyDb;
drop table if exists t_user1;
drop table if exists t_user2;
create table t_user1 (
    userId INT PRIMARY KEY,
    name VARCHAR(32),
    password VARCHAR(11),
    phone VARCHAR(11),
    email VARCHAR(32)
);
create table t_user2 (
    name VARCHAR(32),
    phone VARCHAR(11),
    email VARCHAR(32),
    PRIMARY KEY(name,phone)
);
desc t_user1;
desc t_user2;
SQL
echo "✅ t_user1、t_user2 创建完成"
