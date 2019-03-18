package com.amao.calculate.utils;

import android.telephony.SmsMessage;

import org.xutils.common.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidationUtils {

    private final static String REG_PHONE = "^1[3,4,5,6,7,8,9]\\d{9}$";

    private final static String REG_VCODE = "^\\d{6}$";
    private final static String REG_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private final static String REG_MODIFY_LOGIN_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";  //修改登录密码 6-18位字母加数字

    public static boolean matches(String reg, String input) {
        if (input == null || input.length() <= 0) return false;
        return Pattern.matches(reg, input);
    }

    public static boolean isPhone(String phone) {
        return matches(REG_PHONE, phone);
    }

    public static boolean isVCode(String vcode) {
        return matches(REG_VCODE, vcode);
    }

    public static boolean isEmail(String email) {
        return matches(REG_EMAIL, email);
    }

    public static boolean isPwd(String pwd) {
        return matches(REG_MODIFY_LOGIN_PWD, pwd);
    }

    public static String getValidCode(Object[] pdusObj) {
        int size = (pdusObj == null ? 0 : pdusObj.length);
        for (int i = 0; i < size; i++) {
            byte[] obj = (byte[]) pdusObj[i];
            SmsMessage msg = SmsMessage.createFromPdu(obj);
            String body = msg.getDisplayMessageBody();
            String address = msg.getDisplayOriginatingAddress();
            if (!address.startsWith("10")) {
                continue;
            }
            if (!body.startsWith("【安全手机】")) {
                continue;
            }

            Pattern pattern = Pattern.compile("(\\d{6})");
            Matcher matcher = pattern.matcher(body);

            if (matcher.find()) {
                String code = matcher.group(0);
                LogUtil.d("main", "验证码为: " + code);
                return code;
            }
        }
        return null;
    }
}
