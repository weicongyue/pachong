package shuju;

import java.util.List;

import javax.management.JMException;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
 /** 
    定制爬虫的核心部分
 */  
public class baiduzhengti implements PageProcessor {  
    private Site site = Site.me().setSleepTime(1);  
    public Site getSite() {  
        return site;  
    }  
    int temp=1;  
     //process（过程）  
     //学科列表
     //初始页面的url的正则表达式
  	public static final String URL_LIST=
  			"http://zhidao\\.baidu\\.com/list\\?"
  			+"cid=110106\\&tag=\\w+";
  	
  	   //某学科的问题列表
       //问题页面的正则表达式
      public static final String URL_AIRICLE = 
  			"http://zhidao\\.baidu\\.com/question"
  			+"/\\d+\\.html\\?fr=qlquick\\&entry"
  			+"=qb_list_default";
    
    public void process(Page page) {  
    	//class是每个页面中问题列表中单个问题的url
        List<String> pages = page.getHtml().xpath("[@class='question-list-item']").links().all(); 
        //如果进入这个页面后class符合要求那么就将其链接加入爬取队列
        page.addTargetRequests(pages);
        //将数据存入数据库
        baidu shuju = new baidu();
        //将列表中的一条打开后的页面的标题获取。class为单个问题页面的class。
        page.putField("title", page.getHtml().xpath("//[@class='ask-title']/text()").toString()); 
 		shuju.setAuthor(page.getHtml().xpath("//[@class='ask-title']/text()").get()); 		
 		new baiduDao().add(shuju);
		// 把对象输出控制台
		System.out.println(shuju);
        // System.out.println(pages);
    }

    //执行这个main方法，即可在控制台看到抓取结果。webmagic默认有3秒抓取间隔，请耐心等待。  
    public static void main(String[] args) {  
        //Spider是爬虫的入口类,addurl为入口url  
        Spider oschinaSpider = Spider.create(new baiduzhengti()).addUrl("http://zhidao.baidu.com/list?cid=110106&tag=JSP")  
                //Pipeline是结果输出和持久化的接口，这里ConsolePipeline表示结果输出到控制台  

//类                                           	说明	                                      备注
//ConsolePipeline	              输出结果到控制台	抽取结果需要实现toString方法
//FilePipeline	                       保存结果到文件	         抽取结果需要实现toString方法
//JsonFilePipeline	     JSON格式保存结果到文件	 
//ConsolePageModelPipeline	                 (注解模式)输出结果到控制台	 
//FilePageModelPipeline	                     (注解模式)保存结果到文件	 
//JsonFilePageModelPipeline	                 (注解模式)JSON格式保存结果到文件	想要持久化的字段需要有getter方法
                .addPipeline(new JsonFilePipeline("F:/data"));  //将文件存贮到本地继续进行解析jsoup
        try {  
            //添加到JMT监控中  
            SpiderMonitor.instance().register(oschinaSpider);  
            //设置线程数  
            //oschinaSpider.thread(5);  
            oschinaSpider.run();  
        } catch (JMException e) {  
            e.printStackTrace();  
        }   
    }  
}  