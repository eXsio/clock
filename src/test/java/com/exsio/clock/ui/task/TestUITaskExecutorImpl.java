package com.exsio.clock.ui.task;

public class TestUITaskExecutorImpl implements UITaskExecutor {
    @Override
    public void execute(UITask task) {
        task.doInUI();
    }
}
