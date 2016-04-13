package com.exsio.clock.ui.controls;

import com.exsio.clock.ui.UI;
import com.exsio.clock.ui.loading.Loading;
import com.exsio.clock.util.SpringProfile;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Service
@Profile(SpringProfile.UI)
class ControlsFramePresenter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControlsFramePresenter.class);
    static final String ISSUES_URL = "https://github.com/eXsio/clock/issues";
    static final String ABOUT_URL = "https://github.com/eXsio/clock";

    private final ControlsFrameView view;

    private final ControlsFormPresenter formPresenter;

    @Autowired
    public ControlsFramePresenter(ControlsFormPresenter formPresenter) {
        this.view = new ControlsFrameView(this);
        this.formPresenter = formPresenter;
    }

    ControlsFramePresenter(ControlsFrameView view, ControlsFormPresenter formPresenter) {
        this.view = view;
        this.formPresenter = formPresenter;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart(ApplicationReadyEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                view.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                view.addWindowListener(getWindowClosingListener());
                view.add(formPresenter.getView(), BorderLayout.CENTER);
                view.pack();

                Loading.dispose();

                view.showOnScreen(0);
                view.setVisible(true);
                LOGGER.info("Application UI started successfully");
            }
        });
    }

    private WindowAdapter getWindowClosingListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formPresenter.isClockStarted()) {
                    JOptionPane.showMessageDialog(view, "Nie możesz zakończyć programu, kiedy działa zegar!", "Uwaga!", JOptionPane.WARNING_MESSAGE);
                } else {
                    String ObjButtons[] = {"Tak", "Nie"};
                    int PromptResult = JOptionPane.showOptionDialog(view, "Jesteś pewien, że chcesz zakończyć program?", "Uwaga!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);
                    if (PromptResult == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }
        };
    }

    Map<String, String> getNetworkInterfacesMap() {
        Map<String, String> map = Maps.newHashMap();
        try {
            for (NetworkInterface iface : getNetworkInterfaces()) {
                for (InetAddress address : Collections.list(iface.getInetAddresses())) {
                    if (Inet4Address.class == address.getClass()) {
                        map.put(iface.getDisplayName(), address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("error while trying to get network interfaces details: {}", e.getMessage(), e);
        }
        return map;
    }

    Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
        Collection<NetworkInterface> result = Lists.newArrayList();
        for (NetworkInterface iface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
            if (!iface.isLoopback() && iface.isUp()) {
                result.add(iface);
            }
        }
        return result;
    }

    void openClockClicked(String ipAddress) {
        try {
            UI.openWebpage(getUri(ipAddress, "/clock/"));
        } catch (Exception e) {
            LOGGER.error("error while trying to open web page: {}", e.getMessage(), e);
        }
    }

    void openClockControlPanelClicked(String ipAddress) {
        try {
            UI.openWebpage(getUri(ipAddress, "/clock/manage.html"));
        } catch (Exception e) {
            LOGGER.error("error while trying to open web page: {}", e.getMessage(), e);
        }
    }

    private URI getUri(String ipAddress, String contextPath) throws Exception {
        return new URL("http://" + ipAddress + ":8080" + contextPath).toURI();
    }

    void aboutClicked() {
        try {
            UI.openWebpage(new URL(ABOUT_URL).toURI());
        } catch (Exception e) {
            LOGGER.error("error while trying to open web page: {}", e.getMessage(), e);
        }
    }

    void createIssueClicked() {
        try {
            UI.openWebpage(new URL(ISSUES_URL).toURI());
        } catch (Exception e) {
            LOGGER.error("error while trying to open web page: {}", e.getMessage(), e);
        }
    }
}
