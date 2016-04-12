package com.exsio.clock.ui.controls;

import com.beust.jcommander.internal.Lists;
import com.exsio.clock.ui.UI;
import com.google.common.base.Optional;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@PrepareForTest({NetworkInterface.class, Inet4Address.class, Desktop.class})
@PowerMockIgnore("javax.swing.*")
public class ControlsFramePresenterTest extends PowerMockTestCase {

    private final static String IP_ADDRESS = "127.0.0.1";

    ControlsFramePresenter underTest;

    ControlsFrameView view;

    @Mock
    ControlsFormPresenter formPresenter;

    @Mock
    ControlsFormView formView;

    NetworkInterface nic;

    Inet4Address inetAddress;

    @Mock
    Desktop desktop;

    @Mock
    ApplicationReadyEvent applicationReadyEvent;

    @BeforeMethod
    public void init() throws IOException {
        MockitoAnnotations.initMocks(this);
        when(formPresenter.getView()).thenReturn(formView);
        view = spy(new ControlsFrameView(mock(ControlsFramePresenter.class)));
        doNothing().when(view).add(Mockito.<Component>any(), anyObject());
        doNothing().when(view).setVisible(anyBoolean());
        mockNetworkInterface();

        Mockito.reset(desktop);
        when(desktop.isSupported(Desktop.Action.BROWSE)).thenReturn(true);

        UI.setDesktop(Optional.of(desktop));

        underTest = new ControlsFramePresenter(view, formPresenter) {

            @Override
            Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
                return Lists.newArrayList(nic);
            }
        };
    }

    private void mockNetworkInterface() {
        nic = PowerMockito.mock(NetworkInterface.class);
        inetAddress = PowerMockito.mock(Inet4Address.class);
        when(nic.getInetAddresses()).thenReturn(Collections.enumeration(Lists.<InetAddress>newArrayList(inetAddress)));
        when(nic.getDisplayName()).thenReturn("NIC1");
        when(inetAddress.getHostAddress()).thenReturn(IP_ADDRESS);
    }

    @Test
    public void test_onApplicationStart() throws InterruptedException {
        underTest.onApplicationStart(applicationReadyEvent);
        Thread.sleep(1000);
        verify(view).setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        verify(view).add(formView, BorderLayout.CENTER);
        verify(view).pack();
        verify(view).showOnScreen(0);
        verify(view).setVisible(true);
        verify(formPresenter).getView();

    }

    @Test
    public void test_openClockClicked() throws Exception {
        underTest.openClockClicked(IP_ADDRESS);
        verify(desktop).browse(new URL("http://127.0.0.1:8080/clock/").toURI());
    }

    @Test
    public void test_openClockControlPanelClicked() throws Exception {
        underTest.openClockControlPanelClicked(IP_ADDRESS);
        verify(desktop).browse(new URL("http://127.0.0.1:8080/clock/manage.html").toURI());
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
