package org.lpl.concurrent;


import static java.util.concurrent.TimeUnit.*;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author lpl
 * @version 1.0
 * @date 2019/7/3
 **/
public class CompletableFutureDemo {

  public static void drinkTea() {

    CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
      System.out.println("T1:洗水壶");
      sleep(1, TimeUnit.SECONDS);

      System.out.println("T1: 烧开水......");
      sleep(15, TimeUnit.SECONDS);
    });

    CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
      System.out.println("T2:洗茶壶");
      sleep(1, SECONDS);

      System.out.println("T2:洗茶杯");
      sleep(2, SECONDS);

      System.out.println("T2:拿茶叶");
      sleep(1, SECONDS);
      return "绿茶";
    });

    CompletableFuture<String> f3 =
        f1.thenCombine(f2, (__, tf) -> {
          System.out.println("T1:拿到茶叶:" + tf);

          System.out.println("T1:泡茶");

          return "上茶" + tf;
        });

    System.out.println(f3.join());
  }

  /**
   * 串行关系
   */
  public static void serial() {
    CompletableFuture<String> f0 = CompletableFuture
        .supplyAsync(() -> "hello word")
        .thenApply(s -> s + "QQ")
        .thenApply(String::toUpperCase);

    System.out.println(f0.join());
  }

  /**
   * 并行的
   */
  public static void parallel() {

  }

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

  public static int getRandom() {
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
//    either();

    exception();
  }
}
