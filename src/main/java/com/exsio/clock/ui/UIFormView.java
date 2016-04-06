package com.exsio.clock.ui;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIFormView extends JPanel {

    private final UIFormPresenter presenter;

    private JComboBox<Integer> minutes = new JComboBox<>();
    private JComboBox<Integer> seconds = new JComboBox<>();
    private JButton startStop = new JButton("Start");
    private JButton set = new JButton("Ustaw");
    private JButton reset = new JButton("Od nowa");
    private JLabel time = new JLabel(" 00:00");

    public UIFormView(UIFormPresenter presenter) {
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

    void setTime(final String clock, final Color color, boolean started) {
        SwingWorker worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                time.setText(clock);
                time.setForeground(color);
                return null;
            }
        };
        worker.execute();
        setStarted(started);
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

    void setStarted(boolean started) {
        if (started) {
            startStop.setText("Stop");
        } else {
            startStop.setText("Start");
        }
    }
}
