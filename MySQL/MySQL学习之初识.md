---
title: MySQL 学习之初识
date: 2017-07-16 20:42:33
tags: MySQL
categories: MySQL
---

MySQL 的基本知识

<!-- more -->
# MySQL 语句的规范

 + 关键字与函数名称全部大写
 + 数据库名称、表名称、字段名称全部小写
 + SQL 语句必须以分号结尾

**PS** MySQL 对大小写不敏感

# MySQL 参数

| 参数                    | 描述         |
| --------------------- | ---------- |
| -D, --database=name   | 打开指定数据库    |
| --delimiter = name    | 指定分隔符      |
| -h, --host=name       | 服务器名称      |
| -p, --password[=name] | 密码   (p小写) |
| -P, --port=#          | 端口号 (P大写)  |
| --prompt=name         | 设置提示符      |
| -u, --user=name       | 用户名        |
| -V, --version         | 输出版本信息并且退出 |

e.g. 登录 MySQL
```mysql
mysql -uroot -p[密码] [-P3306] [-h127.0.0.1]
```
P.S. 
 + 如果默认端口号为 3306，没有修改过的话，那么 `-P3306` 可以不写
 + 如果连接的是本地服务器，那么 `[-h127.0.0.1]` 可以不写 (127.0.0.1为本地回环地址)
 + 若不想密码被看到，那么 `-p` 后的密码可以不加，回车后会再次让你输入

退出 MySQL 时有以下三个命令
```mysql
mysql> \q;
mysql> exit;
mysql> quit;
```

# MySQL 提示符

| 参数   | 描述    |
| ---- | ----- |
| \D   | 完整日期  |
| \d   | 当前数据库 |
| \h   | 服务器名称 |
| \u   | 当前用户  |

e.g. 修改提示符为 "当前用户@服务器 数据库名>"
```sql
mysql> PROMPT \u@\h \d>
PROMPT set to '\u@\h \d>'
root@localhost (none)>
```

# MySQL 常用命令

 + 显示当前版本号：`SELECT VERSION();`
 + 显示当前日期时间：`SELECT NOW();`
 + 显示当前用户：`SELECT USER();`

```mysql
mysql> SELECT VERSION();
+------------+
| VERSION()  |
+------------+
| 5.7.18-log |
+------------+
1 row in set (0.00 sec)

mysql> SELECT NOW();
+---------------------+
| NOW()               |
+---------------------+
| 2017-07-12 21:53:25 |
+---------------------+
1 row in set (0.00 sec)

mysql> SELECT USER();
+----------------+
| USER()         |
+----------------+
| root@localhost |
+----------------+
1 row in set (0.00 sec)
```

# 操作数据库

## 创建数据库

```mysql
CREATE {DATABASE | SCHEMA} [IF NOT EXISTS] db_name [DEFAULT] CHARACTER SET [=] chartset_name;
```

e.g. 创建一个学生表

```myql
mysql> CREATE DATABASE IF NOT EXISTS student CHARACTER SET utf8;
Query OK, 1 row affected (0.00 sec)
```

## 查看当前服务器下的数据表列表

```mysql
SHOW {DATABASES | SCHEMAS} [LIKE 'pattern' | WHERE expr];
```

e.g. 查看 MySQL 自带的数据库

```mysql
mysql> SHOW DATABASES;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sakila             |
| sys                |
| world              |
+--------------------+
6 rows in set (0.15 sec)
```

## 显示已创建的数据库

```mysql
SHOW CREATE DATABASE db_name;
```

e.g. 显示已创建的学生表

```mysql
mysql> SHOW CREATE DATABASE student;
+----------+------------------------------------------------------------------+
| Database | Create Database                                                  |
+----------+------------------------------------------------------------------+
| student  | CREATE DATABASE `student` /*!40100 DEFAULT CHARACTER SET utf8 */ |
+----------+------------------------------------------------------------------+
1 row in set (0.00 sec)
```

## 修改数据库

```mysql
ALTER {DATABASE | SCHEMA} [db_name] [DEFAULT] CHARACTER SET [=] charset_name;
```

e.g. 修改已创建 student 数据库的字符集为 gbk

```mysql
mysql> ALTER DATABASE student CHARACTER SET = gbk;
Query OK, 1 row affected (0.00 sec)
```

## 删除数据库

```mysql
DROP {DATABASE | SCHEMA} [IF EXISTS] db_name;
```

e.g. 删除已创建的 student 数据库 

```mysql
mysql> DROP DATABASE IF EXISTS student;
Query OK, 0 rows affected (0.00 sec)
```