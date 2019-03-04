# Apache Hadoop YARN

## Yarn 架构

YARN 的基本思想是把资源管理和作业调度拆分成单独的守护进程，其拥有一个全局的  ResourceManager（RM) 和对每个应用的 ApplicationMaster（AM)。应用既可以是单个作业，也可以是多个作业。

ResourceManger 和 NodeManager 构成了数据计算框架。ResourceManager 是在系统中的所有应用之间仲裁资源的最终权限。NodeManager 是每台机器的框架代理，负责容器（Container)的管理，监视其资源的使用情况（CPU、内存、磁盘、网络），并向 ResourceManger 或者调度器（Scheduler）汇报这些情况。

实际上，每个应用程序的 ApplicationMaster 是一个特定于框架的库，其任务是协调来自 ResourceManager 的资源，并与 NodeManager 一起执行和监视这些任务。

![](http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/yarn_architecture.gif)

ResourceManager 由两部分组成：Schedulaer 和 ApplicationMaster

Scheduler 会根据容量、队列等约束，负责将资源分配给各种正在运行的应用。在某种意义上，Scheduler 就是一个纯粹的调度器而已，它不对应用进行状态的监控或跟踪。此外，它不会保证因为应用错误或硬件故障而重启失败的任务。Scheduler 根据应用的资源需求来执行其调度功能。它基于资源容器的抽象概念执行调度功能，该资源容器包含内存、CPU、磁盘、网络等。

Scheduler 具有一个可插入策略，该策略负责在不同的队列、应用等之间划分集群资源。当前诸如 [CapacityScheduler](http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/CapacityScheduler.html) 和 [FairScheduler](http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/FairScheduler.html) 就是一些插件的示例。

ApplicaitonMaster 负责接收作业提交，协商执行特定于应用的 ApplicationMaster 的第一个容器，并提供在失败时重启 ApplicationMaster 容器的服务。每个应用的 ApplicationMaster 负责调度来自 Scheduler 的合适的资源容器，跟踪它们的状态并监视其进度。

Hadoop 2.x 中的 MapReduce 与以前的稳定版本（Hadoop 1.x）保持 API 兼容性。这意味着所有 MapReduce 作业都应该在 YARN 上保持不变，只需重新编译即可。

## 总结

 + Client
    - 提交作业
    - 查询作业的运行进度
    - 杀死作业

 + ResuourceManager
    - 整个集群同一时间提供服务的 ResourceManager 只有一个
    - 负责集群资源的统一管理和调度
    - 处理客户端的请求（提交作业、杀死作业等）
    - 监控 NodeManager，当 NodeManager出现故障时，NodeManager 上的运行的任务需要询问 ApplicationMaster 如何处理

 + NodeManager 
    - 整个集群有多个
    - 负责节点本身的资源管理
    - 定期向 ResourceManager 汇报本节点的资源使用情况
    - 接收来自 ResourceManager 的命令（重启 Container 等）
    - 处理来自 ApplicationMaster 的命令

 + ApplicationMaster
    - 每个应用程序对应一个 ApplicaitonMaster
    - 负责应用程序的管理
    - 为应用程序向 ResourceManager 申请资源并分配给内部的 Task
    - 需要与 NodeManager 进行通信（启动/停止 Task）
    - Task 运行在 Container 中，ApplicationMaster 也运行在 Container 中

 + Container
    - 封装了 CPU、Memory 等资源的一个容器
    - 是一个任务运行环境的抽象
xs 
## Refernece

[Apache Hadoop YARN](http://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/YARN.html)


