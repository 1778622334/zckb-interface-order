package cn.gydata.zckb.zckbinterfaceorder.service;

import cn.gydata.zckb.zckbinterfaceorder.auth.BaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import tk.mybatis.mapper.provider.SqlServerProvider;

import java.util.List;

public class BaseService<T> {

    protected BaseMapper<T> mapper;

    @Options(useGeneratedKeys = true)
    @InsertProvider(type = SqlServerProvider.class, method = "dynamicSQL")
    public void insert(T t){
        mapper.insert(t);
    }


    public void insertSelective(T t){
        mapper.insertSelective(t);
    }

    public void update(T t){
        mapper.updateByPrimaryKey(t);
    }

    /**
     *
     * @param t 更改的值
     * @param example 条件
     */
    public void updateByParam(T t,Object example){
        mapper.updateByExampleSelective(t,example);
    }

    public T getById(Object id){
        return mapper.selectByPrimaryKey(id);
    }

    public int countByParam(T param){
        return mapper.selectCount(param);
    }

    public int countByExampleParam(Object param){
        return mapper.selectCountByExample(param);
    }

    public PageInfo<T> getAllWithPage(int page, int size, String order){
        PageHelper.startPage(page,size,order);
        List<T> list = mapper.selectAll();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


    public void updateByPrimaryKeySelective(T t){
        mapper.updateByPrimaryKeySelective(t);
    }

    public PageInfo<T> getAllByParamWithPage(Object param, int page, int size, String order){
        PageHelper.startPage(page,size,order);
        List<T> list = mapper.selectByExample(param);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo<T> getAllOrderByIdDescWithPage(int page, int size){
        String order = "id desc";
        return getAllWithPage(page,size,order);
    }

    public PageInfo<T> getAllByParamOrderByIdDescWithPage(Object param, int page, int size){
        String order = "id desc";
        return getAllByParamWithPage(param,page,size,order);
    }

    public T selectOne(T t){
        return mapper.selectOne(t);
    }

    public T selectOneById(T t){
        return mapper.selectOne(t);
    }

    public List<T> getListByParam(T t){
        return mapper.select(t);

    }

    public List<T> getListByExampleParam(Object param){
        return mapper.selectByExample(param);
    }

    public void deleteByPrimaryKey(Object id){
        mapper.deleteByPrimaryKey(id);
    }

    public void delete(T record){
        mapper.delete(record);
    }

    public void deleteByExample(Object object){
        mapper.deleteByExample(object);
    }
}
