package com.ruoyi.common.utils;

import com.qiniu.util.Hex;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
public class AESUtil {

    private static final String AES = "AES";


    public static String decrypt(String content, String aesKey) {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(aesKey)) {
            log.error("[AESUtil.decrypt] encrypt or key is empty");
            return null;
        }

        try {
            byte[] raw = aesKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(Hex.decodeHex(content.toCharArray())));
        } catch (Exception e) {
            log.error( "AESUtil.decryptAES fail, error:[{}]", e.getMessage());
        }
        return null;
    }

    public static String encrypt(String content, String aesKey) {

        if (StringUtils.isBlank(content) || StringUtils.isBlank(aesKey)) {
            log.error("[AESUtil.encrypt] encrypt or key is empty");
            return null;
        }

        try {
            byte[] raw = aesKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return new String(Hex.encodeHex(cipher.doFinal(content.getBytes())));
        } catch (Exception e) {
            log.error( "AESUtil.decryptAES fail, error:[{}]", e.getMessage());
            return null;
        }
    }
}