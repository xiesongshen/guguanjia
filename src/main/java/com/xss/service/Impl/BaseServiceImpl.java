package com.xss.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xss.service.BaseService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author XSS
 * @date 2020/7/16
 * @desc
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    Mapper<T> map;

    @Override
    public PageInfo<T> selectPage(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<T> list = map.selectAll();
        return new PageInfo<T> (list);
    }

    @Override
    public int deleteByPrimaryKey(Object o) {
        return map.deleteByPrimaryKey(o);
    }

    @Override
    public int delete(T t) {
        return map.delete(t);
    }

    @Override
    public int insert(T t) {
        return map.insert(t);
    }

    @Override
    public int insertSelective(T t) {

        int i = map.insertSelective(t);
        if (i==0){
            throw new RuntimeException("添加失败");
        }

        return i;
    }

    @Override
    public boolean existsWithPrimaryKey(Object o) {
        return map.existsWithPrimaryKey(o);
    }

    @Override
    public List<T> selectAll() {
        return map.selectAll();
    }

    @Override
    public T selectByPrimaryKey(Object o) {
        return map.selectByPrimaryKey(o);
    }

    @Override
    public int selectCount(T t) {
        return map.selectCount(t);
    }

    @Override
    public List<T> select(T t) {
        return map.select(t);
    }

    @Override
    public T selectOne(T t) {
        return map.selectOne(t);
    }

    @Override
    public int updateByPrimaryKey(T t) {
        return map.updateByPrimaryKey(t);
    }

    @Override
    public int updateByPrimaryKeySelective(T t) {
        int i = map.updateByPrimaryKeySelective(t);

        if (i==0){
            throw new RuntimeException("修改失败");
        }
        return i;
    }

    @Override
    public int deleteByExample(Object o) {
        return map.deleteByExample(o);
    }

    @Override
    public List<T> selectByExample(Object o) {
        return map.selectByExample(o);
    }

    @Override
    public int selectCountByExample(Object o) {
        return map.selectCountByExample(o);
    }

    @Override
    public T selectOneByExample(Object o) {
        return map.selectOneByExample(o);
    }

    @Override
    public int updateByExample(T t, Object o) {
        return map.updateByExample(t,o);
    }

    @Override
    public int updateByExampleSelective(T t, Object o) {
        return map.updateByExampleSelective(t,o);
    }

    @Override
    public List<T> selectByExampleAndRowBounds(Object o, RowBounds rowBounds) {
        return map.selectByExampleAndRowBounds(o,rowBounds);
    }

    @Override
    public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
        return map.selectByRowBounds(t,rowBounds);
    }
}
