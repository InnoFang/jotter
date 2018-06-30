---
title: MySQL 学习之数据表(一)
date: 2017-07-23 15:05:27
tags: MySQL
categories: MySQL
---

> 数据表（或称表）是数据库最重要的组成部分之一，是其他对象的基础

在数据表中，行称为记录，列称为字段

# 打开数据库

在对数据表进行操作前都需要先打开数据库，没有数据表的数据库就相当于一个空架子，没有任何意义

<!-- more -->

```mysql 
USE db_name;
```
打开数据库后，要想查看当前打开的数据库，可以用以下指令
```mysql
SELECT DATABASE();
```

e.g. 打开 student 数据库，并查看
```mysql
mysql> USE student;
Database changed
mysql> SELECT DATABASE();
+------------+
| DATABASE() |
+------------+
| student    |
+------------+
1 row in set (0.00 sec)
```

# 创建数据表

```mysql
CREATE TABLE [IF NOT EXISTS] table_name (
    column_name data_type,
    ...
);
```

e.g. 创建一个班级 class 表，内含姓名、年龄和分数这三个字段
```mysql
mysql> CREATE TABLE class(
    -> name VARCHAR(20),
    -> age TINYINT UNSIGNED,
    -> score FLOAT(3,1) UNSIGNED
    -> );
Query OK, 0 rows affected (0.78 sec)
```

# 查看数据表
```mysql
SHOW TABLES [FROM db_name] [LIKE 'pattern' | WHERE expr];
```

e.g. 查看当前数据库下的数据表
```mysql
mysql> SHOW TABLES;
+-------------------+
| Tables_in_student |
+-------------------+
| class             |
+-------------------+
1 row in set (0.00 sec)
```

## 查看数据表结构
```mysql
SHOW COLUMNS FROM tbl_name;
```

e.g. 查看已创建的 class 数据表的结构
```mysql
mysql> SHOW COLUMNS FROM class;
+-------+---------------------+------+-----+---------+-------+
| Field | Type                | Null | Key | Default | Extra |
+-------+---------------------+------+-----+---------+-------+
| name  | varchar(20)         | YES  |     | NULL    |       |
| age   | tinyint(3) unsigned | YES  |     | NULL    |       |
| score | float(3,1) unsigned | YES  |     | NULL    |       |
+-------+---------------------+------+-----+---------+-------+
3 rows in set (0.01 sec)
```


# 插入记录

```mysql
INSERT [INTO] tbl_name [(col_name, ...)] {VALUES | VALUE} ({expr | DEFAULT}, ...), (....)
```
e.g. 不指定字段名，插入一条数据到 class 数据表中（插入的数据为：姓名：Tom，年龄：20，成绩：90.5）
```mysql
mysql> INSERT INTO class VALUES('Tom', 20, 90.5);
Query OK, 1 row affected (0.48 sec)
```

e.g. 指定字段名，插入一条数据到 class 数据表中（插入的数据为：姓名：Mike，年龄：19，成绩：未指定）
```mysql
mysql> INSERT class(name, age) VALUES('Mike', 19);
Query OK, 1 row affected (0.46 sec)
```

如果字段是自增长 (AUTO_INCREMENT) 那么在添加的时候可以用 NULL 或 DEFAULT 来代替，什么意思呢？看一下下面的操作就知道了
```mysql
mysql> CREATE TABLE table1 (
    -> id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    -> name VARCHAR(20) NOT NULL,
    -> age TINYINT UNSIGNED DEFAULT 10
    -> );
Query OK, 0 rows affected (0.59 sec)

mysql> INSERT table1 VALUES(NULL, 'Tom', 18);
Query OK, 1 row affected (0.35 sec)

mysql> INSERT table1 VALUES(DEFAULT, 'John', 15);
Query OK, 1 row affected (0.35 sec)

```

在上面的操作中，先创建了一张表 table1，其中包括一个自增长的 id 字段。那么当我们向表中添加数据时，没有指定具体字段，那么默认就需要为所有的字段都赋上值，所以，这里想说的就是，如果你的字段是自增长的，那么在这种情况下，你可以用 NULL 或 DEFAULT 代替，而不需要指定一个值。


如果某字段是默认约束修饰的话，在插入记录时，也可以用 DEFAULT 来赋予默认值，当然插入时，如果不指定该字段也可以赋予默认值。


除此之外，再插入数据时，是支持表达式和函数的，比如为 table1 的 age 字段赋值 2 * 10 + 2
```mysql
mysql> INSERT table1 VALUE(NULL, 'Tom', 2 * 10 + 2);
Query OK, 1 row affected (0.40 sec)

mysql> SELECT * FROM table1;
+----+------+------+
| id | name | age  |
+----+------+------+
|  1 | Tom  |   18 |
|  2 | John |   15 |
|  3 | Tom  |   22 |
+----+------+------+
3 rows in set (0.00 sec)

```

如果想一次性添加多条记录的话，只需要用逗号隔开即可
```mysql
mysql> INSERT table1 VALUE(NULL, 'Barry', 12), (NULL, 'Harry', 19);
Query OK, 2 rows affected (0.07 sec)
Records: 2  Duplicates: 0  Warnings: 0

mysql> SELECT * FROM table1;
+----+-------+------+
| id | name  | age  |
+----+-------+------+
|  1 | Tom   |   18 |
|  2 | John  |   15 |
|  3 | Tom   |   22 |
|  4 | Barry |   12 |
|  5 | Harry |   19 |
+----+-------+------+
5 rows in set (0.00 sec)

```

以上，基本就是向 MySQL 数据表中插入数据的基本操作了。

## 其他记录插入方式

其实除了使用 INSERT 来插入数据 还可以使用 INSERT SET 或者 INSERT SELECT 来插入数据

+ **INSERT SET**

```mysql
INSERT [INTO] tbl_name SET col_name={expr | DEFAULT}, ...
```
使用这种方式与第一种方式的区别在于，此方法可以使用子查询 (SubQuery)，除此之外，第一种方式可以一次性插入多条记录，而这种方式一次只能添加一条记录

e.g. 使用 INSERT SET 的方式想数据表中添加一条数据
```mysql
mysql> INSERT table1 SET name='Bob', age='24';
Query OK, 1 row affected (0.08 sec)
```
+ **INSERT SELECT**
```mysql
INSERT [INTO] tbl_name [(col_name, ...)] SELECT ...
```
此方法可以将查询结果插入到指定数据表

# 删除记录

单表删除 MySQL 语法
```mysql
DELETE FROM tbl_name [WHERE where_condition]
```

# 更新记录

单表更新 MySQL 语法
```mysql
UPDATE [LOW_PRIORITY] [IGNORE] table_reference SET col_name1={expr1 | DEFAULT}[, col_name2={expr2|DEFAULT}].. [WHERE where_condition]
```

# 查找记录

```mysql
SELECT select_expr[, select_expr...] FROM tbl_name 
                                     [WHERE where_condition] 
                                     [GROUP BY {col_name | position} [ASC | DESC], ...]
                                     [HAVING where_condition]
                                     [ORDER BY {col_name | expr | position} [ASC | DESC], ...]
                                     [LIMIT {[offset,] row_count | row_count OFFSENT offset}]
```



e.g. 查找 class 数据表中所有记录
```mysql
mysql> SELECT * FROM class ;
+------+------+-------+
| name | age  | score |
+------+------+-------+
| Tom  |   20 |  90.5 |
| Mike |   19 |  NULL |
+------+------+-------+
2 rows in set (0.00 sec)
```

## WHERE 语句进行条件查询

## GROUP BY 语句对结果进行分组

## HAVING 语句设置分组条件

## ORDER BY 语句对查询结果进行排序

## LIMIT 语句限制查询数量