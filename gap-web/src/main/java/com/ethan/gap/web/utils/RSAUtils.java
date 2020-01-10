package com.ethan.gap.web.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.ethan.gap.web.utils.BASE64Decoder;

public class RSAUtils {
        
    private static final Logger logger  = Logger.getLogger(RSAUtils.class);
    
    private static final String ENCODING = "UTF-8";
    //加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";    
    //默认的安全服务提供者
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    //签名算法
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    //获取公钥的key
    private static final String PUBLIC_KEY = "RSAPublicKey";
    //获取私钥的key
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    //RSA最大加密明文大小
    private static final int MAX_ENCRYPT_BLOCK = 117;
    //RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;
 
    /**
     * 生成密钥对(公钥和私钥)
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
     
    
    /** 
     * 使用模和指数生成RSA公钥 
     *  
     * @param modulus  模 
     * @param exponent 指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     *  
     * @param modulus  模 
     * @param exponent 指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * 
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }
 
    /**
     * 校验数字签名
     * 
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * 
     * @return
     * @throws Exception
     * 
     */
    public static boolean verify(String data, String publicKey, String sign)throws Exception {
        logger.info("data:"+data);
        logger.info("publicKey:"+publicKey);
        logger.info("sign:"+sign);
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data.getBytes(ENCODING));
        return signature.verify(Base64Utils.decode(sign));
    }
 
    /**
     * 私钥解密
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * 公钥解密
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
 
    /**
     * 公钥加密
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /**
     * 私钥加密
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
 
    /**
     * 获取私钥
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
 
    /**
     * 获取公钥
     * 
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
    
    /** 
     * 私钥加密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        // 模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = StringUtil.splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += StringUtil.bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
  
    /** 
     * 公钥解密 
     *  
     * @param data 
     * @param RSAPublicKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        cipher.init(Cipher.DECRYPT_MODE, publicKey);  
        //模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = StringUtil.asc2Bcd(bytes, bytes.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = StringUtil.splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }
    
    public static String getPubKeyByModel(String model, String exponent, int radix){
        BigInteger n = new BigInteger(model, radix);
        BigInteger e = new BigInteger(exponent, radix);
        RSAPublicKey publicKey = RSAUtils.getPublicKey(n.toString(), e.toString());
        String pubKey = Base64Utils.encode(publicKey.getEncoded());
        return pubKey;
    }
    
    /**
     * 字符串转换成十六进制字符串 
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {  
        char[] chars = "0123456789ABCDEF".toCharArray();  
        StringBuilder sb = new StringBuilder("");  
        byte[] bs = str.getBytes();  
        int bit;  
        for (int i = 0; i < bs.length; i++) {  
            bit = (bs[i] & 0x0f0) >> 4;  
            sb.append(chars[bit]);  
            bit = bs[i] & 0x0f;  
            sb.append(chars[bit]);  
        }  
        return sb.toString();  
    }  
      
    /**
     * 十六进制转换字符串 
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {  
        String str = "0123456789ABCDEF";  
        char[] hexs = hexStr.toCharArray();  
        byte[] bytes = new byte[hexStr.length() / 2];  
        int n;  
        for (int i = 0; i < bytes.length; i++) {  
            n = str.indexOf(hexs[2 * i]) * 16;  
            n += str.indexOf(hexs[2 * i + 1]);  
            bytes[i] = (byte) (n & 0xff);  
        }  
        return new String(bytes);  
    }  
      
    /**
     * bytes转换成十六进制字符串 
     * @param b
     * @return
     */
    public static String byte2HexStr(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
        }  
        return hs.toUpperCase();  
    }  
      
    private static byte uniteBytes(String src0, String src1) {  
        byte b0 = Byte.decode("0x" + src0).byteValue();  
        b0 = (byte) (b0 << 4);  
        byte b1 = Byte.decode("0x" + src1).byteValue();  
        byte ret = (byte) (b0 | b1);  
        return ret;  
    }  
      
    /**
     * 十六进制字符串 转换成bytes 
     * @param src
     * @return
     */
    public static byte[] hexStr2Bytes(String src) {  
        int m = 0, n = 0;  
        int l = src.length() / 2;  
        logger.info(l);  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            m = i * 2 + 1;  
            n = m + 1;  
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));  
        }  
        return ret;  
    } 

    /**
     * 得到私钥
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
          byte[] keyBytes;
          keyBytes = (new BASE64Decoder()).decodeBuffer(key);

          PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
          KeyFactory keyFactory = KeyFactory.getInstance("RSA");
          PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
          return privateKey;
    }
    
    /**
     * 使用给定的私钥解密给定的字符串。
     * 若私钥为 null，或者 encrypttext 为 null或空字符串则返回 null。
     * 私钥不匹配时，返回 null。
     * 
     * @param privateKey 给定的私钥。
     * @param encrypttext 密文。
     * @return 原文字符串。org.apache.commons.codec.binary.Hex
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
        	logger.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }
    
    /**
     * 使用指定的私钥解密数据。
     * 
     * @param privateKey 给定的私钥。
     * @param data 要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }
    
    /**
     * 使用指定私钥解密JS数据
     * @param source
     * @return
     */
    public static String decryptJs(String source){
	    String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI+1OGEa9cjSUI+YW6pfpqr98aJLZIx/xq+U00kTdCk22CMRk9i56vSmkyTxYAURV08InhK1NL1CIp+HUrMXm3r7N4AoR+LNgFCaPq3oKuF2jvwbe6VV2hTZeLhDKnyOP7z5p4RlMwu0w3Z/FuWrz2UWMhqA4HG0aioDmkVXjNEpAgMBAAECgYB2WlNideeySrOab2oe+MO004urk9ftdlZVyIXyCxmBz+9VgmZ2+Tct4foRVNE1m0CCkKBO2/nhXJRTOgm8AVdER1X3tn1BkKExk4/o34s2gKId3kh71cePQ0nAWmIg59wp3bmnSRsP7GGZ2A5rO2HY5xeiCF2wB0Wr9gXSz6LGOQJBAMj8t7corXOKBoVCgP3Aj9BQap7Fwaf8gSFVuWnOcDLkH7GnjDNy8B0h5dSiPJiuzVW+bPNSFXX6MUMyzU6TE5cCQQC3CulRgBRlvXn5M+6wloOkP9i3S2+2AedVtGPO9efm68jew5EpqqoYKOujgE2e4793JCHQYIziQN4bUcoQQNk/AkAQnBHyqQHsknOHf795OPiplnu5M06Vu2BQiO0RuWW8Tu3vmJEVj2IYhjygHeg0Ff4SH/KRCS+M2GhJzWD6JV1xAkBUQtCfNP+uyieRIWf6oH8fKEkCL9bQCVZN7MmZZzgG5HnGmm6DqM2+a2/2B0U0JJFqLhbmzttr+AKGvwLusnuPAkAoQf0t55Sl5mDE02b9WQo8ThQVX5OgcuXSFYL/emKTYAAKrfwfayJdoXXyD8xgWQ9zsASLGsx/ETxUtn8OZkq+";
	    try {
			RSAPrivateKey key=(RSAPrivateKey)getPrivateKey(privateKey);
			return StringUtils.reverse(decryptString(key,source));
		} catch (Exception e) {			
			e.printStackTrace();
			return "";
		}
    }
    
    public static void main(String[] args) {
        String srcData = "hello world";
    	try {
    		Map<String, Object> map = genKeyPair();
    		RSAPublicKey  publicKey = (RSAPublicKey) map.get(PUBLIC_KEY);
    		RSAPrivateKey privateKey = (RSAPrivateKey) map.get(PRIVATE_KEY); 
    		String modelHex = publicKey.getModulus().toString(16);
    		logger.info("modelHex:" + modelHex);
    		String pubKey = getPublicKey(map);
    		String priKey = getPrivateKey(map);
    		logger.info("pubKey:" + pubKey);
    		logger.info("priKey:" + priKey);
    		String signString = sign(srcData.getBytes(ENCODING), priKey);
    		boolean flag = RSAUtils.verify(srcData, pubKey, signString);
    		logger.info("flag:"+flag);
    	} catch (Exception e) {
			e.printStackTrace();
		} 
    
    }
   
}

