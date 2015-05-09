package com.twoshellko.linesweeper;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ParallelModifier;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class V3BonusButton {
	private VertexBufferObjectManager buffer;
	private Sprite buttonBg;
	private Sprite buttonColor;
	private Sprite buttonTouch;
	private Sprite buttonIcon;
	private Sprite buttonCurrencyIcon;
	private Text buttonValue;
	private Scene scene;
	private int type = 0;
	private int value = 0;
	
	
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
	public void setButtonCurrencyIconRegion(ITextureRegion region){
		buttonCurrencyIcon = new Sprite(0, 200, region, buffer);
	}
	public void setButtonText(Text text){
		buttonValue = text;
	}
	public void setType(int t){
		type = t;
	}
	public void buildButton(int xCoord, int CAMERA_HEIGHT){
		int offsetX = 0;
		int offsetY = 0;
		if(type == 1){
			offsetY = -5;
			offsetX = -4;
			buttonColor.setColor(0.898f,0.11f,0.137f);
		}
		if(type == 2){
			offsetY = -2;
			offsetX = 0;
			buttonColor.setColor(0.584f,0.459f,0.804f);
		}
		if(type == 3){
			offsetY = 0;
			offsetX = 0;
			buttonColor.setColor(0.953f,0.424f,0.376f);
		}
		if(type == 4){
			offsetY = 2;
			offsetX = 2;
			buttonColor.setColor(0.631f,0.533f,0.498f);
		}
		if(type == 5){
			offsetY = -2;
			offsetX = 0;
			buttonColor.setColor(0.584f,0.459f,0.804f);
		}
		buttonBg.setX(xCoord);
		buttonBg.setY(CAMERA_HEIGHT-buttonBg.getHeight()+5);
		buttonColor.setX(xCoord+17);
		buttonColor.setY(buttonBg.getY()+14);
		buttonTouch.setX(xCoord+17);
		buttonTouch.setY(buttonBg.getY()+14);
		buttonIcon.setX(buttonColor.getX()+buttonColor.getWidth()/2 - buttonIcon.getWidth()/2 + offsetX);
		buttonIcon.setY(buttonColor.getY()+buttonColor.getHeight()/2 - buttonIcon.getHeight()/2 + offsetY);
		buttonCurrencyIcon.setY(buttonColor.getY());
		buttonCurrencyIcon.setX(buttonColor.getX()+buttonColor.getWidth()-5);
		buttonCurrencyIcon.setColor(0.4f,0.4f,0.4f);
		buttonCurrencyIcon.setScale(0.8f);
		buttonValue.setX(buttonCurrencyIcon.getX()+buttonCurrencyIcon.getWidth());
		buttonValue.setY(buttonColor.getY()-4);
		buttonValue.setColor(0.4f,0.4f,0.4f);
	}
	public void createButton(Scene sc, int v){
		scene = sc;
		scene.attachChild(buttonBg);
		scene.attachChild(buttonColor);
		scene.attachChild(buttonTouch);
		scene.attachChild(buttonIcon);
		scene.attachChild(buttonCurrencyIcon);
		scene.attachChild(buttonValue);
		value = v;
	}
	public float getX(){
		return buttonBg.getX();
	}
	public int getValue(){
		return value;
	}
	public void activateNoPurchase(){
		final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new RotationModifier(0.1f, 0, 15),new RotationModifier(0.1f, 15, 0)),2);

		buttonIcon.registerEntityModifier(scale);
	}
	public void onPress(){
		buttonTouch.setVisible(true);
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
	public void hideButton(){
		buttonBg.setVisible(false);
		buttonColor.setVisible(false);
		buttonTouch.setVisible(false);
		buttonIcon.setVisible(false);
		buttonCurrencyIcon.setVisible(false);
		buttonValue.setVisible(false);
	}
	public void showButton(){
		buttonBg.setVisible(true);
		buttonColor.setVisible(true);
		buttonTouch.setVisible(true);
		buttonIcon.setVisible(true);
		buttonCurrencyIcon.setVisible(true);
		buttonValue.setVisible(true);
	}
}
