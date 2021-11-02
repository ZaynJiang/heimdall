package cn.heimdall.core.utils.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class IncrAtomicCounter {
    private static final int MASK = 0x7FFFFFFF;
    private final AtomicInteger atom;

    public IncrAtomicCounter() {
        atom = new AtomicInteger(0);
    }

    public final int incrementAndGet() {
        return atom.incrementAndGet() & MASK;
    }

    public final int getAndIncrement() {
        return atom.getAndIncrement() & MASK;
    }

    public int get() {
        return atom.get() & MASK;
    }

}