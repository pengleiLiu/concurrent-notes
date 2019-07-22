package org.lpl.concurrent.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author lpl
 * @Date 2019-07-22 18:56
 * @Version 1.0
 **/
public class Cache<String, Data> {

  final Map<String, Data> m = new HashMap<>();

  final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

  //读锁
  final Lock r = rwl.readLock();
  //写锁
  final Lock w = rwl.writeLock();

  /**
   * 读缓存
   */
  Data get(String getKey) {
    r.lock();
    try {
      return m.get(getKey);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      r.unlock();
    }
    return null;
  }

  /**
   * 读缓存
   */
  String put(String key, Data data) {

    m.put(key, data);
    return key;
  }
}

class Data {

  private Integer id;
  private String jsonData;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getJsonData() {
    return jsonData;
  }

  public void setJsonData(String jsonData) {
    this.jsonData = jsonData;
  }
}
