package org.lpl.concurrent.ReadWriteLock;

/**
 * @Author lpl
 * @Date 2019-07-22 19:05
 * @Version 1.0
 **/
public class ReadWriteTest {

  public static void main(String[] args) {

    Cache<String, Data> cache = new Cache<>();

    Data data = new Data();
    data.setId(1);
    data.setJsonData("hello word");
    cache.put("name", data);
    cache.put("1", data);

    Data result = cache.get("1");

    System.out.println(result);
  }

}
