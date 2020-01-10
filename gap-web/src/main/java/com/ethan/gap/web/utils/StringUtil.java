package com.ethan.gap.web.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * */
    public static String objectToString(Object object, Class clazz) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
        StringBuffer sb = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        SimpleDateFormat normalFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                if ("chkValue".equals(field.getName())) {
                    continue;
                }
                if ("respDesc".equals(field.getName())) {
                    continue;
                }
                if (field.get(object) == null) {
                    continue;
                }
                if (sb.length() != 0) {
                    sb.append("&");
                }
                sb.append(field.getName());
                sb.append("=");
                if (field.getType().equals(Date.class)) {
//                    sb.append(DateUtils.getHttpDate((Date) field.get(object), "yyyyMMddHHmmss");
                    sb.append(normalFormat.format((Date) field.get(object)));
                } else if (field.getType().equals(List.class)) {
                    sb.append("[");
                    List list = (List) field.get(object);
                    String genTypeStr = field.getGenericType().toString();
                    if (genTypeStr.contains("<")) {
                        int start = genTypeStr.indexOf("<");
                        int end = genTypeStr.indexOf(">");
                        genTypeStr = genTypeStr.substring(start + 1, end);
                        Class genType = Class.forName(genTypeStr);
                        for (int i = 0; i < list.size(); i++) {
                            String str = objectToString(list.get(i), genType);
                            if (i == 0) {
                                sb.append("{");
                            } else {
                                sb.append(",{");
                            }
                            sb.append(str);
                            sb.append("}");
                        }
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                sb.append("{");
                            } else {
                                sb.append(",{");
                            }
                            sb.append(list.get(i).toString());
                            sb.append("}");
                        }
                    }
                    sb.append("]");
                } else {
                    sb.append(field.get(object).toString());
                }
            }
        }

        return sb.toString();
    }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String map2Str(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            //不拼接respDesc
            if(StringUtils.equals("respDesc", key)){
                continue;
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String mapToString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            //不拼接respDesc
            if(StringUtils.equals("respDesc", key)){
                continue;
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     * @throws UnsupportedEncodingException 
     */
    public static String mapArrayToString(Map<String, String[]> params) throws UnsupportedEncodingException  {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String[] values = params.get(key);
            StringBuffer value = new StringBuffer();
            if (values[0].indexOf("commodityNo=")!=-1) {//判断字段值是产品信息，yfj
                value.append("[");
                for (int j = 0; j < values.length; j++) {
                    String val = values[j];
                    value.append("{");
                    value.append(val);
                    if (j == values.length - 1) {
                        value.append("}");
                    } else {
                        value.append("},");
                    }
                }
                value.append("]");
            } else {
                value.append(values[0]);
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key.replace("[]", "") + "=" + value.toString();
            } else {
                prestr = prestr + key.replace("[]", "") + "=" + value.toString() + "&";
            }
        }

        return prestr;
    }
    
    /**
     * 字符串转16进制byte
     * @param src
     * @return
     */
    public static byte[] string2Hex(String src) {
        int length = (src.length() + 1 ) / 2 ;
        byte [] dst = new byte [length];
        int i;
        byte cTemp;

        for ( i = 0; i < src.length(); i++ ) {
            if( src.charAt(i) < 'A' )
                cTemp = (byte) (src.charAt(i) - '0');
            else if( src.charAt(i) < 'a' )
                cTemp = (byte) (src.charAt(i) - 'A' + 10);
            else
                cTemp = (byte) (src.charAt(i) - 'a' + 10);

            if( i % 2 == 1 )
                dst[i/2] |= cTemp;
            else
                dst[i/2] = (byte) (cTemp << 4);
        }
        return dst;
    }
    
    /**
     * byte转16进制字符串
     * @param b
     * @return
     */
    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1){
                hs = hs + "0" + stmp;
            }else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    
    /**
     * ASCII码转BCD码  
     * @param ascii
     * @param asc_len
     * @return
     */
    public static byte[] asc2Bcd(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }
    
    public static byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /**
     * BCD转字符串 
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }
    
    /**
     * 拆分字符串 
     * @param string
     * @param len
     * @return
     */
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /**
     * 拆分数组   
     * @param data
     * @param len
     * @return
     */
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }
}
