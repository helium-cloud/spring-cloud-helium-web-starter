package org.helium.web.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author heaixin@feinno.com
 * @date 2018/4/14 14:48
 */
public class StringUtils {
    public static boolean isNullOrEmpty(String arg) {
        if (arg == null || arg.isEmpty()) {
            return true;
        }
        if (arg.equalsIgnoreCase("null")){
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(String arg) {
        if (arg == null || arg.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 检查密码合法性
     * 规则：
     * 可以Base64.decode
     * 长度>=8
     * 至少包含字母（区分大小写）、数字
     *
     * @param password
     * @return
     */
    public static boolean isPassword(String password) throws UnsupportedEncodingException {
        if (!isNullOrEmpty(password)) {
            Base64.Decoder base64De = Base64.getDecoder();
            byte[] decodePwd = base64De.decode(password);
            if (decodePwd.length >= 8) {
                int count = 0;
                for (byte item : decodePwd) {
                    //基于ascii码判断
                    if ((item > 47 && item < 58) || (item > 64 && item < 91) || (item > 96 && item < 123)) {
                        count++;
                    }
                }
                if (count == decodePwd.length) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 手机号判定
     * 要求规格为
     * 座机：国际区号-国内区号-电话号
     * 手机：国际区号-手机号
     * 国际区号识别：00 开头，后跟国际区号实际值；如：中国 0086
     * 起始位为 + 或 00 即表示带有国际区号
     * 自国际区号段中获取相应的区号值，目前仅支持中国大陆、中国香港
     *
     * @param phoneNo
     * @return short -1 非法 ；0 手机；1 固话
     */
    public static short isMobileNo(String phoneNo) {
        String phoneNoTemp = new String(phoneNo.getBytes(), StandardCharsets.UTF_8);
        if (!isNullOrEmpty(phoneNoTemp)) {
            if (phoneNoTemp.indexOf("-") > 0) {
                if (phoneNoTemp.indexOf("-", phoneNoTemp.indexOf("-") + 1) > 0) {
                    //固话
                    String[] telNo = phoneNoTemp.split("-");
                    if (telNo[0].equals("0086") || telNo[0].equals("00852")) {
                        //todo 应添加实际的区号验证机制，且需要识别港、澳、台的特殊性
                        return 1;
                    }
                }
                //手机，暂不考虑其它国家（地区）的国际区号
                String[] mobileNo = phoneNoTemp.split("-");
                switch (mobileNo[0]) {
                    case ("0086"):
                        if (mobileNo[1].startsWith("1") && mobileNo[1].length() == 11) {
                            return 0;
                        }
                        break;
                    case ("00852"):
                        if ((mobileNo[1].startsWith("5") || mobileNo[1].startsWith("6") || mobileNo[1].startsWith("9")) && mobileNo[1].length() == 8) {
                            return 0;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return -1;
    }

    /**
     * 判定是否为电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        try {
            if (email.contains("@")) {
                if (email.indexOf(".") > 0) {
                    String[] preffix = email.split("@");
                    if (preffix.length == 2) {
                        if (!isNullOrEmpty(preffix[0]) && !isNullOrEmpty(preffix[1])) {
                            String[] suffix = preffix[1].split("\\.");
                            if (suffix.length >= 2) {
                                int count = 0;
                                for (String item : suffix) {
                                    if (!isNullOrEmpty(item)) {
                                        count++;
                                    }
                                }
                                if (count == suffix.length) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {

        } finally {

        }
        return false;
    }
}
