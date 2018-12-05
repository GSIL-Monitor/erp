package com.stosz.tms.service;

import com.stosz.tms.mapper.BaseDictionaryMapper;
import com.stosz.tms.model.BaseDictionary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BaseDictionaryService {

    @Resource
    private BaseDictionaryMapper dictionaryMapper;

    public List<BaseDictionary> query(String type){
        BaseDictionary paramBean = new BaseDictionary();
        paramBean.setType(type);
        return dictionaryMapper.select(paramBean);
    }

}
