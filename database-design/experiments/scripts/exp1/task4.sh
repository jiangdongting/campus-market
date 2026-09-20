#!/bin/bash
set -e
mysql -uroot -p123123 -h127.0.0.1 <<SQL
create database if not exists MyDb;
use MyDb;
drop table if exists t_student;
drop table if exists t_class;
create table t_class (
    id INT PRIMARY KEY,
    name VARCHAR(22)
);
create table t_student (
    id INT PRIMARY KEY,
    name VARCHAR(22),
    classId INT,
    CONSTRAINT fk_stu_class1 FOREIGN KEY(classId) REFERENCES t_class(id)
);
desc t_class;
desc t_student;
SQL
echo "✅ t_class、t_student 外键表创建完成"
