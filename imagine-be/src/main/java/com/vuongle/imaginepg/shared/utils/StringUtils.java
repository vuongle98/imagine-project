package com.vuongle.imaginepg.shared.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    public static String removeAccents(String text) {
        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }
//    {
//        return Normalizer
//                .normalize(text, Normalizer.Form.NFD)
//                .replaceAll("[^\\p{ASCII}]", "").replaceAll("[?!@#$%^&*()=+{}:;\\.\\,\\'\\?\\>\\<\\/\"|\\[\\]]\\\\*", "");
//
//    }


    public static String preprocessFilePath(String text) {
        text = text.replaceAll("[^a-zA-Z0-9-_. ]", " ");
        return text;
    }

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String toMD5(String text) {
      return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    public static String toBase64(String text) {
      return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static String base64ToString(String base64Text) {
      return new String(Base64.getDecoder().decode(base64Text));
    }

}
