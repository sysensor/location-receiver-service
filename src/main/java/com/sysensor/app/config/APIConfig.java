package com.sysensor.app.config;


public class APIConfig {
    public static final String PATH_CONFIG_CROSS_ORIGIN_URL = "*";
    //Global path config
    public static final String MAIN_PATH_API = "/api";
    public static final String MAIN_PATH_DATA = "/data";

    //Global API Parameters
    public static final String PARAM_SORT = "sort";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "size";

    //Sub Path config
    public static final String SUB_PATH_SEARCH = "search";
    public static final String SUB_PATH_SIGNAL = "signal";
    public static final String SUB_PATH_SIGNAL_SEARCH_FILTER_FIND_ALL_BY_BUS_UUID = "findAllByBusUUID";
    public static final String PARAM_SIGNAL_BUS_UUID = "busUUID";
    public static final String PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_ASC = "created,asc";
    public static final String PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_DESC = "created,desc";

    //Final DATA APIs
    public static final String FINAL_DATA_API_LOCATION_SIGNAL = MAIN_PATH_DATA + "/" + SUB_PATH_SIGNAL;
    public static final String FINAL_DATA_API_LOCATION_SIGNAL_SEARCH_BY_BUS_UUID = FINAL_DATA_API_LOCATION_SIGNAL + "/" + SUB_PATH_SEARCH + "/" + SUB_PATH_SIGNAL_SEARCH_FILTER_FIND_ALL_BY_BUS_UUID;

    //Final Custom APIs
    public static final String FINAL_CUSTOM_API_LOCATION_SIGNAL = MAIN_PATH_API + "/" + SUB_PATH_SIGNAL;
}
