package com.exsio.clock.ui.controls;

import com.exsio.clock.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ControlsFramePresenterITest extends AbstractIntegrationTest {

    @Autowired
    ControlsFramePresenter presenter;

    ControlsFrameView view;

    @BeforeClass
    public void init() {
        super.init();
        view = presenter.getView();
    }

    @Test
    public void test_menu() throws Exception {
        ControlsFramePresenter mockPresenter = mock(ControlsFramePresenter.class);
        setFinalStatic(ControlsFrameView.class.getDeclaredField("presenter"), view, mockPresenter);
        Thread.sleep(1000);
        for (Component menu : view.menuBar.getComponents()) {
            if (menu instanceof JMenu) {
                walkAndClick((JMenu) menu);
            }
        }

        verify(mockPresenter, atLeast(1)).openClockControlPanelClicked(anyString());
        verify(mockPresenter, atLeast(1)).openClockClicked(anyString());
        verify(mockPresenter, atLeast(1)).createIssueClicked();
        verify(mockPresenter, atLeast(1)).aboutClicked();

        setFinalStatic(ControlsFrameView.class.getDeclaredField("presenter"), view, presenter);
    }

    private void walkAndClick(JMenu menu) {
        for (Component item : menu.getMenuComponents()) {
            if (item instanceof JMenu) {
                walkAndClick((JMenu) item);
            } else if (item instanceof JMenuItem) {
                ((JMenuItem) item).doClick();
            }
        }
    }

    private void setFinalStatic(Field field, Object target, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(target, newValue);
    }
}
