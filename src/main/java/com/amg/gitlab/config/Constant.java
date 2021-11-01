package com.amg.gitlab.config;


import java.util.HashMap;
import java.util.Map;

public class Constant {

    public final static String PROJECTS_URL_BASE = "https://gitlab.momoso.com/cm/fulfillment/fulfillment-micro-service/";

    public final static String PROTECTED_BRANCHES_URL = "https://gitlab.momoso.com/api/v4/projects/%s/protected_branches?name=%s&push_access_level=0&merge_access_level=0";

    public final static String UNPROTECT_BRANCHES_URL = "https://gitlab.momoso.com/api/v4/projects/%s/protected_branches/%s";

    public final static String GITLAB_WORK_DIR = "D:\\\\gitlab\\\\work";

    public final static String HEADER_KEY = "PRIVATE-TOKEN";

    public final static String HEADER_VALUE = "XFdoYxftzPbj-ecMzc9q";

    public final static Map<String, Integer> projectMap = new HashMap();

    public final static String[] PROJECT_LIST = {
            "fulfillment-basic-service",
            "fulfillment-order-service",
            "fulfillment-message-service",
            "fulfillment-purchase-service",
            "fulfillment-logistics-service",
            "fulfillment-aftersale-service",
    };

    static {
        projectMap.put("fulfillment-basic-service", 354);
        projectMap.put("fulfillment-order-service", 355);
        projectMap.put("fulfillment-message-service", 363);
        projectMap.put("fulfillment-purchase-service", 364);
        projectMap.put("fulfillment-logistics-service", 365);
        projectMap.put("fulfillment-aftersale-service", 366);
    }

}
