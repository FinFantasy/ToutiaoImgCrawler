import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaiduImgCrawler {

    private static String getBaiduUrl(String baseUrl, int offset, String keyword) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("tn", "resultjson_com");
        map.put("ipn", "rj");
        map.put("word",keyword);
        map.put("pn", String.valueOf(offset));
        map.put("rn","30");

        return CrawlerHelper.getUrl(baseUrl, map, "UTF-8");
    }

    /**
     *
     * @param baseUrl
     * @param keyword
     * @param Number 图片张数
     * @throws IOException
     */
    public static void baiduImgCrawler(String baseUrl, String keyword, int Number) throws IOException{
        int offset = 0;
        while (Number > 0) {
            try {
                String url = getBaiduUrl(baseUrl, offset, keyword);

                Connection.Response response = CrawlerHelper.connect(url);

                JSONObject jsonObject = JSONObject.parseObject(response.body());
                JSONArray jsonArray = jsonObject.getJSONArray("data");


                for (int i=0; i < jsonArray.size(); i++) {

                    JSONObject object1 = jsonArray.getJSONObject(i);
                    String imgUrl = object1.getString("thumbURL");
                    String imgName = object1.getString("os")
                            .substring(object1.getString("os").lastIndexOf(",")+1);

                    try {
                        CrawlerHelper.DonloadImg(imgUrl, imgName);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
            offset += 30;
            Number -= 30;
        }
    }
}
