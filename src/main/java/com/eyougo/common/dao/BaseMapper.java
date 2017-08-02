package com.eyougo.common.dao;


/**
 * Created by mei on 16/05/2017.
 */
public interface BaseMapper<T> {

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);
}
