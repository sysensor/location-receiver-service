package com.sysensor.app.api;

import com.google.gson.Gson;
import com.sysensor.app.TestConst;
import com.sysensor.app.config.APIConfig;
import com.sysensor.app.model.LocationSignal;
import org.hamcrest.collection.IsCollectionWithSize;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class SignalDataAPITest {

    Gson JSON = new Gson();
    @Autowired
    private MockMvc mock;

    @Test
    public void signalDataAPIShouldReturnNineSignals() throws Exception {

        String userName = "admin";
        String userPassword = "admin";
        String userAuthorization = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + userPassword).getBytes());


        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL)
                .param(APIConfig.PARAM_PAGE, "0")
                .param(APIConfig.PARAM_PAGE_SIZE, "10")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(9)))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_ONE_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_TWO_UUID + "')].bus_uuid").value(TestConst.BUS_TWO_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_THREE_UUID + "')].bus_uuid").value(TestConst.BUS_THREE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_FOUR_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_FIVE_UUID + "')].bus_uuid").value(TestConst.BUS_TWO_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_SIX_UUID + "')].bus_uuid").value(TestConst.BUS_THREE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_SEVEN_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_EIGHT_UUID + "')].bus_uuid").value(TestConst.BUS_TWO_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_NINE_UUID + "')].bus_uuid").value(TestConst.BUS_THREE_UUID));
    }

    @Test
    public void signalDataAPIShouldCreateAndDeleteTheSignal() throws Exception {

        String userName = "admin";
        String userPassword = "admin";
        String userAuthorization = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + userPassword).getBytes());

        LocationSignal locationSignal = new LocationSignal();
        locationSignal.setBus_uuid(TestConst.BUS_ONE_UUID);
        locationSignal.setLat("2.09090");
        locationSignal.setLng("80.09000");

        String signalJson = JSON.toJson(locationSignal);
        JSONObject signalFinalJson = new JSONObject(signalJson);

        System.out.println("====" + signalFinalJson.toString());
        List<String> selfList = new ArrayList<>();

        //Create LocationSignal
        this.mock.perform(post(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization)
                .content(signalFinalJson.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath(".bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath(".lat").value("2.09090"))
                .andExpect(jsonPath(".lng").value("80.09000"))
                .andDo((result) -> {
                    JSONObject json = new JSONObject(result.getResponse().getContentAsString());
                    //Capture the returned SELF URL for the delete operation
                    selfList.add(json.getJSONObject("_links").getJSONObject("self").getString("href"));
                });

        //Check the LocationSignal list
        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(10)));

        //Delete the LocationSignal
        this.mock.perform(delete(selfList.get(0))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isNoContent());

        //Check the LocationSignal list
        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(9)));


    }

    @Test
    public void signalDataAPIShouldReturnThreeSignalsForBusOneInAscendingOrderByCreatedDate() throws Exception {

        String userName = "admin";
        String userPassword = "admin";
        String userAuthorization = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + userPassword).getBytes());

        this.mock.perform(get(APIConfig.FINAL_DATA_API_LOCATION_SIGNAL_SEARCH_BY_BUS_UUID)
                .param(APIConfig.PARAM_SIGNAL_BUS_UUID, TestConst.BUS_ONE_UUID)
                .param(APIConfig.PARAM_PAGE, "0")
                .param(APIConfig.PARAM_PAGE_SIZE, "10")
                .param(APIConfig.PARAM_SORT, APIConfig.PARAM_SIGNAL_VALUE_SORT_ATTRIBTE_CREADTED_ASC)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", userAuthorization))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.signal").value(IsCollectionWithSize.hasSize(3)))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_ONE_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_FOUR_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_SEVEN_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$.page.size").value(10))
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.page.number").value(0));
    }

    @Test
    public void signalDataAPIShouldReturnOneSignalForBusOneInDescendingOrderByCreatedDate() throws Exception {

        String userName = "admin";
        String userPassword = "admin";
        String userAuthorization = "Basic " + Base64.getEncoder().encodeToString((userName + ":" + userPassword).getBytes());

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
                .andExpect(jsonPath("$._embedded.signal.[?(@._links.self.href=='http://localhost/data/signal/" + TestConst.SIGNAL_SEVEN_UUID + "')].bus_uuid").value(TestConst.BUS_ONE_UUID))
                .andExpect(jsonPath("$.page.size").value(1))
                .andExpect(jsonPath("$.page.totalElements").value(3))
                .andExpect(jsonPath("$.page.totalPages").value(3))
                .andExpect(jsonPath("$.page.number").value(0));
    }

}
