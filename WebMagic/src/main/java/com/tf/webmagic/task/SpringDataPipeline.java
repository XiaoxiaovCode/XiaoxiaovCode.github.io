package com.tf.webmagic.task;

import com.tf.webmagic.pojo.Foodmenu;
import com.tf.webmagic.service.FoodmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class SpringDataPipeline implements Pipeline {
    @Autowired
    private FoodmenuService foodmenuService;
    @Override
    public void process(ResultItems resultItems, Task task) {


        //获取封装好对象
        Foodmenu foodmenu =resultItems.get("foodmenu");
        //判断是否为空
            if(foodmenu !=null){
                //如果不为空把数据存到数据库
                this.foodmenuService.save(foodmenu);
            }
    }
}
