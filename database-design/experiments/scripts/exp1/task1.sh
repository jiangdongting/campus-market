#!/bin/bash
set -e

mysql -uroot -p123123 -h127.0.0.1 <<EOF
create database if not exists MyDb;
show databases;
EOF

echo "✅ MyDb 数据库创建完毕"
