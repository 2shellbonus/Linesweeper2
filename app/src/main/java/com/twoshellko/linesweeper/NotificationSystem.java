package com.twoshellko.linesweeper;

import java.util.ArrayList;

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
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class NotificationSystem {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private Text notificationText;
	private Text notificationTextSh;
	private Sprite starLeft;
	private Sprite starRight;
	private Scene scene;
	private V3ColorManager colorManager = new V3ColorManager();
	private V3InterfaceBox box = new V3InterfaceBox();
	private Entity notificationBox = new Entity();
	private int CAMERA_WIDTH = 0;
	private int CAMERA_HEIGHT = 0;
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setBoxShadows(ArrayList<ITextureRegion> array){
		interfaceBoxShadow = array;
	}
	public void setStarsRegion(ITextureRegion region, ITextureRegion region2){
		starLeft = new Sprite(0, 200, region, buffer);
		starRight = new Sprite(0, 200, region2, buffer);
	}
	public void setTextStringFont(Font font){
		notificationText = new Text(140, 3, font, "AMAZING!", 50, buffer);
		notificationText.setColor(1,1,1);
		notificationTextSh = new Text(140, 3, font, "AMAZING!", 50, buffer);
		notificationTextSh.setColor(0,0,0);
	}
	public void createNotificationBox(Scene sc,int C_W, int C_H){
		CAMERA_WIDTH = C_W;
		CAMERA_HEIGHT = C_H;
		scene = sc;
		notificationBox.attachChild(starLeft);
		notificationBox.attachChild(starRight);
		starLeft.setX(CAMERA_WIDTH/2-starLeft.getWidth()-180);
		starRight.setX(CAMERA_WIDTH/2+180);
		starLeft.setY(CAMERA_HEIGHT-150);
		starRight.setY(CAMERA_HEIGHT-150);
		box.setBuffer(buffer);
		box.setShadowSprites(interfaceBoxShadow);
		box.buildBox(CAMERA_WIDTH/2, CAMERA_HEIGHT-50, 570, 160);
		box.createBox(notificationBox);
		box.setColor(colorManager.getColor(1));
		notificationTextSh.setX(CAMERA_WIDTH/2 - notificationTextSh.getWidth()/2+2);
		notificationTextSh.setY(CAMERA_HEIGHT-90+2);
		notificationBox.attachChild(notificationTextSh);
		notificationText.setX(CAMERA_WIDTH/2 - notificationText.getWidth()/2);
		notificationText.setY(CAMERA_HEIGHT-90);
		notificationBox.attachChild(notificationText);
		scene.attachChild(notificationBox);
		notificationBox.setY(200);
		notificationBox.setVisible(false);
	}
	public void showNotification(String text, Boolean showStars){
		notificationBox.setVisible(true);
		notificationBox.clearEntityModifiers();
		if(showStars){
			final LoopEntityModifier spawnOut = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.1f),new ParallelEntityModifier(new ScaleModifier(0.3f, 0f, 1f,EaseBackInOut.getInstance()), new AlphaModifier(0.2f,0,1f)), new ScaleModifier(0.25f,1,0.95f),new ScaleModifier(0.25f,0.95f,1),new ParallelEntityModifier(new ScaleModifier(0.5f, 1f, 0f,EaseBackInOut.getInstance()), new AlphaModifier(0.5f,1f,0.0f))),1);
			starLeft.setScaleCenterX(starLeft.getWidth());
			starRight.setScaleCenterX(0);
			starLeft.registerEntityModifier(spawnOut);
			starRight.registerEntityModifier(spawnOut.deepCopy());
		}
		else{
			starRight.setAlpha(0);
			starLeft.setAlpha(0);
		}
		box.changeColor(0.1f, 2);
		final LoopEntityModifier moveEntity = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f, 0, 0,200,0,EaseBackInOut.getInstance()), new DelayModifier(0.65f),new MoveModifier(0.3f, 0, 0,0,200,EaseBackInOut.getInstance())),1);
		moveEntity.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				box.setColor(colorManager.getColor(1));
				notificationBox.setVisible(false);
			}
		});
		notificationBox.registerEntityModifier(moveEntity);
		notificationText.setText(text);
		notificationText.setX(CAMERA_WIDTH/2 - notificationText.getWidth()/2);
		notificationTextSh.setText(text);
		notificationTextSh.setX(CAMERA_WIDTH/2 - notificationText.getWidth()/2+2);
	}
	public void clearNotification(){
		starLeft.clearEntityModifiers();
		starRight.clearEntityModifiers();
		notificationBox.clearEntityModifiers();
		notificationBox.setY(200);
	}
}
