package com.example.somegame;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.example.somegame.SceneManager.SceneType;

public class GameScene extends BaseScene{

	@Override
	public void createScene() {
	    createBackground();		
	}

	@Override
	public void onBackKeyPressed() {
	    SceneManager.getInstance().loadMenuScene(engine);
		
	}

	@Override
	public SceneType getSceneType() {
        
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		camera.setHUD(null);
	    camera.setCenter(400, 240);

	    // TODO code responsible for disposing scene
	    // removing all game scene objects.
	}

private void createBackground()
{
	Sprite bg = new Sprite(0, 0, ResourcesManager.mBackgroundTextureRegion, vbom);

    setBackground(new SpriteBackground(bg));
}

}
