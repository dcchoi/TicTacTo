package com.example.somegame;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import org.andengine.engine.camera.Camera;
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
import org.andengine.entity.sprite.Sprite;
import java.util.Stack;
import org.andengine.input.touch.TouchEvent;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends SimpleBaseGameActivity {

	private static int CAMERA_WIDTH = 300;
	private static int CAMERA_HEIGHT = 300;
	private ITextureRegion mBackgroundTextureRegion,  mO, mX;
	public static boolean flag = true;
	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, 
		    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

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

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
				final Scene scene = new Scene();
				Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
				scene.attachChild(backgroundSprite);
				final Sprite[][] spO = new Sprite[3][3];

				final Sprite[][] spX = new Sprite[3][3];
				Rect[][] rect = new Rect[3][3];

				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						spO[i][j] = new Sprite(i*100,j*100, this.mO,  getVertexBufferObjectManager());
						spX[i][j] = new Sprite(i*100,j*100, this.mX,  getVertexBufferObjectManager());

					}
					}
				
				
				
				
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
			                    		flag = false;
			                    		}else{
			                    		scene.attachChild(spX[rowID][colID]);
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



}
