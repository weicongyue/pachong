package Tomongo;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * CSDN博客爬虫
 * 
 * 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 */
public class baiduProcessor implements PageProcessor {
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
	public void process(Page page) {
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
			//问题标题
			    page.putField("<div class='question_title'>",
           page.getHtml()
.xpath("//div[@id='wgt-ask']/h1/span/text()"));
			//问题的提问者
			page.putField("</div><div class='question_author'>",
page.getHtml()
.xpath("//div[@id='ask-info']/a/text()").all());
			//问题所属科目
page.putField("</div><div class='question_belongs'>", page.getHtml()
.xpath("//span[@class='question-list-item-tag']/a/text()").all());
			//问题描述
page.putField("</div><div class='question_des'>", page.getHtml()
.xpath("//div[@id='wgt-ask']/pre/text()"));
			//问题的提问日期
page.putField("</div><div class='question_date'>", page.getHtml()
.xpath("//div[@id=\"ask-info\"]/span[@class='grid-r ask-time']/text()"));
			//问题的答案
page.putField("</div><div class='answers'>", page.getHtml()
.xpath("//div[@class=\"line content\"]").all());
			//答案提供者昵称
page.putField("</div><div class='replier_withname'>", page.getHtml()
.xpath("//div[@class='line info f-light-gray mb-5 f-12']//a/text()").all());
//答案提供者匿名昵称
page.putField("</div><div class='replier_withoutname'>", page.getHtml()
.xpath("//div[@class='line info f-light-gray mb-5 f-12']//text()").all());
			//回答日期
page.putField("</div><div class='reply_date'>", page.getHtml()
.xpath("//div[@class='line info f-light-gray mb-5 f-12']/span[@class='grid-r pos-time']/text()")
.all());
		}
	}
	public Site getSite() {
		return site;
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Spider.create(new baiduProcessor())
.addUrl("http://zhidao.baidu.com/list?cid=110106&tag=JSP")
.addPipeline(new FilePipeline("F:\\tomango"))  
.run();;
	}
}