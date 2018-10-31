package com.yangz.tank.dao;

import com.yangz.tank.entity.Videostate;
import com.yangz.tank.entity.VideostateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VideostateMapper {
    long countByExample(VideostateExample example);

    int deleteByExample(VideostateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Videostate record);

    int insertSelective(Videostate record);

    List<Videostate> selectByExample(VideostateExample example);

    Videostate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Videostate record, @Param("example") VideostateExample example);

    int updateByExample(@Param("record") Videostate record, @Param("example") VideostateExample example);

    int updateByPrimaryKeySelective(Videostate record);

    int updateByPrimaryKey(Videostate record);
}