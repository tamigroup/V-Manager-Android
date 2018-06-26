package com.tami.vmanager.manager;

import com.tami.vmanager.entity.LoginResponse;

/**
 * Created by why on 2018/6/25.
 */

public class GlobaVariable {

    private GlobaVariable() {
    }

    public static class GlobaVariableFactory {
        private static final GlobaVariable INSTANCE = new GlobaVariable();
    }

    public static GlobaVariable getInstance() {
        return GlobaVariableFactory.INSTANCE;
    }

    public LoginResponse.Item item;
}
