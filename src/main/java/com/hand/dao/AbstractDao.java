package com.hand.dao;

/**
 * 数据库操作抽象类
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 15:09
 */
public abstract class AbstractDao<T> {

    public abstract void insert(T t);
}
