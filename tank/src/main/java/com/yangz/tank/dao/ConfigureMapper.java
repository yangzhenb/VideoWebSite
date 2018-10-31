package com.yangz.tank.dao;

import com.yangz.tank.entity.Configure;
import com.yangz.tank.entity.ConfigureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigureMapper {
    long countByExample(ConfigureExample example);

    int deleteByExample(ConfigureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Configure record);

    int insertSelective(Configure record);

    List<Configure> selectByExample(ConfigureExample example);

    Configure selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Configure record, @Param("example") ConfigureExample example);

    int updateByExample(@Param("record") Configure record, @Param("example") ConfigureExample example);

    int updateByPrimaryKeySelective(Configure record);

    int updateByPrimaryKey(Configure record);
}