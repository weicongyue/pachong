package Tomongo;

import java.io.File;
import java.io.IOException;

import javax.swing.text.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class JsoupTest {
public static void main(String[] args) throws IOException {
File input = new File("F:/webmagic/zhidao.baidu.com/new.txt");  
Document doc = Jsoup.parse(input,"UTF-8","http://zhidao.baidu.com/"); 
Elements one = doc.getElementsByClass("tableone"); 
Elements two = doc.getElementsByClass("tabletwo");
Elements three = doc.getElementsByClass("tablethree");
Elements four = doc.getElementsByClass("tablefour");
//System.out.println("得到的结果是:"+id.text());
System.out.println(one.text());
 
		 System.out.println(  
		   "\"{" +"\""+ "+" +"\""+ "'one':'"   +  one.text()+"',"
		  +"\""+ "+"+"\""+ "'two':'"   +  two.text()   + "',"
		  +"\""+ "+"+"\""+ "'three':'"   +  three.text() + "',"
		  +"\""+ "+"+"\""+ "'four':'"   +  four.text()  + "'"
		  +"\""+ "+"+"\"}"+"\""
		                    );

}
}

//" {" +" 'school_code' : '111111', "  +" 'teacher_idcard' : '0000001', " +" 'teacher_name' : '高育良'" } "