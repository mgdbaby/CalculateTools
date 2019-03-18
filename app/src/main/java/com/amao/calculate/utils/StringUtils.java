package com.amao.calculate.utils;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 判断char是否为中文
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }

    /**
     * 隐藏号码中间5位,缩减号码长度作为昵称
     **/
    public static String hidePhoneMiddle(String phone, String hideStr) {
        StringBuilder builder = new StringBuilder(11);
        builder.append(phone);
        builder.replace(3, 8, hideStr);
        return builder.toString();
    }

//    public static String hidePhoneMiddle(String phone) {
//        if (!ValidationUtils.isPhone(phone)) {
//            return phone;
//        }
//        return hidePhoneMiddle(phone, "****");
//    }

    public static String hidePhone(String phone) {
        char[] chars = phone.toCharArray();
        int size = chars.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i == 3) {
                i += 4;
                builder.append("*****");
            } else {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 忽略大小写（是否包含）
     */
    public static boolean contain(String input, String regex) {
        if(TextUtils.isEmpty(input) || TextUtils.isEmpty(regex)){
            return false;
        }

        String strInput = input.toUpperCase();
        String strRegex = regex.toUpperCase();
        return strRegex.contains(strInput);
    }


//    /***
//     * 获取字符串第一个汉字拼音的首字母
//     */
//    public static String getFirstLetter(String str) {
//        String pinyin;
//        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(pinyin = Pinyin.toPinyin(str.charAt(0)))) {
//            return "";
//        }
//        return String.valueOf(pinyin.charAt(0));
//    }

    /****
     *格式化字母 A-Z,不在其内的返回"_"
     * @param label
     * @return
     */
    public static String formatLabel(String label) {
        if (!TextUtils.isEmpty(label)) {
            int chr = label.charAt(0);
            if (chr >= 65 && chr <= 90) {
                return label;
            }
        }
        return "_";
    }

    //用正则表达式
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isTrimEmpty(String str) {
        return (str == null || isEmpty(str.trim()));
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static String trim(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        }
        return str.trim();
    }

    public static boolean startsWith(String str, String startWith) {
        int length = startWith.length();
        if (length > str.length()) {
            //如果被期待为开头的字符串的长度大于anotherString的长度  
            return false;
        }
        return startWith.equalsIgnoreCase(str.substring(0, length));
    }

    public static float toFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return 0.0F;
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int defVal) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.parseInt(str.trim());
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return defVal;
    }

    public static byte toByte(String str, byte defVal) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Byte.parseByte(str.trim());
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return defVal;
    }

    public static long toLong(String str) {
        return toLong(str, 0);
    }

    public static long toLong(String str, long defVal) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return Long.parseLong(str.trim());
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return defVal;
    }

    public static JSONObject toJSON(String str) {
        if (str != null && str.startsWith("{") && str.endsWith("}")) {
            try {
                return JSON.parseObject(str);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        return null;
    }

    public static String f(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    public static String iso2Utf8(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                return new String(str.getBytes("ISO8859_1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                LogUtil.d("String iso8859_1 to utf8 error.", e);
            }
        }
        return str;
    }

    public static byte[] utf8ToBytes(String content) {
        try {
            if (content != null) {
                return content.getBytes("UTF-8");
            }
        } catch (Exception e) {
            LogUtil.e("utf8ToBytes error.", e);
        }
        return null;
    }

    public static String bytes2Utf8(byte[] data) {
        if (data == null || data.length <= 0) {
            return "";
        }
        try {
            return new String(data, "UTF-8");
        } catch (Exception e) {
            LogUtil.e("Bytes2String error.", e);
            return new String(data);
        }
    }

    public static String formatArray(int[] array) {
        if (array == null || array.length <= 0) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        for (int o : array) {
            builder.append(o).append("\n");
        }
        return builder.toString();
    }

    public static String newLine(){
        return "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
    }

    public static String formatBundle(Bundle bundle) {
        if (bundle == null || bundle.size() <= 0) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        for (String key : bundle.keySet()) {
            builder.append(key).append(" = ").append(bundle.get(key)).append(newLine());
        }
        return builder.toString();
    }

    public static String formatList(Collection<?> collection) {
        if (collection == null || collection.size() <= 0) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(o).append("\n");
        }
        return builder.toString();
    }

    public static String formatCursor(Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        do {
            builder.append(cursor).append("\n");
        } while (cursor.moveToNext());
        return builder.toString();
    }

    public static String cursor2String(Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        do {
            builder.append("\n");
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String value = cursor.getString(i);
                int len = value == null ? 0 : value.length();
                if (len > 50) {
                    builder.append("\n");
                }

                builder.append(cursor.getColumnName(i))
                        .append("=").append(cursor.getString(i))
                        .append(", ");
                if (len > 50) {
                    builder.append("\n");
                }
            }
            if (builder.charAt(builder.length() - 1) == '\n') {
                builder.setLength(builder.length() - 1);
            }
        } while (cursor.moveToNext());
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
//    public static String formatProtobuf(GeneratedMessage.Builder builder) {
//        if (builder == null) {
//            return "null";
//        }
//
//        Map<Descriptors.FieldDescriptor, Object> builderMap = builder.getAllFields();
//        if (builderMap.isEmpty()) {
//            return "null";
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        int idx = 0;
//        for (Descriptors.FieldDescriptor field : builderMap.keySet()) {
//            if (idx > 0) {
//                sb.append("\n");
//            }
//            Object value = builderMap.get(field);
//            String s = (value == null ? "null" : value.toString());
//            sb.append(field.getName()).append(" = ").append(s);
//            idx++;
//        }
//        return sb.toString();
//    }
//
//    @SuppressWarnings("unchecked")
//    public static String formatProtobuf(GeneratedMessage builder) {
//        if (builder == null) {
//            return "null";
//        }
//
//        Map<Descriptors.FieldDescriptor, Object> builderMap = builder.getAllFields();
//        if (builderMap.isEmpty()) {
//            return "null";
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        int idx = 0;
//        for (Descriptors.FieldDescriptor field : builderMap.keySet()) {
//            if (idx > 0) {
//                sb.append("\n");
//            }
//            Object value = builderMap.get(field);
//            String s = (value == null ? "null" : value.toString());
//            sb.append(field.getName()).append(" = ").append(s);
//            idx++;
//        }
//        return sb.toString();
//    }

    /**
     * 格式化
     *
     * @param jsonStr
     * @return
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "null";
        StringBuilder sb = new StringBuilder();
        char last;
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static String byte2Hex(byte[] b) {
        String tmp;
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < b.length; n++) {
            tmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((tmp.length() == 1) ? "0" + tmp : tmp);
        }
        return sb.toString().toLowerCase().trim();
    }


    public static String urlDecode(String srcUrlEncode) {
        try {
            return URLDecoder.decode(srcUrlEncode, "utf-8");
        } catch (IllegalArgumentException e) {
            return srcUrlEncode;
        } catch (Exception e2) {
            LogUtil.e(e2);
            return srcUrlEncode;
        }
    }


    /**
     * 检查输入的数据中是否有特殊字符
     *
     * @param qString 要检查的数据
     * @return boolean 如果包含正则表达式<code>regx</code>中定义的特殊字符，返回true；
     * 否则返回false
     */
    public static boolean hasCrossScriptRisk(String qString) {
        String regx = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        if (qString != null) {
            qString = qString.trim();
            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);
            return m.find();
        }
        return false;
    }




    public static String urlEncode(String src) {
        try {
            return URLEncoder.encode(src, "utf-8");
        } catch (Exception e) {
            LogUtil.e(e);
            return src;
        }
    }
}
