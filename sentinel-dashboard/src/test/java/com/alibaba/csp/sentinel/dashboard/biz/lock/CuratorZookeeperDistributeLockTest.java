package com.alibaba.csp.sentinel.dashboard.biz.lock;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import org.apache.curator.framework.recipes.locks.*;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class CuratorZookeeperDistributeLockTest extends AbstractTestCase {
    private   String key="XX";
    @Resource
    CuratorZookeeperDistributeLock curatorZookeeperDistributeLock;

    @Test
    public void acquireLock_V1()throws Exception{
        InterProcessLock interProcessLock = curatorZookeeperDistributeLock.acquireLock(key);
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper锁");
       interProcessLock.acquire();
        System.out.println("当前线程:"+Thread.currentThread().getName()+" 已经获取zookeeper锁");
        Thread.sleep(Integer.MAX_VALUE);
    }
    @Test
    public void acquireLock_V2()throws Exception{
        key +="x";
        InterProcessSemaphoreMutex interProcessSemaphoreMutex = curatorZookeeperDistributeLock.acquireSemaphoreLock(key);
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper锁");
        interProcessSemaphoreMutex.acquire();
        System.out.println("当前线程:"+Thread.currentThread().getName()+" 已经获取zookeeper锁");
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void acquireLock_V3()throws Exception{
        key +="xx";
        InterProcessReadWriteLock interProcessReadWriteLock = curatorZookeeperDistributeLock.acquireReadWriteLock(key);
        System.out.println("当前线程:"+Thread.currentThread().getName()+"  准备获取zookeeper读、写锁 key="+key);
        interProcessReadWriteLock.writeLock();
        System.out.println("当前线程:"+Thread.currentThread().getName()+" 已经获取zookeeper读、写锁");
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
