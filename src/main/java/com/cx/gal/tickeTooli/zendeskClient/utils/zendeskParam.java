package com.cx.gal.tickeTooli.zendeskClient.utils;

/**
 * Created by Galn on 06/02/2018.
 */
public class zendeskParam {
    //REST PATH
    public static final String USERS= "users.json";
    public static final String SAST_UPDATE_SCAN_SETTINGS = "sast/pluginsScanSettings"; //Update preset and configuration
    public static final String SAST_GET_SCAN_SETTINGS = "/sast/scanSettings/{projectId}"; //Update preset and configuration
    public static final String SAST_CREATE_SCAN = "sast/scans"; //Run a new Scan
    public static final String SAST_SCAN = "sast/scans/{scanId}"; //Get Scan status (by scan ID)
    public static final String TEMPLATE_TO_ENCODE = "{email}/token:{apiToken}";
}

