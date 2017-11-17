package shuju;

import java.util.List;  

import javax.management.JMException;  
  

import us.codecraft.webmagic.Page;  
import us.codecraft.webmagic.Site;  
import us.codecraft.webmagic.Spider;  
import us.codecraft.webmagic.monitor.SpiderMonitor;  
import us.codecraft.webmagic.pipeline.ConsolePipeline;  
import us.codecraft.webmagic.processor.PageProcessor;  
/** 
 * PageProcessor是webmagic-core的一部分，定制一个PageProcessor即可实现自己的爬虫逻辑。 
 * 以下是抓取csdn博客的一段代码 
 */  
public class copyceshi implements PageProcessor {  
    private Site site = Site.me().setSleepTime(1);  
    public Site getSite() {  
        return site;  
    }  
    int temp=1;  
    
    public static final String URL_LIST=
			"http://zhidao\\.baidu\\.com/list\\?"
			+"cid=110106\\&tag=\\w+";
	//某学科的问题列表
    public static final String URL_AIRICLE = 
			"http://zhidao\\.baidu\\.com/question"
			+"/\\d+\\.html\\?fr=qlquick\\&entry"
			+"=qb_list_default";
    //process（过程）  
    public void process(Page page) {  
        //通过page.putField()来保存抽取结果  
      //  page.getHtml().xpath()则是按照某个规则对结果进行抽取，这里抽取支持链式调用  
        List<String> pages=page.getHtml().xpath("[@class='question-title']").links().all();  //定义页面数的(shi jian  dian)
        if (pages.size()>9) {  
            pages.remove(pages.size()-1);  
        }  
        //System.out.println(pages); 
        page.addTargetRequests(pages);//用于获取所有满足"(http://blog\\.csdn\\.net/u012012240/article/month/2016/08/\\d+)"这个正则表达式的链接  
       // 区分是列表页面还是信息页面  
        if (page.getHtml().xpath("//*[@id=\"body\"]/section[4]/div/div[4]/a").match()) {  
            List<String> links = page.getHtml().xpath("[@class='title-link']").links().regex("https://zhidao\\.baidu\\.com/question/\\d+\\.html\\?fr=qlquick\\&entry=qb_list_default\\is_force_answer=0").all();  
            //通过page.addTargetRequests()方法来增加要抓取的URL  
            page.addTargetRequests(links);  
        }else {  
            System.out.println("记录数:"+temp++);  
            page.putField("title", page.getHtml().xpath("[@class='ask-title']/text()").toString());  
        }  
    }  
                              
    //执行这个main方法，即可在控制台看到抓取结果。webmagic默认有3秒抓取间隔，请耐心等待。  
    public static void main(String[] args) {  
        //Spider是爬虫的入口类,addurl为入口url  
        Spider oschinaSpider = Spider.create(new copyceshi()).addUrl("http://zhidao.baidu.com/list?cid=110106&tag=JSP")  //http://blog.csdn.net/CHENYUFENG1991/article/list/1
                //Pipeline是结果输出和持久化的接口，这里ConsolePipeline表示结果输出到控制台  
                .addPipeline(new ConsolePipeline());  
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