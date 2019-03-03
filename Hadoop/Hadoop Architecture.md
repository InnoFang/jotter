# Hadoop

## HDFS 架构

HDFS 即分布式文件系统（Hadoop Distributed File System)。HDFS 具有高容错性，且能运行在低廉的硬件上。HDFS 提供对应用程序数据的高吞吐量访问，适用于具有大数据集的应用程序。

## NameNode 和 DataNode

HDFS 具有 Master/Slave 架构，根据名字可以联想到古时候`奴隶主`和`奴隶`的关系。一个 Hadoop 集群由单个 NameNode 和多个 DataNode 组成。

 - NameNode 
    + 负责客户端请求的响应
    + 负责元数据的管理，比如文件名称、副本系数、块存放的位置等
 - DataNode 
    + 通常是集群中每个节点一个，存储文件对应的数据块（Block)
    + 定期向 NameNode 发送心跳信息，汇报本身及其所有的 block 信息  

HDFS 暴露了一个文件系统并允许用户数据存储在这些文件中。此外，一个文件会把被**拆分**到一个或多个块（block）中，这些块就存储在一系列 DataNode 中。NameNode 则是执行诸如打开、关闭、重命名文件和文件夹等文件系统操作，并且它还决定了块到 DataNode 的映射，即哪个块在哪个 DataNode 中，是由 NameNode 来决定的。DataNode 负责提供来自文件系统客户机的读写请求，还会根据来自 NameNode 的指令来执行对块的创建、删除和复制。 

**PS** 文件拆分的依据是由底层的 blocksize 来决定。如果 blocksize 定义为 128M，那么对于一个 130M 的文件就会被拆分成两个分别为 128M 和 2M 的文件。

![](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/images/hdfsarchitecture.png)

NameNode 和 DataNode 可以运行在廉价的机器上，通常是运行在具有 GNU/Linux 操作系统的机器上。HDFS 由 Java 语言构建，所以任何支持 Java 的机器都可以运行 NameNode 和 DataNode。

一个典型的部署方式是**一个专有机器运行 NameNode，其它每台机器运行一个 DataNode**。当然，并不排除一台机器运行多个 DataNode，但是在实际情况中很少发生。

## Reference

[HDFS Architecture](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html)s