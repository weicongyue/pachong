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
public class souwenzhengti implements PageProcessor {  
    private Site site = Site.me().setSleepTime(1);  
    public Site getSite() {  
        return site;  
    }  
    int temp=1;  
    //process（过程）  
	public static final String URL_LIST= "http://wenwen\\.sogou\\.com/question/\\?qid=\\d+\\&ch=ww\\.fly\\.newques";
			
	//某学科的问题列表
    public static final String URL_AIRICLE ="http://wenwen\\.sogou\\.com/cate/tag\\?tag_id=137\\&tp=\\d+\\&pno=\\d+\\&ch=ww\\.fly\\.fy\\d+\\#questionList";
		
    public void process(Page page) {  
        List<String> pages = page.getHtml().xpath("[@class='btn-page-num']").links().all(); //页面数
        page.addTargetRequests(pages);
        
        if (page.getUrl().regex(URL_AIRICLE).match()) { 
            List<String> links = page.getHtml().xpath("[@class='sort-lst-tab']").links().regex(URL_LIST).all();  
           //通过page.addTargetRequests()方法来增加要抓取的URL                                   
            page.addTargetRequests(links);  
            System.out.println(links);
          
       }
         
         else {  
            System.out.println("记录数:"+temp++);  
            //page.putField("title", page.getHtml().xpath("//[@class='detail-tit']/text()").toString()); 
            baidu shuju = new baidu();
			//问题标题
			//这一句必须要加上，不然都用不了JsonFilePipeline方法。
			page.putField("以下是需要的数据：",
			page.getHtml().xpath("//*[@id=\"container\"]").all());
    		shuju.setAuthor(page.getHtml().xpath("//*[@id=\"container\"]").get());
    		
    		new baiduDao().add(shuju);
			// 把对象输出控制台
			System.out.println(shuju);
       }  
    }  
  
    //执行这个main方法，即可在控制台看到抓取结果。webmagic默认有3秒抓取间隔，请耐心等待。  
    public static void main(String[] args) {  
        //Spider是爬虫的入口类,addurl为入口url  
        Spider oschinaSpider = Spider.create(new souwenzhengti()).addUrl("http://wenwen.sogou.com/cate/tag?tag_id=137&tp=0&pno=0&ch=ww.fly.fy1#questionList")  
                //Pipeline是结果输出和持久化的接口，存储到我们需要的地方 
                .addPipeline(new JsonFilePipeline("F:/data"));  
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