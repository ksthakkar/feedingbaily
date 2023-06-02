package com.mygdx.game.helper;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {
    private TiledMap tileMap1;

    public TileMapHelper(){

    }

    public OrthogonalTiledMapRenderer setUpMap1(){
        tileMap1 = new TmxMapLoader().load("Maps/map1.tmx");
        return new OrthogonalTiledMapRenderer(tileMap1);
    }

}
