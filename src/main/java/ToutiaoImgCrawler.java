import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.*;

/**
 * @author liyonghao 2017.11
 */
public class ToutiaoImgCrawler {



    private static String getToutiaoUrl(String baseUrl, int offset, String keyword, String cur_tab) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("offset", String.valueOf(offset));
        map.put("format","json");
        map.put("keyword", keyword);
        map.put("autoload","true");
        map.put("count", "20");
        map.put("cur_tab", cur_tab);
        return CrawlerHelper.getUrl(baseUrl, map, "UTF-8");
    }


    /**
     *
     * @param baseUrl
     * @param keyword 关键词
     * @param Number 标题数,一个标题有多张图片
     * @param cur_tab 分类
     * @throws IOException
     */
    public static void toutiaoImgCrawler(String baseUrl, String keyword, int Number, String cur_tab) throws IOException{
        int offset = 0;
        while (Number > 0) {
            try {
                String url = getToutiaoUrl(baseUrl, offset, keyword, cur_tab);

                Connection.Response response = CrawlerHelper.connect(url);

                JSONObject jsonObject = JSONObject.parseObject(response.body());
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i=0; i < jsonArray.size(); i++) {

                    JSONObject object1 = jsonArray.getJSONObject(i);
                    JSONArray jsonArray1 = object1.getJSONArray("image_detail");
                    String imgName = object1.getString("media_name");
                    for (int j=0; j < jsonArray1.size(); j++) {

                        JSONObject object2 = jsonArray1.getJSONObject(j);
                        System.out.println(object2.getString("url"));
                        try {
                            CrawlerHelper.DonloadImg(object2.getString("url"), imgName+j);
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e) {
            }
            offset += 20;
            Number -= 20;
        }
    }

}
