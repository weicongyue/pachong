package shuju;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class qadui implements PageProcessor {

	private static int size = 0;// 共抓取到的文章数量
	private Site site=Site.me()
			.setDomain("zhidao.baidu.com")
			.setRetryTimes(2)
			.setSleepTime(100)
			.setUseGzip(true);
	//学科列表
	public static final String URL_LIST=
			"http://zhidao\\.baidu\\.com/list\\?"
			+"cid=110106\\&tag=\\w+";
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
		
		//page.getHtml().xpath()则是按照某个规则对结果进行抽取，这里抽取支持链式调用  
        List<String> pages=page.getHtml().xpath("//*[@id=\"body\"]/section[4]/div/div[4]").links().all();  
        if (pages.size()>5) {  
            pages.remove(pages.size()-1);  
        }  
        page.addTargetRequests(pages);//用于获取所有满足"(http://blog\\.csdn\\.net/u012012240/article/month/2016/08/\\d+)"这个正则表达式的链接  
        //区分是列表页面还是信息页面  
		
		// 列表页
		//如果匹配学科列表，就将这些链接加到待爬取队列
		if(page.getUrl().regex(URL_LIST).match()){
		page.addTargetRequests(page.getHtml()
		.xpath("//ul[@class='question-list-ul']")
		.links().regex(URL_AIRICLE).all());
		page.addTargetRequests(page.getHtml().links()
		.regex(URL_LIST).all());
		}
		
//		 List<String> links = page.getHtml().xpath("[@class='link_title']").links().regex("http://blog\\.csdn\\.net/chenyufeng1991/article/details/\\d+").all();  
//       //通过page.addTargetRequests()方法来增加要抓取的URL  
//       page.addTargetRequests(links); 
//		//某学科的问题列表
		else{
			size++;// 文章数量加1
			baidu shuju = new baidu();
			//问题标题
			//这一句必须要加上，不然都用不了JsonFilePipeline方法。
			page.putField("</div><div id='question_author'>",
			page.getHtml().xpath("//*[@id=\"body\"]").all());
     		shuju.setAuthor(page.getHtml().xpath("//*[@id=\"body\"]").get());
     		
     		new baiduDao().add(shuju);
			// 把对象输出控制台
			System.out.println(shuju);
		}
	}

	
	// 把list转换为string，用,分割
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

public static void main(String[] args) {
	long startTime, endTime;
	System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
	startTime = System.currentTimeMillis();
	// 从用户博客首页开始抓，开启5个线程，启动爬虫
	Spider.create(new qadui()).addUrl("http://zhidao.baidu.com/list?cid=110106&tag=JSP" )
	.addPipeline(new JsonFilePipeline("F:/webmagic"))
	.thread(5)
	.run();
	endTime = System.currentTimeMillis();
	System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
}
}
