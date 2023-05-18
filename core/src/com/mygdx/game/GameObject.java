package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

public abstract class GameObject extends ApplicationAdapter {

    public abstract int getLayer();
    public abstract String getTag();

}
