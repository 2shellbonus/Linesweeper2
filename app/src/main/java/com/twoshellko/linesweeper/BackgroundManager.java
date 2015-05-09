package com.twoshellko.linesweeper;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class BackgroundManager {
	private Rectangle bombBackground;
	private Boolean bombBgActivated = false;
	private Scene scene;
	public void createBackgrounds(Scene sc, Rectangle bombBg){
		scene = sc;
		bombBackground = bombBg;
		scene.attachChild(bombBackground);
		bombBackground.setAlpha(0);
		bombBackground.setColor(0.957f,0.263f,0.212f);
	}
	public void showBombBackground(){
		//if(bombBackground.getAlpha() == 0){
			bombBgActivated = true;
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 0, 0.5f)),1);
			alpha0.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					//pulseBombBackground();
				}
			});
			bombBackground.registerEntityModifier(alpha0);
		//}
		
	}
	public void hideBombBackground(){
		
		if(bombBgActivated){
			bombBgActivated = false;
			bombBackground.clearEntityModifiers();
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, bombBackground.getAlpha(), 0)),1);
			bombBackground.registerEntityModifier(alpha0);
		}
		
	}
	public void wrongTapBackground(){
		if(bombBgActivated == false){
			bombBackground.clearEntityModifiers();
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.1f, 0, 0.5f,EaseCubicIn.getInstance()),new AlphaModifier(0.4f, 0.5f, 0f,EaseCubicOut.getInstance())),1);
			bombBackground.registerEntityModifier(alpha0);
		}
	}
}
