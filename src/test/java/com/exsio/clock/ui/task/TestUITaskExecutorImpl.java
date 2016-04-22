package com.exsio.clock.ui.task;

import com.exsio.clock.AbstractDisplayAwareTest;

public class TestUITaskExecutorImpl extends AbstractDisplayAwareTest implements UITaskExecutor {
    @Override
    public void execute(UITask task) {
        task.doInUI();
    }
}
