package com.coral.mini.program.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient4.3工具类
 * @author hang.luo
 */
public class HttpClientUtils
{
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录

    private static RequestConfig requestConfig = null;

    static
    {
        // 设置请求和传输超时时间
        requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
    }

    /**
     * post请求传输json参数
     * @param url  url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");
        try
        {
            if (null != jsonParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toJSONString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK|| result.getStatusLine().getStatusCode()==HttpStatus.SC_CREATED)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("post请求提交失败:" + url, e);
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     * @param url            url地址
     * @param strParam       参数
     * @return
     */
    public static JSONObject httpPost(String url, String strParam)
    {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try
        {
            if (null != strParam)
            {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                String str = "";
                try
                {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                }
                catch (Exception e)
                {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        }
        catch (IOException e)
        {
            logger.error("post请求提交失败:" + url, e);
        }
        finally
        {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     * @param url 路径
     * @return
     */
    public static JSONObject httpGet(String url)
    {
        // get请求返回结果
        JSONObject jsonResult = null;
        CloseableHttpClient client = HttpClients.createDefault();
        // 发送get请求
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        try
        {
            CloseableHttpResponse response = client.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                // 读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity, "utf-8");
                // 把json字符串转换成json对象
                jsonResult = JSONObject.parseObject(strResult);
            }
            else
            {
                logger.error("get请求提交失败:" + url);
            }
        }
        catch (IOException e)
        {
            logger.error("get请求提交失败:" + url, e);
        }
        finally
        {
            request.releaseConnection();
        }
        return jsonResult;
    }

    public static String doGet(String url, Map<String, String> params){

        //获取httpclient客户端
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";

        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);

            if(null!=params){
                for (String key:params.keySet()) {
                    builder.setParameter(key, params.get(key));
                }
            }

            HttpGet get = new HttpGet(builder.build());


            response = httpclient.execute(get);

            System.out.println(response.getStatusLine());

            if(200==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();
                resultString = EntityUtils.toString(entity, "utf-8");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if(null!=response){
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(null!=httpclient){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return resultString;
    }

    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doPost(String url,Map<String,String> params){
        /**
         * 在4.0及以上httpclient版本中，post需要指定重定向的策略，如果不指定则按默认的重定向策略。
         *
         * 获取httpclient客户端
         */
        CloseableHttpClient httpclient = HttpClientBuilder.create().setRedirectStrategy( new LaxRedirectStrategy()).build();

        String resultString = "";

        CloseableHttpResponse response = null;

        try {


            HttpPost post = new HttpPost(url);

            List<NameValuePair> paramaters = new ArrayList<>();

            if(null!=params){
                for (String key : params.keySet()) {
                    paramaters.add(new BasicNameValuePair(key,params.get(key)));
                }

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity (paramaters);

                post.setEntity(formEntity);
            }


            /**
             * HTTP/1.1 403 Forbidden
             *   原因：
             *      有些网站，设置了反爬虫机制
             *   解决的办法：
             *      设置请求头，伪装浏览器
             */
            post.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

            response = httpclient.execute(post);

            System.out.println(response.getStatusLine());

            if(200==response.getStatusLine().getStatusCode()){
                HttpEntity entity = response.getEntity();
                resultString = EntityUtils.toString(entity, "utf-8");
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            if(null!=response){
                try {
                    response.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(null!=httpclient){
                try {
                    httpclient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return resultString;
    }

    public static String doPost(String url){
        return doPost(url, null);
    }
}