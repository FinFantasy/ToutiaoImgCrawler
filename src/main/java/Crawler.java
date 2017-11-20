import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author liyonghao 2017.11
 */
public class Crawler {

    private static String getUrl(String url, Map<String, String> params, String charset) {
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String key = entry.getKey();
                String value = entry.getValue();
                paramList.add(new BasicNameValuePair(key, value));
            }
            try {
                String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(paramList, charset));
                StringBuffer sb = new StringBuffer();
                sb.append(url);
                if (url.indexOf("?") > 0) {
                    sb.append("&");
                } else {
                    sb.append("?");
                }
                sb.append(paramStr);
                url = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    private static String getUrl(String baseUrl, int offset, String keyword, String cur_tab) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("offset", String.valueOf(offset));
        map.put("format","json");
        map.put("keyword", keyword);
        map.put("autoload","true");
        map.put("count", "20");
        map.put("cur_tab", cur_tab);
        return getUrl(baseUrl, map, "UTF-8");
    }

    private static void DonloadImg(String url, String imgName) throws IOException {

        String base = "/home/liyonghao/Pictures/";
        Connection.Response response = Jsoup.connect(url)
                .proxy("proxy1.bj.petrochina",8080)
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                .ignoreContentType(true)
                .execute();
        String returnType = response.contentType().substring(response.contentType().lastIndexOf("/")+1);
        byte[] img = response.bodyAsBytes();
        String filename = base + imgName + "." + returnType;
        System.out.println(filename);

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = new File(filename);
        if (file.exists()) {
            return;
        }
        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        bos.write(img);
        fos.close();
        bos.close();
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
                String url = getUrl(baseUrl, offset, keyword, cur_tab);

                Connection.Response response = Jsoup.connect(url)
                        .proxy("proxy1.bj.petrochina",8080)
                        .header("Accept", "*/*")
                        .header("Accept-Encoding", "gzip, deflate")
                        .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                        .header("Content-Type", "application/json;charset=UTF-8")
                        .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                        .timeout(10000).ignoreContentType(true)
                        .execute();

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
                            DonloadImg(object2.getString("url"), imgName+j);
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
