package com.yang.graduation.crawler.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 李洋
 * @date 2020/1/2 16:35
 */
@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm = null;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(100);
        //设置单个主机最大连接数
        cm.setDefaultMaxPerRoute(10);
    }

    public String getHtml(String url) {
        //得到httpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet(url);
        //配置连接
        httpGet.setConfig(httpConfig());
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "utf8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    private RequestConfig httpConfig() {
        return RequestConfig.custom().setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10 * 1000).build();
    }
}
