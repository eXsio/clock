package com.exsio.clock.ui.controls;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.ui.UI;
import com.exsio.clock.ui.loading.Loading;
import com.exsio.clock.ui.task.UITask;
import com.exsio.clock.ui.task.UITaskExecutor;
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
import pl.exsio.jin.annotation.TranslationPrefix;

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

import static pl.exsio.jin.translationcontext.TranslationContext.t;

@Service
@Profile(SpringProfile.UI)
@TranslationPrefix("controls")
class ControlsFramePresenter {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControlsFramePresenter.class);
    static final String ISSUES_URL = "https://github.com/eXsio/clock/issues";
    static final String ABOUT_URL = "https://github.com/eXsio/clock";
    static final String LOOPBACK_NAME = "loopback";
    static final String LOOPBACK_IP = "127.0.0.1";
    static final Map<String, String> LOOPBACK = Collections.singletonMap(LOOPBACK_NAME, LOOPBACK_IP);

    private final ControlsFrameView view;
    private final ControlsFormPresenter formPresenter;
    private final UITaskExecutor uiTaskExecutor;

    @Autowired
    public ControlsFramePresenter(ControlsFormPresenter formPresenter, UITaskExecutor uiTaskExecutor) {
        this.uiTaskExecutor = uiTaskExecutor;
        this.view = new ControlsFrameView(this);
        this.formPresenter = formPresenter;
    }

    ControlsFramePresenter(ControlsFrameView view, ControlsFormPresenter formPresenter, UITaskExecutor uiTaskExecutor) {
        this.view = view;
        this.formPresenter = formPresenter;
        this.uiTaskExecutor = uiTaskExecutor;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationStart(ApplicationReadyEvent event) {

        uiTaskExecutor.execute(new UITask() {
            @Override
            public void doInUI() {
                view.init();
                view.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                view.addWindowListener(getWindowClosingListener());
                view.add(formPresenter.getView(), BorderLayout.CENTER);
                formPresenter.init();
                view.pack();

                Loading.dispose();

                view.showOnScreen(0);
                view.setVisible(true);
                LOGGER.info("Application UI started successfully");
            }
        });
    }

    @EventListener(TimeChangedEvent.class)
    public void onTimeChanged(TimeChangedEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.pack();
            }
        });
    }

    private WindowAdapter getWindowClosingListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (formPresenter.isClockStarted()) {
                    JOptionPane.showMessageDialog(view, t("message.cant_close_while_running"), t("message.attention"), JOptionPane.ERROR_MESSAGE);
                } else {

                    String ObjButtons[] = {t("common.positive"), t("common.negative")};
                    int PromptResult = JOptionPane.showOptionDialog(view, t("message.closing_confirmation"), t("message.attention"), JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
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
        if (map.isEmpty()) {
            map.putAll(LOOPBACK);
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

    public ControlsFrameView getView() {
        return view;
    }
}
