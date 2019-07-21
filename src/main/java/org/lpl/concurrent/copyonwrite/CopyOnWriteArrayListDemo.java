package org.lpl.concurrent.copyonwrite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lpl
 * @version 1.0
 * @date 2019/7/17
 **/
public class CopyOnWriteArrayListDemo {

  public static void main(String[] args) {

    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    for (int i = 0; i < 10; i++) {
      copyOnWriteArrayList.add(i + "");
    }
  }
}
