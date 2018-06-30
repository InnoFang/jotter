---
title: MySQL 学习之数据表(二)
date: 2017-07-23 15:05:27
tags: MySQL
categories: MySQL
---

# 数据表的修改

> 数据表的修改操作无非就是列的增加、删除，约束的增加、删除

<!-- more -->

## 列的增加

### 添加单列

```mysql
ALTER TABLE tbl_name ADD [COLUMN] col_name column_definition [FIRST | AFTER col_name]
```

e.g. 现在假设数据库内有一张表 person，person 的字段信息如下
```mysql
mysql> SHOW COLUMNS FROM person;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int(11)     | NO   | PRI | NULL    | auto_increment |
| name  | varchar(20) | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
2 rows in set (0.33 sec)
```

现在需要在此基础上添加一个字段 age ，类型为 TINYINT(无符号)， 不为空且默认值为 10，操作如下
```mysql
mysql>  ALTER TABLE person ADD COLUMN age TINYINT UNSIGNED NOT NULL DEFAULT 10;
Query OK, 0 rows affected (0.52 sec)
Records: 0  Duplicates: 0  Warnings: 0`
```

现在再次查看 person 表的字段信息
```mysql
mysql> SHOW COLUMNS FROM person;
+-------+---------------------+------+-----+---------+----------------+
| Field | Type                | Null | Key | Default | Extra          |
+-------+---------------------+------+-----+---------+----------------+
| id    | int(11)             | NO   | PRI | NULL    | auto_increment |
| name  | varchar(20)         | YES  |     | NULL    |                |
| age   | tinyint(3) unsigned | NO   |     | 10      |                |
+-------+---------------------+------+-----+---------+----------------+
3 rows in set (0.00 sec)

```
字段 age 已经添加成功了


在上面给出的增加字段的代码中，我们省略了 FIRST 或者 AFTER col_name，这两个意思分别是：
 + FIRST : 将新添加的字段放在所有字段的第一位
 + AFTER col_name : 将新增加的字段放在指定字段的后面

如果省略这个描述，那么新增加的列就会默认添加在最后。接下来再添加一个字段 gender（性别）在字段 name 之后
```mysql
mysql> ALTER TABLE person ADD COLUMN gender ENUM('female', 'male', 'unknow') DEFAULT 'unknow' AFTER name;
Query OK, 0 rows affected (0.53 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

再次查看 person 数据表的字段信息   
```mysql
mysql> SHOW COLUMNS FROM person;
+--------+--------------------------------+------+-----+---------+----------------+
| Field  | Type                           | Null | Key | Default | Extra          |
+--------+--------------------------------+------+-----+---------+----------------+
| id     | int(11)                        | NO   | PRI | NULL    | auto_increment |
| name   | varchar(20)                    | YES  |     | NULL    |                |
| gender | enum('female','male','unknow') | YES  |     | unknow  |                |
| age    | tinyint(3) unsigned            | NO   |     | 10      |                |
+--------+--------------------------------+------+-----+---------+----------------+
4 rows in set (0.00 sec)

```

### 添加多列

```mysql
AFTER TABLE tbl_name ADD [COLUMN] (col_name column_definition, ...)
```
列的添加可以一次添加多列，但是需要注意的就是，一次添加多列，字段名和字段定义必须用圆括号括起来，并且字段与字段之间要用逗号隔开。与单列添加不同的是，添加多列需要加括号，并且多列添加不能指定位置信息

e.g. 一次添加两列：first_name 和 last_name
```mysql
mysql> ALTER TABLE person ADD COLUMN (first_name VARCHAR(10), last_name VARCHAR(10));
Query OK, 0 rows affected (0.87 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

再次查看 person 表的信息
```mysql
mysql> SHOW COLUMNS FROM person;
+------------+--------------------------------+------+-----+---------+----------------+
| Field      | Type                           | Null | Key | Default | Extra          |
+------------+--------------------------------+------+-----+---------+----------------+
| id         | int(11)                        | NO   | PRI | NULL    | auto_increment |
| name       | varchar(20)                    | YES  |     | NULL    |                |
| gender     | enum('female','male','unknow') | YES  |     | unknow  |                |
| age        | tinyint(3) unsigned            | NO   |     | 10      |                |
| first_name | varchar(10)                    | YES  |     | NULL    |                |
| last_name  | varchar(10)                    | YES  |     | NULL    |                |
+------------+--------------------------------+------+-----+---------+----------------+
6 rows in set (0.00 sec)

```

## 列的删除

既然有列的增加，那么相对应的就有列的删除


### 删除一列 
```mysql
ALTER TABLE tbl_name DROP [COLUMN] col_name;
```

现在尝试将上一小节中添加的 gender 字段删除
```mysql
mysql> ALTER TABLE person DROP COLUMN gender;
Query OK, 0 rows affected (0.58 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

查看数据表字段结构
```mysql
mysql> SHOW COLUMNS FROM person;
+------------+---------------------+------+-----+---------+----------------+
| Field      | Type                | Null | Key | Default | Extra          |
+------------+---------------------+------+-----+---------+----------------+
| id         | int(11)             | NO   | PRI | NULL    | auto_increment |
| name       | varchar(20)         | YES  |     | NULL    |                |
| age        | tinyint(3) unsigned | NO   |     | 10      |                |
| first_name | varchar(10)         | YES  |     | NULL    |                |
| last_name  | varchar(10)         | YES  |     | NULL    |                |
+------------+---------------------+------+-----+---------+----------------+
5 rows in set (0.00 sec)

```
gender 字段已经被去除


### 删除多列

```mysql
ALTER TABLE tbl_name DROP [COLUMN] col_name1, DROP [COLUMN] col_name2;
```

如果想一次删除多列也是很简单的，比如这里想将 first_name 字段和 last_name 字段删除，可以进行如下操作：
```mysql
mysql> ALTER TABLE person DROP COLUMN first_name, DROP COLUMN last_name;
Query OK, 0 rows affected (0.50 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

再次查看数据表结构
```mysql
mysql> SHOW COLUMNS FROM person;
+-------+---------------------+------+-----+---------+----------------+
| Field | Type                | Null | Key | Default | Extra          |
+-------+---------------------+------+-----+---------+----------------+
| id    | int(11)             | NO   | PRI | NULL    | auto_increment |
| name  | varchar(20)         | YES  |     | NULL    |                |
| age   | tinyint(3) unsigned | NO   |     | 10      |                |
+-------+---------------------+------+-----+---------+----------------+
3 rows in set (0.00 sec)

```
现在 person 表中就只剩下 3 列了

当然如果你想同时删除一行，然后添加一行的话也是可以的，这之间用逗号分隔即可

比如现在想删除 age 字段，添加 gender 字段
```mysql
mysql> ALTER TABLE person DROP age, ADD gender ENUM('female', 'male', 'unknow') DEFAULT 'unknow';
Query OK, 0 rows affected (0.56 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

那么现在 person 数据表的结构就变成了下面这个样子：
```mysql
mysql> SHOW COLUMNS FROM person;
+--------+--------------------------------+------+-----+---------+----------------+
| Field  | Type                           | Null | Key | Default | Extra          |
+--------+--------------------------------+------+-----+---------+----------------+
| id     | int(11)                        | NO   | PRI | NULL    | auto_increment |
| name   | varchar(20)                    | YES  |     | NULL    |                |
| gender | enum('female','male','unknow') | YES  |     | unknow  |                |
+--------+--------------------------------+------+-----+---------+----------------+
3 rows in set (0.00 sec)

```
age 字段已经被删除，取而代之的是一个 gender 字段

## 约束的添加

现在创建一张表 user，内含字段 id，name。用于这一小节的测试:
```mysql
mysql> CREATE TABLE user(
    -> id SMALLINT UNSIGNED,
    -> name VARCHAR(20) NOT NULL
    -> );
Query OK, 0 rows affected (0.28 sec)

```

### 添加主键约束
```mysql
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] PRIMARY KEY [index_type] (index_col_name, ...);
```


此时这张 user 表还没有设置主键，所以现在将 user 表中的 id 字段设为主键约束
```mysql
mysql> ALTER TABLE user ADD CONSTRAINT PK_user_id PRIMARY KEY (id);
Query OK, 0 rows affected (0.43 sec)
Records: 0  Duplicates: 0  Warnings: 0

```
这里的 `CONSTRAINT PK_user_id` 是可以省略不写的，其中 `PK_user_id` ，即 symbol ，这个相当于这个约束的名字。此时查看数据表的结构
```mysql
 mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   | PRI | NULL    |       |
| name  | varchar(20)          | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
2 rows in set (0.00 sec)

```
此时 id 字段已经设为了主键。添加主键约束成功。


### 添加唯一约束

```mysql
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] UNIQUE [INDEX|KEY] [index_name] [index_type] (index_col_name, ...);
```

接下来尝试将 user 表中的 name 字段设置为唯一约束
```mysql
mysql> ALTER TABLE user ADD UNIQUE (name);
Query OK, 0 rows affected (0.24 sec)
Records: 0  Duplicates: 0  Warnings: 0

```
这里为了简便，将一些可以省略的都省略了，接下来再次查看一下数据表的结构
```mysql
mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   | PRI | NULL    |       |
| name  | varchar(20)          | NO   | UNI | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
2 rows in set (0.00 sec)

```
可见，name 字段已经成功设置为唯一约束了。


### 添加外键约束
```mysql
ALTER TABLE tbl_name ADD [CONSTRAINT [symbol]] FOREIGN KEY [index_name] (index_col_name, ...) reference_definition;
```

接下来就是尝试为 user 表添加外键约束。假设现在有一张 groups 表，那么 user 表中有了一个新的字段 uid，uid 指向 groups 中的一个记录

那么现在先创建一张 groups 表（内含字段 id，group_name），再为 user 表添加一个 uid 字段，然后为 uid 添加外键约束
```mysql
mysql> CREATE TABLE groups(
    -> id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    -> group_name VARCHAR(20) NOT NULL
    -> );
Query OK, 0 rows affected (0.34 sec)

mysql> ALTER TABLE user ADD uid INT UNSIGNED;
Query OK, 0 rows affected (0.60 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

现在就可以为 user 表的 uid 字段添加外键约束了
```mysql
mysql> ALTER TABLE user ADD FOREIGN KEY (uid) REFERENCES groups (id);
Query OK, 0 rows affected (0.63 sec)
Records: 0  Duplicates: 0  Warnings: 0

```

现在查看一下 user 表的结构
```mysql
mysql> SHOW CREATE TABLE user;
+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Table | Create Table                                                                                                                                                                                                                                                                                                              |
+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| user  | CREATE TABLE `user` (
  `id` smallint(5) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `uid` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `uid` (`uid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
+-------+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)


```
此时 user 表中多了个 uid 字段，并且已经为其添加了一个外键约束。


### 添加默认约束

```mysql
ALTER TABLE tbl_name ALTER [COLUMN] col_name SET DEFAULT literal;
```

最后我们还可以给数据表中的字段添加默认约束，比如现在再为 user 表添加一个 age 字段，再添加完之后，再为 age 字段添加默认约束
```mysql
mysql> ALTER TABLE user ADD age TINYINT UNSIGNED NOT NULL;
Query OK, 0 rows affected (0.92 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   | PRI | NULL    |       |
| name  | varchar(20)          | NO   | UNI | NULL    |       |
| uid   | int(10) unsigned     | YES  | MUL | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```
先为 user 添加一个新的字段，但是并未指定默认约束，所以接下来就为 age 字段添加默认约束
```mysql
mysql> ALTER TABLE user ALTER age SET DEFAULT 0;
Query OK, 0 rows affected (0.36 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   | PRI | NULL    |       |
| name  | varchar(20)          | NO   | UNI | NULL    |       |
| uid   | int(10) unsigned     | YES  | MUL | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | 0       |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```
此时，age 字段的默认值就变为了 0

## 约束的删除

### 删除主键约束

```mysql
ALTER TABLE tbl_name DROP PRIMARY KEY
```

这里可能有的人会有点疑惑，为什么删除主键不需要指定列的名字？因为每一张表有且只有一个主键，所以不需要指定字段名。

那么利用上一节的 user 表，尝试将 user 表中的主键约束去掉
```mysql
mysql> ALTER TABLE user DROP PRIMARY KEY;
Query OK, 0 rows affected (0.57 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   |     | NULL    |       |
| name  | varchar(20)          | NO   | PRI | NULL    |       |
| uid   | int(10) unsigned     | YES  | MUL | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```

可以发现，此时 id 已经不是主键约束了


### 删除唯一约束

```mysql
ALTER TABLE tbl_name DROP {INDEX | KEY} index_name;
```
因为一张表可以有多个唯一约束，所以这里需要指定字段名。所以，要删字段的唯一约束，就首先要知道该字段的约束的名字。因为我们要删除的不是字段，而是该字段的约束。

所以，在删除 user 表中的唯一约束之前，需要先知道该字段的约束的名字
```mysql
mysql> SHOW INDEXES FROM user;
+-------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table | Non_unique | Key_name | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+-------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| user  |          0 | name     |            1 | name        | A         |           0 |     NULL | NULL   |      | BTREE      |         |               |
| user  |          1 | uid      |            1 | uid         | A         |           0 |     NULL | NULL   | YES  | BTREE      |         |               |
+-------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
2 rows in set (0.00 sec)

```

可以看到 name 这个字段的唯一约束的名字就是 name，那么下面就可以进行 name 这个字段的唯一约束的删除了
```mysql
mysql> ALTER TABLE user DROP INDEX name;
Query OK, 0 rows affected (0.57 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   |     | NULL    |       |
| name  | varchar(20)          | NO   |     | NULL    |       |
| uid   | int(10) unsigned     | YES  | MUL | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```

此时，name 这个字段唯一约束已经删除。

### 删除外键约束

```mysql
ALTER TABLE tbl_name DROP FOREIGN KEY fk_symbol;
```

与唯一约束同理，要删除该字段的外键约束，就需要先知道该字段的外键约束的名字
```mysql
mysql> SHOW CREATE TABLE user;
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Table | Create Table                                                                                                                                                                                                                                                                                                |
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| user  | CREATE TABLE `user` (
  `id` smallint(5) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `uid` int(10) unsigned DEFAULT NULL,
  `age` tinyint(3) unsigned NOT NULL,
  KEY `uid` (`uid`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)

```
字段的外键约束的名字使系统默认赋值给我们的，这里通过查看可以知道 uid 字段的外键约束的名字是 `user_ibfk_1`

下面就可以对 uid 字段的外键约束进行删除了
```mysql
mysql> ALTER TABLE user DROP FOREIGN KEY user_ibfk_1;
Query OK, 0 rows affected (0.12 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW CREATE TABLE user;
+-------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Table | Create Table                                                                                                                                                                                                                     |
+-------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| user  | CREATE TABLE `user` (
  `id` smallint(5) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `uid` int(10) unsigned DEFAULT NULL,
  `age` tinyint(3) unsigned NOT NULL,
  KEY `uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
+-------+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)

```
uid 的外键约束已经删除了，但是我们还可以看到 uid 的索引还是存在，如果不想要这个索引，那么也可以删除
```mysql
mysql> ALTER TABLE user DROP INDEX uid;
Query OK, 0 rows affected (0.44 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW CREATE TABLE user;
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| Table | Create Table                                                                                                                                                                                                |
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
| user  | CREATE TABLE `user` (
  `id` smallint(5) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `uid` int(10) unsigned DEFAULT NULL,
  `age` tinyint(3) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
+-------+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
1 row in set (0.00 sec)

```

此时索引也已经删除


### 删除默认约束

```mysql
ALTER TABLE tbl_name ALTER [COLUMN] col_name DROP DEFAULT;
```

这里删除默认约束就比较简单了，只需要知道列的名字就可以了。最后再来删除掉 age 字段的默认约束

```mysql
mysql> ALTER TABLE user ALTER age DROP DEFAULT;
Query OK, 0 rows affected (0.07 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   | PRI | NULL    |       |
| name  | varchar(20)          | NO   | UNI | NULL    |       |
| uid   | int(10) unsigned     | YES  | MUL | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```
此时，age 字段就没有默认值了。

## 修改数据表

### 修改列定义

```mysql
ALTER TABLE tbl_name MODIFY [COLUMN] col_name column_definition [FIRST | AFTER col_name];
```

有时候在定义完数据列之后，发现在定义上出现了问题，那么就可以通过修改列定义去更正

比如现在我们通过查看 user 数据表
```mysql
mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   |     | NULL    |       |
| name  | varchar(20)          | NO   |     | NULL    |       |
| uid   | int(10) unsigned     | YES  |     | NULL    |       |
| age   | tinyint(3) unsigned  | NO   |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```

我们发现 age 这个字段没有默认值，并且现在我想让该字段位于 name 这个字段之后，那么我们就可以进行如下操作
```mysql
mysql> ALTER TABLE user MODIFY age TINYINT UNSIGNED DEFAULT 10 AFTER name;
Query OK, 0 rows affected (0.45 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   |     | NULL    |       |
| name  | varchar(20)          | NO   |     | NULL    |       |
| age   | tinyint(3) unsigned  | YES  |     | 10      |       |
| uid   | int(10) unsigned     | YES  |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```

如果不想修改 age 字段的列定义，只是想移动 age 字段的位置的话，那么就需要把 age 字段原来的定义重新写一遍即可。

这里需要十分注意的就是，如果将字段的类型从大类型改到小类型的话，有可能造成数据的丢失，所以在修改之前一定要注意这个问题。

### 修改列名称

```mysql
ALTER TABLE tbl_name CHANGE [COLUMN] old_col_name new_col_name column_definition [FIRST | AFTER col_name];
```

这里的 `CHANGE` 关键字除了可以修改列的名称，还可以修改列的定义，所以它的功能要大于 `MODIFY`

那么现在如果我们想把 uid 字段的名字更改为 u_id 并且把数据类型修改为 SMALLINT(无符号) ，那么就可以进行如下操作
```mysql
mysql> ALTER TABLE user CHANGE uid u_id SMALLINT UNSIGNED;
Query OK, 0 rows affected (0.55 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SHOW COLUMNS FROM user;
+-------+----------------------+------+-----+---------+-------+
| Field | Type                 | Null | Key | Default | Extra |
+-------+----------------------+------+-----+---------+-------+
| id    | smallint(5) unsigned | NO   |     | NULL    |       |
| name  | varchar(20)          | NO   |     | NULL    |       |
| age   | tinyint(3) unsigned  | YES  |     | 10      |       |
| u_id  | smallint(5) unsigned | YES  |     | NULL    |       |
+-------+----------------------+------+-----+---------+-------+
4 rows in set (0.00 sec)

```
这里原先的 uid 字段已经更名为了 u_id 且数据类型也由原来的 INT 变为了 SMALLINT 


### 修改数据表名

修改数据表名字一共有两个方法，分别是使用 `ALTER` 和使用 'RENAME' 来实现

 + 方法一
```mysql
ALTER TABLE tbl_name RENAME [TO | AS] new_tbl_name;
```

 + 方法二
```mysql
RENAME TABLE tbl_name TO new_tbl_name [, tbl_name2 TO new_tbl_name2...];
```

这里通过比较可以发现，方法一一次只能修改一张数据表的名字，但是方法二可以修改多张数据表的名字

这里使用第一种方法修改 user 表的名字，将 user 更改为 users
```mysql
mysql> ALTER TABLE user RENAME TO users;
Query OK, 0 rows affected (0.14 sec)

```
这里，数据表的更名已经完成，如果要查看数据表的话，可以使用 `SHOW TABLES;` 进行查看。

最后需要强调的是，**在平常的开发过程中，应该尽量少的使用数据表的更名和数据列的更名，原因是如果我们曾经创建过索引等这些表明或列名被引用的操作的话，如果修改了列明或表明，可能会导致某些存储过程无法正常工作，所以这里建议不要随意更改数据表或数据列的名字**