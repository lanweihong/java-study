package com.lanweihong.common.core.utils;

/**
 * 唯一 id 生成器
 * @author lanweihong 986310747@qq.com
 * @date 2021/7/27 14:31
 */
public enum UniqueIdGenerator {
    /**
     * 实例
     */
    INSTANCE(0, 2);
    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1480166465631L;

    /**
     * 每一部分占用的位数 ，支持单节点最高1000*1024的并发
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     *  机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATA_CENTER_BIT = 5;

    /**
     * 支持的最大数据中心 id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

    /**
     * 支持的最大机器 id，为 31
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 机器标识左移位数 12
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 数据中心标识左移位数 12+5=17
     */
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    /**
     * 时间戳左移位数 17+5=22
     */
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    /**
     * 数据中心id (0~31)
     */
    private final long dataCenterId;

    /**
     * 机器标识 (0~31)
     */
    private final long machineId;

    /**
     * 序列号
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastStamp = -1L;

    UniqueIdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public long nextId() {
        return generateId();
    }

    private long generateId() {
        long currStamp = getNewStamp();
        // 当前时间小于上次生成时间，说明系统时钟回退过，即有问题
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStamp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        // 更新上次生成时间
        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT
                | dataCenterId << DATA_CENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    protected long getNextMill() {
        long mill = getNewStamp();
        while (mill <= lastStamp) {
            mill = getNewStamp();
        }
        return mill;
    }

    protected long getNewStamp() {
        return System.currentTimeMillis();
    }
}
