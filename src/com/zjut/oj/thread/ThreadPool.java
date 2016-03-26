package com.zjut.oj.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 总体说明
 * 	线程池
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: ThreadPool.java,v 0.1 2015-3-18 上午9:23:15 Exp $
 */
public final class ThreadPool extends ThreadPoolExecutor {

	private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

	private static ThreadPool threadPool = new ThreadPool(3, Integer.MAX_VALUE, 10000, TimeUnit.MILLISECONDS,
			queue);

	public static ThreadPool getInstance() {
		return threadPool;
	}

	@Override
	public void execute(Runnable command) {
		super.execute(command);
	}

	public static void remove(Long threadId) {
		
	}

}
