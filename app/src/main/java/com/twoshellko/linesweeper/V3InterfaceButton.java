package com.twoshellko.linesweeper;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class V3InterfaceButton {
	private VertexBufferObjectManager buffer;
	private Sprite buttonBg;
	private Sprite buttonColor;
	private Sprite buttonTouch;
	private Sprite buttonIcon;
	private Text buttonText;
	private Entity button = new Entity();
	private Entity scene;
	private V3ColorManager colorManager = new V3ColorManager();
	private int buttonColorValue = 0;
	
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setButtonBgRegion(ITextureRegion region){
		buttonBg = new Sprite(0, 200, region, buffer);
	}
	public void setButtonColorRegion(ITextureRegion region){
		buttonColor = new Sprite(0, 200, region, buffer);
		buttonTouch = new Sprite(0, 200, region, buffer);
		buttonTouch.setVisible(false);
		buttonTouch.setColor(1,0.792f,0.157f);
		buttonTouch.setAlpha(0);
	}
	public void setButtonIconRegion(ITextureRegion region){
		buttonIcon = new Sprite(0, 200, region, buffer);
	}
	public void setButtonText(Text text){
		buttonText = text;
	}
	public void buildButton(int xCoord, int yCoord, int color, int offsetX, int offsetY){
		
		buttonColor.setColor(colorManager.getColor(color));
		buttonColorValue = color;
		buttonBg.setX(xCoord);
		buttonBg.setY(yCoord-buttonBg.getHeight()+5);
		buttonColor.setX(xCoord+17);
		buttonColor.setY(buttonBg.getY()+14);
		buttonTouch.setX(xCoord+17);
		buttonTouch.setY(buttonBg.getY()+14);
		buttonIcon.setX(buttonColor.getX()+buttonColor.getWidth()/2 - buttonIcon.getWidth()/2 + offsetX);
		buttonIcon.setY(buttonColor.getY()+buttonColor.getHeight()/2 - buttonIcon.getHeight()/2 + offsetY);
		buttonText.setColor(0,0,0);
		buttonText.setX(buttonColor.getX()+buttonColor.getWidth()/2-buttonText.getWidth()/2);
		buttonText.setY(buttonColor.getY()+buttonColor.getHeight()+12);
	}
	public void createButton(Entity sc, int c_h){
		scene = sc;
		button.attachChild(buttonBg);
		button.attachChild(buttonColor);
		button.attachChild(buttonTouch);
		button.attachChild(buttonIcon);
		button.attachChild(buttonText);
		scene.attachChild(button);
		button.setScaleCenter(buttonColor.getX()+buttonColor.getWidth()/2, buttonColor.getY()+buttonColor.getHeight()/2);
		button.setScale(0);
		hideButton();
	}
	public void onPress(){
		buttonTouch.setVisible(true);
		buttonColor.setAlpha(0.8f);
		buttonIcon.setColor(0,0,0);
		final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.15f, 0.1f, 0.55f,EaseCubicOut.getInstance()), new AlphaModifier(0.15f,0,0.75f)),new ParallelEntityModifier(new ScaleModifier(0.15f, 0.55f, 1f,EaseCubicIn.getInstance()), new AlphaModifier(0.35f,0.75f,0.0f))),1);

		spawnOut.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				buttonTouch.setVisible(false);
			}
		});
		buttonTouch.registerEntityModifier(spawnOut);
	}
	public void onRelease(Boolean showAnimation){
		buttonIcon.setColor(1,1,1);
		buttonColor.setAlpha(1f);
		if(showAnimation == false){
			buttonTouch.clearEntityModifiers();
			buttonTouch.setVisible(false);
		}
		
	}
	public float getX(){
		return buttonBg.getX();
	}
	public float getY(){
		return buttonBg.getY();
	}
	public void activateButton(){
		buttonIcon.setAlpha(1);
	}
	public void deactivateButton(){
		buttonIcon.setAlpha(0.35f);
	}
	public void setText(String t){
		buttonText.setText(t);
		buttonText.setX(buttonColor.getX()+buttonColor.getWidth()/2-buttonText.getWidth()/2);
	}
	public void animateIn(float delay){
		//button.setY(CAMERA_HEIGHT);
		//button.setScale(0);
		showButton();
		
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,0,1.0f, EaseBackInOut.getInstance())),1);
		button.registerEntityModifier(slide);
		
	}
	public void animateOut(float delay){
		
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,1.0f,0, EaseBackInOut.getInstance())),1);
			slide.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					hideButton();
				}
			});
			button.registerEntityModifier(slide);
		
		
	}
	public void scaleOut(){
		button.setScale(0);
	}
	private void showButton(){
		button.setVisible(true);
	}
	private void hideButton(){
		button.setVisible(false);
	}
	public Boolean isVisible(){
		return button.isVisible();
	}
	public int getButtonColorValue(){
		return buttonColorValue;
	}
}
