package com.yangz.tank.service;

import com.yangz.tank.dao.ConfigureMapper;
import com.yangz.tank.entity.Configure;
import com.yangz.tank.entity.ConfigureExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigureService {

    @Autowired
    private ConfigureMapper configureMapper;

    public Configure getConfigureByName(String name){
        ConfigureExample example = new ConfigureExample();
        ConfigureExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Configure> list = configureMapper.selectByExample(example);
        return list != null?list.get(0) : null;
    }

    public List<Configure> getConfigures(){
        ConfigureExample example = new ConfigureExample();
        return configureMapper.selectByExample(example);
    }
}
