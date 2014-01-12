package com.bharathmg.irctc.utils;

/**
 * Created by bharathmg on 12/01/14.
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMACGenerator {

    String input;
    String privateKey;

    public HMACGenerator(String input,String privateKey){
        this.input = input;
        this.privateKey = privateKey;
    }

    /**
     */
    public String generateHMAC() {
        String mykey = "my_private_key";
        String test = "pnrbuddy";
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(privateKey.getBytes(),"HmacSHA1");
            mac.init(secret);
            byte[] digest = mac.doFinal(input.getBytes());
            StringBuilder hmac_sign = new StringBuilder();
            for (byte b : digest) {
//                System.out.format("%02x", b);
                hmac_sign.append(String.format("%02x",b));
            }
            System.out.println();

            return new String(hmac_sign);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
