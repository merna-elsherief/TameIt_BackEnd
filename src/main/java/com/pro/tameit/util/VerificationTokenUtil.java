package com.pro.tameit.util;

import java.util.UUID;

public class VerificationTokenUtil {

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
