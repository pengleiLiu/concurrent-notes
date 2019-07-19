package org.lpl.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 喝茶例子
 *
 * @Author lpenglei@163.com
 * @Date 2019-07-19 14:35
 * @Version 1.0
 **/
public class DrinkTea {

  public static void main(String[] args) {

    CompletableFuture<Void> t1 = CompletableFuture.runAsync(() -> {

      System.out.println("T1:洗水壶");
      sleep(1, TimeUnit.SECONDS);

      System.out.println("T1:烧开水");
      sleep(10, TimeUnit.SECONDS);

    });

    CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> {

      System.out.println("T2:洗茶壶");
      sleep(1, TimeUnit.SECONDS);

      System.out.println("T2:洗茶杯");
      sleep(1, TimeUnit.SECONDS);

      System.out.println("T2:拿茶叶");
      sleep(2, TimeUnit.SECONDS);

      return "龙井";
    });

    CompletableFuture t3 = t1.thenCombine(t2, (__, tn) -> {

      System.out.println("T1拿到茶叶:" + tn);
      System.out.println("T1泡茶:" + tn);

      return "上茶:" + tn;
    });

    System.out.println(t3.join());
  }


  static void sleep(int t, TimeUnit u) {
    try {
      u.sleep(t);
    } catch (InterruptedException e) {
    }
  }
}
