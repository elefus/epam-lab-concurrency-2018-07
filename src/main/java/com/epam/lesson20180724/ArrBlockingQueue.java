package com.epam.lesson20180724;


// fixed size (constructor)
// fair / unfair

// add/offer/put/offer(timeout)
// remove/poll/take/poll(timeout)
// element/peek

// T1
// [_, _, _, _, _]
// .add(1)
// [1, _, _, _, _]
// .add(2)
// [1, 2, _, _, _]
// .add(3)
// [1, 2, 3, _, _]
// .add(4)
// [1, 2, 3, 4, _]
// .add(5)
// [1, 2, 3, 4, 5]
// .put(6)
// [1, 2, 3, 4, 5]
// ... blocking ...
// [6, 2, 3, 4, 5]

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// T2
// .take()
// [_, 2, 3, 4, 5]
// [6, 2, 3, 4, 5]
// .take()
// [6, _, 3, 4, 5]
// .take()
// [6, _, _, 4, 5]
public class ArrBlockingQueue<E> {

    private final Object[] data;
    private final Lock lock;
    private final Condition notEmpty;
    private final Condition notFull;

    private int takeIndex = 0;
    private int putIndex = 0;
    private int count = 0;

    public ArrBlockingQueue(int size) {
        this(size, false);
    }

    public ArrBlockingQueue(int size, boolean fair) {
        data = new Object[size];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * Добавляет элемент, в случае если в очереди есть место
     * @return true - элемент добавлен, иначе - false
     */
    public boolean offer(E element) {
        Objects.requireNonNull(element);
        lock.lock();
        try {
            if (count == data.length) {
                return false;
            } else {
                enqueue(element);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Добавляет элемент, в случае если в очереди есть место
     * @throws IllegalStateException - очередь заполнена.
     */
    public boolean add(E element) throws IllegalStateException {
        if (offer(element)) {
            return true;
        } else {
            throw new IllegalStateException("Queue full!");
        }
    }

    /**
     * Добавляет элемент в очередь.
     * Ожидает, если очередь заполнена
     */
    public void put(E element) throws InterruptedException {
        Objects.requireNonNull(element);
        lock.lockInterruptibly();
        try {
            while (count == data.length) {
                notFull.await();
            }
            enqueue(element);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Добавляет элемент в очередь.
     * Ожидает указанный интервал, если очередь заполнена
     * @return true - элемент добавлен, false - в противном случае
     */
    public boolean offer(E element, long timeout, TimeUnit unit) throws InterruptedException {
        Objects.requireNonNull(element);
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (count == data.length) {
                if (nanos <= 0) {
                    return false;
                }
                nanos = notFull.awaitNanos(nanos);
            }
            enqueue(element);
            return true;
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(E element) {
        data[putIndex] = element;
        putIndex = (putIndex + 1) % data.length;
        ++count;
        if (count == 1) {
            notEmpty.signal();
        }
    }

    public E peek() {
        lock.lock();
        try {
            return takeAt();
        } finally {
            lock.unlock();
        }
    }

    public E element() throws NoSuchElementException {
        E element = peek();
        if (element == null) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return element;
    }

    @SuppressWarnings("unchecked")
    private E takeAt() {
        return (E) data[takeIndex];
    }

    public E take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    public E poll() {
        lock.lock();
        try {
            return (count == 0) ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(Object object) {
        Objects.requireNonNull(object);
        lock.lock();
        try {
            if (count > 0) {
                for (int i = takeIndex, end = putIndex, to = (i < end) ? end : data.length; ; i = 0, to = end) {
                    for (; i < to; ++i) {
                        if (object.equals(data[i])) {
                            removeAt(i);
                            return false;
                        }
                    }
                    if (to == end) {
                        break;
                    }
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void removeAt(int removeIndex) {
        if (removeIndex == takeIndex) {
            dequeue();
        } else {
            for (int i = removeIndex; ;) {
                int pred = i;
                i = (i + 1) % data.length;
                if (i == putIndex) {
                    data[pred] = null;
                    putIndex = pred;
                    break;
                }
                data[pred] = data[i];
            }
            --count;
            notFull.signal();
        }
    }

    private E dequeue() {
        E element = takeAt();
        data[takeIndex] = null;
        takeIndex = (takeIndex + 1) % data.length;
        --count;
        if (count == data.length - 1) {
            notFull.signal();
        }
        return element;
    }
}
