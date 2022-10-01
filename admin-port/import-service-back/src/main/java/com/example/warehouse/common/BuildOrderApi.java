package com.example.warehouse.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.warehouse.entity.AgentChannel;
import com.example.warehouse.entity.CustomerPack;
import com.example.warehouse.entity.CustomerPackReceiverAddress;
import com.example.warehouse.vo.account.AgentOrderProductDetailVo;
import com.example.warehouse.vo.account.AgentOrderReqVo;
import com.example.warehouse.vo.customer.CustomerPackNumberVo;
import com.example.warehouse.vo.customer.CustomerPackVo;
import lombok.Data;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BuildOrderApi {

    /**
     * 创建德威订单
     *
     * @param customerPackVo
     * @return
     */
    public static ResponseBody buildDWOrder(CustomerPackVo customerPackVo, AgentChannel agentChannel, AgentOrderReqVo agentOrderReqVo) {

        List<CustomerPackNumberVo> customerPackNumbers = customerPackVo.getCustomerPackNumbers();
        ResponseBody responseBody = new ResponseBody();
        String trackNumber = "";
        List<AgentOrderProductDetailVo> agentOrderProductDetailVoList = JSONObject.parseArray(agentOrderReqVo.getSonPackProductDetail(), AgentOrderProductDetailVo.class);
        if(agentChannel.getChannelCode().contains("EMS") && !CollectionUtils.isEmpty(customerPackNumbers) && customerPackVo.getCustomerPackNumbers().size() > 1){
            int i = 1;
            responseBody.setStatusCode(200);
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers){
                String orderNumber = customerPackVo.getOrderNumber()+i;
                AgentOrderProductDetailVo agentOrderProductDetailVo = agentOrderProductDetailVoList.get(i-1);
                // 填充订单详情
                String orderItem = orderItemsSetting(customerPackNumber, agentOrderProductDetailVo);

                JSONObject jsonObject = DWPostRequest(customerPackVo, orderItem, agentChannel, agentOrderReqVo, orderNumber, 1);

                List<JSONObject> jsonObjects = JSONArray.parseArray(jsonObject.get("returnDatas").toString(), JSONObject.class);
                JSONObject returnDataObject = jsonObjects.get(0);
                if(returnDataObject.containsKey("statusCode") && returnDataObject.get("statusCode").equals("success")){
                    trackNumber = StringUtils.isEmpty(trackNumber) ? returnDataObject.getString("trackNumber")
                            : trackNumber+"," + returnDataObject.getString("trackNumber");
                }else {
                    responseBody.setStatusCode(500);
                    responseBody.setMessage(returnDataObject.getString("message"));
                    break;
                }
                i++;
            }
        }else {
            int i = 1;
            String orderItem = "";
            // 拼接订单产品详情
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers){
                AgentOrderProductDetailVo agentOrderProductDetailVo = agentOrderProductDetailVoList.get(i-1);
                orderItem = StringUtils.isEmpty(orderItem) ? orderItemsSetting(customerPackNumber, agentOrderProductDetailVo)
                        : orderItem+"," + orderItemsSetting(customerPackNumber, agentOrderProductDetailVo);
                i++;
            }

            JSONObject jsonObject = DWPostRequest(customerPackVo, orderItem, agentChannel, agentOrderReqVo, customerPackVo.getOrderNumber(), customerPackVo.getPackNum());
            List<JSONObject> jsonObjects = JSONArray.parseArray(jsonObject.get("returnDatas").toString(), JSONObject.class);
            JSONObject returnDataObject = jsonObjects.get(0);
            if(returnDataObject.containsKey("statusCode") && returnDataObject.get("statusCode").equals("success")){
                trackNumber = returnDataObject.getString("trackNumber");
                responseBody.setStatusCode(200);
            }else {
                responseBody.setStatusCode(500);
                responseBody.setMessage(returnDataObject.getString("message"));
            }
        }

        responseBody.setAgentNumber(trackNumber);
        responseBody.setOrderNumber(customerPackVo.getOrderNumber());

        return responseBody;
    }

    /**
     * 创建亚美api
     *
     * @param customerPackVo
     * @param agentChannel
     * @param agentOrderReqVo
     * @return
     */
    public static ResponseBody buildYameiApi(CustomerPackVo customerPackVo, AgentChannel agentChannel, AgentOrderReqVo agentOrderReqVo) {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();

        String urlString = "appToken=c613d7056dedab2575db83373e2e7a91"
            + "&appKey=32289d42acbcc83cf0e4b3399b93abdb32289d42acbcc83cf0e4b3399b93abdb"
            + "&serviceMethod=createorder"
            + "&paramsJson=";

        /**
         * transporateTypeCode 亚美渠道代码
         * 西马普货MP-20(MP-MY-P20)
         * 西马特货MP-20(MP-MY-P20T)
         * 新加坡普货MP-20(MP-SG-20P)
         * 新加坡特货MP-20(MP-SG-20T)
         * 东马普货MP-20(PK0033)
         * 东马特货MP-20(PK0034)
         * 仰光空运专线普货(PK0051)
         * 马来西亚海运快递(SEA-F-MY)
         * 新加坡快船精品快递(SEA-F-SG)
         */
        String cname = agentChannel.getProductName();
        String englishGoodsName = "clothes";
        String price = "10";
        if ( customerPackVo.getActualWeight() > 10 ){
            price = "20";
        }else if ( customerPackVo.getActualWeight() > 20 ){
            price = "50";
        }else if ( customerPackVo.getActualWeight() > 30 ){
            price = "70";
        }
        String jsonStr = "{"
            + "\"reference_no\":\" " + customerPackVo.getOrderNumber() + "\","
            + "\"shipping_method\":\"" + agentChannel.getChannelCode() + "\","
            + "\"order_weight\":" + customerPackVo.getActualWeight() + ","
            + "\"order_pieces\":" + customerPackVo.getPackNum() + ","
            + "\"mail_cargo_type\":\"4\","
            + "\"consignee\":{\"consignee_company\":\"\","
            + "\"consignee_province\":\"..\","
            + "\"consignee_city\":\"..\","
            + "\"consignee_street\":\"" + customerPackVo.getAddress() + " \","
            + "\"consignee_postcode\":\"" + customerPackVo.getCode() + "\","
            + "\"consignee_name\":\"" + customerPackVo.getAddressee() + "\","
            + "\"consignee_telephone\":\"" + "\","
            + "\"consignee_mobile\":\"" + customerPackVo.getPhoneNumber() + "\","
            + "\"consignee_countrycode\":\"" + customerPackVo.getRouteVo().getCountryCode() + "\"},"
            + "\"shipper\":{\"shipper_countrycode\":\"CN\","
            + "\"shipper_city\":\".\",\"shipper_street\":\"..\","
            + "\"shipper_areacode\":\"\",\"shipper_name\":\".\","
            + "\"shipper_telephone\":\"..\",\"shipper_mobile\":\"..\"},"
            + "\"invoice\":[{\"invoice_enname\":\"" + englishGoodsName + "\","
            + "\"invoice_cnname\":\"" + cname + "\",\"invoice_quantity\":1,"
            + "\"unit_code\":\"PCE\",\"invoice_unitcharge\":" + price +","
            + "\"net_weight\":" + customerPackVo.getActualWeight() + ","
            + "\"invoice_note\":\"" + englishGoodsName + "\""
            + "}]}";
        String parUrl = "";
        try {
            parUrl = URLEncoder.encode(jsonStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            result =
                httpUtil.requestHttpGet("http://yamei.rtb56.com/webservice/PublicService.asmx/ServiceInterfaceUTF8?" + urlString + parUrl);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(result);

        JSONObject jsStr = JSONObject.parseObject(result);

        Integer statusCode = 200;
        String message = "创建订单成功";
        String agentId = "";
        String agentNumber = "";
        if(jsStr.getString("success").equals("1")){
            statusCode = 200;
            message = jsStr.getString("enmessage");
            JSONObject dataObject = (JSONObject)jsStr.get("data");
            agentNumber = dataObject.get("shipping_method_no").toString();
        }else {
            statusCode = 500;
            message = jsStr.getString("enmessage");
        }

        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatusCode(statusCode);
        responseBody.setAgentId(agentId);
        responseBody.setAgentNumber(agentNumber);
        responseBody.setMessage(message);
        responseBody.setLabelUrl(null);
        return responseBody;
    }

    /**
     * 创建亚美api
     *
     * @param customerPack
     * @param address
     * @param transporateTypeCode
     * @param country
     * @param goodsName
     * @return
     */
    public static JSONObject buildYameiApiPost(CustomerPack customerPack, CustomerPackReceiverAddress address, String transporateTypeCode, String country, String goodsName) {
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();

        String urlString = "appToken=c613d7056dedab2575db83373e2e7a91"
                + "&appKey=32289d42acbcc83cf0e4b3399b93abdb32289d42acbcc83cf0e4b3399b93abdb"
                + "&serviceMethod=createorder"
                + "&paramsJson=";

        /**
         * transporateTypeCode 亚美渠道代码
         * 西马普货MP-20(MP-MY-P20)
         * 西马特货MP-20(MP-MY-P20T)
         * 新加坡普货MP-20(MP-SG-20P)
         * 新加坡特货MP-20(MP-SG-20T)
         * 东马普货MP-20(PK0033)
         * 东马特货MP-20(PK0034)
         * 仰光空运专线普货(PK0051)
         * 马来西亚海运快递(SEA-F-MY)
         * 新加坡快船精品快递(SEA-F-SG)
         */

        String englishGoodsName = goodsName.equals("化妆品") ? "cosmetic" : "clothes";
        String jsonStr = "{"
                + "\"reference_no\":\" " + customerPack.getOrderNumber() + "\","
                + "\"shipping_method\":\"" + transporateTypeCode + "\","
                + "\"order_weight\":" + customerPack.getActualWeight() + ","
                + "\"order_pieces\":" + customerPack.getPackNum() + ","
                + "\"mail_cargo_type\":\"4\","
                + "\"consignee\":{\"consignee_company\":\"\","
                + "\"consignee_province\":\"..\","
                + "\"consignee_city\":\"..\","
                + "\"consignee_street\":\"" + address.getReceiverAddress() + " \","
                + "\"consignee_postcode\":\"" + address.getCode() + "\","
                + "\"consignee_name\":\"" + address.getAddressee() + "\","
                + "\"consignee_telephone\":\"" + address.getPhoneNumber() + "\","
                + "\"consignee_mobile\":\"" + address.getPhoneNumber() + "\","
                + "\"consignee_countrycode\":\"" + country + "\"},"
                + "\"shipper\":{\"shipper_countrycode\":\"CN\","
                + "\"shipper_city\":\".\",\"shipper_street\":\"..\","
                + "\"shipper_areacode\":\"\",\"shipper_name\":\".\","
                + "\"shipper_telephone\":\"..\",\"shipper_mobile\":\"..\"},"
                + "\"invoice\":[{\"invoice_enname\":\"" + englishGoodsName + "\","
                + "\"invoice_cnname\":\"" + goodsName + "\",\"invoice_quantity\":1,"
                + "\"unit_code\":\"PCE\",\"invoice_unitcharge\":15.0,"
                + "\"net_weight\":" + customerPack.getActualWeight() + ","
                + "\"invoice_note\":\"" + englishGoodsName + "\""
                + "}]}";
        String parUrl = "";
        try {
            parUrl = URLEncoder.encode(jsonStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            result =
                    httpUtil.requestHttpGet("http://yamei.rtb56.com/webservice/PublicService.asmx/ServiceInterfaceUTF8?" + urlString + parUrl);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(result);

        JSONObject jsStr = JSONObject.parseObject(result);
        return jsStr;
    }

    /**
     * 创建凡士达订单接口
     * @param customerPackVo
     * @param agentOrderReqVo
     * @return
     */
    public static ResponseBody buildEMSOrder(CustomerPackVo customerPackVo, AgentOrderReqVo agentOrderReqVo) {
        List<CustomerPackNumberVo> customerPackNumbers = customerPackVo.getCustomerPackNumbers();
        JSONObject jsStr;
        String agentId = "";
        String agentNumber = "";
        String message = "";
        Integer statusCode = 0;
        List<String> fsdPrintURL = new ArrayList<>();
        String printURL = "http://fqd56.com:8089/order/FastRpt/API_RPT.aspx?PrintType=A4&format=1&order_id=";
        if(customerPackNumbers.size() > 1){
            int i = 1;
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
                String orderNumber = customerPackVo.getOrderNumber() + "-" +i;
                jsStr = FSDPostRequest(customerPackVo, customerPackNumber, agentOrderReqVo, orderNumber);
                if(jsStr.getString("ack").equals("false")){
                    statusCode = 500;
                    message = jsStr.getString("message");
                    break;
                }else {
                    statusCode = 200;
                    agentId = jsStr.getString("order_id");
                    fsdPrintURL.add(printURL + agentId);
                    agentNumber = StringUtils.isEmpty(agentNumber) ? jsStr.getString("tracking_number"): agentNumber + "," + jsStr.getString("tracking_number");
                }
                i ++;
            }
        }else {
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
                jsStr = FSDPostRequest(customerPackVo, customerPackNumber, agentOrderReqVo, customerPackVo.getOrderNumber());
                if(jsStr.getString("ack").equals("false")){
                    statusCode = 500;
                    message = jsStr.getString("message");
                    break;
                }else {
                    statusCode = 200;
                    agentId = jsStr.getString("order_id");
                    fsdPrintURL.add(printURL + agentId);
                    agentNumber = jsStr.getString("tracking_number");
                }
            }
        }
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatusCode(statusCode);
        responseBody.setAgentId(agentId);
        responseBody.setAgentNumber(agentNumber);
        responseBody.setMessage(message);

        responseBody.setLabelUrl(fsdPrintURL);
        return responseBody;
    }

    /**
     * 创建成达订单接口
     * @param customerPackVo
     * @param agentOrderReqVo
     * @return
     */
    public static ResponseBody buildChengDaOrder(CustomerPackVo customerPackVo, AgentOrderReqVo agentOrderReqVo) {
        List<CustomerPackNumberVo> customerPackNumbers = customerPackVo.getCustomerPackNumbers();
        JSONObject jsStr;
        String agentId = "";
        String agentNumber = "";
        String message = "";
        Integer statusCode = 0;
        List<String> fsdPrintURL = new ArrayList<>();
        String printURL = "http://139.159.250.10:8089/order/FastRpt/API_RPT.aspx?PrintType=A4&format=1&order_id=";
        if(customerPackNumbers.size() > 1){
            int i = 1;
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
                String orderNumber = customerPackVo.getOrderNumber() + "-" +i;
                jsStr = ChengDaPostRequest(customerPackVo, customerPackNumber, agentOrderReqVo, orderNumber);
                if(jsStr.getString("ack").equals("false")){
                    statusCode = 500;
                    message = jsStr.getString("message");
                    break;
                }else {
                    statusCode = 200;
                    agentId = jsStr.getString("order_id");
                    fsdPrintURL.add(printURL + agentId);
                    agentNumber = StringUtils.isEmpty(agentNumber) ? jsStr.getString("tracking_number"): agentNumber + "," + jsStr.getString("tracking_number");
                }
                i ++;
            }
        }else {
            for (CustomerPackNumberVo customerPackNumber : customerPackNumbers) {
                jsStr = ChengDaPostRequest(customerPackVo, customerPackNumber, agentOrderReqVo, customerPackVo.getOrderNumber());
                if(jsStr.getString("ack").equals("false")){
                    statusCode = 500;
                    message = jsStr.getString("message");
                    break;
                }else {
                    statusCode = 200;
                    agentId = jsStr.getString("order_id");
                    fsdPrintURL.add(printURL + agentId);
                    agentNumber = jsStr.getString("tracking_number");
                }
            }
        }
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatusCode(statusCode);
        responseBody.setAgentId(agentId);
        responseBody.setAgentNumber(agentNumber);
        responseBody.setMessage(message);

        responseBody.setLabelUrl(fsdPrintURL);
        return responseBody;
    }

    public static void main(String[] args) {
//        buildEMSOrder("FL422420410926", "2074", "SNY", "NSW");
//        JSONObject result = buildDWOrder("FL1563371372438");
//        System.out.println(result);
        String str = "{\"returnDatas\":[{\"corpBillid\":\"DW2012161213GZ\",\"trackNumber\":\"CX900771247CN\",\"labelBillid\":\"\",\"shipmentid\":\"\",\"childTrackingNumber\":\"\",\"customerNumber\":\"FL1563371372438\",\"message\":\"\",\"statusCode\":\"success\"}],\"statusCode\":\"success\"}";
        JSONObject result = JSON.parseObject(str);
        System.out.println(result.get("returnDatas"));
        List<JSONObject> jsonObject = JSONArray.parseArray(result.get("returnDatas").toString(), JSONObject.class);
        System.out.println(jsonObject.get(0));
        System.out.println(jsonObject.get(0).get("trackNumber"));

    }

    /**
     * 凡士达接口包装请求
     * @param customerPackVo
     * @param customerPackNumberVo
     * @param agentOrderReqVo
     * @param orderNumber
     * @return
     */
    private static JSONObject FSDPostRequest(CustomerPackVo customerPackVo,CustomerPackNumberVo customerPackNumberVo,
        AgentOrderReqVo agentOrderReqVo, String orderNumber){

        String strParam = "{" + "\"buyerid\": \"\","
            + "\"order_piece\": \"1\","
            + "\"consignee_mobile\": \"\","
            + "\"order_returnsign\": \"Y\","
            + "\"trade_type\": \"ZYXT\"," + "\"duty_type\":\"DDU\","
            + "\"battery_type\":\"\","
            + "\"consignee_name\": \""+customerPackVo.getAddressee()+"\","
            + "\"consignee_companyname\": \"\","
            + "\"consignee_address\": \""+customerPackVo.getAddress()+"\","
            + "\"consignee_telephone\": \""+customerPackVo.getPhoneNumber()+"\","
            + "\"country\": \""+customerPackVo.getRouteVo().getCountryCode()+"\","
            + "\"consignee_state\": \""+agentOrderReqVo.getProvince()+"\","
            + "\"consignee_city\": \""+agentOrderReqVo.getCity()+"\","
            + "\"consignee_suburb\": \"\","
            + "\"consignee_postcode\": \""+agentOrderReqVo.getCode()+"\","
            + "\"consignee_passportno\": \"\","
            + "\"consignee_email\": \"\","
            + "\"consignee_taxno\": \"\","
            + "\"consignee_streetno\": \"\","
            + "\"consignee_doorno\": \"\","
            + "\"shipper_taxnotype\":\"\","
            + "\"shipper_taxno\":\"\","
            + "\"shipper_taxnocountry\":\"CN\","
            + "\"customer_id\": \"49341\","
            + "\"customer_userid\": \"45461\","
            + "\"order_customerinvoicecode\": \"" + orderNumber + "\","
            + "\"product_id\": \"" +agentOrderReqVo.getChannelCode()+ "\","
            + "\"weight\": \""+customerPackNumberVo.getActualWeight()+"\","
            + "\"product_imagepath\": \"\","
            + "\"order_transactionurl\": \"\","
            + "\"order_cargoamount\": \"\","
            + "\"order_insurance\": \""+customerPackVo.getInsurancePrice()+"\","
            + "\"cargo_type\": \"P\","
            + "\"order_customnote\": \"\","
            + "\"orderInvoiceParam\":[{"
            + "\"invoice_amount\": \""+20.0+"\","
            + "\"invoice_pcs\": \"1\","
            + "\"invoice_title\": \"red clothes\","
            + "\"invoice_weight\": \""+customerPackNumberVo.getActualWeight()+"\","
            + "\"sku\": \"红色衣服\","
            + "\"sku_code\": \"\","
            + "\"hs_code\": \"\","
            + "\"transaction_url\": \"\","
            + "\"invoiceunit_code\": \"\","
            + "\"invoice_imgurl\": \"\","
            + "\"invoice_brand\": \"\","
            + "\"invoice_rule\": \"\","
            + "\"invoice_currency\": \"USD\","
            + "\"invoice_taxno\": \"\","
            + "\"origin_country\": \"CN\","
            + "\"invoice_material\": \"\","
            + "\"invoice_purpose\": \"\""
            + "}" + "],"
            + "\"orderVolumeParam\": [{"
            + "\"volume_height\": \""+(customerPackNumberVo.getHeight() > 3 ? customerPackNumberVo.getHeight() -2 : customerPackNumberVo.getHeight())+"\","
            + "\"volume_length\": \""+(customerPackNumberVo.getLength() > 3 ? customerPackNumberVo.getLength() -2 : customerPackNumberVo.getLength())+"\","
            + "\"volume_width\": \""+(customerPackNumberVo.getWidth() > 3 ? customerPackNumberVo.getWidth() -2 : customerPackNumberVo.getWidth())+"\","
            + "\"volume_weight\": \""+customerPackNumberVo.getActualWeight()+"\""
            + "}]" + "}";
        String strUrl = "http://fqd56.com:8082/createOrderApi.htm";
        HttpClient objClient = new HttpClient();
        objClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        PostMethod objPostMethod = new PostMethod(strUrl);

        try {
            strParam = URLEncoder.encode(strParam, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        NameValuePair objNameValuePair = new NameValuePair();
        objNameValuePair.setName("param");
        objNameValuePair.setValue(strParam);
        objPostMethod.addParameter(objNameValuePair);
        String strResponseBody = "";

        try {
            int statusCode = objClient.executeMethod(objPostMethod);

            strResponseBody = URLDecoder.decode(new String(objPostMethod.getResponseBody()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsStr = JSONObject.parseObject(strResponseBody);
        System.out.println("凡士达转单结果：" + jsStr);
        return jsStr;
    }

    /**
     * 成达接口订单包装请求
     * @param customerPackVo
     * @param customerPackNumberVo
     * @param agentOrderReqVo
     * @param orderNumber
     * @return
     */
    private static JSONObject ChengDaPostRequest(CustomerPackVo customerPackVo,CustomerPackNumberVo customerPackNumberVo,
        AgentOrderReqVo agentOrderReqVo, String orderNumber){

        String strParam = "{" + "\"buyerid\": \"\","
            + "\"order_piece\": \"1\","
            + "\"consignee_mobile\": \"\","
            + "\"order_returnsign\": \"Y\","
            + "\"trade_type\": \"ZYXT\"," + "\"duty_type\":\"DDU\","
            + "\"battery_type\":\"\","
            + "\"consignee_name\": \""+customerPackVo.getAddressee()+"\","
            + "\"consignee_companyname\": \"\","
            + "\"consignee_address\": \""+customerPackVo.getAddress()+"\","
            + "\"consignee_telephone\": \""+customerPackVo.getPhoneNumber()+"\","
            + "\"country\": \""+customerPackVo.getRouteVo().getCountryCode()+"\","
            + "\"consignee_state\": \""+agentOrderReqVo.getProvince()+"\","
            + "\"consignee_city\": \""+agentOrderReqVo.getCity()+"\","
            + "\"consignee_suburb\": \"\","
            + "\"consignee_postcode\": \""+agentOrderReqVo.getCode()+"\","
            + "\"consignee_passportno\": \"\","
            + "\"consignee_email\": \"\","
            + "\"consignee_taxno\": \"\","
            + "\"consignee_streetno\": \"\","
            + "\"consignee_doorno\": \"\","
            + "\"shipper_taxnotype\":\"\","
            + "\"shipper_taxno\":\"\","
            + "\"shipper_taxnocountry\":\"CN\","
            + "\"customer_id\": \"22441\","
            + "\"customer_userid\": \"19541\","
            + "\"order_customerinvoicecode\": \"" + orderNumber + "\","
            + "\"product_id\": \"" +agentOrderReqVo.getChannelCode()+ "\","
            + "\"weight\": \""+customerPackNumberVo.getActualWeight()+"\","
            + "\"product_imagepath\": \"\","
            + "\"order_transactionurl\": \"\","
            + "\"order_cargoamount\": \"\","
            + "\"order_insurance\": \""+customerPackVo.getInsurancePrice()+"\","
            + "\"cargo_type\": \"P\","
            + "\"order_customnote\": \"\","
            + "\"orderInvoiceParam\":[{"
            + "\"invoice_amount\": \""+20.0+"\","
            + "\"invoice_pcs\": \"1\","
            + "\"invoice_title\": \"red clothes\","
            + "\"invoice_weight\": \""+customerPackNumberVo.getActualWeight()+"\","
            + "\"sku\": \"红色衣服\","
            + "\"sku_code\": \"\","
            + "\"hs_code\": \"\","
            + "\"transaction_url\": \"\","
            + "\"invoiceunit_code\": \"\","
            + "\"invoice_imgurl\": \"\","
            + "\"invoice_brand\": \"\","
            + "\"invoice_rule\": \"\","
            + "\"invoice_currency\": \"USD\","
            + "\"invoice_taxno\": \"\","
            + "\"origin_country\": \"CN\","
            + "\"invoice_material\": \"\","
            + "\"invoice_purpose\": \"\""
            + "}" + "],"
            + "\"orderVolumeParam\": [{"
            + "\"volume_height\": \""+(customerPackNumberVo.getHeight() > 3 ? customerPackNumberVo.getHeight() -2 : customerPackNumberVo.getHeight())+"\","
            + "\"volume_length\": \""+(customerPackNumberVo.getLength() > 3 ? customerPackNumberVo.getLength() -2 : customerPackNumberVo.getLength())+"\","
            + "\"volume_width\": \""+(customerPackNumberVo.getWidth() > 3 ? customerPackNumberVo.getWidth() -2 : customerPackNumberVo.getWidth())+"\","
            + "\"volume_weight\": \""+customerPackNumberVo.getActualWeight()+"\""
            + "}]" + "}";
        String strUrl = "http://139.159.250.10:8082/createOrderApi.htm";
        HttpClient objClient = new HttpClient();
        objClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

        PostMethod objPostMethod = new PostMethod(strUrl);

        try {
            strParam = URLEncoder.encode(strParam, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        NameValuePair objNameValuePair = new NameValuePair();
        objNameValuePair.setName("param");
        objNameValuePair.setValue(strParam);
        objPostMethod.addParameter(objNameValuePair);
        String strResponseBody = "";

        try {
            int statusCode = objClient.executeMethod(objPostMethod);

            strResponseBody = URLDecoder.decode(new String(objPostMethod.getResponseBody()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsStr = JSONObject.parseObject(strResponseBody);
        System.out.println("成达转单结果：" + jsStr);
        return jsStr;
    }

    private static JSONObject DWPostRequest(CustomerPackVo customerPackVo, String orderItems,
                                            AgentChannel agentChannel, AgentOrderReqVo agentOrderReqVo,
                                            String orderNumber, Integer packNum){
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String jsonString1 = "{\"Verify\": {\"Clientid\": \"202011261\", \"Token\": \"eGO8dC61WWHVDSr7o1wW\""
                + "},\"OrderType\": \"1\", \"OrderDatas\": ["
                + "{\"CustomerNumber\":\"" + orderNumber + "\","
                + "\"TrackNumber\": \"\", "
                + "\"ChannelCode\": \""+agentChannel.getChannelCode()+"\", "
                + "\"CountryCode\": \""+customerPackVo.getRouteVo().getCountryCode()+"\", "
                + "\"HouseCode\": \"moscow\", "
                + "\"PackageType\": \"G\", "
                + "\"GoodsType\": \"WPX\", "
                + "\"TotalWeight\": \""+customerPackVo.getActualWeight()+"\", "
                + "\"TotalValue\": \"0\", "
                + "\"Number\": \""+packNum+"\", "
                + "\"Collamt\": \"0\", "
                + "\"Note\": \""+agentChannel.getProductName()+"\", "
                + "\"Recipient\": "
                + "{\"Name\": \""+ customerPackVo.getAddressee() +"\", "
                + "\"Company\": \""+ customerPackVo.getCustomerName() +"\", "
                + "\"Addres1\": \""+ customerPackVo.getAddress() +"\","
                + "\"Addres2\": \"..\","
                + "\"Tel\": \""+ customerPackVo.getPhoneNumber() +"\", "
                + "\"Mobile\": \""+ customerPackVo.getPhoneNumber() +"\", "
                + "\"Province\": \""+agentOrderReqVo.getProvince()+"\", "
                + "\"City\": \""+agentOrderReqVo.getCity()+"\", "
                + "\"Post\": \""+ agentOrderReqVo.getCode() +"\", "
                + "\"Email\": \"\", "
                + "\"Fax\": \"\""
                + "},"
                + "\"Sender\": "
                + "{ \"Name\": \"David\", "
                + "\"Company\": \"邮政寄件中心\", "
                + "\"Addres\": \"Guang Dong Shen Zhen\", "
                + "\"Country\": \"CN\", "
                + "\"Mobile\": \"8613433543782\", "
                + "\"Tel\": \"0755-8899895\", "
                + "\"Post\": \"000000\", "
                + "\"Province\": \"Deutschland\", "
                + "\"City\": \"Markelfingen\", "
                + "\"Email\": \"Test@163.com\", "
                + "\"Fax\": \"0755-98974547\""
                + "},\"OrderItems\": ["
                + orderItems
                + "],"
                +"\"Volumes\": ["
                +"{\"Weight\": \"0.54\", "
                +"\"Number\": \"1\", "
                +"\"Length\": \"20\","
                +"\"Width\": \"50\", "
                +"\"Height\": \"50\""
                + "}]"
                + "}" + "]" + "}";

        String result = httpUtil.post("http://dwgj.kingtrans.net/PostInterfaceService?method=createOrder", jsonString1);

        if (result.startsWith("<!")) {
            result = "{\"statusCode\":\"success\",\"returnDatas\":[{\"statusCode\":\"error\",\"trackNumber\":\"1Z840YV520950053512\",\"message\":\"单号系统中不存在！\"}]}";
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println("德威转单结果：" + jsonObject);
        return jsonObject;
    }

    private static String orderItemsSetting(CustomerPackNumberVo customerPackNumber, AgentOrderProductDetailVo agentOrderProductDetail){

            String orderItem = "{\"Sku\": \"PK_543HD\", "
                + "\"Cnname\": \""+agentOrderProductDetail.getProductName()+"\", "
                + "\"Enname\": \""+agentOrderProductDetail.getProductEName()+"\", "
                + "\"Price\": \""+agentOrderProductDetail.getGoodsValue()+"\", "
                + "\"Weight\": \""+customerPackNumber.getActualWeight()+"\", "
                + "\"Num\": \"1\", "
                + "\"Money\": \"USD\", "
                + "\"Unit\": \"PCS\", "
                + "\"ProductLink\": \"\", "
                + "\"PackageNo\": \"1\", "
                +"\"Texture\": \"Texture\", "
                + "\"TransactionUrl\":\"\", "
                + "\"Application\":\"Application\", "
                + "\"CustomsCode\": \"\", "
                + "\"PeihuoInfo\": \""+agentOrderProductDetail.getProductName()+"\""
                + "}";

            return orderItem;
    }


    /**
     * http返回体
     */
    @Data
    public static class ResponseBody {
        /**
         * 返回状态码
         */
        Integer statusCode;

        /**
         * 订单号
         */
        String orderNumber;

        /**
         * 转单号
         */
        String agentNumber;

        /**
         * 返回消息
         */
        String message;

        /**
         * 代理系统的id，凡士达ems接口特有
         */
        String agentId;

        /**
         * 标签url，有些为空
         */
        List<String> labelUrl;
    }

}
