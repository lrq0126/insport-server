/**
 * 货物列表
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 
  * @return {type} {description}
  */
export function getActivityRewardList ({
    page,
    limit,
    activityName,
    activityTimeType,
    isEnable,
}) {
  return request({
    url: "/activityReward/getActivityRewardList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        activityName: activityName,
        activityTimeType: activityTimeType,
        isEnable: isEnable,
    }
  });
}
 /**
  * 
  * @return {type} {description}
  */
  export function getActivityReward (id) {
  return request({
    url: "/activityReward/getActivityReward",
    method: "post",
    data:{
      id: id
    }
  });
}


export function saveActivityReward ({
    id,
    isEnable,
    activityName,
    activityType,
    activityTimeType,
    startTime,
    endTime,
    rewardType,
    rewardContentId,
    version,
    remarks
}) {
  return request({
    url: "/activityReward/saveActivityReward",
    method: "post",
    data:{
        id: id,
        isEnable: isEnable,
        activityName: activityName,
        activityType: activityType,
        activityTimeType: activityTimeType,
        startTime: startTime,
        endTime: endTime,
        rewardType: rewardType,
        rewardContentId: rewardContentId,
        version: version,
        remarks: remarks
    }
  });
}

 /**
  * 
  * @return {type} {description}
  */
  export function deleteActivityReward (id) {
    return request({
      url: "/activityReward/deleteActivityReward",
      method: "post",
      data:{
        id: id
      }
    });
  }

  
 /**
  * 
  * @return {type} {description}
  */
  export function enableActivityReward ({
    id,
    enableType
  }) {
    return request({
      url: "/activityReward/enableActivityReward",
      method: "post",
      data:{
        id: id,
        enableType: enableType
      }
    });
  }


  /** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓活动海报↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */

  export function getActivityPosterList ({
    page,
    limit,
    posterName,
    posterType,
    isEnable,
}) {
  return request({
    url: "/ActivityPoster/getActivityPosterList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        posterName: posterName,
        posterType: posterType,
        isEnable: isEnable,
    }
  });
}
 /**
  * 
  * @return {type} {description}
  */
  export function getActivityPoster (id) {
  return request({
    url: "/ActivityPoster/getActivityPoster",
    method: "post",
    data:{
      id: id
    }
  });
}


export function saveActivityPoster ({
    id,
    isEnable,
    posterName,
    posterType,
    redirectsAddress,
    isPlaceQrcode,
    qrcodeScaling,
    widthPosition,
    heightPosition,
    version,
    remarks,
    isChangImg,
    files
}) {
  return request({
    url: "/ActivityPoster/saveActivityPoster",
    method: "post",
    data:{
        id: id,
        isEnable: isEnable,
        posterName: posterName,
        posterType: posterType,
        redirectsAddress: redirectsAddress,
        isPlaceQrcode: isPlaceQrcode,
        qrcodeScaling: qrcodeScaling,
        widthPosition: widthPosition,
        heightPosition: heightPosition,
        version: version,
        remarks: remarks,
        isChangImg: isChangImg,
        files: files,
    }
  });
}


export function checkPosterModel (posterType) {
  return request({
    url: "/ActivityPoster/checkPosterModel?posterType=" + posterType,
    method: "get",
  });
}

export function generatePoster (id) {
return request({
  url: "/ActivityPoster/generatePoster",
  method: "post",
  data:{
      id: id,
  }
});
}

 /**
  * 
  * @return {type} {description}
  */
  export function deleteActivityPoster (id) {
    return request({
      url: "/ActivityPoster/deleteActivityPoster",
      method: "post",
      data:{
        id: id
      }
    });
  }

  
 /**
  * 
  * @return {type} {description}
  */
  export function enableActivityPoster ({
    id
  }) {
    return request({
      url: "/ActivityPoster/enableActivityPoster",
      method: "post",
      data:{
        id: id
      }
    });
  }

  /**
  * 
  * @return {type} {description}
  */
   export function unEnableActivityPoster ({
    id
  }) {
    return request({
      url: "/ActivityPoster/unEnableActivityPoster",
      method: "post",
      data:{
        id: id
      }
    });
  }

/*****************邮件接口*********************/

export function getEmailRecord ({
  page,
  limit,
  title,
  sendObjectType,
  sendObjectName,
  contentType,
  content
}) {
  return request({
    url: "/emailRecord/getEmailRecordList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        title: title,
        sendObjectType: sendObjectType,
        sendObjectName: sendObjectName,
        contentType: contentType,
        content: content
    }
  });
}

export function saveEmail ({
  id,
  title,
  sendObjectId,
  sendObjectType,
  sendObjectName,
  contentType,
  content
}) {
  return request({
    url: "/emailRecord/saveEmail",
    method: "post",
    data:{
        id: id,
        title: title,
        sendObjectId: sendObjectId,
        sendObjectType: sendObjectType,
        sendObjectName: sendObjectName,
        contentType: contentType,
        content: content
    }
  });
}

export function sendEmail ({
  id,
  title,
  sendObjectId,
  sendObjectType,
  sendObjectName,
  contentType,
  content
}) {
  return request({
    url: "/emailRecord/sendEmail",
    method: "post",
    data:{
        id: id,
        title: title,
        sendObjectId: sendObjectId,
        sendObjectType: sendObjectType,
        sendObjectName: sendObjectName,
        contentType: contentType,
        content: content
    }
  });
}

export function getEmailInfo(id){
  return request({
    url: "/emailRecord/getEmail",
    method: "post",
    data:{
        id: id
    }
  });
}

export function deleteEmailRecord (id) {
  return request({
    url: "/emailRecord/deleteEmailRecord",
    method: "post",
    data:{
        id: id
    }
  });
}
