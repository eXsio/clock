package com.exsio.clock.ui.controls;

import com.exsio.clock.model.Time;
import com.exsio.clock.ui.task.UITask;
import com.exsio.clock.ui.task.UITaskExecutor;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;
import pl.exsio.jin.annotation.TranslationPrefix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pl.exsio.jin.translationcontext.TranslationContext.t;

@TranslationPrefix("controls.form")
class ControlsFormView extends JPanel {

    private final ControlsFormPresenter presenter;
    private final UITaskExecutor uiTaskExecutor;

    protected JComboBox<Integer> minutes = new JComboBox<>();
    protected JComboBox<Integer> seconds = new JComboBox<>();
    protected JButton startStop;
    protected JButton set;
    protected JButton reset;
    protected JLabel time = new JLabel(formatTimeLabel(new Time().toString(), new Time().toString()));

    ControlsFormView(ControlsFormPresenter presenter, UITaskExecutor uiTaskExecutor) {
        super(new BorderLayout());
        this.presenter = presenter;
        this.uiTaskExecutor = uiTaskExecutor;
    }

    void init() {

        startStop = new JButton(t("start"));
        set = new JButton(t("set"));
        reset = new JButton(t("reset"));

        setupTime();
        setupMinutes();
        setupSeconds();
        setupStartStop();
        setupSet();
        setupReset();
        add(setupForm().getPanel());
    }

    void setTime(final String timeStr, final String boundaryStr, final Color color, boolean started) {

        uiTaskExecutor.execute(new UITask() {
            @Override
            public void doInUI() {
                time.setText(formatTimeLabel(timeStr, boundaryStr));
                time.setForeground(color);
            }
        });
        setStarted(started);
    }

    private String formatTimeLabel(String timeStr, String boundaryStr) {
        return String.format("%s / %s", timeStr, boundaryStr);
    }

    private DefaultFormBuilder setupForm() {
        DefaultFormBuilder builder = new DefaultFormBuilder(new FormLayout(""));
        builder.border(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        builder.append(t("set_time"), minutes);
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
            time.setForeground(ControlsFormPresenter.NORMAL_COLOR);
        }
    }

    private void setupMinutes() {
        for (int i = 0; i < Time.LIMIT; i++) {
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

        uiTaskExecutor.execute(new UITask() {
            @Override
            public void doInUI() {
                if (started) {
                    startStop.setText(t("stop"));
                } else {
                    startStop.setText(t("start"));
                }
            }
        });
    }
}
