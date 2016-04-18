package com.exsio.clock.ui.controls;

import com.beust.jcommander.internal.Lists;
import com.exsio.clock.ui.UI;
import com.exsio.clock.ui.task.TestUITaskExecutorImpl;
import com.google.common.base.Optional;
import com.google.common.net.InetAddresses;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ControlsFramePresenterTest {

    private final static String IP_ADDRESS = "192.168.0.1";

    private final static String NIC_NAME = "NIC";

    ControlsFramePresenter underTest;

    ControlsFrameView view;

    @Mock
    ControlsFormPresenter formPresenter;

    @Mock
    ControlsFormView formView;

    NetworkInterface nic;

    @Mock
    Desktop desktop;

    @Mock
    ApplicationReadyEvent applicationReadyEvent;

    @BeforeMethod
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        initializeNetwork();

        when(formPresenter.getView()).thenReturn(formView);

        ControlsFramePresenter presenter = mock(ControlsFramePresenter.class);
        when(presenter.getNetworkInterfacesMap()).thenReturn(Collections.singletonMap(NIC_NAME, IP_ADDRESS));

        view = spy(new ControlsFrameView(presenter));
        doNothing().when(view).add(Mockito.<Component>any(), anyObject());
        doNothing().when(view).setVisible(anyBoolean());

        Mockito.reset(desktop);
        when(desktop.isSupported(Desktop.Action.BROWSE)).thenReturn(true);

        UI.setDesktop(Optional.of(desktop));

        underTest = new ControlsFramePresenter(view, formPresenter, new TestUITaskExecutorImpl()) {

            @Override
            Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
                return Lists.newArrayList(nic);
            }
        };
    }

    private void initializeNetwork() throws Exception {
        Constructor<NetworkInterface> constructor = NetworkInterface.class.getDeclaredConstructor(String.class, int.class, InetAddress[].class);
        constructor.setAccessible(true);
        nic = constructor.newInstance(NIC_NAME, 0, new InetAddress[]{InetAddresses.forString(IP_ADDRESS)});

        Field dispName = NetworkInterface.class.getDeclaredField("displayName");
        dispName.setAccessible(true);
        dispName.set(nic, NIC_NAME);
    }

    @Test
    public void test_getNetworkInterfacesMap() {
        Map<String, String> result = underTest.getNetworkInterfacesMap();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.containsKey(NIC_NAME));
        assertEquals(result.get(NIC_NAME), IP_ADDRESS);
    }

    @Test
    public void test_getNetworkInterfacesMap_empty() {
        underTest = new ControlsFramePresenter(view, formPresenter, new TestUITaskExecutorImpl()) {

            @Override
            Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
                return Lists.newArrayList();
            }
        };

        Map<String, String> result = underTest.getNetworkInterfacesMap();
        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertTrue(result.containsKey(ControlsFramePresenter.LOOPBACK_NAME));
        assertEquals(result.get(ControlsFramePresenter.LOOPBACK_NAME), ControlsFramePresenter.LOOPBACK_IP);
    }

    @Test
    public void test_onApplicationStart() throws InterruptedException {
        underTest.onApplicationStart(applicationReadyEvent);
        Thread.sleep(1000);
        verify(view).setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        verify(view).add(formView, BorderLayout.CENTER);
        verify(view).init();
        verify(view).pack();
        verify(view).setVisible(true);
        verify(formPresenter).init();
        verify(formPresenter).getView();

    }

    @Test
    public void test_openClockClicked() throws Exception {
        underTest.openClockClicked(IP_ADDRESS);
        verify(desktop).browse(new URL("http://" + IP_ADDRESS + ":8080/clock/").toURI());
    }

    @Test
    public void test_openClockControlPanelClicked() throws Exception {
        underTest.openClockControlPanelClicked(IP_ADDRESS);
        verify(desktop).browse(new URL("http://" + IP_ADDRESS + ":8080/clock/manage.html").toURI());
    }

    @Test
    public void test_aboutClicked() throws Exception {
        underTest.aboutClicked();
        verify(desktop).browse(new URL(ControlsFramePresenter.ABOUT_URL).toURI());
    }

    @Test
    public void test_createIssueClicked() throws Exception {
        underTest.createIssueClicked();
        verify(desktop).browse(new URL(ControlsFramePresenter.ISSUES_URL).toURI());
    }
}
