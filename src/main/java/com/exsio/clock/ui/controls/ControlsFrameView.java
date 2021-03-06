package com.exsio.clock.ui.controls;


import com.exsio.clock.ui.ScreenAwareFrame;
import com.exsio.clock.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.exsio.jin.annotation.TranslationPrefix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import static pl.exsio.jin.translationcontext.TranslationContext.t;

@TranslationPrefix("controls")
class ControlsFrameView extends ScreenAwareFrame {

    private final ControlsFramePresenter presenter;

    protected final JMenuBar menuBar = new JMenuBar();

    ControlsFrameView(ControlsFramePresenter presenter) {

        this.presenter = presenter;

        setVisible(false);
        setResizable(false);
        setIconImage(UI.getIcon());
    }

    public void init() {
        setTitle(t("title"));
        initMenu();
    }

    private void initMenu() {

        menuBar.add(getWWWMenu());
        menuBar.add(getAboutMenu());
        setJMenuBar(menuBar);
    }

    private JMenu getWWWMenu() {
        JMenu menu = new JMenu(t("menu.www"));
        Map<String, String> networkInterfacesMap = presenter.getNetworkInterfacesMap();
        if(networkInterfacesMap.size() > 1) {
            for (Map.Entry<String, String> networkInterfaceItem : networkInterfacesMap.entrySet()) {
                JMenu ifaceMenu = new JMenu(networkInterfaceItem.getKey());
                fillNetworkInterfacesMenu(ifaceMenu, networkInterfaceItem);
                menu.add(ifaceMenu);
            }
        } else {
            for (Map.Entry<String, String> networkInterfaceItem : networkInterfacesMap.entrySet()) {
                fillNetworkInterfacesMenu(menu, networkInterfaceItem);
            }
        }
        return menu;
    }

    private void fillNetworkInterfacesMenu(JMenu menu, Map.Entry<String, String> networkInterfaceItem) {
        menu.add(getOpenClockItem(networkInterfaceItem.getValue()));
        menu.add(getOpenClockControlPanelItem(networkInterfaceItem.getValue()));
    }

    private JMenu getAboutMenu() {
        JMenu menu = new JMenu(t("menu.help"));
        menu.add(getAboutItem());
        menu.add(getCreateIssueItem());
        return menu;
    }

    private JMenuItem getAboutItem() {
        JMenuItem aboutItem = new JMenuItem(t("menu.about"));
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.aboutClicked();
            }
        });
        return aboutItem;
    }

    private JMenuItem getCreateIssueItem() {
        JMenuItem aboutItem = new JMenuItem(t("menu.issue"));
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.createIssueClicked();
            }
        });
        return aboutItem;
    }

    private JMenuItem getOpenClockControlPanelItem(final String ipAddress) {
        JMenuItem openClockControlPanelItem = new JMenuItem(t("menu.panel"));
        openClockControlPanelItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.openClockControlPanelClicked(ipAddress);
            }
        });
        return openClockControlPanelItem;
    }

    private JMenuItem getOpenClockItem(final String ipAddress) {
        JMenuItem openClockItem = new JMenuItem(t("menu.clock"));
        openClockItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.openClockClicked(ipAddress);
            }
        });
        return openClockItem;
    }
}
