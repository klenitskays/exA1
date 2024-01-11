package com.example.ex1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Ex1Application {

    public static long ipv4ToLong(String ipAddress) {
        String[] octets = ipAddress.split("\\.");
        long result = 0;
        for (int i = 0; i < 4; i++) {
            result |= (Long.parseLong(octets[i]) << (8 * (3 - i)));
        }
        return result;
    }

    public static String longToIpv4(long ipLong) {
        return ((ipLong >> 24) & 0xFF) + "."
                + ((ipLong >> 16) & 0xFF) + "."
                + ((ipLong >> 8) & 0xFF) + "."
                + (ipLong & 0xFF);
    }

    public static void main(String[] args) {
        long longIp1 = 2149583360L;
        String ipStr1 = longToIpv4(longIp1);
        System.out.println(longIp1 + " ==> " + ipStr1);

        long longIp2 = 255L;
        String ipStr2 = longToIpv4(longIp2);
        System.out.println(longIp2 + " ==> " + ipStr2);

        String ipStr3 = "128.32.10.0";
        long longIp3 = ipv4ToLong(ipStr3);
        System.out.println(ipStr3 + " ==> " + longIp3);
    }
}
