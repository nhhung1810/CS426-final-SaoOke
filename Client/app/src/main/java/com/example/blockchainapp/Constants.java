package com.example.blockchainapp;

import com.example.blockchainapp.Campaign.Campaign;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Constants {
    public static final String BASE_URL = "https://mcoin-mobile.herokuapp.com/";
    public static final int PORT = 5000;
    public static final String RESOURCE_LOCATION = "CharityBlockchain";

    // SESSION INFORMATION
    public static Campaign[] ALL_CAMPAIGN_LIST = {};
    public static String[] USER_CAMPAIGN_LIST = {"Empty"};
    public static Long BALANCE = Long.valueOf(1000);
    public static String REAL_PUBLIC_KEY;
    public static String REAL_PRIVATE_KEY;
    public static String USERNAME = "";
    public static PrivateKey PRIVATE_KEY;
    public static PublicKey PUBLIC_KEY;
    public static boolean SESSION_ACTIVE = false;
}
