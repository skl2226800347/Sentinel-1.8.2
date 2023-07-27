package com.alibaba.csp.sentinel.demo.zookeeper.lock;

import com.alibaba.csp.sentinel.demo.zookeeper.lock.lock.CuratorZookeeperDistributeLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CuratorZookeeperDistributeLockTest  extends AbstractTestCase{
    private  String key="XX";

    @Resource
    CuratorZookeeperDistributeLock curatorZookeeperDistributeLock;

    @Test
    public void acquireLock_V1()throws Exception{
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper锁");
        InterProcessMutex interProcessMutex =curatorZookeeperDistributeLock.acquireLock(key);
        //interProcessMutex.acquire();
        boolean acquireLock = interProcessMutex.acquire(100000, TimeUnit.MILLISECONDS);
        System.out.println("当前线程:"+Thread.currentThread().getName()+" acquireLock:"+acquireLock+"   "+(acquireLock ==true ?"  已经获取zookeeper锁":"获取锁失败"));
        interProcessMutex.release();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void acquireLock_V2()throws Exception{
        key +="x";
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper锁");
        InterProcessSemaphoreMutex interProcessSemaphoreMutex =curatorZookeeperDistributeLock.acquireSemaphoreLock(key);
        //interProcessMutex.acquire();
        boolean acquireLock = interProcessSemaphoreMutex.acquire(10000, TimeUnit.MILLISECONDS);
        System.out.println("当前线程:"+Thread.currentThread().getName()+" acquireLock:"+acquireLock+"   "+(acquireLock ==true ?"  已经获取zookeeper锁":"获取锁失败"));
        //interProcessSemaphoreMutex.release();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void acquireLock_V3()throws Exception{
        key +="xx";
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper读、写锁key="+key);
        InterProcessReadWriteLock interProcessReadWriteLock =curatorZookeeperDistributeLock.acquireReadWriteLock(key);
        //interProcessMutex.acquire();
        boolean acquireLock = interProcessReadWriteLock.writeLock().acquire(100000, TimeUnit.MILLISECONDS);
        System.out.println("当前线程:"+Thread.currentThread().getName()+" 是否获取zookeeper读、写锁acquireLock:"+acquireLock);
        interProcessReadWriteLock.readLock();
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void acquireLock_V5()throws Exception{
        key +="xxx";
        List<String> keys = Arrays.asList(key);
        InterProcessMultiLock interProcessMultiLock = curatorZookeeperDistributeLock.acquireMultiLock(keys);
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper锁");
        interProcessMultiLock.acquire();
        System.out.println("当前线程:"+Thread.currentThread().getName()+" 已经获取zookeeper锁");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
