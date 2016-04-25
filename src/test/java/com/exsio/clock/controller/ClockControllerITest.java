package com.exsio.clock.controller;

import com.exsio.clock.AbstractIntegrationTest;
import com.exsio.clock.model.JsonpResult;
import com.exsio.clock.model.TimeInfo;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

public class ClockControllerITest extends AbstractIntegrationTest {

    protected final static String GET_STATE = "basic";

    @Test(dependsOnGroups = GET_STATE)
    public void test_flow() throws Exception {

        //SET BOUNDARY
        MvcResult result = mockMvc.perform(get("/api/set/0/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

        String respBody = result.getResponse().getContentAsString();
        assertNotNull(respBody);

        JsonpResult jsonpResult = gson.fromJson(respBody, JsonpResult.class);
        assertEquals(jsonpResult, JsonpResult.success());

        TimeInfo timeInfo = getTimeInfo();

        assertEquals(timeInfo.getBoundary(), "00:01");
        assertEquals(timeInfo.getTime(), "00:00");
        assertFalse(timeInfo.isClockStarted());
        assertFalse(timeInfo.isAlert());

        //START THE CLOCK
        result = mockMvc.perform(get("/api/start").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

        respBody = result.getResponse().getContentAsString();
        assertNotNull(respBody);

        jsonpResult = gson.fromJson(respBody, JsonpResult.class);
        assertEquals(jsonpResult, JsonpResult.success());

        Thread.sleep(3000);
         timeInfo = getTimeInfo();

        assertEquals(timeInfo.getBoundary(), "00:01");
        assertTrue(timeInfo.isClockStarted());
        assertTrue(timeInfo.isAlert());

        //STOP THE CLOCK
        result = mockMvc.perform(get("/api/stop").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

        respBody = result.getResponse().getContentAsString();
        assertNotNull(respBody);

        jsonpResult = gson.fromJson(respBody, JsonpResult.class);
        assertEquals(jsonpResult, JsonpResult.success());

        Thread.sleep(1000);
        timeInfo = getTimeInfo();

        assertEquals(timeInfo.getBoundary(), "00:01");
        assertFalse(timeInfo.isClockStarted());
        assertTrue(timeInfo.isAlert());

        //RESET THE CLOCK
        result = mockMvc.perform(get("/api/reset").contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(result.getResponse().getStatus(), 200);

        respBody = result.getResponse().getContentAsString();
        assertNotNull(respBody);

        jsonpResult = gson.fromJson(respBody, JsonpResult.class);
        assertEquals(jsonpResult, JsonpResult.success());

        Thread.sleep(1000);
        timeInfo = getTimeInfo();

        assertEquals(timeInfo.getBoundary(), "00:01");
        assertEquals(timeInfo.getTime(), "00:00");
        assertFalse(timeInfo.isClockStarted());
        assertFalse(timeInfo.isAlert());
    }

    @Test(groups = GET_STATE)
    public void test_getState() throws Exception {
        mockMvc.perform(get("/api/reset")).andReturn();
        mockMvc.perform(get("/api/set/0/0")).andReturn();
        MvcResult result = mockMvc.perform(get("/api/state")).andReturn();

        assertEquals(result.getResponse().getStatus(), 200);
        String respBody = result.getResponse().getContentAsString();
        assertNotNull(respBody);

        TimeInfo timeInfo = gson.fromJson(respBody, TimeInfo.class);
        assertEquals(timeInfo.getBoundary(), "00:00");
        assertEquals(timeInfo.getTime(), "00:00");
        assertFalse(timeInfo.isClockStarted());
        assertFalse(timeInfo.isAlert());
    }

    protected TimeInfo getTimeInfo() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/state")).andReturn();
        String respBody = result.getResponse().getContentAsString();
        return gson.fromJson(respBody, TimeInfo.class);
    }


}
