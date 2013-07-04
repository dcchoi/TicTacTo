package com.example.somegame;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;

import android.os.Bundle;
import android.app.Activity;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.KeyEvent;
import android.view.Menu;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
 
import java.io.IOException;
import java.io.InputStream;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.entity.sprite.Sprite;
import java.util.Stack;
import org.andengine.input.touch.TouchEvent;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import org.andengine.engine.*;
import org.andengine.*;
public class MainActivity extends BaseGameActivity {

	private MenuScene menuScene;
	//private static int CAMERA_WIDTH = 300;
	//private static int CAMERA_HEIGHT = 300;


	//private ITextureRegion mBackgroundTextureRegion,  mO, mX,mMenuRegion1,mMenuRegion2;
	public static boolean flag = true;
	private Camera camera;
	private ResourcesManager resourcesManager;

	
	@Override
	public LimitedFPSEngine onCreateEngine(EngineOptions pEngineOptions) 
	{
	    return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		 camera = new Camera(0, 0,  800, 480);
		    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy( 800, 480), this.camera);
		    engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		    return engineOptions;
	}	
	/*
	@Override
	protected void onCreateResources() {
		try{
			  // 1 - Set up bitmap textures
		    ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/grid.png");
		        }
		    });
		    ITexture X = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/x.png");
		        }
		    });
		    ITexture O = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/o.png");
		        }
		    });
		   ITexture menu = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("popup.png");
		        }
		    });
		    
		    

		    // 2 - Load bitmap textures into VRAM
		    backgroundTexture.load();
		    X.load();
		    O.load();
		    // 3 - Set up texture regions
		    this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		    this.mO = TextureRegionFactory.extractFromTexture(O);
		    this.mX = TextureRegionFactory.extractFromTexture(X);

		}catch(Exception e){
			Debug.e(e);
		}
		
	}
	
	
	private MenuScene createMenu()
	{
	    menuScene = new MenuScene(camera);
	    
		//final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(0, mMenuRegion2, getVertexBufferObjectManager()), 2, 1);
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(1, mMenuRegion1, getVertexBufferObjectManager()), 2, 1);
	    
	  //  menuScene.addMenuItem(optionsMenuItem);
	    menuScene.addMenuItem(playMenuItem);
	    
	    menuScene.buildAnimations();
	    menuScene.setBackgroundEnabled(false);
	    
	 //   menuScene.setOnMenuItemClickListener(this);
	    return menuScene;
	}
	
	
	

	@Override
	protected Scene onCreateScene() {
		//  Create new scene
				final Scene scene = new Scene();
				Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
				scene.attachChild(backgroundSprite);
		// Create X and O Sprites		
				final Sprite[][] spO = new Sprite[3][3];
				final Sprite[][] spX = new Sprite[3][3];
				Rect[][] rect = new Rect[3][3];

				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						spO[i][j] = new Sprite(i*100,j*100, this.mO,  getVertexBufferObjectManager());
						spX[i][j] = new Sprite(i*100,j*100, this.mX,  getVertexBufferObjectManager());

					}
					}
				
		 //Create touchevents from rectangles
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						rect[i][j]= new Rect(i*100,j*100,(i*100)+100,(j*100)+100, this.getVertexBufferObjectManager(),i,j){
							boolean tag = true;
							@Override
							public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {


								switch (pSceneTouchEvent.getAction()) {
			                    case TouchEvent.ACTION_DOWN: {    
			                    	if(tag == true){
			                    		if(flag){
			                    		scene.attachChild(spO[rowID][colID]);
			                    		setMarker('O');
			                    		flag = false;
			                    		}else{
			                    		scene.attachChild(spX[rowID][colID]);
			                    		setMarker('X');
			                    		flag = true;
			                    		}
			                    		tag = false;
			                    	}
			                    	}
			                    }
								return true;
							}
	
					};
				}
				}
			//put rectangles into the scene, add color
				int k = 0;
				for(int i=2; i>=0; i--){
					for(int j=2; j>=0; j--){
						scene.attachChild(rect[i][j]);
						scene.registerTouchArea(rect[i][j]);
						rect[i][j].setColor(10*k,0,0);
						rect[i][j].setAlpha(200);
						
						k++;
					}
				}
				
				scene.setTouchAreaBindingOnActionDownEnabled(true);


		return scene;
	}
*/

	@Override
	public void onCreateResources(	
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		 ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		    resourcesManager = ResourcesManager.getInstance();
		    pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
	    SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
	    {
	            public void onTimePassed(final TimerHandler pTimerHandler) 
	            {
	            	mEngine.unregisterUpdateHandler(pTimerHandler);
	                SceneManager.getInstance().createMenuScene();
	            }
	    }));
	    pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}
	


@Override
public boolean onKeyDown(int keyCode, KeyEvent event) 
{  
    if (keyCode == KeyEvent.KEYCODE_BACK)
    {
        SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
    }
    return false; 
}

}
