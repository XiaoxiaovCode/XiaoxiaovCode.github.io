package com.tf.webmagic.service.impl;


import com.tf.webmagic.dao.FoodmenuDao;
import com.tf.webmagic.pojo.Foodmenu;
import com.tf.webmagic.service.FoodmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FoodmenuServiceImpl implements FoodmenuService {
    @Autowired
    FoodmenuDao dao;
    @Override
    @Transactional
    public void save(Foodmenu foodmenu) {
        //根据url查询数据
        Foodmenu param=new Foodmenu();
        param.setUrl(foodmenu.getUrl());

        //执行查询
        List<Foodmenu> list=this.select(param);
        //判断查询结果是否为空
        if(list.size()==0){
            //如果查询结果为空，表示景点信息不存在，或者已经更新了，需要新增或者更新数据库
            this.dao.saveAndFlush(foodmenu);
        }
    }

    @Override
    public List<Foodmenu> select(Foodmenu foodmenu) {
        //设置查询条件
        Example example=Example.of(foodmenu);
        List list=this.dao.findAll(example);
        return list;
    }
}
