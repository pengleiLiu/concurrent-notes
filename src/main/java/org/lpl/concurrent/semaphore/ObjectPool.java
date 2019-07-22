package org.lpl.concurrent.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * @author lpl
 * @version 1.0
 * @date 2019/7/11
 **/
public class ObjectPool<T, R> {

  final List<T> pool;

  final Semaphore semaphore;

  ObjectPool(int size, T t) {
    pool = new Vector<T>() {
    };

    for (int i = 0; i < size; i++) {
      pool.add(t);
    }

    semaphore = new Semaphore(size);
  }

  R exec(Function<T, R> fun) {
    T t = null;
    try {
      semaphore.acquire();
      t = pool.remove(0);
      Thread.sleep(5000);
      return fun.apply(t);
    } catch (InterruptedException e) {
      return null;
    } finally {
      pool.add(t);
      semaphore.release();
    }

  }

  public static void main(String[] args) {
    ObjectPool<Integer, String> pool = new ObjectPool<>(10, 2);

    for (int i = 0; i < 100; i++) {

      new Thread(() ->
          pool.exec(t -> {
            System.out.println(Thread.currentThread().getName() + "打印:" + t);
            return t.toString();
          })).start();
    }

  }
}
