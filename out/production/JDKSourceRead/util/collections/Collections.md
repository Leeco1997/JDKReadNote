## （一）JAVA SE — 集合

[TOC]

### 1. Queue

   ![img](https://images2017.cnblogs.com/blog/1182892/201711/1182892-20171122100317930-842768608.png) 

#### 1.1 BlockingQueue

##### 1.1.1 AyyayBlockingQueue

##### 1.1.2 LinkedBlockingQueue

1. 带有头节点的单链表，有头节点和尾节点两个。head&&last。

2. 有两把锁。takeLock &&putLock。

3.  **AtomicInteger** count = new AtomicInteger(); 

4. 默认的无参构造。

   

   public LinkedBlockingDeque() {    this(Integer.MAX_VALUE);}

   ``

   



#### 1.2 AbstractQueue

##### 1.2.1 PriorityQueue

##### 1.2.2 ConcurrentLinkedQueue





### 2. List

### 3. Map

### 4. Stack

