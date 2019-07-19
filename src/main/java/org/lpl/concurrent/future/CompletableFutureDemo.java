package org.lpl.concurrent.future;


import static java.util.concurrent.TimeUnit.*;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lpenglei@163.com
 * @version 1.0
 * @date 2019/7/3
 **/
public class CompletableFutureDemo {

  /**
   * 串行关系
   */
  public static void serial() {

    CompletableFuture<String> f0 = CompletableFuture
        .supplyAsync(() -> "hello word")
        .thenApply(s -> s + "QQ")
        .thenApply(String::toUpperCase);

    CompletableFuture<String> f1 = CompletableFuture
        .supplyAsync(() -> "tencent");

    System.out.println(f0.join());
  }

  /**
   * 并行的 参考喝茶例子
   */
  public static void parallel() {

  }

  /**
   * 或者关系
   * <p>任务1、任务2任何一个完成 结束任务</p>
   */
  public static void either() {

    CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
      int random = getRandom();
      sleep(random, SECONDS);
      return "a";
    });
    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
      int random = getRandom();
      sleep(random, SECONDS);
      return "b";
    });

    CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s);

    System.out.println(f3.join());
  }

  private static int getRandom() {
    Random random = new Random();
    int num = random.nextInt(10);
    System.out.println(num);
    return num;
  }

  public static void exception() {

    CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 7 / 0)
        .thenApply(r -> r * 10)
        .exceptionally(a -> 2)
        .whenComplete((result, e) -> {
          System.out.println(result);
        });
    System.out.println(f1.join());
  }

  public static void async() {

    CompletableFuture<String> f0 = CompletableFuture
        .supplyAsync(() -> {

          sleep(2, SECONDS);
          return Thread.currentThread().getName() + ">1\n";
        })
        .thenApplyAsync(s -> s + Thread.currentThread().getName() + ">2\n")
        .thenApplyAsync(s -> s + Thread.currentThread().getName() + ">3\n")
        .thenApplyAsync(s -> s + Thread.currentThread().getName() + ">4\n");

    System.out.println(f0.join());
  }

  /**
   *
   */
  static void sleep(int t, TimeUnit u) {
    try {
      u.sleep(t);
    } catch (InterruptedException e) {
    }
  }

  public static void main(String[] args) {
    //drinkTea();
    //serial();
    //either();
    //serial();
    async();
    //exception();
  }
}
