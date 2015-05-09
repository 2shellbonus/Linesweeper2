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
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class V3BackgroundManager {
	private Rectangle freezeBackground;
	private Rectangle bombBackground;
	private Boolean bombBgActivated = false;
	private Scene scene;
	public void createBackgrounds(Scene sc, Rectangle freezeBg, Rectangle bombBg){
		scene = sc;
		freezeBackground = freezeBg;
		bombBackground = bombBg;
		scene.attachChild(freezeBackground);
		scene.attachChild(bombBackground);
		freezeBackground.setColor(0.129f,0.588f,0.953f);
		freezeBackground.setAlpha(0);
		bombBackground.setAlpha(0);
		bombBackground.setColor(0.957f,0.263f,0.212f);
	}
	public void showFreezeBackground(){
		if(freezeBackground.getAlpha() == 0){
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0, 0.5f)),1);
			alpha0.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					//pulseFreezeBackground();
				}
			});
			freezeBackground.registerEntityModifier(alpha0);
		}
		
	}
	public void hideFreezeBackground(){
		if(freezeBackground.getAlpha() >= 0.5f){
			freezeBackground.clearEntityModifiers();
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0.5f, 0)),1);
			freezeBackground.registerEntityModifier(alpha0);
		}
		
	}
	private void pulseBombBackground(){
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 0.5f, 0.1f),new AlphaModifier(0.8f, 0.1f, 0.5f)),-1);
		bombBackground.registerEntityModifier(alpha.deepCopy());
	}
	public void showBombBackground(){
		if(bombBackground.getAlpha() == 0){
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
		}
		
	}
	public void hideBombBackground(){
		bombBgActivated = false;
		if(bombBackground.getAlpha() >= 0.5f){
			bombBackground.clearEntityModifiers();
			final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.3f, 0.5f, 0)),1);
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
