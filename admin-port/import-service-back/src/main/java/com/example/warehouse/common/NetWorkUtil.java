package com.example.warehouse.common;

import com.baidu.speech.restapi.json.JSONArray;
import com.baidu.speech.restapi.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @des 网络工具类
 * @author  wenguosheng
 * @date 2019年4月24日00:43:37
 */
@Slf4j
public class NetWorkUtil {
    /**
     * log for this class
     */

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     * @throws IOException
     */
    public final static String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");
        if (log.isDebugEnabled()) {
            log.debug("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (log.isDebugEnabled()) {
                    log.debug("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (log.isDebugEnabled()) {
                    log.debug("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (log.isDebugEnabled()) {
                    log.debug("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (log.isDebugEnabled()) {
                    log.debug("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (log.isDebugEnabled()) {
                    log.debug("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    public static String getCookies(String url) throws IOException {
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
            .setDefaultCookieStore(cookieStore).build();

        CloseableHttpResponse res = null;

        // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
        HttpGet get = new HttpGet(url);

        res = httpClient.execute(get, context);

        // 获取常用Cookie,包括_xsrf信息
        StringBuffer cookie=new StringBuffer();
        for (Cookie c : cookieStore.getCookies()) {
            //拼接所有cookie变成一个字符串；
            cookie.append(c.getName()+"="+c.getValue()+";");
            System.out.println(c.getName() + ": " + c.getValue());
        }

        String cookieres=cookie.toString();
        cookieres=cookieres.substring(0,cookieres.length()-1);
        res.close();
        return cookieres;
    }

    /**
     * 根据ip地址查询当地所在地区
     * @param ip
     * @return
     */
    public static String getAddressByIp(String ip) {
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ip+"&co=&resource_id=6006&t=1433920989928&ie=utf8&oe=utf-8&format=json");;
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            JSONObject jsStr = new JSONObject(result.toString());
            JSONArray jsData = (JSONArray) jsStr.get("data");
            JSONObject data= (JSONObject) jsData.get(0);//位置
            return (String) data.get("location");
        } catch (IOException e) {
            return "读取失败";
        }
    }

    public static void main(String[] args) throws IOException {
//        String URL = "https://tracking.lmd.com.sg";
//        System.out.println("cookie:" + getCookies(URL));
//        //通过延迟2000毫秒然后再去请求可解决js异步加载获取不到数据的问题
//        Document doc= Jsoup.connect(URL).get();
//        // 通过class的名字得到（即XX）,一个数组对象Elements里面有我们想要的数据,至于这个div的值呢你打开浏览器按下F12就知道了;
//        Elements elements = doc.getElementsByClass("track-form");
//        if ( elements != null ){
//            Element e = elements.first().getAllElements().first();
//            String input = e.getElementsByAttribute("value").toString();
//            input = input.substring(input.lastIndexOf("=") + 2);
//            input = input.substring(0 , input.lastIndexOf("\"") - 1);
//            System.out.println("token:" + input);
//            System.out.println("token:");
//        }

        String[] privinces={"北京市","天津市","河北省","山西省","内蒙古","辽宁省","吉林省","黑龙江省","上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西","海南省","重庆市","四川省","贵州省","云南省","西藏","陕西省","甘肃省","青海省","宁夏","新疆","台湾省","香港","澳门"};

        String address=getAddressByIp("222.247.156.54");
        boolean china=false;
        for(String privince:privinces){
            if(address.startsWith(privince)){
                china=true;
                break;
            }
        }
        System.out.println("222.247.156.54"+";"+address+";是否在中国："+ (china == true ? "是" : "否"));
    }
}
