package com.xf.cs.en.DB;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * CSDN博客爬虫
 * 
 * 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 */
public class baiduProcessor implements PageProcessor {

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

			//https://zhidao.baidu.com/question/2057683075320906187.html?fr=qlquick&entry=qb_list_default&is_force_answer=0
			//https://zhidao.baidu.com/list?cid=110106&tag=JSP
	public Site getSite() {
		return site;
	}

	
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		// 列表页
		//如果匹配学科列表，就将这些链接加到待爬取队列
		if(page.getUrl().regex(URL_LIST).match()){
		page.addTargetRequests(page.getHtml()
		.xpath("//ul[@class='question-list-ul']")
		.links().regex(URL_AIRICLE).all());
		page.addTargetRequests(page.getHtml().links()
		.regex(URL_LIST).all());
		}
		
		//某学科的问题列表
		else{
			size++;// 文章数量加1
			baidu shujuku = new baidu();
			//问题标题
			
	        page.putField("'question_title'",page.getHtml().xpath("//div[@id='wgt-ask']/h1/span/text()"));
	        //shujuku.setTitle(page.getHtml().xpath("//div[@id='wgt-ask']/h1/span/text()").get());

			//问题的提问者
			page.putField("'question_author'",
					page.getHtml().xpath("//div[@id='ask-info']/a/text()").all());
			//shujuku.setAuthor(page.getHtml().xpath("//div[@id='ask-info']/a/text()").get());
						//问题所属科目
			
			// 把对象存入数据库
			new baiduDao().add(shujuku);
			// 把对象输出控制台
			System.out.println(shujuku);
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
	Spider.create(new baiduProcessor()).addUrl("http://zhidao.baidu.com/list?cid=110106&tag=JSP" ).thread(5).run();
	endTime = System.currentTimeMillis();
	System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
}
}