package com.twoshellko.linesweeper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.BaseParticleEmitter;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.StrokeFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;



import com.batch.android.Batch;
import com.batch.android.BatchUnlockListener;
import com.batch.android.Config;
import com.batch.android.Offer;

import com.google.android.gms.games.Games;
import com.hintdesk.core.util.StringUtil;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;
/*import com.inmobi.commons.InMobi;
import com.inmobi.commons.InMobi.LOG_LEVEL;
import com.inmobi.monetization.IMErrorCode;
import com.inmobi.monetization.IMInterstitial;
import com.inmobi.monetization.IMInterstitialListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubInterstitial.InterstitialAdListener;*/


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Game extends GBaseGameActivity implements IOnSceneTouchListener, InterstitialAdListener, BatchUnlockListener{
	protected Scene scene;
	protected Scene mGameScene;
	protected Scene mPauseScene;
	protected Scene mGameOverScene;
	protected Scene mInterfaceScene;
	protected Scene mTutorialScene;
	protected Scene mBestScoreScene;
	protected Scene mCountdownScene;
	protected Camera mCamera;
	private static int CAMERA_WIDTH = 1080;
	private static int CAMERA_HEIGHT = 1920;
	private Boolean immersive = true;
	private Boolean sound = true;
	private Boolean music = true;
	private Boolean destroy = false;
	private Boolean zoomEffect = false;
	private Boolean bgSpin = false;
	private Boolean showAd = true;
	private Boolean startGame = false;
	private Boolean twitterLogin = false;
	private int twitterLocation = 1;
	private Boolean showTutorial = true;
	private Boolean showTutorialFrenzy = true;
	private int twitterScore = 0;

	//TEXTURE ATLASES
	private BuildableBitmapTextureAtlas myGameAtlas;
	private BuildableBitmapTextureAtlas myInterfaceAtlas;
	private BitmapTextureAtlas myStretchInterfaceAtlas;

	//LINE SPECIFIC STUFF GOES HERE
	private BuildableBitmapTextureAtlas myLineAtlas;
	//private BitmapTextureAtlas myLineStretchHAtlas;
	//private BitmapTextureAtlas myLineStretchVAtlas;

	private ArrayList<ITextureRegion> lineTopArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineBottomArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineLeftArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineRightArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineStretchHorizontalArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineStretchVerticalArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineTopArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineBottomArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineLeftArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineRightArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineHorizontalSArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> specialLineVerticalSArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private ITextureRegion special_bg_t;
	private ITextureRegion special_bg_b;
	private ITextureRegion special_bg_l;
	private ITextureRegion special_bg_r;
	private ITextureRegion special_bg_hs;
	private ITextureRegion special_bg_vs;
	private ITextureRegion special_bg_h;
	private ITextureRegion special_bg_v;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mParticleTextureRegion;

	private ITextureRegion mLifeHudWick;
	private ITextureRegion mLifeHudBg;
	private ITextureRegion mLifeHudBomb;
	private ITextureRegion mLifeHudCircle;
	private ITextureRegion myButtonRegularRegion;
	private ITextureRegion myButtonRegularTopRegion;
	private ITextureRegion myNewGameIcon;
	private ITextureRegion myRateIcon;
	private ITextureRegion myShareIcon;
	private ITextureRegion myLeaderboardIcon;
	private ITextureRegion myReturnMenuIcon;
	private ITextureRegion myBonusButtonBg;
	private ITextureRegion myBonusButtonColor;
	private ITextureRegion logo_bg;
	private ITextureRegion logo_top;
	private ITextureRegion logo_frenzy_bg;
	private ITextureRegion logo_frenzy_top;
	private ITextureRegion logo_restart_icon;
	private ITextureRegion mySettingsIcon;
	private ITextureRegion myPlayIcon;
	private ITextureRegion myLinesweeperIcon;
	private ITextureRegion myFrenzyIcon;
	private ITextureRegion myTouchIconRegion;
	private ITextureRegion myHomeIcon;
	private ITextureRegion myFacebookIcon;
	private ITextureRegion myTwitterIcon;
	private ITextureRegion myToggleTop;
	private ITextureRegion myToggleColor;
	private ITextureRegion myPauseIcon;
	private ITextureRegion myStarsRightRegion;
	private ITextureRegion myStarsLeftRegion;
	private ITextureRegion myTrophyRegion;
	private ITextureRegion myTutorialRegion;
	private ITextureRegion myYesIcon;
	private ITextureRegion myNoIcon;
	private ITextureRegion myPlayButtonRegion;
	private ITextureRegion mySoundIcon;
	private ITextureRegion myMusicIcon;
	private ITextureRegion mySmallLogo;





	//FONTS
	private Font scoreSmallFont;
	private Font scoreLargeFont;
	private Font scoreXLargeFont;
	private Font logoLargeFont;
	private StrokeFont bonusStrokeFont;
	//private Font ggVLargeFont;

	private int[] CAMERA_BOUNDS = {5, CAMERA_WIDTH-5, 340, CAMERA_HEIGHT-160};
	private int NUMBER_OF_TEXTURES = 14;
	private int NUMBER_OF_SPECIAL_BG = 1;
	private int NUMBER_OF_SPECIALS = 1;
	private int NUMBER_OF_LINES_TO_SPAWN = 50;
	private int GAMETYPE = 1;

	private V3GameGenerator lineGenerator = new V3GameGenerator();
	private V3ColorManager colorManager = new V3ColorManager();
	private GameManager gameManager = new GameManager();
	private BackgroundManager gameBackgrounds = new BackgroundManager();

	private void loadSharedPreferences(){
		final SharedPreferences settings = getSharedPreferences("Linesweeper",MODE_PRIVATE);
		gameManager.setSharedPreferences(settings);
		final SharedPreferences settings2 =  getSharedPreferences(getHighScroesSharedPrefsString(GAMETYPE), MODE_PRIVATE);
		showAd = settings.getBoolean("showAd", true);
		music = settings.getBoolean("music", true);
		sound = settings.getBoolean("sound", true);
		destroy = settings2.getBoolean("destroy", false);
		zoomEffect = settings.getBoolean("zoomEffect", true);
		bgSpin = settings.getBoolean("bgSpin", true);
		immersive = settings.getBoolean("immersive", true);
		twitterLogin = settings.getBoolean("twitterLogin", false);
		twitterScore = settings.getInt("twitterScore", 0);
		showTutorial = settings.getBoolean("showTutorial", true);
		showTutorialFrenzy = settings.getBoolean("showTutorialFrenzy", true);
		twitterLocation = settings.getInt("twitterLocation", 1);

	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub

	}

	protected int getLayoutID() {
		return R.layout.game;
	}
	@Override
	protected int getRenderSurfaceViewID() {
		return R.id.gameview;
	}
	@Override
	public EngineOptions onCreateEngineOptions() {
		DisplayMetrics dm = new DisplayMetrics();

		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int viewWidth = dm.widthPixels;
		int viewHeight = dm.heightPixels;
		loadSharedPreferences();
		if (Build.VERSION.SDK_INT >= 19 && immersive) {
			WindowManager w = this.getWindowManager();
			Display d = w.getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			d.getMetrics(metrics);
			try {
				Point realSize = new Point();
				Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
				viewWidth = realSize.x;
				viewHeight = realSize.y;
			} catch (Exception ignored) {
			}
		}
		if(viewWidth > viewHeight){
			int width1 = viewWidth;
			int height1 = viewHeight;
			viewWidth = height1;
			viewHeight = width1;
		}
		float ratio = (float) viewHeight/viewWidth;
		float screenHeight = CAMERA_WIDTH*ratio;
		CAMERA_HEIGHT = (int) screenHeight;
		//Log.d("CAMERA_WIDTH", String.valueOf(CAMERA_WIDTH));
		//Log.d("CAMERA_HEIGHT", String.valueOf(CAMERA_HEIGHT));


		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		CAMERA_BOUNDS[3] = CAMERA_HEIGHT-60;
		//CAMERA_BOUNDS[3] = CAMERA_HEIGHT-135;
		// Attach the HUD to t


		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engineOptions.getRenderOptions().setMultiSampling(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(false);
		//musicBoolean = true;

		//FACEBOOK
		startFaceBook();
		// this part is optional
		//shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() { ... });
		//BatchCreate();

		Game.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Chartboost.startWithAppId(Game.this, "5548fb2743150f23a675f2b5", "92663f1da7383f0d54583557d174946b093dc44f");
		        /* Optional: If you want to program responses to Chartboost events, supply a delegate object here and see step (10) for more information */
				//Chartboost.setDelegate(delegate);
				//Chartboost.onCreate(Game.this);
				//InMobi.initialize(Game.this, "5c36aeccbb644a6da5fc35b35313e2e3");
				//InMobi.setLogLevel(LOG_LEVEL.DEBUG);
			}
		});
		//createMoPub();

		return engineOptions;
	}
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//this.myLoadingAtlas = new BitmapTextureAtlas(this.getTextureManager(), 480, 960, TextureOptions.BILINEAR);
		FontFactory.setAssetBasePath("font/");
		final ITexture loadingFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)

			throws Exception {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		GAMETYPE= getIntent().getIntExtra("GAME_TYPE", 1);
		scene = new Scene();

		scene.setBackground(new Background(1,1,1));
		pOnCreateSceneCallback.onCreateSceneFinished(this.scene);
	}
	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(0.01f, new ITimerCallback() {
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				loadResources();
				loadScenes();

				try
				{
					Thread.sleep(50);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				mEngine.setScene(mGameScene);
			}
		}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void loadResources(){
		mBitmapTextureAtlas =  new BitmapTextureAtlas(this.getTextureManager(), 32, 32, TextureOptions.BILINEAR);
		mParticleTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "particle_star.png", 0, 0);
		mBitmapTextureAtlas.load();
		myLineAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
		myLineAtlas.clearTextureAtlasSources();
		for(int i=0;i<NUMBER_OF_TEXTURES;i++){
			ITextureRegion regionTop = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "line_"+String.valueOf(i+1)+"_t.png");
			ITextureRegion regionBottom = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "line_"+String.valueOf(i+1)+"_b.png");
			ITextureRegion regionLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "line_"+String.valueOf(i+1)+"_l.png");
			ITextureRegion regionRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "line_"+String.valueOf(i+1)+"_r.png");
			BitmapTextureAtlas myLineStretchHAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1, 106, TextureOptions.BILINEAR);
			ITextureRegion stretchH = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineStretchHAtlas, this, "line_"+String.valueOf(i+1)+"_h.png", 0 , 0);
			myLineStretchHAtlas.load();
			BitmapTextureAtlas myLineStretchVAtlas = new BitmapTextureAtlas(this.getTextureManager(), 106, 1, TextureOptions.BILINEAR);
			ITextureRegion stretchV = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineStretchVAtlas, this, "line_"+String.valueOf(i+1)+"_v.png", 0 , 0);
			myLineStretchVAtlas.load();
			lineTopArray.add(regionTop);
			lineBottomArray.add(regionBottom);
			lineLeftArray.add(regionLeft);
			lineRightArray.add(regionRight);
			lineStretchHorizontalArray.add(stretchH);
			lineStretchVerticalArray.add(stretchV);
		}
		for(int i=0;i<NUMBER_OF_SPECIALS;i++){
			ITextureRegion regionTop = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_t.png");
			ITextureRegion regionBottom = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_b.png");
			ITextureRegion regionLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_l.png");
			ITextureRegion regionRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_r.png");
			ITextureRegion regionHS = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_hs.png");
			ITextureRegion regionVS = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, String.valueOf(i+1)+"_vs.png");
			specialLineTopArray.add(regionTop);
			specialLineBottomArray.add(regionBottom);
			specialLineLeftArray.add(regionLeft);
			specialLineRightArray.add(regionRight);
			specialLineHorizontalSArray.add(regionHS);
			specialLineVerticalSArray.add(regionVS);
		}
		special_bg_t = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_t.png");
		special_bg_b = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_b.png");
		special_bg_l = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_l.png");
		special_bg_r = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_r.png");
		special_bg_hs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_hs.png");
		special_bg_vs = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineAtlas, this, "special_bg_vs.png");
		BitmapTextureAtlas myLineStretchHAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1, 106, TextureOptions.BILINEAR);
		special_bg_h = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineStretchHAtlas, this, "special_bg_h.png", 0 , 0);
		myLineStretchHAtlas.load();
		BitmapTextureAtlas myLineStretchVAtlas = new BitmapTextureAtlas(this.getTextureManager(), 106, 1, TextureOptions.BILINEAR);
		special_bg_v = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myLineStretchVAtlas, this, "special_bg_v.png", 0 , 0);
		myLineStretchVAtlas.load();
		try
		{
			this.myLineAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			this.myLineAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}
		myInterfaceAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR);
		myInterfaceAtlas.clearTextureAtlasSources();

		mLifeHudWick = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "life_hud_wick.png");
		mLifeHudBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "life_hud_bg.png");
		mLifeHudBomb = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "life_hud_bomb.png");
		mLifeHudCircle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "life_hud_circle.png");
		myButtonRegularRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "button_regular.png");
		myButtonRegularTopRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "button_regular_top.png");
		myBonusButtonBg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "bonus_bg.png");
		myBonusButtonColor = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "bonus_color.png");
		myReturnMenuIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "return_menu.png");
		myNewGameIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "new_game_icon.png");
		myRateIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "rate_icon.png");
		myShareIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "share_icon.png");
		myLeaderboardIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "leaderboard_icon.png");
		logo_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "logo_bg.png");
		logo_top = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "logo_top.png");
		logo_frenzy_bg = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "frenzy_bg.png");
		logo_frenzy_top = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "frenzy_top.png");
		logo_restart_icon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "restart_icon.png");
		mySettingsIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "settings_icon.png");
		myPlayIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "resume_icon.png");
		mySoundIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "sound_icon.png");
		myMusicIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "music_icon.png");
		//myLinesweeperIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "linesweeper_icon.png");
		//myFrenzyIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "frenzy_icon.png");
		myTouchIconRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "touch_icon.png");
		myHomeIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "home.png");
		myTwitterIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "twitter.png");
		myFacebookIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "facebook.png");
		myToggleTop = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "toggle_top.png");
		myToggleColor = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "toggle_color.png");
		myPauseIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "pause.png");
		myStarsRightRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "stars.png");
		myStarsLeftRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "stars_l.png");
		myTrophyRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "trophy.png");
		myYesIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "yes_icon.png");
		myNoIcon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "no_icon.png");
		myTutorialRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "tutorial_icon.png");
		myPlayButtonRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "play_button.png");
		mySmallLogo= BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "small_logo.png");

		BitmapTextureAtlas myBoxStretchTAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1, 24, TextureOptions.BILINEAR);
		BitmapTextureAtlas myBoxStretchBAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1, 24, TextureOptions.BILINEAR);
		BitmapTextureAtlas myBoxStretchLAtlas = new BitmapTextureAtlas(this.getTextureManager(), 24, 1, TextureOptions.BILINEAR);
		BitmapTextureAtlas myBoxStretchRAtlas = new BitmapTextureAtlas(this.getTextureManager(), 24, 1, TextureOptions.BILINEAR);interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "box_shadow_tl.png"));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBoxStretchTAtlas, this, "box_shadow_tc.png",0,0));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "box_shadow_tr.png"));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBoxStretchLAtlas, this, "box_shadow_lc.png",0,0));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBoxStretchRAtlas, this, "box_shadow_rc.png",0,0));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "box_shadow_bl.png"));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myBoxStretchBAtlas, this, "box_shadow_bc.png",0,0));
		interfaceBoxShadow.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(myInterfaceAtlas, this, "box_shadow_br.png"));

		myBoxStretchTAtlas.load();
		myBoxStretchBAtlas.load();
		myBoxStretchLAtlas.load();
		myBoxStretchRAtlas.load();
		try
		{
			this.myInterfaceAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			this.myInterfaceAtlas.load();
		}
		catch (final TextureAtlasBuilderException e)
		{
			Debug.e(e);
		}


		final ITexture droidFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		final ITexture droidFontTexture2 = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		final ITexture droidFontTexture3 = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		final ITexture droidFontTexture4 = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);
		final ITexture strokeFontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		//final ITexture droidFontTexture5 = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.BILINEAR);*/
		this.scoreSmallFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture, this.getAssets(), "RobotoBoldC.ttf", 38, true, Color.argb(255, 255, 255, 255));
		this.scoreSmallFont.load();
		this.scoreLargeFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture2, this.getAssets(), "RobotoBoldC.ttf", 50, true, Color.argb(255, 255, 255, 255));
		this.scoreLargeFont.load();
		this.scoreXLargeFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture3, this.getAssets(), "RobotoBold.ttf", 155, true, Color.argb(255, 255, 255, 255));
		this.scoreXLargeFont.load();
		this.logoLargeFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture4, this.getAssets(), "RobotoThin.ttf", 110, true, Color.argb(255, 255, 255, 255));
		this.logoLargeFont.load();
		this.bonusStrokeFont = new StrokeFont(this.getFontManager(), strokeFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 80, true, Color.argb(255, 255, 138, 101), 2, Color.BLACK);
		this.bonusStrokeFont.load();
		/*this.ggVLargeFont = FontFactory.createFromAsset(this.getFontManager(), droidFontTexture4, this.getAssets(), "RobotoBold.ttf", 100, true, Color.argb(255, 255, 255, 255));
		this.ggVLargeFont.load();*/

		loadSounds();
		loadMusic();
	}
	public void loadScenes() {
		mGameScene = new Scene();
		mGameScene.setTouchAreaBindingOnActionDownEnabled(true);
		mGameScene.setOnSceneTouchListener(this);
		Rectangle sceneBg = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		sceneBg.setColor(1,0.973f,0.882f);
		//sceneBg.setColor(colorManager.getColor(15));
		mGameScene.attachChild(sceneBg);
		drawBackground(mGameScene);
		setBackgrounds();
		setNewRecordBonus();
		setScore();
		setLifeHud();
		createPauseButton();
		createNotificationSystem();


		mGameScene.registerUpdateHandler(new TimerHandler(1 / 30.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				playLinesweeperMusic();
				playFrenzyMusic();
				gameManager.updateGameState(startGame);
				if(gameManager.getGameLife() < 12000 && GAMETYPE ==1){
					gameBackgrounds.showBombBackground();
				}
				if(gameManager.getGameLife() < 8000 && GAMETYPE ==1){
					playTickTock();
				}
				if(gameManager.getGameLife() > 12000 && GAMETYPE ==1){
					gameBackgrounds.hideBombBackground();
					stopTickTock();
				}
				if(GAMETYPE == 2){
					updateScoreTextValue();
				}


				updateLifeHud();
				int message = gameManager.getSpeedMessage();
				final float duration = 0.65f;
				if(message == 1){
					notificator.showNotification(getString(R.string.Great), true);
					playNotificationSound();
					bonusText.clearEntityModifiers();
					if(GAMETYPE == 1){
						bonusText.setText(getString(R.string.PlusLife));
					}
					if(GAMETYPE == 2){
						bonusText.setText(getString(R.string.PlusSecond1));
					}
					bonusText.setX(bonusTouchX-bonusText.getWidth()/2);
					bonusText.setY(bonusTouchY-bonusText.getHeight()-100);
					final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(duration, 1f, 1.5f,EaseCubicIn.getInstance()),new MoveModifier(duration, bonusText.getX(),bonusText.getX(),bonusText.getY(),bonusText.getY()-50,EaseCubicIn.getInstance()), new AlphaModifier(duration,1f,0.0f))),1);
					bonusText.registerEntityModifier(spawnOut);
				}
				if(message == 2){
					notificator.showNotification(getString(R.string.Excellent), true);
					bonusText.clearEntityModifiers();
					if(GAMETYPE == 1){
						bonusText.setText(getString(R.string.PlusLife));
					}
					if(GAMETYPE == 2){
						bonusText.setText(getString(R.string.PlusSecond2));
					}
					bonusText.setX(bonusTouchX-bonusText.getWidth()/2);
					bonusText.setY(bonusTouchY-bonusText.getHeight()-100);
					final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(duration, 1f, 1.5f,EaseCubicIn.getInstance()),new MoveModifier(duration, bonusText.getX(),bonusText.getX(),bonusText.getY(),bonusText.getY()-50,EaseCubicIn.getInstance()), new AlphaModifier(duration,1f,0.0f))),1);
					bonusText.registerEntityModifier(spawnOut);
					playNotificationSound();
				}
				if(message == 3){
					notificator.showNotification(getString(R.string.AmazingNotification), true);
					bonusText.clearEntityModifiers();
					if(GAMETYPE == 1){
						bonusText.setText(getString(R.string.PlusLife));
					}
					if(GAMETYPE == 2){
						bonusText.setText(getString(R.string.PlusSecond3));
					}
					bonusText.setX(bonusTouchX-bonusText.getWidth()/2);
					bonusText.setY(bonusTouchY-bonusText.getHeight()-100);
					final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(duration, 1f, 1.5f,EaseCubicIn.getInstance()),new MoveModifier(duration, bonusText.getX(),bonusText.getX(),bonusText.getY(),bonusText.getY()-50,EaseCubicIn.getInstance()), new AlphaModifier(duration,1f,0.0f))),1);
					bonusText.registerEntityModifier(spawnOut);
					playNotificationSound();
				}
					/*position = position+1f;
					((BaseParticleEmitter)particleSystem.getParticleEmitter()).setCenter(position, 100);*/

					/*if(gameManager.getGameLife()<=0){
						playBombExplosionSound();
					}*/
				if(gameManager.isGameOver()){

					if(GAMETYPE == 1){

						int top = getTopLineIndex();
						lineArray.get(top).setRedLine();
					}
					playBombExplosionSound();
					animateInGameOver(0.3f,true);
					stopTickTock();
				}


			}
		}));

		//loadNewGame();
		addTouchHint();

		//gameOverScene();
		interfaceScene();
		drawBestScore();
		drawGameOver();
		drawTutorial();
		drawPause();
		drawGGShareDialog();
		drawCountdown();
		createBonusText();
		/*final LoopEntityModifier wait = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.15f)),1);
		wait.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				
				
			}
		});
		scoreTextValue.registerEntityModifier(wait);*/
		
		/*if(destroy){
			restoreGame();
		}
		else{
			loadNewGame();
		}*/
		Game.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				initControl();
			}
		});
		/*PackageInfo info;
		try {
		    info = getPackageManager().getPackageInfo("com.twoshellko.linesweeper", PackageManager.GET_SIGNATURES);
		    for (Signature signature : info.signatures) {
		        MessageDigest md;
		        md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        String something = new String(Base64.encode(md.digest(), 0));
		        //String something = new String(Base64.encodeBytes(md.digest()));
		        Log.d("hash key", something);
		    }
		} catch (NameNotFoundException e1) {
		    Log.d("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
		    Log.d("no such an algorithm", e.toString());
		} catch (Exception e) {
		    Log.d("exception", e.toString());
		}*/
		if(!twitterLogin){
			animateInMainMenu(0,true);
		}
		else{
			ggScoreText.setText(String.valueOf(twitterScore));
			gameManager.setScore(twitterScore);
			ggScoreText.setX(CAMERA_WIDTH/2-ggScoreText.getWidth()/2-15);
			final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = settings.edit();
			prefEditor.putBoolean("twitterLogin", false);
			prefEditor.commit();
			animateInGameOver(0,true);

		}
		//animateInBestScoreBox(0);
		//Text testText = new Text(100, 200, this.bonusStrokeFont, "+0.5s", this.getVertexBufferObjectManager());
		//mGameScene.attachChild(testText);
		//testText.setAlpha(0.5f);
		//showInMobi();
	}

	private Text scoreText;
	private Text scoreTime;
	private Text scoreTextValue;
	private Text scoreTextValueShade;
	private Text scoreText1Value;
	private Text scoreText1ValueShade;
	private void setScore(){
		scoreTextValue = new Text(340, 137, this.scoreXLargeFont, "0", 50, this.getVertexBufferObjectManager());
		scoreTextValue.setColor(0.3f,0.3f,0.3f);
		scoreTextValue.setX(CAMERA_WIDTH/2 - scoreTextValue.getWidth()/2-15);
		scoreTextValueShade = new Text(340, 137, this.scoreXLargeFont, "0", 50, this.getVertexBufferObjectManager());
		scoreTextValueShade.setColor(0.1f,0.1f,0.1f);
		scoreTextValueShade.setX(scoreTextValue.getX()+3);
		scoreTextValueShade.setY(scoreTextValue.getY()+3);

		scoreText1Value = new Text(340, 137, this.scoreXLargeFont, "0", 50, this.getVertexBufferObjectManager());
		scoreText1ValueShade = new Text(340, 137, this.scoreXLargeFont, "0", 50, this.getVertexBufferObjectManager());
		scoreText1Value.setColor(colorManager.getColor(14));
		scoreText1ValueShade.setColor(0.1f,0.1f,0.1f);
		//mGameScene.attachChild(scoreText);
		scoreText = new Text(550, 0, this.scoreLargeFont, getString(R.string.Score), 50, this.getVertexBufferObjectManager());
		scoreTime = new Text(50, 0, this.scoreLargeFont, getString(R.string.Time), 50, this.getVertexBufferObjectManager());
		scoreText.setColor(0.2f,0.2f,0.2f);
		scoreTime.setColor(0.2f,0.2f,0.2f);
		mGameScene.attachChild(scoreTextValueShade);
		mGameScene.attachChild(scoreTextValue);
		mGameScene.attachChild(scoreText1ValueShade);
		mGameScene.attachChild(scoreText1Value);
		mGameScene.attachChild(scoreText);
		mGameScene.attachChild(scoreTime);
	}
	private void updateScoreTextValue(){
		if(GAMETYPE == 1){
			scoreTextValue.setText(String.valueOf(gameManager.getScore()));
			scoreTextValueShade.setText(String.valueOf(gameManager.getScore()));
		}
		if(GAMETYPE == 2){
			scoreTextValue.setText(gameManager.getTime());
			scoreTextValueShade.setText(gameManager.getTime());
			scoreText1Value.setText(String.valueOf(gameManager.getScore()));
			scoreText1ValueShade.setText(String.valueOf(gameManager.getScore()));
		}
		//scoreTextValue.setX(CAMERA_WIDTH-scoreTextValue.getWidth()-10);

		if(GAMETYPE == 1){
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.1f, 1, 1.1f, EaseCubicInOut.getInstance()),new ScaleModifier(0.1f, 1.1f, 1, EaseCubicInOut.getInstance())),1);
			scoreTextValue.registerEntityModifier(scale);
			scoreTextValue.setX(CAMERA_WIDTH/2 - scoreTextValue.getWidth()/2-15);
			scoreTextValueShade.registerEntityModifier(scale.deepCopy());
			scoreTextValueShade.setX(CAMERA_WIDTH/2 - scoreTextValue.getWidth()/2-15+3);
		}

	}
	private V3InterfaceButton pauseButton = new V3InterfaceButton();
	private void createPauseButton(){
		pauseButton.setBuffer(getVertexBufferObjectManager());
		pauseButton.setButtonBgRegion(myBonusButtonBg);
		pauseButton.setButtonColorRegion(myBonusButtonColor);
		pauseButton.setButtonIconRegion(myPauseIcon);
		Text text = new Text(0, 0, this.scoreSmallFont, "", 50, this.getVertexBufferObjectManager());
		pauseButton.setButtonText(text);
		pauseButton.buildButton(CAMERA_WIDTH-170, 175,14,0,0);
		pauseButton.createButton(mGameScene,CAMERA_HEIGHT);
		pauseButton.animateIn(0);
	}

	private Sprite lifeHudBg;
	private Sprite lifeHudBomb;
	private Sprite lifeHudWick;
	private Sprite lifeHudCircleMask;
	private Rectangle lifeHudBgHighlight;
	private Rectangle lifeHudRightMask;
	private void setLifeHud(){
		lifeHudBg = new Sprite(0, 0, this.mLifeHudBg, this.getVertexBufferObjectManager());
		lifeHudBgHighlight = new Rectangle(150, 68, 652, 60, this.getVertexBufferObjectManager());
		lifeHudBgHighlight.setColor(0.4f,0.4f,0.4f);
		//lifeHudBgHighlight.setColor(0.35f,0.35f,0.35f);
		lifeHudCircleMask = new Sprite(lifeHudBgHighlight.getX()+lifeHudBgHighlight.getWidth()-30, lifeHudBgHighlight.getY(), this.mLifeHudCircle, this.getVertexBufferObjectManager());
		lifeHudCircleMask.setColor(0.4f,0.4f,0.4f);

		lifeHudWick = new Sprite(126, 0, this.mLifeHudWick, this.getVertexBufferObjectManager());
		lifeHudWick.setY(lifeHudBgHighlight.getY()+lifeHudBgHighlight.getHeight()/2 - lifeHudWick.getHeight()/2);

		lifeHudBomb = new Sprite(0, 50, this.mLifeHudBomb, this.getVertexBufferObjectManager());
		lifeHudBomb.setX(27);
		lifeHudBomb.setY(lifeHudBgHighlight.getY()+lifeHudBgHighlight.getHeight()/2 - lifeHudBomb.getHeight()/2 -1);

		lifeHudRightMask = new Rectangle(185, lifeHudBgHighlight.getY(), 620, 60, this.getVertexBufferObjectManager());
		//lifeHudRightMask.setColor(0.45f,0.45f,0.45f);
		lifeHudRightMask.setColor(0.4f,0.4f,0.4f);
		mGameScene.attachChild(lifeHudBg);
		mGameScene.attachChild(lifeHudBgHighlight);
		mGameScene.attachChild(lifeHudCircleMask);
		mGameScene.attachChild(lifeHudWick);
		mGameScene.attachChild(lifeHudBomb);
		mGameScene.attachChild(lifeHudRightMask);
		addParticle(690,98);

	}
	private void updateLifeHud(){
		float width = (-0.031f)*gameManager.getGameLife() + 620;
		//lifeHudLeftMask.setWidth(width);
		lifeHudRightMask.setWidth(width);
		lifeHudRightMask.setX(805-lifeHudRightMask.getWidth());
		((BaseParticleEmitter)particleSystem.getParticleEmitter()).setCenter(805-width-28, 82);
		//((BaseParticleEmitter)particleSystem1.getParticleEmitter()).setCenter(CAMERA_WIDTH-width-32, 130);
		//particleSystem.setParticlesSpawnEnabled(startGame);
		//particleSystem1.setParticlesSpawnEnabled(startGame);
	}

	/*private Sprite rightStars;
	private Sprite leftStars;
	private Sprite centerStar;*/
	private int gameStartBest = 0;
	private int gameStartBestFrenzy = 0;
	private Boolean newRecordBonusTriggered = false;
	private Boolean recordLineSpawned = false;
	private void setNewRecordBonus(){
		gameStartBest = gameManager.getBestScore(GAMETYPE);
		//gameStartBestFrenzy = gameManager.getBestScore(2);
		newRecordBonusTriggered = false;
		/*rightStars = new Sprite(0, 0, this.myStarsRightRegion, this.getVertexBufferObjectManager());
		rightStars.setX(CAMERA_WIDTH/2-20);
		rightStars.setY(180);
		mGameScene.attachChild(rightStars);
		leftStars = new Sprite(0, 0, this.myStarsLeftRegion, this.getVertexBufferObjectManager());
		leftStars.setX(CAMERA_WIDTH/2-leftStars.getWidth()+20);
		leftStars.setY(180);
		mGameScene.attachChild(leftStars);
		centerStar = new Sprite(0, 0, this.myStarsCenterRegion, this.getVertexBufferObjectManager());
		centerStar.setX(CAMERA_WIDTH/2 - centerStar.getWidth()/2);
		centerStar.setY(160);
		centerStar.setColor(0.988f,0.788f,0.129f);
		mGameScene.attachChild(centerStar);
		leftStars.setAlpha(0);
		rightStars.setAlpha(0);
		centerStar.setAlpha(0);
		leftStars.setVisible(false);
		rightStars.setVisible(false);
		centerStar.setVisible(false);*/
	}
	private void activateNewRectordBonus(){
		if(gameManager.getLinesRemoved() == 25){
			notificator.showNotification("25 "+getString(R.string.Lines), false);
			playNotification2Sound();
		}
		if(gameManager.getLinesRemoved() == 50){
			notificator.showNotification("50 "+getString(R.string.Lines), true);
			playNotification2Sound();
		}
		if(gameManager.getLinesRemoved() == 75){
			notificator.showNotification("75 "+getString(R.string.Lines), false);
			playNotification2Sound();
		}
		if(gameManager.getLinesRemoved() == 100){
			notificator.showNotification("100 "+getString(R.string.Lines), true);
			playNotification2Sound();
		}
		if(gameStartBest != 0 && gameManager.getScore()>gameStartBest && newRecordBonusTriggered == false){
			newRecordBonusTriggered = true;
			final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.2f, 0f, 1f,EaseCubicOut.getInstance()), new AlphaModifier(0.2f,0,1f)), new DelayModifier(0.0f),new ParallelEntityModifier(new ScaleModifier(0.5f, 1f, 0f,EaseCubicIn.getInstance()), new AlphaModifier(0.5f,1f,0.0f))),1);
			final LoopEntityModifier spawnOut2 = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.2f, 0f, 1f,EaseCubicOut.getInstance()), new AlphaModifier(0.2f,0,1f)), new DelayModifier(0.1f),new ParallelEntityModifier(new ScaleModifier(0.5f, 1f, 0f,EaseCubicIn.getInstance()), new AlphaModifier(0.5f,1f,0.0f))),1);

			/*leftStars.setVisible(true);
			rightStars.setVisible(true);
			centerStar.setVisible(true);
			spawnOut2.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					leftStars.setVisible(false);
					rightStars.setVisible(false);
					centerStar.setVisible(false);
				}
			});
			leftStars.setScaleCenterX(leftStars.getWidth());
			rightStars.setScaleCenterX(0);
			leftStars.registerEntityModifier(spawnOut);
			rightStars.registerEntityModifier(spawnOut.deepCopy());
			centerStar.registerEntityModifier(spawnOut2.deepCopy());*/
			scoreTextValue.setColor(colorManager.getColor(1));
			notificator.showNotification(getString(R.string.NewRecord), true);
			playNotificationSound();
		}
		if(gameStartBest != 0 && !recordLineSpawned){
			int record = getRecordLineIndex();
			if(record != -1000000){
				lineArray.get(record).spawnSpecial();
			}

		}
	}

	private ArrayList<Line> lineArray = new ArrayList<Line>();
	private void addLine(Boolean addToTop){
		Line line = new Line();
		line.setCameraSize(CAMERA_WIDTH, CAMERA_HEIGHT);
		line.setTotalLineAmount(NUMBER_OF_LINES_TO_SPAWN);
		lineGenerator.setBounds(CAMERA_BOUNDS[0], CAMERA_BOUNDS[1], CAMERA_BOUNDS[2], CAMERA_BOUNDS[3]);
		int lineColor = lineGenerator.generateLineColor();
		int lineType = lineGenerator.generateLineType();
		int[] dimensions = {0,0,0,0};
		Boolean pHorizontal = false;
		Boolean cHorizontal = true;
		int previousLineAdress = 0;
		int previousIndex = 0;
		if(lineArray.size() == 0){
			pHorizontal = false;
			cHorizontal = lineGenerator.generateLineOrientation(pHorizontal);
			dimensions = lineGenerator.generateSize(500, 500, 106, 250, cHorizontal,pHorizontal);
			line.setZindex(1,zoomEffect);


		}
		else{
			if(addToTop){
				previousLineAdress = 0;
				previousIndex = 0;
				for(int j=0;j<lineArray.size();j++){
					if(lineArray.get(j).getZindex()>previousIndex){
						previousIndex = lineArray.get(j).getZindex();
						previousLineAdress = j;
					}
				}
				line.setZindex(previousIndex+1,zoomEffect);


			}
			else{
				previousLineAdress = 1000;
				previousIndex = 1000;
				for(int j=0;j<lineArray.size();j++){
					if(lineArray.get(j).getZindex()<previousIndex){
						previousIndex = lineArray.get(j).getZindex();

						previousLineAdress = j;
					}
				}

				line.setZindex(1,zoomEffect);

				for(int j=0;j<lineArray.size();j++){
					lineArray.get(j).setZindex(lineArray.get(j).getZindex()+1,zoomEffect);
				}
			}
			cHorizontal = lineGenerator.generateLineOrientation(lineArray.get(previousLineAdress).isHorizontal());
			dimensions = lineGenerator.generateSize(lineArray.get(previousLineAdress).getX(), lineArray.get(previousLineAdress).getY(), lineArray.get(previousLineAdress).getWidth(), lineArray.get(previousLineAdress).getHeight(), cHorizontal,lineArray.get(previousLineAdress).isHorizontal());

		}

		//cHorizontal = true;

		line.setBuffer(this.getVertexBufferObjectManager());
		line.setOrientation(cHorizontal);

		line.setDimensions(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);

		if(cHorizontal){
			line.setCap1(lineLeftArray.get(lineColor));
			line.setCenterStretch(lineStretchHorizontalArray.get(lineColor));
			line.setCap2(lineRightArray.get(lineColor));

			line.setSpecialCap1(special_bg_l);
			line.setSpecialCenterStretch(special_bg_h);
			line.setSpecialCap2(special_bg_r);
			line.setSpecialCenter(special_bg_hs);

			line.setSpecialIcon1(specialLineLeftArray.get(0));
			line.setSpecialCenterIcon(specialLineHorizontalSArray.get(0));
			line.setSpecialIcon2(specialLineRightArray.get(0));
		}
		else{
			line.setCap1(lineTopArray.get(lineColor));
			line.setCenterStretch(lineStretchVerticalArray.get(lineColor));
			line.setCap2(lineBottomArray.get(lineColor));

			line.setSpecialCap1(special_bg_t);
			line.setSpecialCenterStretch(special_bg_v);
			line.setSpecialCap2(special_bg_b);
			line.setSpecialCenter(special_bg_vs);

			line.setSpecialIcon1(specialLineTopArray.get(0));
			line.setSpecialCenterIcon(specialLineVerticalSArray.get(0));
			line.setSpecialIcon2(specialLineBottomArray.get(0));
		}
		line.setColor(lineColor);
		line.createLine(mGameScene,zoomEffect);

		lineArray.add(line);
		mGameScene.sortChildren();

		//GET TEXTURE BASED ON COLOR
		//GET TEXTURE BASED ON TYPE
	}
	private void loadNewGame(){
		startGame = false;
		for(int i=0;i<NUMBER_OF_LINES_TO_SPAWN;i++){
			addLine(true);
		}
		gameStartBest = gameManager.getBestScore(GAMETYPE);
		newRecordBonusTriggered = false;
		recordLineSpawned = false;
		scoreTextValue.setVisible(true);
		scoreTextValueShade.setVisible(true);
		//scoreTextValue.setColor(0.3f,0.3f,0.3f);
		scoreTextValue.setColor(colorManager.getColor(14));
		notificator.clearNotification();
		//activateNewRectordBonus();
	}
	private void removeLine(){
		final int top = getTopLineIndex();
		lineArray.get(top).reduceLife();

		if(lineArray.get(top).okToRemoveLine()){
			touchIcon.hideTouchIcon();
			playRemoveLineSound();
			final Line removeMeLine = lineArray.get(top);

			gameManager.updateLinesRemoved();
			gameManager.activateLineRemovedBonus();
			gameManager.updateScore();
			updateScoreTextValue();
			lineArray.remove(top);
			Game.this.runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					removeMeLine.removeLine();

				}
			});
			if(GAMETYPE == 1 || GAMETYPE == 2){
				addLine(false);
			}
			/*if(GAMETYPE == 2){
				for(int j=0;j<lineArray.size();j++){	
					lineArray.get(j).setZindex(lineArray.get(j).getZindex()+1,zoomEffect);
				}
			}*/
			activateNewRectordBonus();
		}
		/*if(GAMETYPE == 2){
			if(gameManager.getLinesRemoved() == 60){
				gameManager.setGameOver();
				animateInGameOver(0, true);
			}
		}*/
	}
	private int getTopLineIndex(){
		int index=0;
		int topLevelAdress = 0;
		for(int j=0;j<lineArray.size();j++){
			if(lineArray.get(j).getZindex()>index){

				index = lineArray.get(j).getZindex();
				topLevelAdress = j;
			}
		}

		return topLevelAdress;
	}
	private int getRecordLineIndex(){
		int index=0;
		int topLevelAdress = 0;
		for(int j=0;j<lineArray.size();j++){
			if(lineArray.get(j).getZindex()>index){

				index = lineArray.get(j).getZindex();
				topLevelAdress = j;
			}
		}
		if(gameManager.getBestScore(GAMETYPE) <NUMBER_OF_LINES_TO_SPAWN){
			for(int j=0;j<lineArray.size();j++){
				if(lineArray.get(j).getZindex() == index-gameManager.getBestScore(GAMETYPE)+1){
					topLevelAdress = j;
					recordLineSpawned = true;
				}
			}
		}
		else{
			if(gameManager.getScore()+59 == gameManager.getBestScore(GAMETYPE)){
				for(int j=0;j<lineArray.size();j++){
					if(lineArray.get(j).getZindex() == index-59){
						topLevelAdress = j;
						recordLineSpawned = true;
					}
				}
			}
		}
		/*if(gameManager.getBestScore(GAMETYPE) >=10 && gameManager.getBestScore(GAMETYPE)<NUMBER_OF_LINES_TO_SPAWN){
			for(int j=0;j<lineArray.size();j++){
				if(lineArray.get(j).getZindex() == index-10){
					topLevelAdress = j;
				}
			}
		}*/

		if(!recordLineSpawned){
			topLevelAdress = -1000000;
		}


		return topLevelAdress;
	}
	private Mesh bg;
	private Mesh bg2;
	private void drawBackground(Scene scene){
		float MeshArray[] = new float[324];
		float MeshArray2[] = new float[324];
		int j=0;
		double diagonal = Math.sqrt(CAMERA_HEIGHT/2*CAMERA_HEIGHT/2 + (CAMERA_WIDTH/2)*(CAMERA_WIDTH/2));
		for(int i=0; i<36;i++){
			double line1X = (diagonal+5)*Math.cos(Math.toRadians(10*i));
			double line1Y = (diagonal+5)*Math.sin(Math.toRadians(10*i));
			double line2X = (diagonal+5)*Math.cos(Math.toRadians(5+10*i));
			double line2Y = (diagonal+5)*Math.sin(Math.toRadians(5+10*i));
			MeshArray[j] = 0;
			j++;
			MeshArray[j] = 0;
			j++;
			MeshArray[j] = 0;
			j++;
			MeshArray[j] = (float)line1X;
			j++;
			MeshArray[j] = (float)line1Y;
			j++;
			MeshArray[j] = 0;
			j++;
			MeshArray[j] = (float)line2X;
			j++;
			MeshArray[j] = (float)line2Y;
			j++;
			MeshArray[j] = 0;
			j++;
		}
		j=0;
		for(int i=0; i<36;i++){
			double line1X = (diagonal+5)*Math.cos(Math.toRadians(10*i));
			double line1Y = (diagonal+5)*Math.sin(Math.toRadians(10*i));
			double line2X = (diagonal+5)*Math.cos(Math.toRadians(5+10*i));
			double line2Y = (diagonal+5)*Math.sin(Math.toRadians(5+10*i));
			MeshArray2[j] = 0;
			j++;
			MeshArray2[j] = 0;
			j++;
			MeshArray2[j] = 0;
			j++;
			MeshArray2[j] = (float)line1X;
			j++;
			MeshArray2[j] = (float)line1Y;
			j++;
			MeshArray2[j] = 0;
			j++;
			MeshArray2[j] = (float)line2X;
			j++;
			MeshArray2[j] = (float)line2Y;
			j++;
			MeshArray2[j] = 0;
			j++;
		}

		bg = new Mesh(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, MeshArray, 108, DrawMode.TRIANGLE_FAN, mEngine.getVertexBufferObjectManager());
		bg2 = new Mesh(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, MeshArray2, 108, DrawMode.TRIANGLE_FAN, mEngine.getVertexBufferObjectManager());
		//bg.setRotationCenter(CAMERA_WIDTH/2, CAMERA_HEIGHT/2);
		//bg.setColor(0.925f,0.898f,0.808f);
		bg.setColor(1f,0.878f,0.698f);
		bg2.setColor(1f,0.878f,0.698f);
		bg2.setAlpha(0.35f);
		if(bgSpin){
			final LoopEntityModifier bgRotation = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(75, 0, 360)),-1);
			bg.registerEntityModifier(bgRotation);
			final LoopEntityModifier bgRotation2 = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(75, -0.75f, 359.25f)),-1);
			bg2.registerEntityModifier(bgRotation2);
		}
		scene.attachChild(bg2);
		scene.attachChild(bg);

	}
	private void setBackgrounds(){
		Rectangle bombBg = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		Rectangle freezeBg = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		gameBackgrounds.createBackgrounds(mGameScene, bombBg);
	}

	private String getHighScroesSharedPrefsString(int gametype){
		String pref = "";
		if(gametype == 1){
			pref = "HighscoresMine";
		}
		if(gametype == 2){
			pref = "HighscoresFrenzy";
		}
		return pref;
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if(pSceneTouchEvent.isActionDown()) {
			Log.d("PRESS", "OCCURED");
			if(pSceneTouchEvent.getX()>CAMERA_WIDTH-200 && pSceneTouchEvent.getY() < 200){
				pauseButton.onPress();
				playMenuButtonSound();
				animateInPause(0);
				//notificator.showNotification("NEW RECORD!",true);
			}

			if(isWithinBounds(pSceneTouchEvent.getX(),pSceneTouchEvent.getY())){
				bonusTouchX = pSceneTouchEvent.getX();
				bonusTouchY = pSceneTouchEvent.getY();
				int top = getTopLineIndex();
				startGame = true;
				if (pSceneTouchEvent.getX() >= lineArray.get(top).getX()  && pSceneTouchEvent.getX() <= lineArray.get(top).getX()+lineArray.get(top).getWidth()
						&& pSceneTouchEvent.getY() >= lineArray.get(top).getY()  && pSceneTouchEvent.getY() <= lineArray.get(top).getY()+lineArray.get(top).getHeight()){

					removeLine();
				}
				else{
					//removeLine();
					if(GAMETYPE == 1){
						lineArray.get(top).setRedLine();
					}
					gameManager.setWrongTap();
					gameBackgrounds.wrongTapBackground();
					playWrongPressSound();
					float duration = 0.65f;
					if(GAMETYPE == 2){
						bonusText.clearEntityModifiers();
						bonusText.setText(getString(R.string.MinusSecond));
						bonusText.setX(bonusTouchX-bonusText.getWidth()/2);
						bonusText.setY(bonusTouchY-bonusText.getHeight()-100);
						final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(duration, 1f, 1.5f,EaseCubicIn.getInstance()),new MoveModifier(duration, bonusText.getX(),bonusText.getX(),bonusText.getY(),bonusText.getY()-50,EaseCubicIn.getInstance()), new AlphaModifier(duration,1f,0.0f))),1);
						bonusText.registerEntityModifier(spawnOut);
					}
					//animateInGameOver();
				}
			}


		}
		if(pSceneTouchEvent.isActionMove()) {

		}
		if(pSceneTouchEvent.isActionUp()) {
			//if(pSceneTouchEvent.getX()>CAMERA_WIDTH-200 && pSceneTouchEvent.getY() < 200){
			pauseButton.onRelease(false);

			//}
		}
		return false;
	}
	private Boolean isWithinBounds(float touchX, float touchY){
		Boolean within = true;
		if(touchX < CAMERA_BOUNDS[0]){
			within = false;
		}
		if(touchX > CAMERA_BOUNDS[1]){
			within = false;
		}
		if(touchY < CAMERA_BOUNDS[2]){
			within = false;
		}
		if(touchY > CAMERA_BOUNDS[3]){
			within = false;
		}
		return within;
	}

	private NotificationSystem notificator = new NotificationSystem();
	private void createNotificationSystem(){
		notificator.setBuffer(getVertexBufferObjectManager());
		notificator.setBoxShadows(interfaceBoxShadow);
		notificator.setStarsRegion(myStarsLeftRegion, myStarsRightRegion);
		notificator.setTextStringFont(scoreLargeFont);
		notificator.createNotificationBox(mGameScene, CAMERA_WIDTH, CAMERA_HEIGHT);
	}




	private ArrayList<V3InterfaceBox> interfaceBoxes = new ArrayList<V3InterfaceBox>();
	private Boolean ggContinueMenu = false;
	private Rectangle scoreBoardTouch;


	private Text bonusText;
	private float bonusTouchX;
	private float bonusTouchY;
	private void createBonusText(){
		bonusText = new Text(100, 200, this.bonusStrokeFont, "+0.5s", this.getVertexBufferObjectManager());
		mGameScene.attachChild(bonusText);
		bonusText.setZIndex(10000000);
		bonusText.setAlpha(0);
	}












	private Sprite interfaceBg;
	private Sprite logoBg;
	private Sprite logoTop;
	private Sprite logoBgFrenzy;
	private Sprite logoTopFrenzy;
	private Text logoTextBig1;
	private Text logoTextBig2;
	private ArrayList<V3InterfaceButton> interfaceButtonArray = new ArrayList<V3InterfaceButton>();
	private Rectangle mainMenuTouch0;
	private Rectangle mainMenuTouch1;
	private Rectangle mainMenuTouch2;
	private Rectangle mainMenuTouch3;
	private Rectangle mainMenuTouch4;
	private void interfaceScene(){
		logoStartPostition = CAMERA_HEIGHT/2 - 165;
		mInterfaceScene = new Scene();
		mInterfaceScene.setBackgroundEnabled(false);
		mInterfaceScene.setTouchAreaBindingOnActionDownEnabled(true);
		interfaceBg = new Sprite(15, 12, this.myButtonRegularTopRegion, this.getVertexBufferObjectManager());
		interfaceBg.setColor(1,0.973f,0.882f);
		interfaceBg.setAlpha(1f);
		interfaceBg.setX(CAMERA_WIDTH/2-interfaceBg.getWidth()/2);
		interfaceBg.setY(CAMERA_HEIGHT/2-interfaceBg.getHeight());
		mInterfaceScene.attachChild(interfaceBg);
		drawLogo();
		drawMainMenu();
		drawSettings();


	}
	private Entity logoButtons = new Entity();
	private Rectangle lineSweeperTextBg;
	private Rectangle frenzyTextBg;
	private int logoStartPostition = 0;
	private int slideAmount = 830;
	private ArrayList<V3InterfaceBox> mainMenuBoxArray = new ArrayList<V3InterfaceBox>();
	private void drawLogo(){

		V3InterfaceBox box1 = new V3InterfaceBox();
		box1.setBuffer(getVertexBufferObjectManager());
		box1.setShadowSprites(interfaceBoxShadow);
		box1.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 10, 10);
		box1.createBox(logoButtons);
		box1.setColor(0.9f, 0.9f, 0.9f);
		//box1.setEntityXY(0, CAMERA_HEIGHT);
		mainMenuBoxArray.add(box1);

		V3InterfaceBox box2 = new V3InterfaceBox();
		box2.setBuffer(getVertexBufferObjectManager());
		box2.setShadowSprites(interfaceBoxShadow);
		box2.buildBox(CAMERA_WIDTH/2+slideAmount, CAMERA_HEIGHT/2, 10, 10);
		box2.createBox(logoButtons);
		box2.setColor(0.9f, 0.9f, 0.9f);
		//box1.setEntityXY(0, CAMERA_HEIGHT);
		mainMenuBoxArray.add(box2);


		lineSweeperTextBg = new Rectangle(0, CAMERA_HEIGHT/2+300, 690, 140, this.getVertexBufferObjectManager());
		lineSweeperTextBg.setX(CAMERA_WIDTH/2-lineSweeperTextBg.getWidth()/2);
		lineSweeperTextBg.setColor(colorManager.getColor(3));
		logoButtons.attachChild(lineSweeperTextBg);

		frenzyTextBg= new Rectangle(0, CAMERA_HEIGHT/2+300, 690, 140, this.getVertexBufferObjectManager());
		frenzyTextBg.setX(CAMERA_WIDTH/2-frenzyTextBg.getWidth()/2 +slideAmount);
		frenzyTextBg.setColor(colorManager.getColor(7));
		logoButtons.attachChild(frenzyTextBg);

		logoBg = new Sprite(0, logoStartPostition, this.logo_bg, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		logoBg.setX(CAMERA_WIDTH/2-logoBg.getWidth()/2);
		logoTop = new Sprite(0, logoStartPostition+97, this.logo_top, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		//logoTop.setX(CAMERA_WIDTH/2-logoTop.getWidth()/2+10);
		logoTop.setX(CAMERA_WIDTH/2-logoTop.getWidth()/2+13);
		logoTop.setY(logoStartPostition+45);

		logoTextBig1 = new Text(140, 23, this.scoreLargeFont, getString(R.string.PlayLinesweeper), 50, this.getVertexBufferObjectManager());
		logoTextBig1.setColor(colorManager.getColor(15));
		logoTextBig1.setX(CAMERA_WIDTH/2-logoTextBig1.getWidth()/2);
		logoTextBig1.setY(lineSweeperTextBg.getY()+lineSweeperTextBg.getHeight()/2-logoTextBig1.getHeight()/2);



		logoButtons.attachChild(logoBg);
		logoButtons.attachChild(logoTop);
		logoBg.setAlpha(0);
		logoTop.setAlpha(0);
		logoTextBig1.setAlpha(0);
		//logoButtons.attachChild(logoTextSmall1);
		logoButtons.attachChild(logoTextBig1);

		logoBgFrenzy = new Sprite(0, logoStartPostition, this.logo_frenzy_bg, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		logoBgFrenzy.setX(CAMERA_WIDTH/2-logoBgFrenzy.getWidth()/2+slideAmount);
		logoTopFrenzy = new Sprite(0, logoStartPostition+88, this.logo_frenzy_top, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		logoTopFrenzy.setX(CAMERA_WIDTH/2-logoTopFrenzy.getWidth()/2+2+slideAmount);

		logoBgFrenzy.setAlpha(0);
		logoTopFrenzy.setAlpha(0);
		logoButtons.attachChild(logoBgFrenzy);
		logoButtons.attachChild(logoTopFrenzy);


		logoTextBig2 = new Text(140, 23, this.scoreLargeFont, getString(R.string.Frenzy), 50, this.getVertexBufferObjectManager());
		logoTextBig2.setColor(colorManager.getColor(15));
		logoTextBig2.setX(CAMERA_WIDTH/2-logoTextBig2.getWidth()/2+slideAmount);
		logoTextBig2.setY(frenzyTextBg.getY()+frenzyTextBg.getHeight()/2-logoTextBig2.getHeight()/2);

		//logoButtons.attachChild(logoTextSmall2);
		logoButtons.attachChild(logoTextBig2);
		logoTextBig2.setAlpha(0);

		lineSweeperTextBg.setAlpha(0);
		frenzyTextBg.setAlpha(0);


		mInterfaceScene.attachChild(logoButtons);
	}
	private void addParticleInterface(){
		float duration = 0.5f;
		float RATE_MIN    = 15;
		float RATE_MAX	   = 25;
		int PARTICLES_MAX = 12;
		particleSystemInterface = new SpriteParticleSystem(new PointParticleEmitter(660, logoStartPostition+105), RATE_MIN, RATE_MAX, PARTICLES_MAX, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		particleSystemInterface.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 1f,1));
		particleSystemInterface.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystemInterface.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystemInterface.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-6, 6, -6, 6));
		particleSystemInterface.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystemInterface.addParticleInitializer(new ExpireParticleInitializer<Sprite>(duration));
		particleSystemInterface.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 0.4f*duration, 0.1f, 1.6f));
		particleSystemInterface.addParticleModifier(new ColorParticleModifier<Sprite>(0, 0.5f*duration, 1, 0.9f, 1f, 0.9f, 0.5f, 0f));
		particleSystemInterface.addParticleModifier(new ColorParticleModifier<Sprite>(0.5f*duration, duration, 0.9f, 0.99f, 0.9f, 0f, 0, 0));
		particleSystemInterface.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.2f*duration, 0, 1));
		particleSystemInterface.addParticleModifier(new AlphaParticleModifier<Sprite>(0.2f*duration, duration, 1, 0));
		//particleSystemInterface.setParticlesSpawnEnabled(false);
		logoButtons.attachChild(particleSystemInterface);

	}
	private void addParticeleGameOver(){
		float duration = 0.5f;
		float RATE_MIN    = 15;
		float RATE_MAX	   = 25;
		int PARTICLES_MAX = 12;
		particleSystemGameOver = new SpriteParticleSystem(new PointParticleEmitter(660, logoStartPostition+105+100), RATE_MIN, RATE_MAX, PARTICLES_MAX, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		particleSystemGameOver.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 1f,1));
		particleSystemGameOver.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystemGameOver.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystemGameOver.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-6, 6, -6, 6));
		particleSystemGameOver.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystemGameOver.addParticleInitializer(new ExpireParticleInitializer<Sprite>(duration));
		particleSystemGameOver.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 0.4f*duration, 0.1f, 1.6f));
		particleSystemGameOver.addParticleModifier(new ColorParticleModifier<Sprite>(0, 0.5f*duration, 1, 0.9f, 1f, 0.9f, 0.5f, 0f));
		particleSystemGameOver.addParticleModifier(new ColorParticleModifier<Sprite>(0.5f*duration, duration, 0.9f, 0.99f, 0.9f, 0f, 0, 0));
		particleSystemGameOver.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.2f*duration, 0, 1));
		particleSystemGameOver.addParticleModifier(new AlphaParticleModifier<Sprite>(0.2f*duration, duration, 1, 0));
		particleSystemGameOver.setParticlesSpawnEnabled(false);
		mGameOverScene.attachChild(particleSystemGameOver);
	}
	private ArrayList<V3InterfaceButton> mainMenuButtonArray = new ArrayList<V3InterfaceButton>();
	private Text bestScoreText;
	private Text bestScoreValue;
	private Text bestScoreTextFrenzy;
	private Text bestScoreValueFrenzy;
	private Text appNameText;
	private Sprite appLogo;
	private Boolean dragging = false;
	private Boolean allowDrag = false;
	private float touchX = 0;
	private float touchY = 0;
	private int boxHeight = 930;
	private SpriteParticleSystem particleSystemInterface;
	private void drawMainMenu(){

		addParticleInterface();
		int position = CAMERA_HEIGHT/2-410;
		int topHeight = CAMERA_HEIGHT/2-boxHeight/2;
		appLogo = new Sprite(15, 125, this.mySmallLogo, this.getVertexBufferObjectManager());
		appNameText = new Text(140, 135, this.logoLargeFont, getString(R.string.Linesweeper), 50, this.getVertexBufferObjectManager());
		appLogo.setY(topHeight/2 - appLogo.getHeight()/2);
		appNameText.setY(topHeight/2 - appNameText.getHeight()/2+12);
		appLogo.setAlpha(0);
		appNameText.setAlpha(0);

		appNameText.setColor(0.2f,0.2f,0.2f);
		appLogo.setX(CAMERA_WIDTH/2 - (appLogo.getWidth()+15+appNameText.getWidth())/2);
		appNameText.setX(appLogo.getX()+appLogo.getWidth()+15);
		mInterfaceScene.attachChild(appLogo);
		mInterfaceScene.attachChild(appNameText);



		bestScoreText = new Text(140, position, this.scoreSmallFont, getString(R.string.BestScore), 50, this.getVertexBufferObjectManager());
		bestScoreText.setColor(0.4f,0.4f,0.4f);
		bestScoreText.setX(CAMERA_WIDTH/2-bestScoreText.getWidth()/2);

		bestScoreValue = new Text(140, position+15, this.scoreXLargeFont, String.valueOf(gameManager.getBestScore(1)), 50, this.getVertexBufferObjectManager());
		bestScoreValue.setColor(0.2f,0.2f,0.2f);
		bestScoreValue.setX(CAMERA_WIDTH/2-bestScoreValue.getWidth()/2);

		bestScoreTextFrenzy = new Text(140, position, this.scoreSmallFont, getString(R.string.BestScore), 50, this.getVertexBufferObjectManager());
		bestScoreTextFrenzy.setColor(0.4f,0.4f,0.4f);
		bestScoreTextFrenzy.setX(CAMERA_WIDTH/2-bestScoreTextFrenzy.getWidth()/2+slideAmount);

		bestScoreValueFrenzy = new Text(140, position+15, this.scoreXLargeFont, String.valueOf(gameManager.getBestScore(2)), 50, this.getVertexBufferObjectManager());
		bestScoreValueFrenzy.setColor(0.2f,0.2f,0.2f);
		bestScoreValueFrenzy.setX(CAMERA_WIDTH/2-bestScoreValueFrenzy.getWidth()/2+slideAmount);

		logoButtons.attachChild(bestScoreText);
		logoButtons.attachChild(bestScoreValue);
		logoButtons.attachChild(bestScoreTextFrenzy);
		logoButtons.attachChild(bestScoreValueFrenzy);

		int bottomHeight = CAMERA_HEIGHT - (CAMERA_HEIGHT/2+boxHeight/2);
		createInterfaceButton(mainMenuButtonArray, mInterfaceScene, 464-290, CAMERA_HEIGHT-bottomHeight/2+40, mySettingsIcon, getString(R.string.Settings), 9,0,0);
		createInterfaceButton(mainMenuButtonArray, mInterfaceScene, 464, CAMERA_HEIGHT-bottomHeight/2+40, myLeaderboardIcon, getString(R.string.Online), 8,0,0);
		createInterfaceButton(mainMenuButtonArray, mInterfaceScene, 464+290, CAMERA_HEIGHT-bottomHeight/2+40, myRateIcon, getString(R.string.RateGame), 13,0,-2);

		mainMenuTouch0 = new Rectangle(0, CAMERA_HEIGHT/2-550, CAMERA_WIDTH, 1100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						//mainMenuButtonArray.get(0).onPress();

						touchX = pSceneTouchEvent1.getX();
						touchY = pSceneTouchEvent1.getY();
						allowDrag = true;
						/*if(logoButtons.getX() == 0){
							logoTop.setScale(0.9f);
							logoTextBig1.setColor(0.2f,0.2f,0.2f);
						}
						if(logoButtons.getX() == -slideAmount){
							logoTopFrenzy.setScale(0.9f);
							logoTextBig2.setColor(0.2f,0.2f,0.2f);
						}*/
						break;

					case TouchEvent.ACTION_MOVE:
						if(pSceneTouchEvent1.getX()>touchX+50 && allowDrag){
							logoTop.setScale(1f);
							logoTopFrenzy.setScale(1f);
							logoTextBig1.setColor(colorManager.getColor(15));
							logoTextBig2.setColor(colorManager.getColor(15));
							allowDrag = false;
							dragging = true;
							slideMenuRight();

						}
						if(pSceneTouchEvent1.getX()<touchX-50 && allowDrag){
							logoTop.setScale(1f);
							logoTopFrenzy.setScale(1f);
							logoTextBig1.setColor(colorManager.getColor(15));
							logoTextBig2.setColor(colorManager.getColor(15));
							allowDrag = false;
							dragging = true;
							slideMenuLeft();
						}
						break;

					case TouchEvent.ACTION_UP:
						//mainMenuButtonArray.get(0).onRelease(false);
						logoTop.setScale(1f);
						logoTopFrenzy.setScale(1f);
						logoTextBig1.setColor(colorManager.getColor(15));
						logoTextBig2.setColor(colorManager.getColor(15));
						if(!dragging){
							if(touchX < 100){
								slideMenuRight();
							}
							if(touchX >CAMERA_WIDTH-100){
								slideMenuLeft();
							}
							if(touchX < CAMERA_WIDTH - 100 && touchX > 100){
								if(logoButtons.getX() == 0){
									playMenuButtonSound();
									setGametypeLinesweeper();
									Game.this.runOnUpdateThread(new Runnable() {

										@Override
										public void run() {

											for(int i=0;i<lineArray.size();i++){
												lineArray.get(i).removeLine();
											}
											lineArray.clear();
											loadNewGame();
											startGame  = false;
											gameManager.resetGameState();
											updateScoreTextValue();
											int top = getTopLineIndex();
											touchIcon.moveTouchIcon(lineArray.get(top).getX()+lineArray.get(top).getWidth()/2, lineArray.get(top).getY()+lineArray.get(top).getHeight()/2);
											touchIcon.showTouchIcon();
										}
									});
									animateOutMainMenu(0,true);
									if((showTutorial && GAMETYPE == 1)||(showTutorialFrenzy && GAMETYPE == 2)){
										animateInTutorial(0.0f);
									}
									/*else{
										animateInCountdown();
									}*/

								}
								if(logoButtons.getX() == -slideAmount){
									playMenuButtonSound();
									setGametypeFrenzy();
									Game.this.runOnUpdateThread(new Runnable() {

										@Override
										public void run() {

											for(int i=0;i<lineArray.size();i++){
												lineArray.get(i).removeLine();
											}
											lineArray.clear();
											loadNewGame();
											startGame  = false;
											gameManager.resetGameState();
											updateScoreTextValue();
											int top = getTopLineIndex();
											touchIcon.moveTouchIcon(lineArray.get(top).getX()+lineArray.get(top).getWidth()/2, lineArray.get(top).getY()+lineArray.get(top).getHeight()/2);
											touchIcon.showTouchIcon();
										}
									});
									animateOutMainMenu(0,true);
									if((showTutorial && GAMETYPE == 1)||(showTutorialFrenzy && GAMETYPE == 2)){
										animateInTutorial(0.0f);
									}
									/*else{
										animateInCountdown();
									}*/
								}

							}

						}
						dragging = false;
						break;
				}

				return true;
			}

		};
		mainMenuTouch1 = new Rectangle(mainMenuButtonArray.get(0).getX(), mainMenuButtonArray.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						mainMenuButtonArray.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						mainMenuButtonArray.get(0).onRelease(false);
						animateInSettings(0.2f);
						animateOutMainMenu(0,false);


						break;
				}

				return true;
			}

		};
		mainMenuTouch2 = new Rectangle(mainMenuButtonArray.get(1).getX(), mainMenuButtonArray.get(1).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						mainMenuButtonArray.get(1).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						mainMenuButtonArray.get(1).onRelease(false);
						if(mHelper.isSignedIn()){

							Game.this.runOnUiThread(new Runnable() {
								//@Overrides
								public void run() {
									Game.this.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()), 5001);
								}
							});

						}
						else{
							beginUserInitiatedSignIn();
						}
						break;
				}

				return true;
			}

		};
		mainMenuTouch3 = new Rectangle(mainMenuButtonArray.get(2).getX(), mainMenuButtonArray.get(2).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						mainMenuButtonArray.get(2).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						mainMenuButtonArray.get(2).onRelease(false);
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=com.twoshellko.linesweeper"));
						startActivity(intent);
						break;
				}

				return true;
			}

		};

	}
	private void slideMenuLeft(){
		//mainMenuBoxArray.get(0).changeColor(0, 16);
		//mainMenuBoxArray.get(1).changeColor(0, 15);
		final LoopEntityModifier toggle2 = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f,logoButtons.getX(),-slideAmount,0,0,EaseCubicInOut.getInstance())),1);
		logoButtons.registerEntityModifier(toggle2);
	}
	private void slideMenuRight(){
		//mainMenuBoxArray.get(0).changeColor(0, 15);
		//mainMenuBoxArray.get(1).changeColor(0, 16);
		final LoopEntityModifier toggle2 = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f,logoButtons.getX(),0,0,0,EaseCubicInOut.getInstance())),1);
		logoButtons.registerEntityModifier(toggle2);
	}
	private void animateInMainMenu(float delay, Boolean scene){
		//Log.d("MAIN MENU", "ANIMATING IN");
		playMainMenuMusic();
		scoreTextValue.setVisible(false);
		scoreTextValueShade.setVisible(false);
		mInterfaceScene.registerTouchArea(mainMenuTouch0);
		mInterfaceScene.registerTouchArea(mainMenuTouch1);
		mInterfaceScene.registerTouchArea(mainMenuTouch2);
		mInterfaceScene.registerTouchArea(mainMenuTouch3);

		//SHOWING_MENU = true;
		mGameScene.setChildScene(mInterfaceScene, false, true, true);
		if(scene){
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.3f, 0,50,EaseCubicIn.getInstance())),1);
			interfaceBg.registerEntityModifier(scale);
		}
		else{
			interfaceBg.setScale(50);
		}

		mainMenuBoxArray.get(0).morphBox(delay, 740, boxHeight, 15);
		mainMenuBoxArray.get(1).morphBox(delay, 740, boxHeight, 15);

		mainMenuButtonArray.get(0).animateIn(delay);
		mainMenuButtonArray.get(1).animateIn(delay);
		mainMenuButtonArray.get(2).animateIn(delay);

		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.05f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		logoBg.registerEntityModifier(scale0.deepCopy());
		logoTop.registerEntityModifier(scale1.deepCopy());
		logoBgFrenzy.registerEntityModifier(scale0.deepCopy());
		logoTopFrenzy.registerEntityModifier(scale1.deepCopy());
		logoTextBig1.registerEntityModifier(scale1.deepCopy());
		logoTextBig2.registerEntityModifier(scale1.deepCopy());
		bestScoreText.registerEntityModifier(scale1.deepCopy());
		bestScoreValue.registerEntityModifier(scale1.deepCopy());
		bestScoreTextFrenzy.registerEntityModifier(scale1.deepCopy());
		bestScoreValueFrenzy.registerEntityModifier(scale1.deepCopy());
		lineSweeperTextBg.registerEntityModifier(scale1.deepCopy());
		frenzyTextBg.registerEntityModifier(scale1.deepCopy());
		appLogo.registerEntityModifier(scale1.deepCopy());
		appNameText.registerEntityModifier(scale1.deepCopy());

		particleSystemInterface.setParticlesSpawnEnabled(false);
		//Log.d("LOGO BG Y", String.valueOf(logoBg.getY()));
		Game.this.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {

				for(int i=0;i<lineArray.size();i++){
					lineArray.get(i).removeLine();
				}
				lineArray.clear();

			}
		});
	}
	private void animateOutMainMenu(float delay, Boolean scene){
		mInterfaceScene.unregisterTouchArea(mainMenuTouch0);
		mInterfaceScene.unregisterTouchArea(mainMenuTouch1);
		mInterfaceScene.unregisterTouchArea(mainMenuTouch2);
		mInterfaceScene.unregisterTouchArea(mainMenuTouch3);

		mainMenuButtonArray.get(0).animateOut(delay);
		mainMenuButtonArray.get(1).animateOut(delay);
		mainMenuButtonArray.get(2).animateOut(delay);

		mainMenuBoxArray.get(0).morphBox(delay+0.15f, 10, 10, 15);
		mainMenuBoxArray.get(1).morphBox(delay+0.15f, 10, 10, 15);

		particleSystemInterface.setParticlesSpawnEnabled(false);
		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		logoBg.registerEntityModifier(scale0.deepCopy());
		logoTop.registerEntityModifier(scale1.deepCopy());
		logoBgFrenzy.registerEntityModifier(scale0.deepCopy());
		logoTopFrenzy.registerEntityModifier(scale1.deepCopy());
		logoTextBig1.registerEntityModifier(scale1.deepCopy());
		logoTextBig2.registerEntityModifier(scale1.deepCopy());
		bestScoreText.registerEntityModifier(scale1.deepCopy());
		bestScoreValue.registerEntityModifier(scale1.deepCopy());
		bestScoreTextFrenzy.registerEntityModifier(scale1.deepCopy());
		bestScoreValueFrenzy.registerEntityModifier(scale1.deepCopy());
		lineSweeperTextBg.registerEntityModifier(scale1.deepCopy());
		frenzyTextBg.registerEntityModifier(scale1.deepCopy());
		appLogo.registerEntityModifier(scale1.deepCopy());
		appNameText.registerEntityModifier(scale1.deepCopy());

		if(scene){
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.0f),new ScaleModifier(0.5f, 50,0,EaseCubicIn.getInstance())),1);
			scale.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					mGameScene.clearChildScene();

				}
			});
			interfaceBg.registerEntityModifier(scale);
		}


	}












	private Text ggScoreText;
	private Text ggBestScoreText;
	private Text ggLogoTextBig;
	private Text ggScoreTopText;
	private Text ggText;
	//private Rectangle ggPlayAgainBg;
	//private Rectangle ggScoreBg;
	private ArrayList<V3InterfaceButton> gameOverButtonArray = new ArrayList<V3InterfaceButton>();
	private Rectangle ggTouch0;
	private Rectangle ggTouch1;
	private Rectangle ggTouch2;
	private Rectangle ggTouch3;
	private Sprite ggLogoTop;
	private Sprite ggLogoBg;
	private Sprite ggLogoFrenzyTop;
	private Sprite ggLogoFrenzyBg;
	private Sprite ggRestartIcon;
	private SpriteParticleSystem particleSystemGameOver;
	private Sprite ggBg;
	//private V3InterfaceBox ggInterfaceBox = new V3InterfaceBox();
	private void drawGameOver(){
		mGameOverScene = new Scene();
		mGameOverScene.setBackgroundEnabled(false);
		mGameOverScene.setTouchAreaBindingOnActionDownEnabled(true);
		ggBg = new Sprite(15, 12, this.myButtonRegularTopRegion, this.getVertexBufferObjectManager());
		ggBg.setColor(1,0.973f,0.882f);
		ggBg.setAlpha(1f);
		ggBg.setScale(0f);
		ggBg.setX(CAMERA_WIDTH/2-ggBg.getWidth()/2);
		ggBg.setY(CAMERA_HEIGHT/2-ggBg.getHeight());
		mGameOverScene.attachChild(ggBg);

		int h2 = CAMERA_HEIGHT/2-490;
		ggText = new Text(140, h2/2 - 75, this.logoLargeFont, getString(R.string.GameOver), 50, this.getVertexBufferObjectManager());
		ggText.setX(CAMERA_WIDTH/2-ggText.getWidth()/2);
		ggText.setColor(0.2f,0.2f,0.2f);
		ggText.setAlpha(0);
		mGameOverScene.attachChild(ggText);
		
		/*ggInterfaceBox.setBuffer(getVertexBufferObjectManager());
		ggInterfaceBox.setShadowSprites(interfaceBoxShadow);
		ggInterfaceBox.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 10, 10);
		ggInterfaceBox.createBox(mGameOverScene);
		ggInterfaceBox.setColor(0.9f, 0.9f, 0.9f);*/
		
		/*ggPlayAgainBg = new Rectangle(0, CAMERA_HEIGHT/2+325, 690, 140, this.getVertexBufferObjectManager());
		ggPlayAgainBg.setX(CAMERA_WIDTH/2-ggPlayAgainBg.getWidth()/2);
		ggPlayAgainBg.setColor(colorManager.getColor(3));
		ggPlayAgainBg.setAlpha(0);
		ggPlayAgainBg.setScale(0);
		mGameOverScene.attachChild(ggPlayAgainBg);*/
		
		/*ggScoreBg = new Rectangle(0, CAMERA_HEIGHT/2-465, 690, 300, this.getVertexBufferObjectManager());
		ggScoreBg.setX(CAMERA_WIDTH/2-ggPlayAgainBg.getWidth()/2);
		ggScoreBg.setColor(0.82f,0.82f,0.825f);
		ggScoreBg.setAlpha(0);
		mGameOverScene.attachChild(ggScoreBg);*/


		int position = CAMERA_HEIGHT/2-400;
		ggScoreTopText = new Text(0, position-30, this.scoreLargeFont, getString(R.string.Score), 10, this.getVertexBufferObjectManager());
		ggScoreTopText.setColor(0.2f,0.2f,0.2f);
		ggScoreTopText.setX(CAMERA_WIDTH/2- ggScoreTopText.getWidth()/2);
		ggScoreTopText.setAlpha(0);
		ggScoreText = new Text(0, position, this.scoreXLargeFont, "6588", 10, this.getVertexBufferObjectManager());
		ggScoreText.setColor(0.2f,0.2f,0.2f);
		ggScoreText.setAlpha(0);

		ggBestScoreText = new Text(0, ggScoreText.getY()+160, this.scoreSmallFont, "YOUR BEST: 17000", 50, this.getVertexBufferObjectManager());
		ggBestScoreText.setColor(0.4f,0.4f,0.4f);
		ggBestScoreText.setAlpha(0);

		ggLogoBg = new Sprite(0, logoStartPostition+75, this.logo_bg, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ggLogoBg.setX(CAMERA_WIDTH/2-ggLogoBg.getWidth()/2);
		ggLogoTop = new Sprite(0, logoStartPostition+75+45, this.logo_top, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ggLogoTop.setX(CAMERA_WIDTH/2-logoTop.getWidth()/2+13);
		ggLogoBg.setAlpha(0);
		ggLogoTop.setAlpha(0);
		ggLogoBg.setVisible(false);
		ggLogoTop.setVisible(false);

		ggLogoFrenzyBg = new Sprite(0, logoStartPostition+75, this.logo_frenzy_bg, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ggLogoFrenzyBg.setX(CAMERA_WIDTH/2-ggLogoFrenzyBg.getWidth()/2);
		ggLogoFrenzyTop = new Sprite(0, logoStartPostition+75+94, this.logo_frenzy_top, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		ggLogoFrenzyTop.setX(CAMERA_WIDTH/2-ggLogoFrenzyTop.getWidth()/2+1);
		ggLogoFrenzyBg.setAlpha(0);
		ggLogoFrenzyTop.setAlpha(0);
		ggLogoFrenzyBg.setVisible(false);
		ggLogoFrenzyTop.setVisible(false);


		ggLogoTextBig = new Text(140, 23, this.scoreLargeFont, getString(R.string.PlayAgain), 50, this.getVertexBufferObjectManager());
		ggLogoTextBig.setColor(colorManager.getColor(15));
		ggLogoTextBig.setColor(0.2f,0.2f,0.2f);
		ggLogoTextBig.setX(CAMERA_WIDTH/2-ggLogoTextBig.getWidth()/2);
		//ggLogoTextBig.setY(ggPlayAgainBg.getY()+ggPlayAgainBg.getHeight()/2-ggLogoTextBig.getHeight()/2);
		ggLogoTextBig.setY(ggLogoFrenzyBg.getY()+ggLogoFrenzyBg.getHeight()+30);

		ggLogoTextBig.setAlpha(0);



		ggRestartIcon = new Sprite(0, logoStartPostition+50, this.logo_restart_icon, this.getVertexBufferObjectManager());
		ggRestartIcon.setY(ggLogoTop.getY()+5);
		ggRestartIcon.setX(CAMERA_WIDTH/2 - ggRestartIcon.getWidth()/2);
		final LoopEntityModifier rotation = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(0.5f, 0,360,EaseCubicInOut.getInstance()),new DelayModifier(2f)),-1);
		ggRestartIcon.registerEntityModifier(rotation);
		ggRestartIcon.setAlpha(0);
		ggRestartIcon.setVisible(false);


		int h = CAMERA_HEIGHT - (CAMERA_HEIGHT/2+490);
		createInterfaceButton(gameOverButtonArray, mGameOverScene, 464-290, CAMERA_HEIGHT-h/2+40, myLeaderboardIcon, getString(R.string.Online), 7,0,-2);
		createInterfaceButton(gameOverButtonArray, mGameOverScene, 464, CAMERA_HEIGHT-h/2+40, myHomeIcon, getString(R.string.ReturnHome), 13,0,-2);
		createInterfaceButton(gameOverButtonArray, mGameOverScene, 464+290, CAMERA_HEIGHT-h/2+40, myShareIcon, getString(R.string.ShareGame), 14,-3,0);

		mGameOverScene.attachChild(ggScoreTopText);
		mGameOverScene.attachChild(ggScoreText);
		mGameOverScene.attachChild(ggBestScoreText);
		mGameOverScene.attachChild(ggLogoBg);
		mGameOverScene.attachChild(ggLogoFrenzyBg);
		mGameOverScene.attachChild(ggRestartIcon);
		mGameOverScene.attachChild(ggLogoTop);
		mGameOverScene.attachChild(ggLogoFrenzyTop);
		mGameOverScene.attachChild(ggLogoTextBig);
		addParticeleGameOver();

		ggTouch0 = new Rectangle(CAMERA_WIDTH/2-370, CAMERA_HEIGHT/2 - 490, 740, 980, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:

						playMenuButtonSound();
						ggLogoTop.setScale(0.9f);
						ggLogoFrenzyTop.setScale(0.9f);
						ggLogoTextBig.setColor(0.2f,0.2f,0.2f);
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						ggLogoTop.setScale(1f);
						ggLogoFrenzyTop.setScale(1f);
						//ggLogoTextBig.setColor(colorManager.getColor(15));
						Game.this.runOnUpdateThread(new Runnable() {

							@Override
							public void run() {
								for(int i=0;i<lineArray.size();i++){
									lineArray.get(i).removeLine();

								}
								lineArray.clear();
								loadNewGame();
								startGame = false;
								gameManager.resetGameState();

								updateScoreTextValue();
								int top = getTopLineIndex();
								touchIcon.moveTouchIcon(lineArray.get(top).getX()+lineArray.get(top).getWidth()/2, lineArray.get(top).getY()+lineArray.get(top).getHeight()/2);
								touchIcon.showTouchIcon();
							}
						});
						Log.d("GAMEOVER", "PRESS 0");
						animateOutGameOver(0,true);
						if((showTutorial && GAMETYPE == 1)||(showTutorialFrenzy && GAMETYPE == 2)){
							animateInTutorial(0.0f);
						}
						stopMainMenuMusic();
						break;
				}

				return true;
			}

		};
		ggTouch1 = new Rectangle(gameOverButtonArray.get(0).getX(), gameOverButtonArray.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						gameOverButtonArray.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						gameOverButtonArray.get(0).onRelease(false);
						if(mHelper.isSignedIn()){

							Game.this.runOnUiThread(new Runnable() {
								//@Overrides
								public void run() {
									Game.this.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()), 5001);
								}
							});

						}
						else{
							beginUserInitiatedSignIn();
						}

						//animateInMainMenu(0.2f,false);


						break;
				}

				return true;
			}

		};
		ggTouch2 = new Rectangle(gameOverButtonArray.get(1).getX(), gameOverButtonArray.get(1).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						gameOverButtonArray.get(1).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						gameOverButtonArray.get(1).onRelease(false);
						animateOutGameOver(0,false);
						break;
				}

				return true;
			}

		};
		ggTouch3 = new Rectangle(gameOverButtonArray.get(2).getX(), gameOverButtonArray.get(2).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						gameOverButtonArray.get(2).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						gameOverButtonArray.get(2).onRelease(false);
						animateInShareDialog(0);
						break;
				}

				return true;
			}

		};


	}
	private Boolean bestScoreBoxVisible = false;
	private void animateInGameOver(float delay, Boolean scene){
		//Log.d("GAMEOVER", "ANIMATING IN");
		//scoreTextValue.setVisible(false);
		//scoreTextValueShade.setVisible(false);

		playMainMenuMusic();
		stopLinesweeperMusic();
		stopFrenzyMusic();

		if(mInterstitial.isReady()){
			mInterstitial.show();
		}


		//showInMobiLoaded();

		//showBatchAd();
		if(scene){
			mGameScene.setChildScene(mGameOverScene, false, true, true);
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ScaleModifier(0.3f, 0,50,EaseCubicIn.getInstance())),1);

			ggBg.registerEntityModifier(scale);
		}
		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.05f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		//ggInterfaceBox.morphBox(delay, 740, 980, 3);
		//ggInterfaceBox.changeColor(delay, 6);
		if(GAMETYPE == 1){
			ggLogoBg.setVisible(true);
			ggLogoTop.setVisible(true);
			ggRestartIcon.setVisible(true);
			particleSystemGameOver.setParticlesSpawnEnabled(false);
			ggLogoFrenzyBg.setVisible(false);
			ggLogoFrenzyTop.setVisible(false);
			//ggPlayAgainBg.setColor(colorManager.getColor(3));
		}
		if(GAMETYPE == 2){
			ggLogoFrenzyBg.setVisible(true);
			ggLogoFrenzyTop.setVisible(true);
			ggRestartIcon.setVisible(true);
			ggLogoBg.setVisible(false);
			ggLogoTop.setVisible(false);
			particleSystemGameOver.setParticlesSpawnEnabled(false);
			//ggPlayAgainBg.setColor(colorManager.getColor(7));

		}
		ggText.clearEntityModifiers();
		ggLogoBg.clearEntityModifiers();
		ggLogoTop.clearEntityModifiers();
		ggLogoFrenzyBg.clearEntityModifiers();
		ggLogoFrenzyTop.clearEntityModifiers();
		ggRestartIcon.clearEntityModifiers();
		//ggPlayAgainBg.clearEntityModifiers();
		//ggScoreBg.clearEntityModifiers();

		final LoopEntityModifier rotation = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(0.5f, 0,360,EaseCubicInOut.getInstance()),new DelayModifier(2f)),-1);
		ggRestartIcon.registerEntityModifier(rotation);

		ggText.registerEntityModifier(scale0.deepCopy());
		ggLogoBg.registerEntityModifier(scale0.deepCopy());
		ggLogoTop.registerEntityModifier(scale1.deepCopy());
		ggLogoFrenzyBg.registerEntityModifier(scale0.deepCopy());
		ggLogoFrenzyTop.registerEntityModifier(scale1.deepCopy());
		ggRestartIcon.registerEntityModifier(scale1.deepCopy());
		//ggPlayAgainBg.registerEntityModifier(scale0.deepCopy());
		//ggScoreBg.registerEntityModifier(scale0.deepCopy());
		gameOverButtonArray.get(0).animateIn(delay);
		gameOverButtonArray.get(1).animateIn(delay);
		gameOverButtonArray.get(2).animateIn(delay);

		if(GAMETYPE == 1){
			if(mHelper.isSignedIn()){
				Games.Leaderboards.submitScore(getApiClient(),getString(R.string.leaderboard_linesweeper), gameManager.getScore());
			}
			ggScoreText.setText(String.valueOf(gameManager.getScore()));

		}
		if(GAMETYPE == 2){
			ggScoreText.setText(String.valueOf(gameManager.getScore()));
			if(mHelper.isSignedIn()){
				Games.Leaderboards.submitScore(getApiClient(),getString(R.string.leaderboard_time_frenzy), gameManager.getScore());
			}
		}
		//ggBestScoreText.setText(String.valueOf(gameManager.getBestScore(GAMETYPE)));
		//Log.d("GAMETYPE", String.valueOf(GAMETYPE));
		ggBestScoreText.setText(String.valueOf(getString(R.string.BestScore)+": "+gameManager.getBestScore(GAMETYPE)));
		if(GAMETYPE == 1){
			bestScoreValue.setText(String.valueOf(gameManager.getBestScore(GAMETYPE)));
			bestScoreValue.setX(CAMERA_WIDTH/2-bestScoreValue.getWidth()/2);
		}

		if((gameManager.getScore()<gameManager.getBestScore(GAMETYPE))){
			bestScoreBoxVisible = false;
		}
		if(GAMETYPE == 2){
			//ggBestScoreText.setText(String.valueOf(getString(R.string.BestScore)+" "+gameManager.getBestTime()));
			bestScoreValueFrenzy.setText(String.valueOf(gameManager.getBestScore(GAMETYPE)));
			bestScoreValueFrenzy.setX(CAMERA_WIDTH/2-bestScoreValueFrenzy.getWidth()/2+slideAmount);
		}

		ggScoreText.setX(CAMERA_WIDTH/2-ggScoreText.getWidth()/2);

		ggBestScoreText.setX(CAMERA_WIDTH/2-ggBestScoreText.getWidth()/2);
		final LoopEntityModifier scaleIn = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.2f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		scaleIn.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				mGameOverScene.registerTouchArea(ggTouch0);
				mGameOverScene.registerTouchArea(ggTouch1);
				mGameOverScene.registerTouchArea(ggTouch2);
				mGameOverScene.registerTouchArea(ggTouch3);

				if((gameManager.getScore()>gameStartBest && gameStartBest !=0 && !twitterLogin) || (twitterLogin && twitterLocation ==1)){

					animateInBestScoreBox(0);
					twitterLogin = false;
				}
				if(twitterLogin && twitterLocation ==2){
					animateInShareDialog(0);
					twitterLogin = false;

				}

			}
		});

		ggScoreText.clearEntityModifiers();
		ggScoreTopText.clearEntityModifiers();
		ggBestScoreText.clearEntityModifiers();
		ggLogoTextBig.clearEntityModifiers();

		ggScoreText.registerEntityModifier(scaleIn);
		ggScoreTopText.registerEntityModifier(scaleIn.deepCopy());
		ggBestScoreText.registerEntityModifier(scaleIn.deepCopy());
		ggLogoTextBig.registerEntityModifier(scaleIn.deepCopy());



		//ggGameOverText.registerEntityModifier(scaleIn.deepCopy());
		//interfaceBoxes.get(0).changeColor(0.1f,1);
	}
	private void animateOutGameOver(float delay, Boolean scene){
		mGameOverScene.unregisterTouchArea(ggTouch0);
		mGameOverScene.unregisterTouchArea(ggTouch1);
		mGameOverScene.unregisterTouchArea(ggTouch2);
		mGameOverScene.unregisterTouchArea(ggTouch3);
		mInterstitial.load();
		//ggInterfaceBox.morphBox(delay, 10, 10, 15);
		//Log.d("GAMEOVER", "ANIMATING OUT");
		if(scene){
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.5f, 50,0,EaseCubicIn.getInstance())),1);
			scale.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					mGameScene.clearChildScene();
					//animateInCountdown();
				}
			});


			ggBg.registerEntityModifier(scale);
		}
		else{
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.5f)),1);
			scale.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					mGameScene.clearChildScene();
					animateInMainMenu(0f,false);
				}
			});


			ggBg.registerEntityModifier(scale);
		}

		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		scale1.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				ggLogoBg.setVisible(false);
				ggLogoTop.setVisible(false);
				ggRestartIcon.setVisible(false);
			}
		});


		ggText.registerEntityModifier(scale0.deepCopy());
		ggLogoBg.registerEntityModifier(scale0.deepCopy());
		ggLogoTop.registerEntityModifier(scale1.deepCopy());
		ggLogoFrenzyBg.registerEntityModifier(scale0.deepCopy());
		ggLogoFrenzyTop.registerEntityModifier(scale1.deepCopy());
		ggRestartIcon.registerEntityModifier(scale1);
		//ggPlayAgainBg.registerEntityModifier(scale1.deepCopy());
		//ggScoreBg.registerEntityModifier(scale1.deepCopy());
		final LoopEntityModifier scaleOut = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0f),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);


		ggScoreText.registerEntityModifier(scaleOut.deepCopy());
		ggScoreTopText.registerEntityModifier(scaleOut.deepCopy());
		ggBestScoreText.registerEntityModifier(scaleOut.deepCopy());
		ggLogoTextBig.registerEntityModifier(scaleOut.deepCopy());

		//ggGameOverText.registerEntityModifier(scaleOut.deepCopy());

		gameOverButtonArray.get(0).animateOut(delay);
		gameOverButtonArray.get(1).animateOut(delay);
		gameOverButtonArray.get(2).animateOut(delay);


		particleSystemGameOver.setParticlesSpawnEnabled(false);

	}












	private V3InterfaceBox ggBestScoreBox = new V3InterfaceBox();
	private Text ggBestHeader;
	private Text ggBestDescription1;
	private Text ggBestShareHeader;
	//private Text ggBestShareDescription1;
	private Sprite ggBestLeftStars;
	private Sprite ggBestRightStars;
	private Sprite ggBestTrophy;
	private Rectangle ggBestTouch0;
	private Rectangle ggBestTouch1;
	private Rectangle ggBestTouch2;
	private Rectangle ggBestTouch3;
	private Sprite ggBestBg;
	private ArrayList<V3InterfaceButton> bestScoreButtonArray = new ArrayList<V3InterfaceButton>();
	private void drawBestScore(){
		mBestScoreScene = new Scene();
		mBestScoreScene.setBackgroundEnabled(false);
		mBestScoreScene.setTouchAreaBindingOnActionDownEnabled(true);

		ggBestBg = new Sprite(15, 12, this.myButtonRegularTopRegion, this.getVertexBufferObjectManager());
		ggBestBg.setColor(0.2f,0.2f,0.2f);
		ggBestBg.setAlpha(0.5f);
		ggBestBg.setScale(50f);
		ggBestBg.setX(CAMERA_WIDTH/2-interfaceBg.getWidth()/2);
		ggBestBg.setY(CAMERA_HEIGHT/2-interfaceBg.getHeight());
		mBestScoreScene.attachChild(ggBestBg);

		ggBestScoreBox.setBuffer(getVertexBufferObjectManager());
		ggBestScoreBox.setShadowSprites(interfaceBoxShadow);
		ggBestScoreBox.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 10, 10);
		ggBestScoreBox.createBox(mBestScoreScene);
		ggBestScoreBox.setColor(colorManager.getColor(15));

		ggBestLeftStars = new Sprite(0, CAMERA_HEIGHT/2-420, this.myStarsLeftRegion, this.getVertexBufferObjectManager());

		ggBestLeftStars.setRotationCenterX(ggBestLeftStars.getWidth());
		ggBestLeftStars.setRotation(-6);
		//ggBestLeftStars.setAlpha(0);


		ggBestRightStars = new Sprite(0, CAMERA_HEIGHT/2-420, this.myStarsRightRegion, this.getVertexBufferObjectManager());

		ggBestRightStars.setRotationCenterX(0);
		ggBestRightStars.setRotation(6);
		//ggBestRightStars.setAlpha(0);


		ggBestTrophy = new Sprite(0, CAMERA_HEIGHT/2-410, this.myTrophyRegion, this.getVertexBufferObjectManager());
		ggBestTrophy.setX(CAMERA_WIDTH/2 - ggBestTrophy.getWidth()/2);
		ggBestLeftStars.setX(CAMERA_WIDTH/2 - ggBestTrophy.getWidth()/2-40);
		ggBestRightStars.setX(CAMERA_WIDTH/2 - ggBestTrophy.getWidth()/2+20);
		//ggBestTrophy.setAlpha(0);



		ggBestHeader = new Text(180, ggBestTrophy.getY()+ggBestTrophy.getHeight(), this.logoLargeFont, getString(R.string.Amazing), 50, this.getVertexBufferObjectManager());
		ggBestHeader.setColor(0.2f,0.2f,0.2f);
		ggBestHeader.setX(CAMERA_WIDTH/2 - ggBestHeader.getWidth()/2);

		ggBestDescription1 = new Text(230, ggBestHeader.getY()+ggBestHeader.getHeight()+5, this.scoreSmallFont, getString(R.string.AmazingDescription), 100, this.getVertexBufferObjectManager());
		ggBestDescription1.setColor(0.4f,0.4f,0.4f);

		ggBestShareHeader = new Text(230, ggBestDescription1.getY()+ggBestDescription1.getHeight()+80, this.scoreLargeFont, getString(R.string.ShareResult), 50, this.getVertexBufferObjectManager());
		ggBestShareHeader.setColor(0.2f,0.2f,0.2f);

		//ggBestShareDescription1 = new Text(230, ggBestShareHeader.getY()+ggBestShareHeader.getHeight()+5, this.scoreSmallFont, getString(R.string.ChallengeFriends), 100, this.getVertexBufferObjectManager());
		//ggBestShareDescription1.setColor(0.4f,0.4f,0.4f);

		createInterfaceButton(bestScoreButtonArray, mBestScoreScene, 464-250, CAMERA_HEIGHT/2+370, myTwitterIcon, getString(R.string.Twitter), 2,0,0);
		createInterfaceButton(bestScoreButtonArray, mBestScoreScene, 464, CAMERA_HEIGHT/2+370, myShareIcon, getString(R.string.OtherApp), 14,-2,0);
		createInterfaceButton(bestScoreButtonArray, mBestScoreScene, 464+250, CAMERA_HEIGHT/2+370, myFacebookIcon, getString(R.string.Facebook), 1,0,3);
		createInterfaceButton(bestScoreButtonArray, mBestScoreScene, 865, CAMERA_HEIGHT/2-395, myNoIcon, "", 13,0,0);



		ggBestLeftStars.setAlpha(0);
		ggBestRightStars.setAlpha(0);
		ggBestTrophy.setAlpha(0);
		ggBestHeader.setAlpha(0);
		ggBestDescription1.setAlpha(0);
		ggBestShareHeader.setAlpha(0);
		//ggBestShareDescription1.setAlpha(0);
		mBestScoreScene.attachChild(ggBestLeftStars);
		mBestScoreScene.attachChild(ggBestRightStars);
		mBestScoreScene.attachChild(ggBestTrophy);
		mBestScoreScene.attachChild(ggBestHeader);
		mBestScoreScene.attachChild(ggBestDescription1);
		mBestScoreScene.attachChild(ggBestShareHeader);
		//mBestScoreScene.attachChild(ggBestShareDescription1);


		ggBestTouch0 = new Rectangle(bestScoreButtonArray.get(0).getX(), bestScoreButtonArray.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						bestScoreButtonArray.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						bestScoreButtonArray.get(0).onRelease(false);
						logIn(1);
						break;
				}

				return true;
			}

		};
		ggBestTouch1 = new Rectangle(bestScoreButtonArray.get(1).getX(), bestScoreButtonArray.get(1).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						bestScoreButtonArray.get(1).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						bestScoreButtonArray.get(1).onRelease(false);
						Intent tweetIntent = new Intent(Intent.ACTION_SEND);
						tweetIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.FacebookDescription)+" " +String.valueOf(gameManager.getScore())+" "+getString(R.string.FacebookDescription1));
						tweetIntent.setType("text/plain");
						startActivity(tweetIntent);
						break;
				}

				return true;
			}

		};
		ggBestTouch2 = new Rectangle(bestScoreButtonArray.get(2).getX(), bestScoreButtonArray.get(2).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						bestScoreButtonArray.get(2).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						bestScoreButtonArray.get(2).onRelease(false);
						/*ShareLinkContent content = new ShareLinkContent.Builder()
				        .setContentUrl(Uri.parse("https://developers.facebook.com"))
				        .setContentTitle("HELLO WORLD")
				        .setContentDescription("THIS IS A TEST")
				        .build();
						//ShareApi.share(content, null);
						ShareDialog.show(.Game, content);*/
						postFaceBook();

						break;
				}

				return true;
			}

		};
		ggBestTouch3 = new Rectangle(bestScoreButtonArray.get(3).getX(), bestScoreButtonArray.get(3).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						bestScoreButtonArray.get(3).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						bestScoreButtonArray.get(3).onRelease(false);
						animateOutBestScoreBox(0);

						break;
				}

				return true;
			}

		};

	}
	private void animateInBestScoreBox(float delay){
		mGameOverScene.setChildScene(mBestScoreScene, false, true, true);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.05f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.4f, 0,0.7f,EaseCubicIn.getInstance())),1);
		ggBestBg.registerEntityModifier(scale);
		ggBestLeftStars.registerEntityModifier(scale0.deepCopy());
		ggBestRightStars.registerEntityModifier(scale0.deepCopy());
		ggBestTrophy.registerEntityModifier(scale1.deepCopy());
		ggBestHeader.registerEntityModifier(scale1.deepCopy());
		ggBestDescription1.registerEntityModifier(scale1.deepCopy());
		ggBestShareHeader.registerEntityModifier(scale1.deepCopy());
		//ggBestShareDescription1.registerEntityModifier(scale1.deepCopy());
		bestScoreButtonArray.get(0).animateIn(0);
		bestScoreButtonArray.get(1).animateIn(0);
		bestScoreButtonArray.get(2).animateIn(0);
		bestScoreButtonArray.get(3).animateIn(0);
		ggBestScoreBox.morphBox(delay, 800, 950, 15);

		mBestScoreScene.registerTouchArea(ggBestTouch0);
		mBestScoreScene.registerTouchArea(ggBestTouch1);
		mBestScoreScene.registerTouchArea(ggBestTouch2);
		mBestScoreScene.registerTouchArea(ggBestTouch3);
	}
	private void animateOutBestScoreBox(float delay){
		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.4f,0.7f,0.0f,EaseCubicIn.getInstance())),1);
		scale.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				mGameOverScene.clearChildScene();
			}
		});
		ggBestBg.registerEntityModifier(scale);
		ggBestLeftStars.registerEntityModifier(scale0.deepCopy());
		ggBestRightStars.registerEntityModifier(scale0.deepCopy());
		ggBestTrophy.registerEntityModifier(scale1.deepCopy());
		ggBestHeader.registerEntityModifier(scale1.deepCopy());
		ggBestDescription1.registerEntityModifier(scale1.deepCopy());
		ggBestShareHeader.registerEntityModifier(scale1.deepCopy());
		//ggBestShareDescription1.registerEntityModifier(scale1.deepCopy());
		bestScoreButtonArray.get(0).animateOut(0);
		bestScoreButtonArray.get(1).animateOut(0);
		bestScoreButtonArray.get(2).animateOut(0);
		bestScoreButtonArray.get(3).animateOut(0);
		ggBestScoreBox.morphBox(delay, 10, 10, 15);

		mBestScoreScene.unregisterTouchArea(ggBestTouch0);
		mBestScoreScene.unregisterTouchArea(ggBestTouch1);
		mBestScoreScene.unregisterTouchArea(ggBestTouch2);
		mBestScoreScene.unregisterTouchArea(ggBestTouch3);
		//mGameOverScene.clearChildScene();
	}










	private Text countdown3Text;
	private Text countdown2Text;
	private Text countdown1Text;
	private Rectangle countdownBg;
	private void drawCountdown(){
		mCountdownScene = new Scene();
		mCountdownScene.setBackgroundEnabled(false);
		mCountdownScene.setTouchAreaBindingOnActionDownEnabled(true);
		countdownBg = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		countdownBg.setColor(1,0.973f,0.882f);
		countdownBg.setAlpha(1);

		countdown1Text= new Text(0, 0, this.scoreXLargeFont, "1", 1, this.getVertexBufferObjectManager());
		countdown2Text= new Text(0, 0, this.scoreXLargeFont, "2", 1, this.getVertexBufferObjectManager());
		countdown3Text= new Text(0, 0, this.scoreXLargeFont, "3", 1, this.getVertexBufferObjectManager());
		countdown1Text.setColor(0.2f,0.2f,0.2f);
		countdown2Text.setColor(0.2f,0.2f,0.2f);
		countdown3Text.setColor(0.2f,0.2f,0.2f);
		countdown1Text.setAlpha(0);
		countdown2Text.setAlpha(0);
		countdown3Text.setAlpha(0);
		countdown1Text.setX(CAMERA_WIDTH/2 - countdown1Text.getWidth()/2);
		countdown2Text.setX(CAMERA_WIDTH/2 - countdown2Text.getWidth()/2);
		countdown3Text.setX(CAMERA_WIDTH/2 - countdown3Text.getWidth()/2);

		countdown1Text.setY(CAMERA_HEIGHT/2 - countdown1Text.getHeight()/2);
		countdown2Text.setY(CAMERA_HEIGHT/2 - countdown2Text.getHeight()/2);
		countdown3Text.setY(CAMERA_HEIGHT/2 - countdown3Text.getHeight()/2);

		mCountdownScene.attachChild(countdownBg);
		mCountdownScene.attachChild(countdown1Text);
		mCountdownScene.attachChild(countdown2Text);
		mCountdownScene.attachChild(countdown3Text);
	}
	private void animateInCountdown(){
		startGame = false;
		countdown1Text.clearEntityModifiers();
		countdown2Text.clearEntityModifiers();
		countdown3Text.clearEntityModifiers();

		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(1.6f),new ParallelEntityModifier(new AlphaModifier(0.1f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.1f, 0,2,EaseCubicInOut.getInstance())),new ParallelEntityModifier(new AlphaModifier(0.9f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.9f, 2,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale2 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.8f),new ParallelEntityModifier(new AlphaModifier(0.1f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.1f, 0,2,EaseCubicInOut.getInstance())),new ParallelEntityModifier(new AlphaModifier(0.9f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.9f, 2,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale3 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0),new ParallelEntityModifier(new AlphaModifier(0.1f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.1f, 0,2,EaseCubicInOut.getInstance())),new ParallelEntityModifier(new AlphaModifier(0.9f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.9f, 2,0,EaseCubicInOut.getInstance()))),1);
		scale1.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				playCountDownSound();
				mGameScene.clearChildScene();
				startGame = true;
				countdown1Text.setAlpha(0);
				countdown2Text.setAlpha(0);
				countdown3Text.setAlpha(0);
			}
		});
		scale2.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				//playCountDownSound();
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				playCountDownSound();
			}
		});
		scale3.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				playCountDownSound();
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				playCountDownSound();
			}
		});

		final LoopEntityModifier wait = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.05f)),1);
		wait.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				mGameScene.setChildScene(mCountdownScene, false, true, true);
				countdown1Text.registerEntityModifier(scale1);
				countdown2Text.registerEntityModifier(scale2);
				countdown3Text.registerEntityModifier(scale3);
			}
		});

		scoreTextValue.registerEntityModifier(wait);
	}





	protected Scene ggShareScene;
	private Entity ggShareDialog= new Entity();
	private V3InterfaceBox ggShareBox = new V3InterfaceBox();
	private Text ggShareHeader;
	private Rectangle ggShareBackground;
	private ArrayList<V3InterfaceButton> ggShareButtons = new ArrayList<V3InterfaceButton>();

	private Rectangle ggShareTouch0;
	private Rectangle ggShareTouch1;
	private Rectangle ggShareTouch2;
	private Rectangle ggShareTouch3;
	private void drawGGShareDialog(){
		ggShareScene = new Scene();
		ggShareScene.setBackgroundEnabled(false);
		ggShareScene.setTouchAreaBindingOnActionDownEnabled(true);
		ggShareBackground = new Rectangle(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, this.getVertexBufferObjectManager());
		ggShareBackground.setColor(0.2f,0.2f,0.2f);
		ggShareBackground.setAlpha(0f);

		ggShareScene.attachChild(ggShareBackground);
		ggShareBox.setBuffer(getVertexBufferObjectManager());
		ggShareBox.setShadowSprites(interfaceBoxShadow);
		ggShareBox.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT-175, 800, 350);
		ggShareBox.createBox(ggShareDialog);
		ggShareBox.setColor(colorManager.getColor(15));

		ggShareHeader= new Text(200, CAMERA_HEIGHT-330, this.scoreLargeFont, getString(R.string.ShareVia), 50, this.getVertexBufferObjectManager());
		ggShareHeader.setColor(0.2f,0.2f,0.2f);
		ggShareDialog.attachChild(ggShareHeader);

		createInterfaceButton(ggShareButtons, ggShareDialog, 464-250, CAMERA_HEIGHT-80, myTwitterIcon, getString(R.string.Twitter), 2,0,0);
		createInterfaceButton(ggShareButtons, ggShareDialog, 464, CAMERA_HEIGHT-80, myFacebookIcon, getString(R.string.Facebook), 1,0,3);
		createInterfaceButton(ggShareButtons, ggShareDialog, 464+250, CAMERA_HEIGHT-80, myShareIcon, getString(R.string.OtherApp), 14,-2,0);
		createInterfaceButton(ggShareButtons, ggShareDialog, 865, CAMERA_HEIGHT-275, myNoIcon, "", 13,0,0);

		ggShareDialog.setY(400);

		ggShareScene.attachChild(ggShareDialog);

		ggShareTouch0 = new Rectangle(ggShareButtons.get(0).getX(), ggShareButtons.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						ggShareButtons.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						ggShareButtons.get(0).onRelease(false);
						logIn(2);
						break;
				}

				return true;
			}

		};
		ggShareTouch1 = new Rectangle(ggShareButtons.get(1).getX(), ggShareButtons.get(1).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						ggShareButtons.get(1).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						ggShareButtons.get(1).onRelease(false);
						postFaceBook();
						break;
				}

				return true;
			}

		};
		ggShareTouch2 = new Rectangle(ggShareButtons.get(2).getX(), ggShareButtons.get(2).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						ggShareButtons.get(2).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						ggShareButtons.get(2).onRelease(false);
						/*ShareLinkContent content = new ShareLinkContent.Builder()
				        .setContentUrl(Uri.parse("https://developers.facebook.com"))
				        .setContentTitle("HELLO WORLD")
				        .setContentDescription("THIS IS A TEST")
				        .build();
						//ShareApi.share(content, null);
						ShareDialog.show(.Game, content);*/
						Intent tweetIntent = new Intent(Intent.ACTION_SEND);
						tweetIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.FacebookDescription)+" " +String.valueOf(gameManager.getScore())+" "+getString(R.string.FacebookDescription1));
						tweetIntent.setType("text/plain");
						startActivity(tweetIntent);


						break;
				}

				return true;
			}

		};
		ggShareTouch3 = new Rectangle(ggShareButtons.get(3).getX(), ggShareButtons.get(3).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						ggShareButtons.get(3).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						ggShareButtons.get(3).onRelease(false);
						animateOutShareDialog(0);

						break;
				}

				return true;
			}

		};
	}
	private void animateInShareDialog(float delay){
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0,0.7f,EaseCubicIn.getInstance())),1);
		ggShareBackground.registerEntityModifier(scale);
		final LoopEntityModifier move = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.2f, 0,0,400,0,EaseCubicIn.getInstance())),1);
		ggShareDialog.registerEntityModifier(move);
		ggShareButtons.get(0).animateIn(0);
		ggShareButtons.get(1).animateIn(0);
		ggShareButtons.get(2).animateIn(0);
		ggShareButtons.get(3).animateIn(0);
		mGameOverScene.setChildScene(ggShareScene, false, true, true);
		ggShareScene.registerTouchArea(ggShareTouch0);
		ggShareScene.registerTouchArea(ggShareTouch1);
		ggShareScene.registerTouchArea(ggShareTouch2);
		ggShareScene.registerTouchArea(ggShareTouch3);
	}
	private void animateOutShareDialog(float delay){
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.4f,0.7f,0.0f,EaseCubicIn.getInstance())),1);
		scale.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				mGameOverScene.clearChildScene();
			}
		});
		ggShareBackground.registerEntityModifier(scale);
		final LoopEntityModifier move = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.2f, 0,0,0,400,EaseCubicOut.getInstance())),1);
		ggShareDialog.registerEntityModifier(move);
		ggShareButtons.get(0).animateOut(0);
		ggShareButtons.get(1).animateOut(0);
		ggShareButtons.get(2).animateOut(0);
		ggShareButtons.get(3).animateOut(0);
		ggShareScene.unregisterTouchArea(ggShareTouch0);
		ggShareScene.unregisterTouchArea(ggShareTouch1);
		ggShareScene.unregisterTouchArea(ggShareTouch2);
		ggShareScene.unregisterTouchArea(ggShareTouch3);
	}

	private Text settingsHeader;
	private Rectangle settingsTouch0;
	private Rectangle settingsTouch1;
	private Rectangle settingsTouch2;
	private Rectangle settingsTouch3;
	private Rectangle settingsTouch4;
	private Rectangle settingsReturnTouch;
	private Boolean settings = false;
	private ArrayList<V3InterfaceToggleButton> settingsToggleButtons = new ArrayList<V3InterfaceToggleButton>();
	private ArrayList<V3InterfaceButton> settingsButtons = new ArrayList<V3InterfaceButton>();
	private void drawSettings(){
		int spacer = 135;

		settingsHeader = new Text(140, appNameText.getY(), this.logoLargeFont, getString(R.string.SettingsHeader), 50, this.getVertexBufferObjectManager());
		settingsHeader.setX(CAMERA_WIDTH/2 -settingsHeader.getWidth()/2);
		settingsHeader.setColor(0.2f,0.2f,0.2f);
		settingsHeader.setAlpha(0);
		mInterfaceScene.attachChild(settingsHeader);
		createToggleButton(settingsToggleButtons, CAMERA_WIDTH/2,CAMERA_HEIGHT/2-2*spacer,getString(R.string.MusicOn),true);
		createToggleButton(settingsToggleButtons, CAMERA_WIDTH/2,CAMERA_HEIGHT/2-spacer,getString(R.string.SoundOn),true);
		createToggleButton(settingsToggleButtons, CAMERA_WIDTH/2,CAMERA_HEIGHT/2,getString(R.string.ImmersiveOn),true);
		createToggleButton(settingsToggleButtons, CAMERA_WIDTH/2,CAMERA_HEIGHT/2+spacer,getString(R.string.BGSpinOn),true);
		createToggleButton(settingsToggleButtons, CAMERA_WIDTH/2,CAMERA_HEIGHT/2+2*spacer,getString(R.string.ZoomOn),true);
		int bottomHeight = CAMERA_HEIGHT - (CAMERA_HEIGHT/2+boxHeight/2);
		createInterfaceButton(settingsButtons,mInterfaceScene,464,CAMERA_HEIGHT-bottomHeight/2+40,myHomeIcon,getString(R.string.ReturnHome),13,0,0);

		settingsTouch0 = new Rectangle(CAMERA_WIDTH/2-350, CAMERA_HEIGHT/2-50-2*spacer, 700, 100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsToggleButtons.get(0).onPress();
						playMenuButtonSound();
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						music = !music;
						if(music == false){
							settingsToggleButtons.get(0).deactivateButton();
							settingsToggleButtons.get(0).setText(getString(R.string.MusicOff));
							stopMainMenuMusic();
							//stopMusic();
						}
						if(music == true){
							settingsToggleButtons.get(0).activateButton();
							settingsToggleButtons.get(0).setText(getString(R.string.MusicOn));
							playMainMenuMusic();
							//playMusic();
						}
						prefEditor.putBoolean("music", music);
						prefEditor.commit();
						settingsToggleButtons.get(0).onRelease();
						break;
				}

				return true;
			}

		};
		settingsTouch1 = new Rectangle(CAMERA_WIDTH/2-350, CAMERA_HEIGHT/2-50-spacer, 700, 100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsToggleButtons.get(1).onPress();
						playMenuButtonSound();
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						sound = !sound;
						if(sound == false){
							settingsToggleButtons.get(1).deactivateButton();
							settingsToggleButtons.get(1).setText(getString(R.string.SoundOff));
							//stopMusic();
						}
						if(music == true){
							settingsToggleButtons.get(1).activateButton();
							settingsToggleButtons.get(1).setText(getString(R.string.SoundOn));
							//playMusic();
						}
						prefEditor.putBoolean("sound", sound);
						prefEditor.commit();
						settingsToggleButtons.get(1).onRelease();
						break;
				}

				return true;
			}

		};
		settingsTouch2 = new Rectangle(CAMERA_WIDTH/2-350, CAMERA_HEIGHT/2-50, 700, 100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsToggleButtons.get(2).onPress();
						playMenuButtonSound();
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						immersive = !immersive;
						if(immersive == false){
							settingsToggleButtons.get(2).deactivateButton();
							settingsToggleButtons.get(2).setText(getString(R.string.ImmersiveOff));
							//stopMusic();
						}
						if(immersive == true){
							settingsToggleButtons.get(2).activateButton();
							settingsToggleButtons.get(2).setText(getString(R.string.ImmersiveOn));
							//playMusic();
						}
						prefEditor.putBoolean("immersive", immersive);
						prefEditor.commit();
						Intent startActivity = new Intent(Game.this, Game.class);
						Game.this.startActivity(startActivity);
						finish();
						settingsToggleButtons.get(2).onRelease();
						break;
				}

				return true;
			}

		};
		settingsTouch3 = new Rectangle(CAMERA_WIDTH/2-350, CAMERA_HEIGHT/2-50+spacer, 700, 100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsToggleButtons.get(3).onPress();
						playMenuButtonSound();
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						bgSpin = !bgSpin;
						if(bgSpin == false){
							settingsToggleButtons.get(3).deactivateButton();
							settingsToggleButtons.get(3).setText(getString(R.string.BGSpinOff));
							bg.clearEntityModifiers();
							//stopMusic();
						}
						if(bgSpin == true){
							settingsToggleButtons.get(3).activateButton();
							settingsToggleButtons.get(3).setText(getString(R.string.BGSpinOn));
							final LoopEntityModifier bgRotation = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(75, 0, 360)),-1);
							bg.registerEntityModifier(bgRotation);
							//playMusic();
						}
						prefEditor.putBoolean("bgSpin", bgSpin);
						prefEditor.commit();

						settingsToggleButtons.get(3).onRelease();
						break;
				}

				return true;
			}

		};
		settingsTouch4 = new Rectangle(CAMERA_WIDTH/2-350, CAMERA_HEIGHT/2-50+2*spacer, 700, 100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsToggleButtons.get(4).onPress();
						playMenuButtonSound();
						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						zoomEffect = !zoomEffect;
						if(zoomEffect == false){
							settingsToggleButtons.get(4).deactivateButton();
							settingsToggleButtons.get(4).setText(getString(R.string.ZoomOff));
							//stopMusic();
						}
						if(zoomEffect == true){
							settingsToggleButtons.get(4).activateButton();
							settingsToggleButtons.get(4).setText(getString(R.string.ZoomOn));
							//playMusic();
						}
						prefEditor.putBoolean("zoomEffect", zoomEffect);
						prefEditor.commit();
						settingsToggleButtons.get(4).onRelease();
						break;
				}

				return true;
			}

		};
		settingsReturnTouch = new Rectangle(settingsButtons.get(0).getX(), settingsButtons.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						settingsButtons.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						settingsButtons.get(0).onRelease(true);
						animateOutSettings(0);
						animateInMainMenu(0.2f,false);
						break;
				}

				return true;
			}

		};
	}
	private void animateInSettings(float delay){
		//SHOWING_SETTINGS = true;
		//animateCurrencyToWhite(delay);
		//headerText.setText(getString(R.string.Settings));
		//settingsHeaderBox.moveEntity(delay,580, -CAMERA_HEIGHT/2-233+70);
		settings = true;
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		settingsHeader.registerEntityModifier(scale1.deepCopy());

		settingsToggleButtons.get(0).animateIn(delay);
		settingsToggleButtons.get(1).animateIn(delay);
		settingsToggleButtons.get(2).animateIn(delay);
		settingsToggleButtons.get(3).animateIn(delay);
		settingsToggleButtons.get(4).animateIn(delay);

		settingsButtons.get(0).animateIn(delay);
		if(music){
			settingsToggleButtons.get(0).activateButton();
			settingsToggleButtons.get(0).setText(getString(R.string.MusicOn));
		}
		else{
			settingsToggleButtons.get(0).deactivateButton();
			settingsToggleButtons.get(0).setText(getString(R.string.MusicOff));
		}
		if(sound){
			settingsToggleButtons.get(1).activateButton();
			settingsToggleButtons.get(1).setText(getString(R.string.SoundOn));
		}
		else{
			settingsToggleButtons.get(1).deactivateButton();
			settingsToggleButtons.get(1).setText(getString(R.string.SoundOff));
		}
		if(immersive){
			settingsToggleButtons.get(2).activateButton();
			settingsToggleButtons.get(2).setText(getString(R.string.ImmersiveOn));
		}
		else{
			settingsToggleButtons.get(2).deactivateButton();
			settingsToggleButtons.get(2).setText(getString(R.string.ImmersiveOff));
		}
		if(bgSpin){
			settingsToggleButtons.get(3).activateButton();
			settingsToggleButtons.get(3).setText(getString(R.string.BGSpinOn));
		}
		else{
			settingsToggleButtons.get(3).deactivateButton();
			settingsToggleButtons.get(3).setText(getString(R.string.BGSpinOff));
		}
		if(zoomEffect){
			settingsToggleButtons.get(4).activateButton();
			settingsToggleButtons.get(4).setText(getString(R.string.ZoomOn));
		}
		else{
			settingsToggleButtons.get(4).deactivateButton();
			settingsToggleButtons.get(4).setText(getString(R.string.ZoomOff));
		}
		mInterfaceScene.registerTouchArea(settingsTouch0);
		mInterfaceScene.registerTouchArea(settingsTouch1);
		mInterfaceScene.registerTouchArea(settingsTouch2);
		mInterfaceScene.registerTouchArea(settingsTouch3);
		mInterfaceScene.registerTouchArea(settingsTouch4);
		mInterfaceScene.registerTouchArea(settingsReturnTouch);
	}
	private void animateOutSettings(float delay){
		settings = false;
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		settingsHeader.registerEntityModifier(scale1.deepCopy());

		settingsToggleButtons.get(0).animateOut(delay);
		settingsToggleButtons.get(1).animateOut(delay);
		settingsToggleButtons.get(2).animateOut(delay);
		settingsToggleButtons.get(3).animateOut(delay);
		settingsToggleButtons.get(4).animateOut(delay);
		settingsButtons.get(0).animateOut(delay);
		mInterfaceScene.unregisterTouchArea(settingsTouch0);
		mInterfaceScene.unregisterTouchArea(settingsTouch1);
		mInterfaceScene.unregisterTouchArea(settingsTouch2);
		mInterfaceScene.unregisterTouchArea(settingsTouch3);
		mInterfaceScene.unregisterTouchArea(settingsTouch4);
		mInterfaceScene.unregisterTouchArea(settingsReturnTouch);
	}


	private V3InterfaceBox tutorialBox = new V3InterfaceBox();
	private ArrayList<V3InterfaceButton> tutorialButtonArray = new ArrayList<V3InterfaceButton>();
	private Sprite tutorialIcon;
	private Sprite tutorialTick;
	private Rectangle tutorialCheckbox1;
	private Rectangle tutorialCheckbox2;
	private Text tutorialText1;
	private Text tutorialText2;
	private Text tutorialText3;
	private Rectangle tutorialTouch0;
	private Rectangle tutorialTouch1;

	private void drawTutorial(){
		mTutorialScene = new Scene();
		mTutorialScene.setBackgroundEnabled(false);
		mTutorialScene.setTouchAreaBindingOnActionDownEnabled(true);

		tutorialBox.setBuffer(getVertexBufferObjectManager());
		tutorialBox.setShadowSprites(interfaceBoxShadow);
		tutorialBox.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, 10, 10);
		tutorialBox.createBox(mTutorialScene);
		tutorialBox.setColor(colorManager.getColor(15));

		tutorialIcon  = new Sprite(15, CAMERA_HEIGHT/2 - 230, this.myTutorialRegion, this.getVertexBufferObjectManager());
		tutorialIcon.setX(CAMERA_WIDTH/2-tutorialIcon.getWidth()/2+30);

		tutorialText1 = new Text(140, CAMERA_HEIGHT/2-40, this.scoreLargeFont, getString(R.string.TapTop), 50, this.getVertexBufferObjectManager());
		tutorialText1.setX(CAMERA_WIDTH/2-tutorialText1.getWidth()/2);
		tutorialText1.setColor(0.2f,0.2f,0.2f);

		tutorialText2 = new Text(140, CAMERA_HEIGHT/2+25, this.scoreSmallFont, getString(R.string.MissGameOver), 50, this.getVertexBufferObjectManager());
		tutorialText2.setX(CAMERA_WIDTH/2-tutorialText2.getWidth()/2);
		tutorialText2.setColor(0.4f,0.4f,0.4f);

		tutorialCheckbox1 = new Rectangle(CAMERA_WIDTH/2-200, CAMERA_HEIGHT/2+100, 45, 45, this.getVertexBufferObjectManager());
		tutorialCheckbox1.setColor(0.3f,0.3f,0.3f);
		tutorialCheckbox2 = new Rectangle(CAMERA_WIDTH/2-200, CAMERA_HEIGHT/2+100, 41, 41, this.getVertexBufferObjectManager());
		tutorialCheckbox2.setColor(colorManager.getColor(15));

		tutorialText3 = new Text(tutorialCheckbox1.getX()+65, tutorialCheckbox1.getY()-2, this.scoreSmallFont, getString(R.string.DontShow), 50, this.getVertexBufferObjectManager());
		tutorialText3.setColor(0.2f,0.2f,0.2f);

		tutorialCheckbox1.setX(CAMERA_WIDTH/2 - (tutorialCheckbox1.getWidth()+20+tutorialText3.getWidth())/2);
		tutorialCheckbox2.setX(tutorialCheckbox1.getX()+2);
		tutorialCheckbox2.setY(tutorialCheckbox1.getY()+2);
		tutorialText3.setX(tutorialCheckbox1.getX()+tutorialCheckbox1.getWidth()+20);

		tutorialTick  = new Sprite(15, CAMERA_HEIGHT/2 - 230, this.myYesIcon, this.getVertexBufferObjectManager());
		tutorialTick.setX(tutorialCheckbox2.getX()+2);
		tutorialTick.setY(tutorialCheckbox2.getY()-5);
		tutorialTick.setColor(0.2f,0.2f,0.2f);
		tutorialTick.setVisible(false);

		mTutorialScene.attachChild(tutorialIcon);
		mTutorialScene.attachChild(tutorialText1);
		mTutorialScene.attachChild(tutorialText2);
		mTutorialScene.attachChild(tutorialText3);

		mTutorialScene.attachChild(tutorialCheckbox1);
		mTutorialScene.attachChild(tutorialCheckbox2);
		mTutorialScene.attachChild(tutorialTick);

		tutorialCheckbox1.setAlpha(0);
		tutorialCheckbox2.setAlpha(0);
		tutorialIcon.setAlpha(0);
		tutorialText1.setAlpha(0);
		tutorialText2.setAlpha(0);
		tutorialText3.setAlpha(0);

		createInterfaceButton(tutorialButtonArray, mTutorialScene, 464, CAMERA_HEIGHT/2+330, myYesIcon, "", 7,0,0);

		tutorialTouch0 = new Rectangle(CAMERA_WIDTH/2 - 625/2, tutorialCheckbox2.getY()-13, 625, 72, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:

						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						tutorialTick.setVisible(!tutorialTick.isVisible());
						final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
						SharedPreferences.Editor prefEditor = settings.edit();
						if(GAMETYPE == 1){
							prefEditor.putBoolean("showTutorial", !tutorialTick.isVisible());
							showTutorial = !tutorialTick.isVisible();
						}
						if(GAMETYPE == 2){
							prefEditor.putBoolean("showTutorialFrenzy", !tutorialTick.isVisible());
							showTutorialFrenzy = !tutorialTick.isVisible();
						}
						prefEditor.commit();

						break;
				}

				return true;
			}

		};
		tutorialTouch1 = new Rectangle(tutorialButtonArray.get(0).getX(), tutorialButtonArray.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						tutorialButtonArray.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						tutorialButtonArray.get(0).onRelease(false);
						animateOutTutorial(0);
						break;
				}

				return true;
			}

		};
	}
	private void animateInTutorial(float delay){
		final LoopEntityModifier wait = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.15f)),1);
		wait.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				mGameScene.setChildScene(mTutorialScene, false, true, true);
				mTutorialScene.registerTouchArea(tutorialTouch0);
				mTutorialScene.registerTouchArea(tutorialTouch1);
			}
		});
		scoreTextValue.registerEntityModifier(wait);

		if(GAMETYPE == 1){
			tutorialText1.setText(getString(R.string.TapTop));
			tutorialText2.setText(getString(R.string.MissGameOver));
			//Log.d("GAMETYPE", "1");
		}
		if(GAMETYPE == 2){
			tutorialText1.setText(getString(R.string.TapTopFrenzy));
			tutorialText2.setText(getString(R.string.FrenzyDescription));
			//Log.d("GAMETYPE", "2");
		}
		tutorialText1.setX(CAMERA_WIDTH/2-tutorialText1.getWidth()/2);
		if(tutorialText1.getWidth()>500){
			tutorialBox.morphBox(delay, (int)tutorialText1.getWidth()+100, 525, 15);
		}
		else{
			tutorialBox.morphBox(delay, 625, 525, 15);
		}
		tutorialButtonArray.get(0).animateIn(0);


		tutorialText2.setX(CAMERA_WIDTH/2-tutorialText2.getWidth()/2);
		final LoopEntityModifier scaleIn = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		scaleIn.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {


			}
		});
		tutorialCheckbox1.registerEntityModifier(scaleIn);
		tutorialCheckbox2.registerEntityModifier(scaleIn.deepCopy());
		tutorialText1.registerEntityModifier(scaleIn.deepCopy());
		tutorialText2.registerEntityModifier(scaleIn.deepCopy());
		tutorialText3.registerEntityModifier(scaleIn.deepCopy());
		tutorialIcon.registerEntityModifier(scaleIn.deepCopy());
	}
	private void animateOutTutorial(float delay){
		mTutorialScene.unregisterTouchArea(tutorialTouch0);
		mTutorialScene.unregisterTouchArea(tutorialTouch1);
		tutorialBox.morphBox(delay, 10, 10, 15);
		tutorialButtonArray.get(0).animateOut(0);
		final LoopEntityModifier scaleIn = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.5f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.5f, 1,0,EaseCubicInOut.getInstance()))),1);
		scaleIn.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {

				mGameScene.clearChildScene();

			}
		});
		if(tutorialTick.isVisible()){
			tutorialTick.registerEntityModifier(scaleIn);
		}
		tutorialCheckbox1.registerEntityModifier(scaleIn);
		tutorialCheckbox2.registerEntityModifier(scaleIn.deepCopy());
		tutorialText1.registerEntityModifier(scaleIn.deepCopy());
		tutorialText2.registerEntityModifier(scaleIn.deepCopy());
		tutorialText3.registerEntityModifier(scaleIn.deepCopy());
		tutorialIcon.registerEntityModifier(scaleIn.deepCopy());

	}

	private Sprite pauseBg;
	private Text pauseGamePaused;
	private Text pauseResume;
	private Sprite pauseLogoBg;
	private Sprite pauseLogoTop;
	private ArrayList<V3InterfaceButton> pauseButtonArray = new ArrayList<V3InterfaceButton>();
	private Rectangle pauseTouch0;
	private Rectangle pauseTouch1;
	private Rectangle pauseTouch2;
	private Rectangle pauseTouch3;
	private Boolean home = false;

	private void drawPause(){
		mPauseScene = new Scene();
		mPauseScene.setBackgroundEnabled(false);
		mPauseScene.setTouchAreaBindingOnActionDownEnabled(true);
		pauseBg = new Sprite(15, 12, this.myButtonRegularTopRegion, this.getVertexBufferObjectManager());
		pauseBg.setColor(1,0.973f,0.882f);
		pauseBg.setAlpha(1f);
		pauseBg.setX(CAMERA_WIDTH-150);
		pauseBg.setY(80);
		pauseBg.setScaleCenter(pauseBg.getWidth()-15, 25);

		pauseLogoBg = new Sprite(0, logoStartPostition, this.logo_bg, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		pauseLogoBg.setX(CAMERA_WIDTH/2-pauseLogoBg.getWidth()/2);
		pauseLogoTop = new Sprite(0, logoStartPostition+107, this.myPlayButtonRegion, this.getVertexBufferObjectManager()){
			@Override
			protected void preDraw(final GLState pGLState, final Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		pauseLogoTop.setX(CAMERA_WIDTH/2-pauseLogoTop.getWidth()/2);

		pauseGamePaused = new Text(140, 23, this.logoLargeFont, getString(R.string.GamePaused), 50, this.getVertexBufferObjectManager());
		pauseGamePaused.setColor(0.2f,0.2f,0.2f);
		pauseGamePaused.setX(CAMERA_WIDTH/2-pauseGamePaused.getWidth()/2);
		pauseGamePaused.setY(120);

		pauseResume = new Text(140, 23, this.scoreLargeFont, getString(R.string.Resume), 50, this.getVertexBufferObjectManager());
		pauseResume.setColor(0.3f,0.3f,0.3f);
		pauseResume.setX(CAMERA_WIDTH/2-pauseResume.getWidth()/2);
		pauseResume.setY(pauseLogoBg.getY()+pauseLogoBg.getHeight()+30);


		mPauseScene.attachChild(pauseBg);
		mPauseScene.attachChild(pauseGamePaused);
		mPauseScene.attachChild(pauseLogoBg);
		mPauseScene.attachChild(pauseLogoTop);
		mPauseScene.attachChild(pauseResume);

		pauseGamePaused.setAlpha(0);
		pauseLogoBg.setAlpha(0);
		pauseLogoTop.setAlpha(0);
		pauseResume.setAlpha(0);

		createInterfaceButton(pauseButtonArray, mPauseScene, 464, CAMERA_HEIGHT-150, myHomeIcon, getString(R.string.ReturnHome), 13,0,0);

		pauseTouch0 = new Rectangle(pauseLogoBg.getX(), pauseLogoBg.getY(), pauseLogoBg.getWidth(), pauseLogoBg.getHeight()+100, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						pauseLogoTop.setScale(0.9f);
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:
						pauseLogoTop.setScale(1f);
						home = false;
						animateOutPause(0);
						break;
				}

				return true;
			}

		};
		pauseTouch1 = new Rectangle(pauseButtonArray.get(0).getX(), pauseButtonArray.get(0).getY(), 140, 180, this.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent1, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				switch(pSceneTouchEvent1.getAction()) {

					case TouchEvent.ACTION_DOWN:
						pauseButtonArray.get(0).onPress();
						playMenuButtonSound();

						break;

					case TouchEvent.ACTION_MOVE:

						break;

					case TouchEvent.ACTION_UP:

						pauseButtonArray.get(0).onRelease(true);

						home = true;
						animateOutPause(0);
						
						/*if((showTutorial && GAMETYPE == 1) || (showTutorialFrenzy && GAMETYPE == 2)){
							animateInTutorial(0.0f);
						}*/

						break;
				}

				return true;
			}

		};
	}
	private void animateInPause(float delay){

		mPauseScene.registerTouchArea(pauseTouch0);
		mPauseScene.registerTouchArea(pauseTouch1);
		mGameScene.setChildScene(mPauseScene, false, true, true);
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.3f, 0,50,EaseCubicIn.getInstance())),1);
		pauseBg.registerEntityModifier(scale);

		pauseButtonArray.get(0).animateIn(delay);

		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.05f),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 0,1,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 0,1,EaseCubicInOut.getInstance()))),1);
		pauseLogoBg.registerEntityModifier(scale0.deepCopy());
		pauseLogoTop.registerEntityModifier(scale1.deepCopy());

		pauseResume.registerEntityModifier(scale1.deepCopy());
		pauseGamePaused.registerEntityModifier(scale1.deepCopy());

		mPauseScene.registerTouchArea(pauseTouch0);
		mPauseScene.registerTouchArea(pauseTouch1);

	}
	private void animateOutPause(float delay){

		pauseButton.onRelease(false);
		mPauseScene.unregisterTouchArea(pauseTouch0);
		mPauseScene.unregisterTouchArea(pauseTouch1);


		pauseButtonArray.get(0).animateOut(delay);

		final LoopEntityModifier scale0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f),new ParallelEntityModifier(new AlphaModifier(0.3f, 1,0,EaseCubicInOut.getInstance()), new ScaleModifier(0.3f, 1,0,EaseCubicInOut.getInstance()))),1);
		pauseLogoBg.registerEntityModifier(scale0.deepCopy());
		pauseLogoTop.registerEntityModifier(scale1.deepCopy());

		pauseResume.registerEntityModifier(scale1.deepCopy());
		pauseGamePaused.registerEntityModifier(scale1.deepCopy());
		if(home){
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.0f),new ScaleModifier(0.4f, 50,50,EaseCubicIn.getInstance())),1);
			scale.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					mGameScene.clearChildScene();
					animateInMainMenu(0,false);
					stopTickTock();

				}
			});
			pauseBg.registerEntityModifier(scale);
			stopFrenzyMusic();
			stopLinesweeperMusic();
		}
		else{
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.0f),new ScaleModifier(0.5f, 50,0,EaseCubicIn.getInstance())),1);
			scale.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {

				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					mGameScene.clearChildScene();
					//animateInCountdown();
				}
			});
			pauseBg.registerEntityModifier(scale);
		}


	}

	private void createToggleButton(ArrayList<V3InterfaceToggleButton> array, int x, int y, String text,Boolean toggle){
		V3InterfaceToggleButton button = new V3InterfaceToggleButton();
		button.setBoxShadows(interfaceBoxShadow);
		button.setBuffer(getVertexBufferObjectManager());
		button.setTextStringFont(text, scoreSmallFont);
		button.setToggleRectangle();
		button.setToggleColorRegion(myToggleColor);
		button.setToggleTopRegion(myToggleTop);
		button.createToggleButton(mInterfaceScene, x, y,toggle);
		array.add(button);
	}
	private void createInterfaceButton(ArrayList<V3InterfaceButton> array, Entity sc, int xCoord, int yCoord, ITextureRegion icon, String string, int color,int offsetX,int offsetY){
		V3InterfaceButton button = new V3InterfaceButton();
		button.setBuffer(getVertexBufferObjectManager());
		button.setButtonBgRegion(myBonusButtonBg);
		button.setButtonColorRegion(myBonusButtonColor);
		button.setButtonIconRegion(icon);
		Text text = new Text(0, 0, this.scoreSmallFont, string, 50, this.getVertexBufferObjectManager());
		button.setButtonText(text);
		button.buildButton(xCoord, yCoord,color,offsetX,offsetY);
		button.createButton(sc,CAMERA_HEIGHT);
		array.add(button);
	}

	private void createToggleButton(Scene sc, ArrayList<InterfaceButton> array, int x, int y, String textSmall, String textBig , ITextureRegion region, int color,int offsetX,int offsetY){
		InterfaceButton button = new InterfaceButton();
		button.setBoxShadows(interfaceBoxShadow);
		button.setBuffer(getVertexBufferObjectManager());
		button.setTextStringFont(textSmall, scoreSmallFont, textBig, scoreLargeFont);
		button.setIconRegion(region,offsetX,offsetY);
		button.setButtonPressRegion(myBonusButtonColor);
		//button.setToggleColorRegion(myToggleColor);
		//button.setToggleTopRegion(myToggleTop);
		button.createButton(sc, x, y, color);
		array.add(button);
	}

	private Music mMusic;
	private Music mMainMenuMusic;
	private Music mFrenzyMusic;
	private Music mTickTock;
	private Sound mBombExplosion;
	private Sound mLineRemoved1;
	private Sound mLineRemoved2;
	private Sound mLineRemoved3;
	private Sound mLineRemoved4;
	private Sound mWrongPress;
	private Sound mButtonPress;
	private Sound mNotification;
	private Sound mNotification2;
	private Sound mCountdown;
	private Sound mTimeUp;
	private void loadSounds(){
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mButtonPress = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "menu_click2.mp3");
			/*this.mLineRemoved1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "xylo_1.mp3");
			this.mLineRemoved2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "xylo_2.mp3");
			this.mLineRemoved3 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "xylo_3.mp3");
			this.mLineRemoved4 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "xylo_4.mp3");*/
			this.mLineRemoved1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "linePop1.mp3");
			this.mLineRemoved2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "linePop1_1.mp3");
			this.mLineRemoved3 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "linePop1_2.mp3");
			this.mLineRemoved4 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "linePop1_3.mp3");
			/*if(gametype == 1 || gametype == 2){
				this.mMultiplierIncrease = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "multiplier_up.mp3");
			}
			if(gametype == 3){
				this.mMultiplierIncrease = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "multiplier_down.mp3");
				//this.mMultiplierIncrease.setVolume(0.5f);
			}*/
			/*this.mWrongPress = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "missline2.mp3");
			this.mWrongPress.setVolume(0.7f);*/
			this.mWrongPress = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "wrong_click.mp3");
			this.mBombExplosion = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "bomb_explode.mp3");
			this.mNotification = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "notification2.mp3");
			this.mNotification2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "notification.mp3");
			this.mCountdown = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "countdown.mp3");
			this.mTimeUp = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "timeup.mp3");
			/*if(gametype == 2){
				this.mGameover = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "gameover_attack.mp3");
			}*/
		} catch (final IOException e) {
			Debug.e(e);
		}
	}
	private void loadMusic(){
		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "music.mp3");
			this.mMainMenuMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "main_menu.mp3");
			this.mFrenzyMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "time.mp3");
			/*if(gametype == 1){
				this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "music.mp3");
			}
			if(gametype == 2){
				this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "time.mp3");
			}
			if(gametype == 3){
				this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "minesweeper.mp3");
			}*/
			this.mMusic.setLooping(true);
			this.mMusic.setVolume(0.5f);
			this.mMainMenuMusic.setLooping(true);
			this.mMainMenuMusic.setVolume(0.5f);
			this.mFrenzyMusic.setLooping(true);
			this.mFrenzyMusic.setVolume(0.5f);

			this.mTickTock = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "ticktock.mp3");
			this.mTickTock.setLooping(true);
			this.mTickTock.setVolume(0.5f);

		} catch (final IOException e) {
			Debug.e(e);
		}
	}
	private void playTickTock(){
		if(sound== true){
			if(mTickTock.isPlaying() == false){
				mTickTock.play();
			}
		}
	}
	private void stopTickTock(){
		if(sound){
			if(mTickTock.isPlaying() == true){
				mTickTock.pause();
			}
		}

	}
	private void playBombExplosionSound(){
		if(sound == true && GAMETYPE == 1){
			this.mBombExplosion.play();
		}
		if(sound == true && GAMETYPE == 2){
			this.mTimeUp.play();
		}
	}
	private void playRemoveLineSound(){
		if(sound == true){
			if(gameManager.getSpeed() >= 21 || gameManager.getLinesRemoved() <10){
				this.mLineRemoved1.play();
				mLineRemoved1.setVolume(0.6f);
			}
			if(gameManager.getSpeed() < 21 && gameManager.getSpeed() >= 16 && gameManager.getLinesRemoved() >=10){
				this.mLineRemoved2.play();
				mLineRemoved2.setVolume(0.6f);
			}
			if(gameManager.getSpeed() < 16 && gameManager.getSpeed() >= 10&& gameManager.getLinesRemoved() >=10){
				this.mLineRemoved3.play();
				mLineRemoved3.setVolume(0.6f);
			}
			if(gameManager.getSpeed() < 10 && gameManager.getLinesRemoved() >=10){
				this.mLineRemoved4.play();
				mLineRemoved4.setVolume(0.6f);
			}
		}
	}
	private void playWrongPressSound(){
		if(sound == true){
			this.mWrongPress.play();
		}
	}
	private void playMenuButtonSound(){
		if(sound){
			this.mButtonPress.play();
		}

	}
	private void playNotificationSound(){
		if(sound){
			this.mNotification.play();
		}
	}
	private void playNotification2Sound(){
		if(sound){
			this.mNotification2.play();
		}
	}
	private void playCountDownSound(){
		if(sound){
			this.mCountdown.play();
		}
	}

	private void playLinesweeperMusic(){
		if(music == true && mMusic != null && GAMETYPE == 1){
			if(mMusic.isPlaying() == false){
				mMusic.play();
			}

		}
	}
	private void stopLinesweeperMusic(){
		if(mMusic != null){
			if(mMusic.isPlaying()){
				mMusic.pause();
				mMusic.seekTo(0);
			}
		}
	}
	private void playFrenzyMusic(){
		if(music == true && mFrenzyMusic != null && GAMETYPE == 2){
			if(mFrenzyMusic.isPlaying() == false){
				mFrenzyMusic.play();

			}

		}
	}
	private void stopFrenzyMusic(){
		if(mFrenzyMusic != null){
			if(mFrenzyMusic.isPlaying()){
				mFrenzyMusic.pause();
				mFrenzyMusic.seekTo(0);
			}
		}
	}
	private void playMainMenuMusic(){
		if(music == true && mMainMenuMusic != null){
			if(mMainMenuMusic.isPlaying() == false){
				mMainMenuMusic.play();
			}

		}
	}
	private void stopMainMenuMusic(){
		if(mMainMenuMusic != null){
			if(mMainMenuMusic.isPlaying()){
				mMainMenuMusic.pause();
				//mMainMenuMusic.seekTo(0);
			}
		}
	}
	//private static final float RATE_MIN    = 10;
	// private static final float RATE_MAX	   = 20;
	private static final float RATE_MIN    = 25;
	private static final float RATE_MAX	   = 35;
	private static final int PARTICLES_MAX = 15;
	private SpriteParticleSystem particleSystem;
	private SpriteParticleSystem particleSystem1;
	private float duration = 0.3f;
	private float position = 100;
	private void addParticle(int x, int y){
		particleSystem = new SpriteParticleSystem(new PointParticleEmitter(x, y), RATE_MIN, RATE_MAX, PARTICLES_MAX, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 1f,1));
		particleSystem.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-6, 6, -6, 6));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(duration));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 0.4f*duration, 0.1f, 1.6f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0, 0.5f*duration, 1, 0.9f, 1f, 0.9f, 0.5f, 0f));
		particleSystem.addParticleModifier(new ColorParticleModifier<Sprite>(0.5f*duration, duration, 0.9f, 0.99f, 0.9f, 0f, 0, 0));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.2f*duration, 0, 1));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.2f*duration, duration, 1, 0));
		mGameScene.attachChild(particleSystem);
		/*particleSystem1 = new SpriteParticleSystem(new PointParticleEmitter(150, 130), RATE_MIN, RATE_MAX, PARTICLES_MAX, this.mParticleTextureRegion, this.getVertexBufferObjectManager());
		particleSystem1.addParticleInitializer(new ColorParticleInitializer<Sprite>(1f, 1f,1));
		particleSystem1.addParticleInitializer(new AlphaParticleInitializer<Sprite>(0));
		particleSystem1.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem1.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-6, 6, -6, 6));
		particleSystem1.addParticleInitializer(new RotationParticleInitializer<Sprite>(0.0f, 360.0f));
		particleSystem1.addParticleInitializer(new ExpireParticleInitializer<Sprite>(duration));
		particleSystem1.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 0.4f*duration, 0.1f, 1.6f));
		particleSystem1.addParticleModifier(new ColorParticleModifier<Sprite>(0, 0.5f*duration, 1, 0.9f, 1f, 0.9f, 0.5f, 0f));
		particleSystem1.addParticleModifier(new ColorParticleModifier<Sprite>(0.5f*duration, duration, 0.9f, 0.99f, 0.9f, 0f, 0, 0));
		particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(0, 0.2f*duration, 0, 1));
		particleSystem1.addParticleModifier(new AlphaParticleModifier<Sprite>(0.2f*duration, duration, 1, 0));
		mGameScene.attachChild(particleSystem1);*/
		/*final ParticleSystem particleSystem = new ParticleSystem(
	    		new PointParticleEmitter(50, engine.getCamera().getHeight() / 2),
	    		RATE_MIN,
	    		RATE_MAX,
	    		PARTICLES_MAX,
	    		this.mParticleTextureRegion
			);
	        
	        particleSystem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
	        
	        particleSystem.addParticleInitializer(new VelocityInitializer(15, 40, -5, 5));
	        particleSystem.addParticleInitializer(new AccelerationInitializer(10, 15, -1, 1));
	        
	        particleSystem.addParticleModifier(new ExpireModifier(5.5f));
	        particleSystem.addParticleModifier(new ScaleModifier(1.0f, 3.0f, 0f, 5f));
	        particleSystem.addParticleModifier(new AlphaModifier(1.0f, 0f, 0f, 5f));*/
	}

	V3Touch touchIcon = new V3Touch();
	private void addTouchHint(){
		//int top = getTopLineIndex();
		touchIcon.setBuffer(getVertexBufferObjectManager());
		//touchIcon.setTapText(getString(R.string.TapTop), scoreLargeFont);
		touchIcon.createIcon(mGameScene, myTouchIconRegion, 0, 0);

	}


	@Override
	public synchronized void onWindowFocusChanged(boolean pHasWindowFocus) {
		super.onWindowFocusChanged(pHasWindowFocus);
		if (pHasWindowFocus && immersive) {
			if (Build.VERSION.SDK_INT >= 19) {
				int uiOptions
						= 256 // View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_FULLSCREEN
						| 4096; // View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
				getWindow().getDecorView().setSystemUiVisibility(uiOptions);
			}
		}

	}


	/*@Override
	public void onResumeGame(){
		super.onPauseGame();
		initControl();
		
	}*/

	/*protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	    initControl();
	}*/


	//GAMEMODE SWITCH
	private void setGametypeFrenzy(){
		GAMETYPE = 2;
		gameManager.setGametype(GAMETYPE);
		gameManager.resetGameState();
		scoreText.setVisible(true);
		scoreTime.setVisible(true);
		scoreText1ValueShade.setVisible(true);
		scoreText1Value.setVisible(true);
		hideLifeHud();
		gameBackgrounds.hideBombBackground();
		scoreTextValue.setX(50);
		scoreTextValue.setY(30);
		scoreTextValueShade.setX(50+3);
		scoreTextValueShade.setY(30+3);

		scoreText1Value.setX(550);
		scoreText1Value.setY(30);
		scoreText1ValueShade.setX(550+3);
		scoreText1ValueShade.setY(30+3);
		gameStartBest = gameManager.getBestScore(GAMETYPE);
		playFrenzyMusic();
		stopLinesweeperMusic();
		stopMainMenuMusic();
	}
	private void setGametypeLinesweeper(){
		GAMETYPE = 1;
		scoreText.setVisible(false);
		scoreTime.setVisible(false);
		scoreText1ValueShade.setVisible(false);
		scoreText1Value.setVisible(false);
		gameManager.setGametype(GAMETYPE);
		gameManager.resetGameState();
		showLifeHud();
		gameBackgrounds.hideBombBackground();
		scoreTextValue.setX(CAMERA_WIDTH/2 - scoreTextValue.getWidth()/2-15);
		scoreTextValue.setY(137);
		scoreTextValueShade.setY(137+3);
		gameStartBest = gameManager.getBestScore(GAMETYPE);
		stopFrenzyMusic();
		playLinesweeperMusic();
		stopMainMenuMusic();
	}

	private void hideLifeHud(){
		lifeHudBg.setVisible(false);
		lifeHudBgHighlight.setVisible(false);
		lifeHudCircleMask.setVisible(false);
		lifeHudWick.setVisible(false);
		lifeHudBomb.setVisible(false);
		lifeHudRightMask.setVisible(false);
		particleSystem.setParticlesSpawnEnabled(false);
	}
	private void showLifeHud(){
		lifeHudBg.setVisible(true);
		lifeHudBgHighlight.setVisible(true);
		lifeHudCircleMask.setVisible(true);
		lifeHudWick.setVisible(true);
		lifeHudBomb.setVisible(true);
		lifeHudRightMask.setVisible(true);
		particleSystem.setParticlesSpawnEnabled(true);
	}

	//TWITTER
	private void logIn(int location) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
		{
			final SharedPreferences settings =  getSharedPreferences("Linesweeper", MODE_PRIVATE);
			SharedPreferences.Editor prefEditor = settings.edit();
			prefEditor.putBoolean("twitterLogin", true);
			prefEditor.putInt("twitterScore", gameManager.getScore());
			prefEditor.putInt("twitterGametype", GAMETYPE);
			prefEditor.putInt("twitterLocation", location);
			prefEditor.commit();
			new TwitterAuthenticateTask().execute();
		}
		else
		{
			///Log.d("TWITTER LOGGED", "IN");
			new TwitterUpdateStatusTask().execute(getString(R.string.FacebookDescription)+" "+String.valueOf(gameManager.getScore())+" "+getString(R.string.FacebookDescription1));
			//Intent intent = new Intent(this, TwitterActivity.class);
			//startActivity(intent);
		}
	}
	class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {

		@Override
		protected void onPostExecute(RequestToken requestToken) {
			if (requestToken!=null)
			{
				//if (!isUseWebViewForAuthentication)
				// {

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL()));
				startActivity(intent);
				finish();
                /*}
                else
                {
                    Intent intent = new Intent(getApplicationContext(), OAuthActivity.class);
                    intent.putExtra(ConstantValues.STRING_EXTRA_AUTHENCATION_URL,requestToken.getAuthenticationURL());
                    startActivity(intent);
                }*/
			}
		}

		@Override
		protected RequestToken doInBackground(String... params) {
			return TwitterUtil.getInstance().getRequestToken();
		}
	}
	private void initControl() {
		Uri uri = getIntent().getData();
		if (uri != null && uri.toString().startsWith(ConstantValues.TWITTER_CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(ConstantValues.URL_PARAMETER_TWITTER_OAUTH_VERIFIER);
			new TwitterGetAccessTokenTask().execute(verifier);
		} else
			new TwitterGetAccessTokenTask().execute("");
	}
	class TwitterGetAccessTokenTask extends AsyncTask<String, String, String> {

		@Override
		protected void onPostExecute(String userName) {
			//textViewUserName.setText(Html.fromHtml("<b> Welcome " + userName + "</b>"));
		}

		@Override
		protected String doInBackground(String... params) {

			Twitter twitter = TwitterUtil.getInstance().getTwitter();
			RequestToken requestToken = TwitterUtil.getInstance().getRequestToken();
			if (!StringUtil.isNullOrWhitespace(params[0])) {
				try {

					AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, params[0]);
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, accessToken.getToken());
					editor.putString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, accessToken.getTokenSecret());
					editor.putBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN, true);
					editor.commit();
					return twitter.showUser(accessToken.getUserId()).getName();
				} catch (TwitterException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			} else {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
				String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");
				AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
				try {
					TwitterUtil.getInstance().setTwitterFactory(accessToken);
					return TwitterUtil.getInstance().getTwitter().showUser(accessToken.getUserId()).getName();
				} catch (TwitterException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}
			}

			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	}
	class TwitterUpdateStatusTask extends AsyncTask<String, String, Boolean> {

		@Override
		protected void onPostExecute(Boolean result) {
			if (result)
				Toast.makeText(getApplicationContext(), "Tweet successfully", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(getApplicationContext(), "Tweet failed", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String accessTokenString = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN, "");
				String accessTokenSecret = sharedPreferences.getString(ConstantValues.PREFERENCE_TWITTER_OAUTH_TOKEN_SECRET, "");

				if (!StringUtil.isNullOrWhitespace(accessTokenString) && !StringUtil.isNullOrWhitespace(accessTokenSecret)) {
					AccessToken accessToken = new AccessToken(accessTokenString, accessTokenSecret);
					twitter4j.Status status = TwitterUtil.getInstance().getTwitterFactory().getInstance(accessToken).updateStatus(params[0]);
					return true;
				}

			} catch (TwitterException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			return false;  //To change body of implemented methods use File | Settings | File Templates.

		}
	}


	//FACEBOOK
	//CallbackManager callbackManager;
	//ShareDialog shareDialog;
	private void startFaceBook(){
		//FacebookSdk.sdkInitialize(getApplicationContext());
		//callbackManager = CallbackManager.Factory.create();
		//shareDialog = new ShareDialog(this);
	}
	private void postFaceBook(){
    	/*if (ShareDialog.canShow(ShareLinkContent.class)) {
		    ShareLinkContent linkContent = new ShareLinkContent.Builder()
		            .setContentTitle(getString(R.string.FacebookTitle))
		            .setContentDescription(
		            		getString(R.string.FacebookDescription)+" "+String.valueOf(gameManager.getScore())+" "+getString(R.string.FacebookDescription1))
		            .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.twoshellko.linesweeper"))
		            .build();

		    shareDialog.show(linkContent);
		}*/
	}






	@Override
	public void onPauseGame(){
		super.onPauseGame();
		stopTickTock();
		stopMainMenuMusic();
		stopFrenzyMusic();
		stopLinesweeperMusic();
	}

	@Override
	public void onBackPressed()
	{
		/*if (Chartboost.onBackPressed()){
        	return;
        }*/
		//else{
		Scene scene = this.mEngine.getScene();

		if(scene.hasChildScene()){
			Scene currentChild = scene.getChildScene();
			if(currentChild == mGameOverScene){
				if(mGameOverScene.hasChildScene()){
					Scene currentSubChild = mGameOverScene.getChildScene();
					if(currentSubChild == ggShareScene){
						animateOutShareDialog(0);
					}
					if(currentSubChild == mBestScoreScene){
						animateOutBestScoreBox(0);
					}
				}
				else{
					animateOutGameOver(0,false);
				}

			}
			if(currentChild == mPauseScene){
				animateOutPause(0);
			}
			if(currentChild == mInterfaceScene){
				if(settings){
					animateOutSettings(0);
					animateInMainMenu(0.2f,false);
				}
				else{
					finish();
				}
			}

		}
		else{
			animateInMainMenu(0,true);
		}
	}




	//}


	//ADS
	//private MoPubInterstitial mInterstitial;
		/*private void createMoPub(){
			mInterstitial = new MoPubInterstitial(this, "bd3c91a82c374491b930ffb72e785da5");
			mInterstitial.setInterstitialAdListener(this);
			mInterstitial.load();
		}*/
	// InterstitialAdListener methods













	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		//Chartboost.onPause(this);
	}
	@Override
	protected void onNewIntent(Intent intent)
	{
		Batch.onNewIntent(this, intent);

		super.onNewIntent(intent);
	}
	    
	    
		
	    /*private MoPubInterstitial mInterstitial;
		private void createMoPub(){
			mInterstitial = new MoPubInterstitial(this, "bd3c91a82c374491b930ffb72e785da5");
			mInterstitial.setInterstitialAdListener(this);
			mInterstitial.load();
		}*/

	//private IMInterstitial interstitialInMobi;
		/*private void showInMobi(){
			Game.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//InMobi.initialize(getApplicationContext(), "5c36aeccbb644a6da5fc35b35313e2e3");
					interstitialInMobi = new IMInterstitial(Game.this, "5c36aeccbb644a6da5fc35b35313e2e3");
					interstitialInMobi.loadInterstitial();
					if(interstitialInMobi.getState() ==IMInterstitial.State.LOADING){
						Log.d("INMOBI", "Loading");
					}
					
					Log.d("inmobi", "loading ad");
					interstitialInMobi.setIMInterstitialListener(new IMInterstitialListener() {
				        public void onShowInterstitialScreen(IMInterstitial arg0) {
				        }
				        @Override
				        public void onLeaveApplication(IMInterstitial arg0) {
				        }
				        @Override
				        public void onDismissInterstitialScreen(IMInterstitial arg0) {
				        }
				        @Override
				        public void onInterstitialLoaded(IMInterstitial arg0) {
				        	Log.d("inmobi", "ad loaded");
				        }
				        @Override
				        public void onInterstitialInteraction(IMInterstitial interstitial, Map<String, String> params) {                
				        }
				        @Override
				        public void onInterstitialFailed(IMInterstitial arg0, IMErrorCode arg1) {
				        	Log.d("inmobi", "ad failed");
				        }
				    }); 
				}
			});
			//interstitialInMobi = new IMInterstitial(this, "5c36aeccbb644a6da5fc35b35313e2e3");
			
		}
		private void showInMobiLoaded(){
			Game.this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if(interstitialInMobi.getState() ==IMInterstitial.State.LOADING){
						Log.d("INMOBI", "Loading");
					}
					if(interstitialInMobi.getState() ==IMInterstitial.State.UNKNOWN){
						Log.d("INMOBI", "Unknown");
					}
					if(interstitialInMobi.getState() ==IMInterstitial.State.INIT){
						Log.d("INMOBI", "init");
					}
					if (interstitialInMobi.getState() ==IMInterstitial.State.READY){
						Log.d("INMOBI", "Ready");
						interstitialInMobi.show();
					}
				}});	
		}*/

	@Override
	public void onInterstitialClicked(MoPubInterstitial arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onInterstitialDismissed(MoPubInterstitial arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onInterstitialFailed(MoPubInterstitial interstitial,
									 MoPubErrorCode errorCode) {
		final String errorMessage = (errorCode != null) ? errorCode.toString() : "";
		Log.d("MoPub error", errorMessage);
		//logToast(this, "Interstitial failed to load: " + errorMessage);
		//interstitial.forceRefresh();

		// TODO Auto-generated method stub

	}
	@Override
	public void onInterstitialLoaded(MoPubInterstitial interstitial) {
		if (interstitial.isReady()) {
			mInterstitial.show();
		} else {
			// Other code
		}

	}
	@Override
	public void onInterstitialShown(MoPubInterstitial arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onRedeemAutomaticOffer(Offer arg0) {
		// TODO Auto-generated method stub

	}

}
