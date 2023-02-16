## 定时任务（三） ---- Java Timer实现定时任务


### test
定时任务（一） ---- Java Timer实现定时任务

QuartzSchedulerThread：负责执行向QuartzScheduler注册的触发Trigger的工作的线程。
ThreadPool：Scheduler使用一个线程池作为任务运行的基础设施，任务通过共享线程池中的线程提供运行效率。
QuartzSchedulerResources：包含创建QuartzScheduler实例所需的所有资源（JobStore，ThreadPool等）。
SchedulerFactory ：提供用于获取调度程序实例的客户端可用句柄的机制。
JobStore： 通过类实现的接口，这些类要为org.quartz.core.QuartzScheduler的使用提供一个org.quartz.Job和org.quartz.Trigger存储机制。作业和触发器的存储应该以其名称和组的组合为唯一性。
QuartzScheduler ：这是Quartz的核心，它是org.quartz.Scheduler接口的间接实现，包含调度org.quartz.Jobs，注册org.quartz.JobListener实例等的方法。
Scheduler ：这是Quartz Scheduler的主要接口，代表一个独立运行容器。调度程序维护JobDetails和触发器的注册表。 一旦注册，调度程序负责执行作业，当他们的相关联的触发器触发（当他们的预定时间到达时）。
Trigger ：具有所有触发器通用属性的基本接口，描述了job执行的时间出发规则。 - 使用TriggerBuilder实例化实际触发器。
JobDetail ：传递给定作业实例的详细信息属性。 JobDetails将使用JobBuilder创建/定义。
Job：要由表示要执行的“作业”的类实现的接口。只有一个方法 void execute(jobExecutionContext context)
(jobExecutionContext 提供调度上下文各种信息，运行时数据保存在jobDataMap中)

Job有个子接口StatefulJob ,代表有状态任务。有状态任务不可并发，前次任务没有执行完，后面任务处于阻塞等到。