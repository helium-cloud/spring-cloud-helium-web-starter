package com.coral.mini.program.common.utils;



import java.math.BigInteger;
import java.util.HashMap;

public final class Utils {



    public static long toLong(byte[] arr) {
        long value = 0;
        for (int i = 0; i < arr.length; i++) {
            value |= arr[i] & 0xFF;
            if (i != arr.length - 1)
                value <<= 8;
        }
        return value;
    }

    public static byte[] PutUint16(boolean useBigEndian, long _longValue) {
        byte[] octets = new byte[2];
        if (!useBigEndian) {
            for (int i = 0; i < octets.length; i++) {
                octets[i] = (byte) ((int) ((_longValue >> (8 * i)) & 0xFF));
            }
        } else {
            for (int i = 0; i < octets.length; i++) {
                octets[octets.length - i - 1] = (byte) ((int) ((_longValue >> (8 * i)) & 0xFF));
            }
        }
        return octets;
    }

    public static byte[] PutUint32(boolean useBigEndian, long _longValue) {
        byte[] octets = new byte[4];
        if (!useBigEndian) {
            for (int i = 0; i < octets.length; i++) {
                octets[i] = (byte) ((int) ((_longValue >> (8 * i)) & 0xFF));
            }
        } else {
            for (int i = 0; i < octets.length; i++) {
                octets[octets.length - i - 1] = (byte) ((int) ((_longValue >> (8 * i)) & 0xFF));
            }
        }
        return octets;
    }

    /**
     * create by cmj
     * 连续构建参数
     * @param key
     * @param value
     * @return
     */
    public static <V> Utils startBuildParam(String key,Object value){
        HashMap<String,Object> param = new HashMap<>();
        param.put(key,value);
        Utils udrUtils = new Utils();
        udrUtils.param = param;
        return udrUtils;
    }
    public Utils buildParam(String key,Object value){
        this.param.put(key,value);
        return this;
    }
    HashMap<String,Object> param = new HashMap<>();//防止new对象调用 buildParm时 param为 null
    public HashMap<String,Object> endBuildParam(){
        return this.param;
    }

    public HashMap<String,String> endBuildStringParam(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        this.param.forEach((k,v)->{
            stringStringHashMap.put(k,v+"");
        });
        return stringStringHashMap;
    }

    private static void validate(String s) {
        if (s == null) {
            throw new IllegalArgumentException("string s may not be null");
        }

        if ((s.length() == 0) || ((s.length() % 2) != 0)) {
            throw new NumberFormatException("string is not a legal hex string.");
        }
    }

    private static byte[] fromShortHexString(String shortHexString) throws NumberFormatException {

        validate(shortHexString);

        int length = shortHexString.length();

        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            int firstCharacter = Character.digit(shortHexString.charAt(i), 16);
            int secondCharacter = Character.digit(shortHexString.charAt(i + 1), 16);

            if (firstCharacter == -1 || secondCharacter == -1) {
                throw new NumberFormatException("string is not a legal hex string.");
            }

            data[i / 2] = (byte) ((firstCharacter << 4) + secondCharacter);
        }
        return data;
    }

    /**
     * 返回 16 进制的长度 必须是 偶数,  不够的高位补0
     * @param sequenceNumber 16进制的字符
     * @param addNum
     * @return
     */
    public static  String hexAdd( String sequenceNumber,long addNum) {
        if(StringUtils.isNotBlank(sequenceNumber)){
            BigInteger bigint=new BigInteger(sequenceNumber, 16);
            long l = bigint.longValue();
            l= l+addNum;
            String hexString = Long.toHexString(l);
            if(hexString.length() % 2 != 0){
                hexString = "0"+hexString;
            }
            //保证长度为不小于12 的偶数
            if(hexString.length()<12){
                for(;;){
                    hexString="0"+hexString;
                    if(!(hexString.length()<12)){
                        break;
                    }
                }
            }
            return hexString;
        }
        return null;
    }

}
