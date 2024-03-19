package com.inland.clibrary.net.okcore


/**
 * Created by cuiyang on 16/6/3.
 */
open class HttpData<T>  {
    var code = -1
    var data: T? = null
    var message: String = "无数据返回"
    var itemType = 0
    val isSuccess: Boolean
        get() = code == 0&& message =="succeed"

    constructor() {}

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    override fun toString(): String {
        return "HttpData(code=$code, data=$data, message='$message', itemType=$itemType)"
    }

//    @Throws(ApiException::class)
//    fun throwAPIException() {
//        if (!isSuccess) {
//            throw ApiException(code, message)
//        }
//    }

}