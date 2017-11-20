import java.io.IOException;


public class JsoupTest {


    public static void main(String[] args) throws IOException{

        String baseUrl = "https://www.toutiao.com/search_content/";

        //Number 标题数
        //分类：1 综合 , 3 图集;  2是视频, 本次不爬
        String cur_tab = "3";
        //Crawler.toutiaoImgCrawler(baseUrl, "美女", 120, cur_tab);
        Crawler.toutiaoImgCrawler(baseUrl,"性感 美女",140, cur_tab);
    }
}
