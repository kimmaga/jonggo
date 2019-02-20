package Test01;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Ex01 {
	static String BASE_URL_F ="http://cafe.naver.com"; //페이지가 나오기 전 "=" 까지 복사해서 붙여넣음
	static String BASE_URL_B = "/ArticleList.nhn?search.clubid=10050146&userDisplay=50&search.boardtype=L&search.specialmenutype=&search.questionTab=A&search.totalCount=501&search.page="; // "="뒤에 숫자를 제외한 나머지 부분을 붙여 넣음
	static int BASE_URL_PAGE = 1; //페이지를 변경하기 위한 변수
	static String COMPLETE_URL = BASE_URL_F+BASE_URL_B+BASE_URL_PAGE;//완성된 URL주소
	public static void main(String[] args) throws IOException {
		try {
			OutputStream output = new FileOutputStream("C:/Temp/output.txt");
			int page =1;
			while(page<10) {
				Document joongoNara = Jsoup.connect(BASE_URL_F+BASE_URL_B+page)
						.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)")
						.header("Connection", "keep-alive")
						.get();
				
				Elements allCategory = joongoNara.select("a.article");

				for(Element element : allCategory) { 
					Document detail = Jsoup.connect(BASE_URL_F+element.attr("href"))
							.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)")
							.header("Connection", "keep-alive")
							.get();
					String url = element.attr("href");
					String title = detail.select("p.title").text(); 
					String price = detail.select("span.cost").text();
					String text = detail.select("span").text();
					byte[] by = (title+"\r\n").getBytes();
					output.write(by);
					System.out.println(title+","+page);
	
				}
				
				page++;
			}
			output.close();
			
		}catch(Exception e) {
			e.getStackTrace();
		}
	}
}
