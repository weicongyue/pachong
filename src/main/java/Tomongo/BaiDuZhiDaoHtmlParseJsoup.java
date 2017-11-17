package Tomongo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.xf.cs.en.DB.baidu;

//import com.alibaba.fastjson.JSONObject;
import org.jsoup.select.Elements;


public class BaiDuZhiDaoHtmlParseJsoup {
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		BaiDuZhiDaoHtmlParseJsoup htmlParseJsoup = new BaiDuZhiDaoHtmlParseJsoup();
		String filepath="F:\\tomango\\zhidao.baidu.com";
		File file=new File(filepath);
		if(file.isDirectory()){
			String[] filelist=file.list();
			for(int i=0;i<filelist.length;i++){
				File input=new File(filepath+"\\"+filelist[i]);
try {
					Document doc=Jsoup.parse(input, "UTF-8", "");
					//Document doc = Jsoup.parse(input,"UTF-8","http://zhidao.baidu.com/");

					
					

					//问题的题目
					Elements question_title = doc.getElementsByClass("question_title");
					String title = question_title.text();
					title = title.substring(title.indexOf(":")+1,
					title.length()).trim();

					//问题的提问作者
					Elements question_author = doc.getElementsByClass("question_author");
					String author = question_author.text();
					author = author.substring(author.indexOf(":")+1,
					author.length()).trim();

					//问题的提问时间
					Elements question_date=doc.getElementsByClass("question_date");
					String q_date=question_date.text();
					q_date = q_date.substring(q_date.indexOf(":")+1,
					q_date.length()).trim();

					//问题的详细描述
					Elements question_des=doc.getElementsByClass("question_des");
					String question=question_des.text();
					question = question.substring(question.indexOf(":")+1,
					question.length()).trim();

					//问题回答时间
					Elements reply_date=doc.getElementsByClass("reply_date");
					List dateList = null;
					String r_date=reply_date.text();
					r_date=r_date.substring(r_date.indexOf(":")+1,
					r_date.length()).trim();
					r_date=r_date.replaceAll(" ", "");
					//dateList=htmlParseJsoup.stringToDateList(r_date,15);

					//问题回答人
					FileInputStream fileInputStream=new FileInputStream(input);
					BufferedReader br = new BufferedReader(
					new InputStreamReader(fileInputStream));
					List<String> list=new ArrayList<>();
					boolean flag=false;
					String line=br.readLine();
					while (line!=null) {
					if (line.equals("</div><div class='replier_withoutname'>:")) {
					flag=true;
					}
					if (line.equals("</div><div class='reply_date'>:")) {
					flag=false;
					}
					if(flag){
					list.add(line);
					}
					line=br.readLine();
					}
					list.remove(0);
					Elements replier=doc.getElementsByClass("replier_withname");
					String repliername_list=replier.text();
					repliername_list=repliername_list.substring(repliername_list
					.indexOf(":")+2, repliername_list.length());
					String[] sList=repliername_list.split(" ");
					int j =0;
					List<String> repliers = new ArrayList<>();
					for (String object : list) {
					if(object.length()!=6){
					repliers.add(sList[j++]);
					}
					else {
					repliers.add(object);
					}	
					}

					//问题回答答案
					Elements answers = doc.getElementsByClass("answers");
					String s_answers=answers.text();
					s_answers=s_answers.substring(s_answers.indexOf(":")+1,
					s_answers.length()).trim();
					s_answers=s_answers.replaceAll("评论 | 分享", " ");
					String[] List_answers=s_answers.split("\\| ");
					for (String string : List_answers) {
					System.out.println(string);
					baidu shujuku = new baidu();
					new MongoDb_Test().add(shujuku);
					}				
}
			catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}




