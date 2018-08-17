package com.sysensor.app.config;


import org.junit.Assert;
import org.junit.Test;

public class APIConfigTest {

    @Test
    public void constanceValuesShouldMatch() {
        Assert.assertEquals("*", APIConfig.PATH_CONFIG_CROSS_ORIGIN_URL);
        //Global path config
        Assert.assertEquals("/api", APIConfig.MAIN_PATH_API);
        Assert.assertEquals("/data", APIConfig.MAIN_PATH_DATA);

        //Global API Parameters
        Assert.assertEquals("page", APIConfig.PARAM_PAGE);
        Assert.assertEquals("size", APIConfig.PARAM_PAGE_SIZE);
        Assert.assertEquals("sort", APIConfig.PARAM_SORT);

        //Sub Path config
        Assert.assertEquals("search", APIConfig.SUB_PATH_SEARCH);
        Assert.assertEquals("signal", APIConfig.SUB_PATH_SIGNAL);
        Assert.assertEquals("findAllByBusUUID", APIConfig.SUB_PATH_SIGNAL_SEARCH_FILTER_FIND_ALL_BY_BUS_UUID);
        Assert.assertEquals("busUUID", APIConfig.PARAM_SIGNAL_BUS_UUID);
        Assert.assertEquals("created,asc", APIConfig.PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_ASC);
        Assert.assertEquals("created,desc", APIConfig.PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_DESC);

        //Final DATA APIs
        Assert.assertEquals("/data/signal", APIConfig.FINAL_DATA_API_LOCATION_SIGNAL);
        Assert.assertEquals("/data/signal/search/findAllByBusUUID", APIConfig.FINAL_DATA_API_LOCATION_SIGNAL_SEARCH_BY_BUS_UUID);

        //Final Custom APIs
        Assert.assertEquals("/api/signal", APIConfig.FINAL_CUSTOM_API_LOCATION_SIGNAL);
    }
}
