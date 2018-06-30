---
title: MySQL 学习之约束
date: 2017-07-17 13:54:17
tags: MySQL
categories: MySQL
---

# 约束的目的  

> 约束保证了数据的**完整性**和**一致性**。

<!-- more -->

# 约束的类型

根据约束所针对的字段的数目的多少来划分，可以划分为：
 
- 列级约束 (针对某一个字段来使用)
- 表级约束 (针对两个或两个以上的字段来使用)

列级约束既可以在列定义时声明，也可以在列定义后声明。表级约束只能在列定义后声明

根据功能来划分，可以划分为：
    
- NOT NULL (非空约束) 
- PRIMARY KEY (主键约束)
- UNIQUE KEY (唯一约束)
- DEFAULT (默认约束)
- FOREIGN KEY (外键约束)

其中 `NOT NULL` 和 `DEFAULT` 不存在表级约束，只有列级约束

# 约束的介绍

## PRIMARY KEY (主键约束)

> 保证记录的完整性，每张表只能存在一个主键，且主键自动为 `NOT NULL`

这里还要提一下自动编号 `AUTO_INCREMENT`。自动编号必须与主键结合使用，一般作用于整型，默认情况下起始值为 1，每次增量为 1

现在建立一张 person 表，并把字段 id 设置为主键自增长
```mysql
mysql> CREATE TABLE IF NOT EXISTS person(
    -> id INT PRIMARY KEY AUTO_INCREMENT,
    -> name varchar(20)
    -> );
Query OK, 0 rows affected (0.29 sec)
```
现在插入几条数据，并显示
```mysql
mysql> INSERT INTO person(name) VALUES('Jack');
Query OK, 1 row affected (0.09 sec)

mysql> INSERT INTO person(name) VALUES('Mary');
Query OK, 1 row affected (0.09 sec)

mysql> INSERT INTO person(name) VALUES('Bob');
Query OK, 1 row affected (0.08 sec)

mysql> SELECT * FROM person;
+----+------+
| id | name |
+----+------+
|  1 | Jack |
|  2 | Mary |
|  3 | Bob  |
+----+------+
3 rows in set (0.00 sec)
```

因为有自动编号的存在，虽然没有指定 id 的值，但是它仍然有一个不重复的值

现在尝试向表中插入一条重复的数据
```mysql
mysql> INSERT INTO person(id, name) VALUES(1, 'Ben');
ERROR 1062 (23000): Duplicate entry '1' for key 'PRIMARY'
```
我尝试向其中添加 id 为 1 的记录，但是出现了错误，可见主键约束过后，就又不能出现重复的值


## NOT NULL (非空约束)

> 字段值禁止为空，即在插入数据时，该字段必须赋值，否则会出现错误。与之对应的是 `NULL` ，表示字段值可以为空


比如现在新建一张表 User ，并给其中的 username 和 password 字段设置为 `NOT NULL`
```mysql
mysql> CREATE TABLE IF NOT EXISTS user (
    -> id INT PRIMARY KEY AUTO_INCREMENT,
    -> username VARCHAR(20) NOT NULL,
    -> password VARCHAR(20) NOT NULL
    -> );
Query OK, 0 rows affected (0.43 sec)
```

现在插入数据：
```mysql
mysql> INSERT INTO user(username, password) VALUES('hello', "111");
Query OK, 1 row affected (0.08 sec)

mysql> INSERT INTO user(username, password) VALUES('Jack', NULL);
ERROR 1048 (23000): Column 'password' cannot be null
```
简单的演示了一下，可见当向非空约束的字段插入空值时会报错

## UNIQUE KEY (唯一约束)

前面讲到主键约束一张表只能有有一个，但是如果还有字段需要保证唯一性的话，就可以使用这个唯一约束

> 唯一约束保证记录的唯一性，字段可以为空值 (NULL)，因此唯一约束的字段只能有一个空值。同时每张数据表可以存在多个唯一约束

现在新建一张表 player ，除了将 id 设置为主键约束外，在将 name 设置为唯一约束
```mysql
mysql> CREATE TABLE IF NOT EXISTS player(
    -> id INT PRIMARY KEY AUTO_INCREMENT,
    -> name VARCHAR(20) NOT NULL UNIQUE KEY
    -> );
Query OK, 0 rows affected (0.39 sec)
```
查看一下当我们尝试添加重复字段时，会出现什么状况
```mysql
mysql> INSERT INTO player(name) VALUES('Tom');
Query OK, 1 row affected (0.09 sec)

mysql> INSERT INTO player(name) VALUES('Tom');
ERROR 1062 (23000): Duplicate entry 'Tom' for key 'name'
```
同样无法添加具有重复字段值的记录。

而与主键约束的区别是，逐渐约束每张表只能有一个，但是唯一约束每张表可以有多个

## DEFAULT (默认约束)

> 当插入记录时，如果没有明确为字段赋值，则自动赋予默认值

现在新建一张表 member，指定字段 gender(性别) 默认值为 'unknow'
```mysql
mysql> CREATE TABLE IF NOT EXISTS member(
    -> id INT PRIMARY KEY AUTO_INCREMENT,
    -> name VARCHAR(20) NOT NULL,
    -> gender ENUM('female', 'male', 'unknow') DEFAULT 'unknow'
    -> );
Query OK, 0 rows affected (0.33 sec)
```

这里使用了枚举 `ENUM`，所以添加值时，只能选择枚举中的值。

现在添加一条记录，但是不指定性别
```mysql
mysql> INSERT INTO member(name) VALUES('Torry');
Query OK, 1 row affected (0.07 sec)

mysql> SELECT * FROM member;
+----+-------+--------+
| id | name  | gender |
+----+-------+--------+
|  1 | Torry | unknow |
+----+-------+--------+
1 row in set (0.00 sec)
```
虽然没有指定具体的值，但是 gender 字段仍然被赋予了默认值

还有就只是虽然指定被默认约束的列，但是在赋值时可以写入 DEFAULT 来赋予默认值
```mysql
mysql> INSERT member(name, gender) VALUES('Bob', DEFAULT);
Query OK, 1 row affected (0.34 sec)

mysql> SELECT * FROM member;
+----+-------+--------+
| id | name  | gender |
+----+-------+--------+
|  1 | Torry | unknow |
|  2 | Bob   | unknow |
+----+-------+--------+
2 rows in set (0.00 sec)

```


## FOREIGN KEY (外键约束)

> 保证数据的一致性，完整性，是一对一或一对多关系

### 要求

 + 子表（具有外键列的表）和父表（子表所参照的表）必须使用相同的存储引擎（只能为 InnoDB，MySQL 默认），且禁止使用临时表
 + 外键列（含有 FOREIGN 关键字的列）和参照列（外键列所参照的列）必须具有相似的数据类型
   - 数字的长度 或 是否有符号位必须相同
   - 字符的长度则可以不同
 + 外键列和参照列必须创建索引。如果外键列不存在索引的话，MySQL 将自动创建索引

### 使用场景
 
举例来说，就是当一个表中出现了冗余字段时，就可以使用外键约束。比如现在有一个学生表，其中含有一个班级字段，那么一个班的学生的这个字段值都是相同的，这就出现了冗余。那么解决这个问题，就可以使用外键约束，即再创建一个班级表，然后学生表中的班级字段就可以关联到班级表的 id 即可。

 e.g. 创建一个班级表和一个学生表，并使用外键约束

创建班级表
 ```mysql
mysql> CREATE TABLE IF NOT EXISTS classes(
    -> id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    -> class VARCHAR(20)
    -> );
Query OK, 0 rows affected (0.27 sec)
 ```

创建学生表
```mysql
mysql> CREATE TABLE IF NOT EXISTS student(
    -> id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    -> name VARCHAR(20) NOT NULL,
    -> cid SMALLINT UNSIGNED,
    -> FOREIGN KEY (cid) REFERENCES classes (id)
    -> );
Query OK, 0 rows affected (0.64 sec)
```
这里需要注意的是
 + student 表为子表，classes 表为父表
 + 外键列为 student 表中的 cid 字段，参照类为 classes 表中的 id 字段。因为是数字，所以这两者必须具有相同的数据类型和有无符号（因为有符号类型，当参照类指定了无符号类型时，外键列也必须指定为无符号类型）
 + 外键列和参照列必须创建索引

### 外键约束的参照操作

> 在进行外键约束的创建以后，在更新表的时候，子表是否也进行相应的操作

+ CASCADE：从父表删除或更新且自动删除或更新子表中匹配行
+ SET NULL：从父表删除或更新行，并设置子表中的外键列为 NULL。如果使用该选项，必须保证子表列没有指定 NOT NULL
+ RESTRICT：拒绝对父表的删除或更新操作
+ NO ACTION：标准 SQL 关键字，在 MySQL 中与 RESTRICT 相同

比如我们可以修改创建学生表的语句，字段保持不变，但是需要修改一下：

```mysql
mysql> CREATE TABLE IF NOT EXISTS student(
    -> id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    -> name VARCHAR(20) NOT NULL,
    -> cid SMALLINT UNSIGNED,
    -> FOREIGN KEY (cid) REFERENCES classes (id) ON DELETE CASCADE
    -> );
```
相比之前多了 `ON DELETE CASCADE`，这表示当父表中的记录被删除时，那么与父表记录匹配的子表的记录也会被删除

**注意**    
当使用外键约束之后，对数据表的操作顺序需要注意：   
 
 + 插入数据：先插入父表数据，后插入子表数据
 + 修改数据：先修改父表数据，后修改子表数据

因为，子表是参照父表进行设计的，所以在进行插入和修改操作时，要优先修改父表数据

 + 删除数据：先删除子表数据，后删除父表数据。这里如果使用了 `CASCADE` 或 `SET NULL` 的参照操作的话，比如使用了上面的 `ON DELETE CASCADE` ，那么倒是可以先删除父表数据。但是删除了父表的记录后，与之匹配的子表的记录也会被删除。