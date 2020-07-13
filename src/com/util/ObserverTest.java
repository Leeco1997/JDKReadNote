package com.util;

import java.util.Observable;
import java.util.Observer;

/**
 * @author liqiao
 * @date 2020/6/3 17:14
 * @description 详见设计模式-观察者模式 【package: designModel.ObserverPattern】
 * Observable里通知观察者有两部操作：
 *         1.setChanged();
 *         2.notifyObservers();
 */

public class ObserverTest implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        //Observable里的方法都使用synchronized加锁。
    }
}
