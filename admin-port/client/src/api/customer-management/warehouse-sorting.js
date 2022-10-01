/**
 * 仓库货架信息
 * @return {type} {description}
 */

 import request from "@/utils/request";

export function ocrInWareSorting({
    systemName,
    loginName,
    deliveryOrderNo,
    kg,
    length,
    width,
    height
}){
    return request({
        url: "/goods/ocr/inWareSorting",
        method: "post",
        data: {
            systemName: systemName,
            loginName: loginName,
            deliveryOrderNo: deliveryOrderNo,
            weight: kg,
            length: length,
            width: width,
            height: height
        }
    });
}


export function getSystemCustomer({systemName, loginName, customerName, page, limit}){
    console.log("系统：", systemName);
    let baseUrl = 'http://jiyun.flycloudstorage.com';
    switch(systemName){
        case "FLY": baseUrl = 'http://jiyun.flycloudstorage.com:8620';
        break;
        case "HUAWEI": baseUrl = 'http://jiyun.huawei138.cn:8630';
        break;
        case "TAOMAO": baseUrl = 'http://gzdw.gdjiyun.com:9120';
        break;
        case "HUASU": baseUrl = 'http://huasu.huawei138.cn:9610';
        break;
        case "HUAYOU": baseUrl = 'http://huayou.flycloudstorage.com:9630'; 
        break;
    }
    let data = {
        pageNumber: page,
        pageSize: limit,
        customerName: customerName,
        loginName: loginName,
      }
    let tableData;
    axios.post(
        baseUrl+'/api/customer/find/all',
        data,
    ).then(function (res) {
        tableData =  res.content
    })

    return tableData;
}

