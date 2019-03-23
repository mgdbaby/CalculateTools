package com.amao.calculate.utils;

/**
 * 所有业务操作what, 所有操作编码全局唯一, 除公共模块外,命名必须带简短的模块前缀
 * 前两位代表业务模块,后三位代表具体操作编码
 */
public interface OptWhat {

    int NONE = 100000;

    int SELECT_NUM = 100001;
    int SELECT_CONTACT = 100002;

    int TRANSFER_REQUEST = 100003;
    int TRANSFER_SWITCH_CHANGE = 100004;
    int BIND_SERVICE_SUCCESS = 100006;
    int SPLASH_SHOW_LOGIN = 100007;

}
