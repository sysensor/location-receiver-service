package com.sysensor.app.api;

import com.google.gson.Gson;
import com.sysensor.app.TestConst;
import com.sysensor.app.config.APIConfig;
import com.sysensor.app.model.LocationSignal;
import com.sysensor.app.repository.LocationSignalRepo;
import org.hamcrest.collection.IsCollectionWithSize;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignalCustomAPITest {

    Gson JSON = new Gson();
    @Autowired
    private MockMvc mock;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    LocationSignalRepo locationSignalRepo;

    @Test
    public void signalCustomAPIShouldBeAbleToPostTheSignal() throws Exception {

        String userName = "admin";
        String userPassword = "admin";
        String userAuthorization = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + userPassword).getBytes());

        LocationSignal locationSignal = new LocationSignal();
        locationSignal.setBus_uuid(TestConst.BUS_ONE_UUID);
        locationSignal.setLat("77.90");
        locationSignal.setLng("88.09000");

        String signalJson = JSON.toJson(locationSignal);
        JSONObject signalFinalJson = new JSONObject(signalJson);

        this.mock.perform(post(APIConfig.FINAL_CUSTOM_API_LOCATION_SIGNAL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization)
                .content(signalFinalJson.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath(".bus_uuid").value("bf4cb437-4881-450b-b0fa-4cfe077b4008"))
                .andExpect(jsonPath(".lat").value("77.90"))
                .andExpect(jsonPath(".lng").value("88.09000"));

        //get the latest signal UUID for deletion
        List<String> deleteList = new ArrayList<>();
        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL_SEARCH_BY_BUS_UUID)
                .param(APIConfig.PARAM_SIGNAL_BUS_UUID, TestConst.BUS_ONE_UUID)
                .param(APIConfig.PARAM_PAGE, "0")
                .param(APIConfig.PARAM_PAGE_SIZE, "1")
                .param(APIConfig.PARAM_SORT, APIConfig.PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_DESC)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$._embedded.signal.[0].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[0].lat").value("77.90"))
                .andExpect(jsonPath("$._embedded.signal.[0].lng").value("88.09000"))
                .andDo((result) -> {
                    JSONObject json = new JSONObject(result.getResponse().getContentAsString());
                    //Capture the returned SELF URL for the delete operation
                    deleteList.add(json.getJSONObject("_embedded").getJSONArray("signal").getJSONObject(0).getJSONObject("_links").getJSONObject("self").getString("href"));
                })
                .andExpect(jsonPath("$.page.size").value(1))
                .andExpect(jsonPath("$.page.totalElements").value(4))
                .andExpect(jsonPath("$.page.totalPages").value(4))
                .andExpect(jsonPath("$.page.number").value(0));

        //Delete the LocationSignal
        this.mock.perform(delete(deleteList.get(0))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isNoContent());

        //Check the LocationSignal list
        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL_SEARCH_BY_BUS_UUID)
                .param(APIConfig.PARAM_SIGNAL_BUS_UUID, TestConst.BUS_ONE_UUID)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(3)));
    }
}
