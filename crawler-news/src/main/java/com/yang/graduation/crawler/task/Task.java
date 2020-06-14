package com.yang.graduation.crawler.task;


import com.yang.graduation.commons.domain.News;
import com.yang.graduation.crawler.pojo.CrawlerNews;
import com.yang.graduation.crawler.util.HttpUtils;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author woshilll
 * @date 2020/1/2 16:48
 */
@Component
public class Task {

    @Resource
    private HttpUtils httpUtils;
    @Reference(version = "1.0.0")
    private NewsService newsService;


    public String getItem(CrawlerNews crawlerNews){
        String newsUrls = crawlerNews.getNewsUrls();
        String[] https = newsUrls.split("https");
        int successCount = 0;
        int count = 0;
        for (String http : https) {
            if (http.startsWith("://") && http.length() > 7) {
                count++;
                http = http.replace(" ", "").replace("\n", "");
                String url = "https" + http;
                String html = httpUtils.getHtml(url);
                if (html == null || html.equals("")) {
                    continue;
                }
                int res = 0;
                try {
                    res = parse(html, crawlerNews.getCategoryId(), crawlerNews.getAudit());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    successCount += res;
                }
            }
        }
       return "一共解析 " + count + " 个地址, 成功 " + successCount + " 个";
    }


    /**
     * 解析html
     * @param html
     * @param categoryId
     * @param audit
     * @return
     * @throws IOException
     */
    private int parse(String html, Integer categoryId, String audit) throws IOException {
        Document doc = Jsoup.parse(html);
        Element root = doc.getElementById("root");
        Elements article = root.select("div[class~=artical*]");
        String title = article.select("h1[class~=topic-*]").text();
        Elements content = article.select("div[class~=text*]");
        String contentHtml = content.html();
        String img = content.select("img").attr("src");
        if (img == null || img.length() < 10) {
            throw new IOException("no img");
        }
        News news = new News();
        news.setTitle(title);
        news.setContentHtml(contentHtml);
        news.setImage(img);
        news.setCategoryId(categoryId);
        news.setAudit(audit);
        news.setContent(news.getTitle());
        return newsService.crawlerNews(news);
    }
}
