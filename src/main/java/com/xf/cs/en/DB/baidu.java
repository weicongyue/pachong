package com.xf.cs.en.DB;

public class baidu {

	private String author;// 编号

	private String title;// 标题

	private String dateq;// 日期

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

	public String getDateq() {
		return dateq;
	}

	public void setDateq(String dateq) {
		this.dateq = dateq;
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

	    @Override
        public String toString() {
		return "CsdnBlog [author=" + author + ", title=" + title + ", belongs=" + belongs+ ",date=" + dateq + ",  des=" +  des + ", answers="
				+ answers + ", withname=" + withname + ", withoutname=" + withoutname + ", dater=" + dater + "]";
	    
	}

}

