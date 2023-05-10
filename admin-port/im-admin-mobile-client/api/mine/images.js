/**
 * 
 * @return {type} {description}
 */
import http from "@/common/net/request.js";

import config from "@/config/apiUrl.config";

import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 提交评价
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function uploadImage(imageType, file, mintor){
	return http.uploadImage(imageType, '/images/uploadImage?imageType='+imageType, file, mintor)	
}