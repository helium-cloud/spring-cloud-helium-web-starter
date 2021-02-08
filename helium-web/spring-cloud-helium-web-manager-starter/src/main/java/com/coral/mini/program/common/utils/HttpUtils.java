package com.coral.mini.program.common.utils;


import com.alibaba.fastjson.JSONObject;
import com.coral.mini.program.common.business.controller.CaptchaController;
import com.coral.mini.program.common.common.constant.SecurityConstant;
import com.coral.mini.program.common.common.vo.Result;
import io.netty.util.internal.StringUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    /**
     * 获取value: header par
     *
     * @param request
     * @return
     */
    public static String getHeader(HttpServletRequest request, String name) {
        String header = request.getHeader(name);
        if (StringUtil.isNullOrEmpty(header)) {
            header = request.getParameter(name);
        }
        return header;
    }

    /**
     * 获取value: header par
     *
     * @param request
     * @return
     */
    public static String getValue(HttpServletRequest request, String name) {
        String header = request.getHeader(name);
        if (StringUtil.isNullOrEmpty(header)) {
            header = request.getParameter(name);
        }
        return header;
    }

    public static String getContent(String url) {
        String content = "";
        JSONObject jsonObject = new JSONObject();
        CloseableHttpClient client = HttpClients.createDefault();//client实例初始化
        HttpGet get = new HttpGet(url);//请求初始化
        //请求执行，获取返回对象
        CloseableHttpResponse response = null;
        try {
            response = client.execute(get);
            InputStream inputStream = response.getEntity().getContent();
            byte[] bufferContent = new  byte[1024];
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            int length = 0;
            while ((length = inputStream.read(bufferContent)) > 0){
                arrayOutputStream.write(bufferContent, 0, length);
            }
            content = new String(arrayOutputStream.toByteArray());
        } catch (Exception e) {
            jsonObject.put("code", 400);
            jsonObject.put("reason", e.getMessage());
            content = jsonObject.toJSONString();
        } finally {
            try {
                if (response != null){
                    response.close();//关闭返回对象
                }
                if (client != null){
                    client.close();//关闭client
                }


            } catch (IOException e) {
                jsonObject.put("code", 400);
                jsonObject.put("reason", e.getMessage());
                content = jsonObject.toJSONString();
            }
        }
        return content;
    }

    public static void response(HttpServletResponse response, int code, String reason) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            jsonObject.put("reason", reason);
            response.getWriter().write(jsonObject.toJSONString());
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 获取request body体
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestBody(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int contentLength = request.getContentLength();
        if (contentLength > 0) {
            int sum = 0;
            byte[] buffer = new byte[contentLength];
            while (sum < contentLength) {
                int count = request.getInputStream().read(buffer);
                if (count > 0) {
                    outputStream.write(buffer, 0, count);
                    sum += count;
                } else if (count < 0) {
                    outputStream.close();
                    throw new IOException(String.format("getRequestBody read return %s. contentLength:%s sum:%s ",
                            count, contentLength, sum));
                }
            }
        }
        outputStream.flush();
        byte[] outputBuffer = outputStream.toByteArray();
        outputStream.close();
        return outputBuffer;
    }
    /**
     * 获取request body String
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestBodyString(HttpServletRequest request) throws IOException {
        byte[] bodyByte = getRequestBody(request);
        return new String(bodyByte);
    }

    /**
     * 获取request body体
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestString(HttpServletRequest request, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s %s\n", request.getMethod(), request.getRequestURI(), request.getProtocol()));
        for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements(); ) {
            String headName = e.nextElement();
            String headValue = request.getHeader(headName);
            sb.append(String.format("%s: %s\n", headName, headValue));
        }

        if (body != null && body.length() > 0) {
            sb.append("\n");
            sb.append(body);
        }

        return sb.toString();
    }

 }
