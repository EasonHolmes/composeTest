package com.ethan.netlibrary.core.cache

/**
 * Created by Ethan Cui on 2022/3/26
 */
enum class NetCode(val code: Int, val errorMsg: String) {
    INVALID_LOGIN_TOKEN(401, "无效的token"),
    SUCCESS(0, "请求成功"),
    INVALID_REQUEST_BODY(400, "缺失请求参数"),
    INVALID_DIGEST(403, "无效签名"),
    PRODUCT_NOT_FOUND(404, "产品不存在"),
    SERVICE_NOT_FOUND(404, "服务不存在"),
    SYSTEM_ERROR(500, "系统错误"),
    NOT_MODEL_CONFIG(202, "配置为空")
}