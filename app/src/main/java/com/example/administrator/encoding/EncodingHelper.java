package com.example.administrator.encoding;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by lei on 2016/10/14.
 */
public class EncodingHelper {
    public static final int DES_ENCODING = 1;
    public static final int DES_DECODING = 2;
    public static final int RSA_ENCODING = 1;
    public static final int RSA_DECODING = 2;


    //两个大素数的乘积
//    private static final String MODULUS = "123123123123";
    private static final String MODULUS = "100631058000714094813874361191853577129731636346" +
            "6842182066057798249316268307506230708031001897812113438517632753293640566406197" +
            "5533777992898527248609143138412802721336537200964823317189470833821316882486106" +
            "1809490615593530405056055952622249066180336803996949444124622212096805545953751" +
            "253607916170340397933039";
    //公钥
    private static final String PUBLIC_KEY = "65537";
    //私钥
//    private static final String PRIVATE_KEY = "234234234234";
    private static final String PRIVATE_KEY = "2690015571531364308778651652837454899882155938107" +
            "5740707715132776187148793016466508650068087107695523642202737697714709374658856" +
            "7337926144909438742059567276066746345636651546167589395765476637152346432730556" +
            "5882948281350395945965370806287562521000896123964377566135765559931285724941861" +
            "0810177817213648575161";

    /**
     * * md5 encoding
     * @param message
     * @return encoding result
     */
    public static String md5Encoding(String message){
        try {
            //* 创建消息摘要实例，md5 表示生成消息摘要的算法名称
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            //* 返回一个哈希结果的数组
            byte[] digest = messageDigest.digest(message.getBytes("UTF-8"));
            //* 用16进制显示生成的消息摘要
            StringBuffer result = new StringBuffer();
            for (byte bt : digest){
                //* 转化为16进制
                result.append(String.format("%02x",bt));
            }
            return result.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *base 64编码
     * @param message
     * @return
     */
    public static String base64Encoding(String message){
        String result = Base64.encodeToString(message.getBytes(), Base64.DEFAULT);

        return result;
    }
    /**
     * * Base64 解密
     */
    public static String decodeBase64(String message){
        byte[] decode = Base64.decode(message, Base64.DEFAULT);
        String result = new String(decode);
        return result;
    }


    /**
     * * 对称DES加/密
     */
    public static String desEncode(String message, String key, int mode){

        String charsetName = "UTF-8";
        try {
            byte[] bytes = key.getBytes(charsetName);
            byte[] temp = new byte[8];  //* des 加密是8位
            System.arraycopy(bytes,0,temp,0, Math.min(bytes.length,temp.length));

            SecretKey secretKey = new SecretKeySpec(temp, "des");
            //* 创建密码生成器，参数表示生成密码的算法名称
            Cipher cipher = Cipher.getInstance("des");
            //* 加密
            if (mode == DES_ENCODING){
                cipher.init(Cipher.ENCRYPT_MODE,secretKey);
                //* 生成密文
                byte[] bytes1 = cipher.doFinal(message.getBytes(charsetName));
                return Base64.encodeToString(bytes1, Base64.DEFAULT);
            }else {

                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] bytes1 = cipher
                        .doFinal(Base64.decode(message, Base64.DEFAULT));
                return new String(bytes1, 0, bytes1.length);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * * RSA 加密
     * @param message
     * @return
     */
    public static String rsaEncoding(String message){
            //获得生成一个公钥/私钥的工厂方法
            try {
                KeyFactory keyFactory = KeyFactory.getInstance("rsa");
                RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new BigInteger(MODULUS), new BigInteger(PUBLIC_KEY));
                //获得一个密码生成器，参数表示生成密码的算法
                Cipher cipher = Cipher.getInstance("rsa");
                //生成公钥
                PublicKey key = keyFactory.generatePublic(rsaPublicKeySpec);
                //用公钥加密文本
                cipher.init(Cipher.ENCRYPT_MODE, key);
                //获得密文
                byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
                //*使用base64编码一下，以免出现乱码，编码时要使用base64解码
                String result = Base64.encodeToString(bytes, Base64.DEFAULT);

//                byte[] bytes = cipher.doFinal(Base64.decode(message, Base64.DEFAULT));
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }

        return null;
    }

    /**
     * *RSA 解密
     * @param message
     * @return
     */
    public static String rsaDecoding(String message){
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("rsa");

            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(new BigInteger(MODULUS), new BigInteger(PRIVATE_KEY));
            Cipher cipher = Cipher.getInstance("rsa");
            //获取私钥
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(Base64.decode(message, Base64.DEFAULT));

            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * * AES加密算法
     * @param message
     * @param password 为8位
     * @return
     */
    public static String aesEncoding(String message, String password){
        try {
//            byte[] content = message.getBytes();
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(message.getBytes("UTF-8"));

//            byte[] bytes = cipher.doFinal(message.getBytes("UTF-8"));
//            *使用base64编码一下，以免出现乱码，编码时要使用base64解码
//            String result = Base64.encodeToString(bytes, Base64.DEFAULT);

            return Base64.encodeToString(result, Base64.DEFAULT); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("lei", "aesEncoding: " + e.getMessage()+ " --- " + e.toString());
        }
        Log.i("lei", "aes: 执行");
        return null;
//        String charsetName = "UTF-8";
//        try {
//            byte[] keyBytes = key.getBytes(charsetName);
//            byte[] temp = new byte[32];
//            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
//            SecretKey secretKey = new SecretKeySpec(temp, "aes");
//            Cipher cipher = Cipher.getInstance("aes");
////            if (mode == ENCRYPT) {
////                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
////                byte[] bytes = cipher.doFinal(src.getBytes(charsetName));
//                return Base64.encodeToString(bytes, Base64.DEFAULT);
////            } else {
////                cipher.init(Cipher.DECRYPT_MODE, secretKey);
////                byte[] bytes = cipher.doFinal(Base64.decode(src, Base64.DEFAULT));
////                return new String(bytes, 0, bytes.length);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    /**
     * * AES 解密       * 有问题
     * @param message
     * @param password
     * @return
     */
    public static String aesDeconding(String message, String password){
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(Base64.decode(message, Base64.DEFAULT));

            return new String(result); // 加密
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("lei", "aesDeconding exception: " + e.getMessage()+ " --- " + e.toString());
        }
        Log.i("lei", "aesDeconding: return null ");
        return null;
    }



//    //* jiami
//    public static byte[] encrypt(String content, String password) {
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(password.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//            byte[] byteContent = content.getBytes("utf-8");
//            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(byteContent);
//            return result; // 加密
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static byte[] decrypt(byte[] content, String password) {
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(password.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
//            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(content);
//            return result; // 加密
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("lei", "decrypt: " + e.getMessage() + " -- " + e.toString());
//        }
//        Log.i("lei", "decrypt: return null");
//        return null;
//    }
}
