package com.amg.gitlab.http;

import com.amg.gitlab.config.Constant;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * http 工具类
 */
public class HttpUtil {

    private static CloseableHttpClient httpclient;

    // 编码格式
    private static String ENCODING = "UTF-8";

    static {
        // 设置请求参数
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.MILLISECONDS);
        pollingConnectionManager.setMaxTotal(1000); // 设置连接池的最大连接数
        pollingConnectionManager.setDefaultMaxPerRoute(200); // 设置每个路由上的默认连接个数
        httpclient = HttpClients.custom().setConnectionManager(pollingConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))  // 设置重试次数，默认关闭
                .build();

    }


    /**
     * https://gitlab.xxx.com/help/api/api_resources.md
     * 发送 post 请求
     * @param url
     * @param paramsMap
     * @return
     */
    public static String post(String url, Map<String, String> paramsMap) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            method.setHeader(Constant.HEADER_KEY, Constant.HEADER_VALUE);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null)
                response.close();
        }
        return null;
    }


    /**
     * https://gitlab.xxx.com/help/api/api_resources.md
     * 发送 post 请求
     * @param url
     * @param paramsMap
     * @return
     */
    public static String delete(String url, Map<String, String> paramsMap) throws Exception {
        CloseableHttpResponse response = null;
        try {
            HttpDelete method = new HttpDelete(url);
            method.setHeader(Constant.HEADER_KEY, Constant.HEADER_VALUE);
            response = httpclient.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null)
                response.close();
        }
        return null;
    }

}
