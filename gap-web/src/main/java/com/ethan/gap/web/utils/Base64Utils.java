package com.ethan.gap.web.utils;

public class Base64Utils {
    
    private static char[] codec_table = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', '+', '/' };
    
    /**
     * 将base64的字符串data进行解码
     * @param 字符串
     * @return 字节流
     * @throws Exception
     */
    @SuppressWarnings("restriction")
    public static byte[] decode(String data) throws Exception {
        
        if(data == null || "".equals(data))
            return null;
        return new sun.misc.BASE64Decoder().decodeBuffer(data);
    }

    /**
     *将data进行base64编码
     * @param data
     * @return 字符串
     * @throws Exception
     */
    public static String encode(byte[] a) {
        
        int totalBits = a.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;// process bits
        StringBuffer toReturn = new StringBuffer();
        while (curPos < totalBits) {
            int bytePos = curPos / 8;
            switch (curPos % 8) {
            case 0:
                toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
                break;
            case 2:

                toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
                break;
            case 4:
                if (bytePos == a.length - 1) {
                    toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
                } else {
                    int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
                    toReturn.append(codec_table[pos]);
                }
                break;
            case 6:
                if (bytePos == a.length - 1) {
                    toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
                } else {
                    int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
                    toReturn.append(codec_table[pos]);
                }
                break;
            default:
                //never hanppen
                break;
            }
            curPos+=6;
        }
        if(nn==2)
        {
            toReturn.append("==");
        }
        else if(nn==4)
        {
            toReturn.append("=");
        }
        return toReturn.toString();

    }
}

