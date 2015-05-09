package com.twoshellko.linesweeper;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseBackOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseElasticInOut;
import org.andengine.util.modifier.ease.EaseElasticOut;

public class V3Timer {
	private VertexBufferObjectManager buffer;
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	private Sprite timerLine;
	private Sprite timerLineCap;
	private Sprite timerDarkCenter;
	private Sprite timerDarkCap;
	private Entity timer = new Entity();
	private Sprite icon;
	private Rectangle life;
	private Scene scene;
	private float lifeX = 0;
	private int type = 0;
	private Boolean removeMe = false;
	private Boolean animationOut = false;
	private int timerLocation = 0;
	
	public void setCameraSize(int width, int height){
		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;
	}
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setLineRegion(ITextureRegion region){
		timerLine = new Sprite(0, 200, region, buffer);
	}
	public void setLineCap(ITextureRegion region){
		timerLineCap = new Sprite(0, 200, region, buffer);
	}
	public void setDarkLineCenter(ITextureRegion region){
		timerDarkCenter = new Sprite(0, 200, region, buffer);
	}
	public void setDarkLineCap(ITextureRegion region){
		timerDarkCap = new Sprite(0, 200, region, buffer);
		
	}
	public void setIcon(ITextureRegion region){
		icon = new Sprite(0, 200, region, buffer);
	}
	private void setLife(){
		life = new Rectangle(0, 0, 88, 8, buffer);
	}
	public void createTimer(Scene sc, int position){
		int yCoord = 115;
		
		setLife();
		timerDarkCap.setY(yCoord);
		timerDarkCenter.setY(yCoord);
		icon.setY(yCoord);
		timerLineCap.setY(yCoord);
		timerLine.setY(yCoord);
		
		timerDarkCenter.setX(-13);
		timerDarkCap.setX(-15+timerDarkCenter.getWidth());
		
		
		icon.setX(timerDarkCenter.getX());
		timerLineCap.setX(timerDarkCap.getX());
		timerLine.setX(timerDarkCenter.getX()-75);
		timerLine.setWidth(timerDarkCenter.getWidth()+75);
		
		life.setX(timerDarkCenter.getX()+77);
		life.setY(timerDarkCap.getY()+46);
		scene = sc;
		timer.attachChild(timerLineCap);
		timer.attachChild(timerLine);
		lifeX = timerDarkCap.getX()+33 + 88;
		
		timer.attachChild(timerDarkCenter);
		timer.attachChild(timerDarkCap);
		timer.attachChild(icon);
		timer.attachChild(life);
		scene.attachChild(timer);
		timer.setX(180);
		timer.setY((position-1)*95);
		animateInTimer();
	}
	public void animateInTimer(){
		final LoopEntityModifier spawn = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.7f, -180, 0,timer.getY(),timer.getY(), EaseElasticOut.getInstance())),1);
		//final LoopEntityModifier spawn = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f, 180, 0,0,0, EaseBackInOut.getInstance())),1);

		timer.registerEntityModifier(spawn);
	}
	public Boolean updateLife(int maxLife, int currentLife){
		Boolean over = false;
		float width = 88*currentLife/maxLife;
		life.setWidth(width);
		//life.setX(lifeX-width);
		if(currentLife <=0){
			over = true;
		}
		return over;
	}
	public void setTimerType(int t){
		type = t;
	}
	public int getTimerType(){
		return type;
	}
	public void animateOutTimer(){
		timer.clearEntityModifiers();
		life.setVisible(false);
		animationOut = true;
		final LoopEntityModifier spawn = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f, 0, -180,timer.getY(),timer.getY(), EaseBackInOut.getInstance())),1);
		//final LoopEntityModifier spawn = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f, 180, 0,0,0, EaseBackInOut.getInstance())),1);
		spawn.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				removeMe = true;
			}
		});
		timer.registerEntityModifier(spawn);
	}
	public void removeTimer(){
		timerLine.detachSelf();
		timerLineCap.detachSelf();
		timerDarkCenter.detachSelf();
		timerDarkCap.detachSelf();
		icon.detachSelf();
		life.detachSelf();
		timerLine.dispose();
		timerLineCap.dispose();
		timerDarkCenter.dispose();
		timerDarkCap.dispose();
		icon.dispose();
		life.dispose();
		timer.detachSelf();
		timer.dispose();
		timer = null;
	}
	public Boolean isRemovable(){
		return removeMe;
	}
	public Boolean isAnimatingOut(){
		return animationOut;
	}
	public void moveUp(){
		if(animationOut == false){
			timer.clearEntityModifiers();
			final LoopEntityModifier move = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.3f, 0, 0,(timerLocation-1)*95,(timerLocation-2)*95, EaseBackInOut.getInstance())),1);
			timer.registerEntityModifier(move);
			timerLocation--;
		}
		
	}
	public void setTimerLocation(int location){
		timerLocation = location;
	}
	public int getTimerLocation(){
		return timerLocation;
	}
}
