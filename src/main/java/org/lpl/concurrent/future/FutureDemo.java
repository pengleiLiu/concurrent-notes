package org.lpl.concurrent.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author lpl
 * @Date 2019-07-19 11:04
 * @Version 1.0
 **/
public class FutureDemo {


  public static void main(String[] args) throws ExecutionException, InterruptedException {

    ExecutorService executorService = Executors.newCachedThreadPool();

    Future<Integer> oddCalcute = executorService.submit(new OddNumCalculateResult());

    Integer evenResult = 0 + 2 + 4 + 6 + 8;

    Integer oddResult = oddCalcute.get();

    Future<Integer> oddPlusCalcute = executorService
        .submit(new AfterOddNumCalculateResult(oddResult));

    Integer oddPlusResult = oddPlusCalcute.get();

    int result = evenResult + oddResult + oddPlusResult;

    System.out.println(result);
  }

}

class OddNumCalculateResult implements Callable<Integer> {

  @Override
  public Integer call() {
    try {
      int a = 1 / 0;
    } catch (Exception e) {
      return 1;
    }

    return 1 + 3 + 5 + 7 + 9;
  }
}

class AfterOddNumCalculateResult implements Callable<Integer> {

  private Integer beforeResult;

  AfterOddNumCalculateResult(Integer beforeResult) {
    this.beforeResult = beforeResult;
  }

  @Override
  public Integer call() {

    return beforeResult + 11 + 13 + 15 + 17 + 19;
  }
}
