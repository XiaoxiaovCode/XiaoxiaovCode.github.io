package com.tf.webmagic.service;


import com.tf.webmagic.pojo.Foodmenu;

import java.util.List;

public interface FoodmenuService {
    /*保存景点信息*/
    public void save(Foodmenu foodmenu);
    /*根据条件查询景点信息*/
    public List<Foodmenu> select(Foodmenu foodmenu);
}
