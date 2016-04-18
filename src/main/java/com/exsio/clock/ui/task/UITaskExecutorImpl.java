package com.exsio.clock.ui.task;

import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class UITaskExecutorImpl implements UITaskExecutor {

    @Override
    public void execute(final UITask task) {

        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                task.doInUI();
                return null;
            }
        }.execute();
    }
}
