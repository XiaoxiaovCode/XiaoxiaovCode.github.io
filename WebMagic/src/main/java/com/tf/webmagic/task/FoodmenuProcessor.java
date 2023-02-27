package com.tf.webmagic.task;

import com.tf.webmagic.pojo.Foodmenu;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
@Component
public class FoodmenuProcessor implements PageProcessor {
    private String url="http://caipu.haochi123.com/C_News/List_News.htm";
    @Override
    public void process(Page page) {
       /* String html=page.getHtml().toString();
        System.out.println(123);*/
        List<Selectable> list=page.getHtml().xpath("//*[@id=\"Be_Bo\"]/div").nodes();

        if (list.size()==0){
            this.saveFoodsmenuinfo(page);
        }else {
            for(Selectable selectable:list){
                String foodmenu=selectable.links().toString();
                System.out.println(foodmenu);
                page.addTargetRequest(foodmenu);
            }
        }
        String html = page.getHtml().toString();
    }

    public void saveFoodsmenuinfo(Page page) {
        //创建景点详情对象
        Foodmenu foodmenu =new Foodmenu();
        //解析页面
        Html html=page.getHtml();
        //获取数据，封装到对象中
        foodmenu.setName(html.xpath("//*[@id=\"Be_Ti\"]/h1/text()").toString());
        foodmenu.setDescription(Jsoup.parse(html.xpath("//*[@id=\"Ch_Bo_Ma\"]/div[8]/div[2]/text()").toString()).text());
        foodmenu.setTips(Jsoup.parse(html.xpath("//*[@id=\"Ch_Bo_Ma\"]/div[11]/div[2]/a[1]").toString()).text());
        foodmenu.setUrl(page.getUrl().toString());
        //保存结果
        page.putField("foodmenu", foodmenu);
    }

    private Site site=Site.me()
            .setCharset("gbk")
            .setTimeOut(10*1000)
            .setRetrySleepTime(3000)
            .setRetryTimes(3);
    @Override
    public Site getSite() {
        return site;
    }
    @Autowired
    private SpringDataPipeline springDataPipeline;
    @Scheduled(initialDelay = 1000,fixedDelay = 10000)
    public void process(){
       Spider.create(new FoodmenuProcessor())
               .addUrl(url)
               .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
               .thread(10)
               .addPipeline(springDataPipeline)
               .run();
   }
}
