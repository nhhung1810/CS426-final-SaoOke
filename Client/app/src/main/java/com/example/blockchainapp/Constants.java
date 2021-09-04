package com.example.blockchainapp;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Constants {
    public static final String BASE_URL = "http://192.168.108.106:5000";
    public static final int PORT = 5000;
    public static final String RESOURCE_LOCATION = "CharityBlockchain";

    // SESSION INFORMATION
    public static int BALANCE;
    public static String USERNAME;
    public static PrivateKey PRIVATE_KEY;
    public static PublicKey PUBLIC_KEY;
    public static boolean SESSION_ACTIVE = false;
}
