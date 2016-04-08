package com.exsio.clock.ui.controls;

import com.exsio.clock.model.Time;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ControlsFormView extends JPanel {

    private final ControlsFormPresenter presenter;

    private JComboBox<Integer> minutes = new JComboBox<>();
    private JComboBox<Integer> seconds = new JComboBox<>();
    private JButton startStop = new JButton("Start");
    private JButton set = new JButton("Ustaw");
    private JButton reset = new JButton("Od nowa");
    private JLabel time = new JLabel(formatTimeLabel(new Time().toString(), new Time().toString()));

    ControlsFormView(ControlsFormPresenter presenter) {
        super(new BorderLayout());
        this.presenter = presenter;

        setupTime();
        setupMinutes();
        setupSeconds();
        setupStartStop();
        setupSet();
        setupReset();

        add(setupForm().getPanel());
    }

    void setTime(final String timeStr, final String boundaryStr, final Color color, boolean started) {
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                time.setText(formatTimeLabel(timeStr, boundaryStr));
                time.setForeground(color);
                return null;
            }
        }.execute();
        setStarted(started);
    }

    private String formatTimeLabel(String timeStr, String boundaryStr) {
        return String.format("%s / %s", timeStr, boundaryStr);
    }

    private DefaultFormBuilder setupForm() {
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
        builder.border(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
        if(time.getFont() != null) {
            time.setFont(new Font(time.getFont().getName(), Font.PLAIN, 30));
        }
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
                presenter.starStopClicked();
            }
        });
    }

    private void setupSet() {
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.setClicked((Integer) minutes.getSelectedItem(), (Integer) seconds.getSelectedItem());
            }
        });
    }


    private void setupReset() {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                presenter.resetClicked();
            }
        });
    }

    void setStarted(final boolean started) {
        new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                if (started) {
                    startStop.setText("Stop");
                } else {
                    startStop.setText("Start");
                }
                return null;
            }
        }.execute();

    }
}
