package com.tami.vmanager.view.swipemenu.listener;

import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.swipemenu.SwipeMenuLayout;

/**
 * Created by why on 2018/7/7.
 */
public abstract class SwipeRightSwitchListener implements SwipeSwitchListener {
    @Override
    public void beginMenuClosed(SwipeMenuLayout swipeMenuLayout) {
        Logger.d("left menu closed");
    }

    @Override
    public void beginMenuOpened(SwipeMenuLayout swipeMenuLayout) {
        Logger.d("left menu opened");
    }
}
