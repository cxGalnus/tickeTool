package com.cx.gal.tickeTooli.logExtractor;

/**
 * Created by Galn on 10/17/2018.
 */


public enum LogTypes {

    CXARM("CxARM"),
    INSTALLATION("Installation"),
    JOB_MANAGER("JobsManager"),
    SCAN_MANAGER("ScansManager"),
    SYSTEM_MANAGER("SystemManager"),
    WEB_API("WebAPI"),
    WEB_CLIENT("WebClient"),
    WEB_SERVICES("WebServices");


    private String value;

    public String value() {
        return value;
    }

    LogTypes(String value) {
        this.value = value;
    }
}
