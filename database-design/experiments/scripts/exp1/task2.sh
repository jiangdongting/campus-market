#!/bin/bash
set -e

mysql -uroot -p123123 -h127.0.0.1 <<SQL
create database if not exists TestDb;
use TestDb;
create table if not exists t_emp (
    id INT,
    name VARCHAR(32),
    deptId INT,
    salary FLOAT
);
desc t_emp;
SQL

echo "✅ TestDb库与t_emp表创建完成"
