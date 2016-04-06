package com.exsio.clock.ui;

import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.ClockInfoModel;
import com.exsio.clock.service.ClockService;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@org.springframework.stereotype.Component
public class UIForm extends JPanel {

    private final ClockService clockService;

    private JComboBox<Integer> minutes = new JComboBox<>();
    private JComboBox<Integer> seconds = new JComboBox<>();
    private JButton startStop = new JButton("Start");
    private JButton set = new JButton("Ustaw");
    private JButton reset = new JButton("Od nowa");
    private JLabel time = new JLabel(" 00:00");

    private boolean started = false;

    @Autowired
    public UIForm(ClockService clockService) {
        super(new BorderLayout());
        this.clockService = clockService;

        setupTime();
        setupMinutes();
        setupSeconds();
        setupStartStop();
        setupSet();
        setupReset();

        add(setupForm().getPanel());
    }

    @EventListener(TimeChangedEvent.class)
    public void onTimeChanged(TimeChangedEvent event) {
        final ClockInfoModel clockInfo = event.getObject();
        SwingWorker worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                time.setText(clockInfo.getClock());
                if (clockInfo.isAlert()) {
                    time.setForeground(Color.RED);
                } else {
                    time.setForeground(Color.BLACK);
                }
                return null;
            }
        };
        worker.execute();
        setStarted(clockInfo.isStarted());
    }

    private DefaultFormBuilder setupForm() {
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
        builder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        builder.appendColumn("right:pref");
        builder.appendColumn("3dlu");
        builder.appendColumn("fill:max(pref; 100px)");
        builder.appendColumn("5dlu");
        builder.appendColumn("right:pref");
        builder.appendColumn("3dlu");
        builder.appendColumn("fill:max(pref; 100px)");
        builder.appendColumn("5dlu");
        builder.appendColumn("right:pref");
        builder.appendColumn("3dlu");
        builder.appendColumn("fill:max(pref; 100px)");

        builder.append("Ustaw czas:", minutes);
        builder.append(":", seconds);
        builder.append("", time);
        builder.nextLine();
        builder.append("", set);
        builder.append("", startStop);
        builder.append("", reset);
        return builder;
    }

    private void setupTime() {
        time.setFont(new Font(time.getFont().getName(), Font.PLAIN, 30));
    }

    private void setupMinutes() {
        for (int i = 0; i < 100; i++) {
            minutes.addItem(i);
        }
    }

    private void setupSeconds() {
        for (int i = 0; i < 60; i++) {
            seconds.addItem(i);
        }
    }

    private void setupStartStop() {
        startStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (started) {
                    clockService.stop();
                    setStarted(false);
                } else {
                    clockService.start();
                    setStarted(true);
                }
            }
        });
    }

    private void setupSet() {
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockService.set((Integer) minutes.getSelectedItem(), (Integer) seconds.getSelectedItem());
            }
        });
    }


    private void setupReset() {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clockService.reset();
            }
        });
    }

    private void setStarted(boolean started) {
        this.started = started;
        if (started) {
            startStop.setText("Stop");
        } else {
            startStop.setText("Start");
        }
    }
}
