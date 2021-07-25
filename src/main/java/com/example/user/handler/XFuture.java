package com.example.user.handler;

import java.util.concurrent.*;

public class XFuture<T> implements Future<T> {

    private volatile T result;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private boolean isDone = false;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public T get() throws InterruptedException {
        countDownLatch.await();
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        countDownLatch.await(timeout, unit);
        if (result == null){
            throw new TimeoutException("timeout");
        }
        return result;
    }

    public void setResult(T result) {
        this.result = result;
        this.countDownLatch.countDown();
        this.isDone = true;
    }
}
