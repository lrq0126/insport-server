package com.dwgj.mlxydedicatedline.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtilManager {

    private static HttpUtilManager instance = new HttpUtilManager();
    private static CloseableHttpClient client;

    private HttpUtilManager() {
        client = HttpClients.createDefault();
    }

    public static HttpUtilManager getInstance() {
        return instance;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    public String requestHttpGet(String url_prex, String url, String param) throws HttpException, IOException {

        url = url_prex + url;
        if (param != null && !"".equals(param)) {
            if (url.endsWith("?")) {
                url = url + param;
            } else {
                url = url + "?" + param;
            }
        }

        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        response = client.execute(httpget);
        String result = null;

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }

        response.close();

        return result;
    }

    public String requestHttpPost(String url_prex, String url, Map<String, String> params) throws HttpException, IOException {
        url = url_prex + url;
        List<NameValuePair> formParams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httppost);
        HttpEntity entity1 = response.getEntity();
        return EntityUtils.toString(entity1);
    }

    public String requestHttpPostWithHeader(String url_prex, String url, Map<String, Object> params,String token) throws HttpException, IOException {
        url = url_prex + url;
        HttpPost httppost = new HttpPost(url);
        praseHttpPost(httppost,params);
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httppost.setHeader("Authorization", "Bearer " + token);
        CloseableHttpResponse response = client.execute(httppost);
        return getResponse(response).toString();
    }

    public StringBuilder getResponse(CloseableHttpResponse httpResponse) throws IOException {
        StringBuilder entityStringBuilder = new StringBuilder();
        HttpEntity httpEntity = httpResponse.getEntity();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            entityStringBuilder.append(line);
        }
        return entityStringBuilder;
    }

    public HttpPost praseHttpPost(HttpPost httpPost,Map<String,Object> params){
        if (!CollectionUtils.isEmpty(params)) {
            JSONObject jsonParam = new JSONObject();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (ObjectUtils.isEmpty(entry)) {
                    continue;
                }
                jsonParam.put(entry.getKey(), entry.getValue());
            }
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            return httpPost;
        }
        return null;
    }

    public String requestHttpPostForNoParams(String url) throws HttpException, IOException {
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
        CloseableHttpResponse response = client.execute(httppost);
        HttpEntity entity1 = response.getEntity();
        String result = EntityUtils.toString(entity1);
        return result;
    }

    public String requestHttpGetForHeader(String url, String accessToken) throws HttpException, IOException {
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpget.setHeader("x-wlk-Authorization", accessToken);
        CloseableHttpResponse response = client.execute(httpget);
        HttpEntity entity1 = response.getEntity();
        String result = EntityUtils.toString(entity1);
        return result;
    }

    public static void main(String[] args) throws HttpException, IOException {
        HttpUtilManager hmg = HttpUtilManager.getInstance();
        Map<String,String> param = new HashMap<>();
        param.put("TrackingNumber","4944859986");
        param.put("Language","cn");
        String jsonString = JSON.toJSONString(param);
        String result = hmg.requestHttpPost("http://dw.kingtrans.cn", "/AppDataService?method=searchTrackByBillid", param);
        System.out.println(result);
//        result = post("http://dw.kingtrans.cn/AppDataService?method=searchTrackByBillid",jsonString);
//        System.out.println(result);
    }

    public String requestHttpGet(String url) throws HttpException, IOException {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        response = client.execute(httpget);
        String result = null;

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }

        response.close();

        return result;
    }

    public String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = client.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
