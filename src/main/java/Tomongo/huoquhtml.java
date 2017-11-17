package Tomongo;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * CSDN博客爬虫
 * 
 * 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 */
public class huoquhtml implements PageProcessor {

	private static int size = 0;// 共抓取到的文章数量
	private Site site=Site.me()
			.setDomain("zhidao.baidu.com")
			.setRetryTimes(2)
			.setSleepTime(100)
			.setUseGzip(true);
				//学科列表
				public static final String URL_LIST=
			"http://zhidao\\.baidu\\.com/list\\?"
			+"cid=110106";
				//某学科的问题列表
			public static final String URL_AIRICLE = 
			"http://zhidao\\.baidu\\.com/question"
			+"/\\d+\\.html\\?fr=qlquick\\&entry"
			+"=qb_list_default";
	public Site getSite() {
		return site;
	}

	
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		
		//如果匹配学科列表，就将这些链接加到待爬取队列
		       Html html = (Html) page.getHtml();
		       System.out.println(html);
		       if(page.getUrl().regex(URL_AIRICLE).match()){
		   		page.addTargetRequests(page.getHtml()		   		
		   		.links().regex(URL_AIRICLE).all());
		   		
		   		}
				else{
					size++;
					System.out.println(html);
//					for(size=0;size<30;size++){
//					 Html html = (Html) page.getHtml();
//					 System.out.println(html);
//				}
				}
			
				System.out.println(html);
           
        
	        
		}
	

	

public static void main(String[] args) {
	long startTime, endTime;
	
	startTime = System.currentTimeMillis();
	// 从用户博客首页开始抓，开启5个线程，启动爬虫
	Spider.create(new huoquhtml()).addUrl("http://zhidao.baidu.com/list?cid=110106" ).thread(5).run();
	endTime = System.currentTimeMillis();
	System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
}
}