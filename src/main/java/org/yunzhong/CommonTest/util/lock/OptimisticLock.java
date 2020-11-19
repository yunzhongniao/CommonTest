package org.yunzhong.CommonTest.util.lock;

/**
 * <pre>
 * 乐观锁：
 * 去拿数据的时候都认为别人不会修改，所以不会上锁。
 * 但是在更新的时候会判断一下在此期间别人有没有去更新这个数据
 * 可以使用版本号机制和CAS算法实现。
 * 版本号机制：
 *      一般是在数据表中加上一个数据版本号version字段，表示数据被修改的次数。A、B同时拿到version=1的数据。A更新时，version=1，表示没有人更改，更新成功，version设置为2.
 *      B更新数据，期望version=1，但是实际上version已经等于2，更新失败。
 * CAS：compare and swap（比较与交换）
 *      先得到对象V的值为A。想要更新成New。当V的值为A的时候，更新成功。
 *      如果V的值不为A，则放弃，再次获得V的值B。回到第一步。
 * </pre>
 * 
 * @author yunzhong
 *
 */
public class OptimisticLock {

}
