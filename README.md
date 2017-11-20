# ToutiaoImgCrawler
今日头条美女图片爬虫

其实你想爬其它主题的图片也是可以的，更换keyword就行了。

先分析url, baseUrl = https://www.toutiao.com/search_content/

参数在url和下面都能看到

![img](https://github.com/FinFantasy/Photo/blob/master/photo/ToutiaoImgCrawler/url.png)

返回json数据, 分析json数据, 图片地址就在image_detail的url里

![img](https://github.com/FinFantasy/Photo/blob/master/photo/ToutiaoImgCrawler/jsonData.png)


结果：

![img](https://github.com/FinFantasy/Photo/blob/master/photo/ToutiaoImgCrawler/crawler.png)