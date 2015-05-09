package com.twoshellko.linesweeper;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class InterfaceButton {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private Text buttonTextSmall;
	private Text buttonTextBig;
	private Sprite buttonIcon;
	private Sprite buttonTouch;
	private Entity button = new Entity();
	private Scene scene;
	private int xCoord = 0;
	private int yCoord = 0;
	private int buttonColor = 0;
	private int iconOffsetX = 0;
	private int iconOffsetY = 0;
	private V3ColorManager colorManager = new V3ColorManager();
	
	private Color colorOn = new Color(1f,0.792f,0.157f);
	private Color colorOff = new Color(0.953f,0.424f,0.376f);
	private V3InterfaceBox box = new V3InterfaceBox();
	private Boolean showToggle;
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setBoxShadows(ArrayList<ITextureRegion> array){
		interfaceBoxShadow = array;
	}
	public void setIconRegion(ITextureRegion region, int offsetX, int offsetY){
		buttonIcon = new Sprite(0, 200, region, buffer);
		iconOffsetX = offsetX;
		iconOffsetY = offsetY;
	}
	public void setButtonPressRegion(ITextureRegion region){
		buttonTouch = new Sprite(0, 200, region, buffer);
		buttonTouch.setColor(1,0.792f,0.157f);
		buttonTouch.setAlpha(0);
	}
	public void setTextStringFont(String textSmall, Font fontSmall, String textBig, Font fontBig){
		buttonTextSmall = new Text(140, 3, fontSmall, textSmall, 50, buffer);
		buttonTextSmall.setColor(0.3f,0.3f,0.3f);
		buttonTextBig = new Text(140, 3, fontBig, textBig, 50, buffer);
		buttonTextBig.setColor(0.25f,0.25f,0.25f);
	}
	public void createButton(Scene s, int x, int y, int color){
		xCoord = x;
		yCoord = y;
		scene = s;
		box.setBuffer(buffer);
		box.setShadowSprites(interfaceBoxShadow);
		box.buildBox(x, y, 732, 165);
		box.createBox(button);
		box.setColor(colorManager.getColor(color));
		buttonTextSmall.setY(y-45);
		buttonTextSmall.setX(x-150);
		buttonTextBig.setY(y-25);
		buttonTextBig.setX(x-150);
		button.attachChild(buttonTextSmall);
		button.attachChild(buttonTextBig);
		
		buttonIcon.setX(x-263+iconOffsetX-buttonIcon.getWidth()/2);
		buttonIcon.setY(y-buttonIcon.getHeight()/2+iconOffsetY);
		
		button.attachChild(buttonIcon);
		scene.attachChild(button);
		button.setScaleCenter(x, y);
		button.setScale(0);
		hideButton();
		buttonColor = color;
		//button.attachChild(buttonTouch);
		
	}
	
	public void setText(String textSmall, String textBig){
		buttonTextSmall.setText(textSmall);
		buttonTextBig.setText(textBig);
	}
	public void onRelease(){
		
		box.setColor(colorManager.getColor(buttonColor));
	}
	public void onPress(float x, float y){
		Color color = new Color(colorManager.getColor(14));
		box.setColor(color);
		//buttonTouch.setVisible(true);
		buttonTouch.setX(x-buttonTouch.getWidth()/2);
		buttonTouch.setY(y-buttonTouch.getHeight()/2);
		/*final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.15f, 0.1f, 0.55f,EaseCubicOut.getInstance()), new AlphaModifier(0.15f,0,0.75f)),new ParallelEntityModifier(new ScaleModifier(0.15f, 0.55f, 1f,EaseCubicIn.getInstance()), new AlphaModifier(0.35f,0.75f,0.0f))),1);

		spawnOut.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				buttonTouch.setVisible(false);
			}
		});
		buttonTouch.registerEntityModifier(spawnOut);*/
	}
	public void animateIn(float delay){
		//button.setY(CAMERA_HEIGHT);
		//button.setScale(0);
		showButton();
		
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,0,1, EaseBackInOut.getInstance())),1);
		button.registerEntityModifier(slide);
		
	}
	public void animateOut(float delay){
		
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,1,0, EaseBackInOut.getInstance())),1);
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
	private void hideButton(){
		button.setVisible(false);
	}
	private void showButton(){
		button.setVisible(true);
	}
	public int getX(){
		return xCoord-366;
	}
	public int getY(){
		return yCoord-82;
	}
}
