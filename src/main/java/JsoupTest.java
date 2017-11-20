import java.io.IOException;


public class JsoupTest {


    public static void main(String[] args) throws IOException{

        String toutiaoBaseUrl = "https://www.toutiao.com/search_content/";

        //Number 标题数
        //分类：1 综合 , 3 图集;  2是视频, 本次不爬
        String cur_tab = "3";
        //ToutiaoImgCrawler.toutiaoImgCrawler(toutiaoBaseUrl, "美女", 120, cur_tab);

        String baiduBaseUrl = "https://image.baidu.com/search/acjson";
        //Number 即为图片张数
        BaiduImgCrawler.baiduImgCrawler(baiduBaseUrl, "美女", 320);
    }
}
