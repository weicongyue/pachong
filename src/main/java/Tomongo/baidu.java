package Tomongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.alibaba.fastjson.JSONObject;


public class baidu {

	private String author;// 编号

	private String title;// 标题

	private String dates;// 日期

	private String belongs;// 标签

	private String des;// 分类

	private String answers;// 阅读人数

	private String withname;// 评论人数

	private String withoutname;// 是否原创

	private String dater; //文字内容

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBelongs() {
		return belongs;
	}

	public void setBelongs(String belongs) {
		this.belongs = belongs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates= dates;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getWithname() {
		return withname;
	}

	public void setWithname(String withname) {
		this.withname = withname;
	}

	public String getWithoutname() {
		return withoutname;
	}

	public void setWithoutname(String withoutname) {
		this.withoutname = withoutname;
	}

	public String getDater() {
		return dater;
	}

	public void setDater(String dater) {
		this.dater = dater;
	}

	
	@SuppressWarnings("unchecked")
	public static JSONObject StringToJson(
			List<String> repliers,String[] answers,
			List<String> dates,String question,String desc,String author,String date) {
					Map<String, Object> map = new HashMap<>();
					map.put("question", question);
					map.put("desc", desc);
					map.put("author", author);
					map.put("date", date);
					//JSONObject jsonObject = JSONObject.fromObject(map);
					JSONObject jsonObject = new JSONObject (map);
					
					
//					JSONObject jsonObj=JSONObject.froanmObject(json); 删掉
//					改成JSONObject jsonObj=new JSONObject (jsondata);
					
					List<JSONObject> list = new ArrayList<>();
					Map oo = new HashMap();
					if(dates.size()!=0){
						int i =0;
						for(String string:dates){
							Map o = new HashedMap();
							o.put("date", string);
							o.put("answer",answers[i]);
							o.put("replier", repliers.get(i++));
							//JSONObject jsonObject2 = JSONObject.fromObject(o);
							JSONObject jsonObject2=new JSONObject (o);
							list.add(jsonObject2);
						}
						oo.put("answers", list.toArray());
					}
					if(!oo.isEmpty()){
						jsonObject.putAll(oo);
					}
					return jsonObject;
				}
}
