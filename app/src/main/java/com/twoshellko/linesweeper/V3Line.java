package com.twoshellko.linesweeper;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class V3Line {
	VertexBufferObjectManager buffer;
	private Boolean horizontal;
	private int type;
	private Sprite cap1;
	private Sprite centerStretch;
	private Sprite cap2;
	private Sprite specialCap1;
	private Sprite specialCenterStretch;
	private Sprite specialCap2;
	private Sprite specialCenter;
	private Sprite specialIcon1;
	private Sprite specialIconCenter;
	private Sprite specialIcon2;
	private Scene scene;
	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private int zIndex;
	private Entity line = new Entity();
	private Boolean specialSpawned = false;
	private int lineLife = 1; 
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	private int TOTAL_LINES;
	private int lineColor = 0;
	
	public void setCameraSize(int width, int height){
		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;
	}
	public void setTotalLineAmount( int lines){
		TOTAL_LINES = lines;
	}
	public void setOrientation(Boolean orientation){
		horizontal = orientation;
	}
	public void setType(int tp){
		type = tp;
	}
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setCap1(ITextureRegion region){
		cap1 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setCenterStretch(ITextureRegion region){
		centerStretch = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setCap2(ITextureRegion region){
		cap2 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialCap1(ITextureRegion region){
		specialCap1 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialCap2(ITextureRegion region){
		specialCap2 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialCenter(ITextureRegion region){
		specialCenter = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialCenterStretch(ITextureRegion region){
		specialCenterStretch = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialIcon1(ITextureRegion region){
		specialIcon1 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialIcon2(ITextureRegion region){
		specialIcon2 = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setSpecialCenterIcon(ITextureRegion region){
		specialIconCenter = new Sprite(xCoord, yCoord, region, buffer);
	}
	public void setDimensions(int xC, int yC, int w, int h ){
		xCoord = xC;
		yCoord = yC;
		width = w;
		height = h;
	}
	public void createLine(Scene sc,Boolean zoom){
		scene = sc;
		
		line.attachChild(cap1);
		line.attachChild(cap2);
		line.attachChild(centerStretch);
		line.attachChild(specialCap1);
		line.attachChild(specialCap2);
		line.attachChild(specialCenterStretch);
		line.attachChild(specialCenter);
		line.attachChild(specialIcon1);
		line.attachChild(specialIconCenter);
		line.attachChild(specialIcon2);
		scene.attachChild(line);
		buildLine();
		setSpecialVisibility(false);
		line.setScaleCenter(CAMERA_WIDTH/2, CAMERA_HEIGHT/2);
		if(zoom){
			line.setScale(0.75f);
		}
		
		
		//blinkSpecialIcons();
	}
	private void buildLine(){
		if(horizontal){
			cap1.setX(xCoord);
			cap1.setY(yCoord);
			centerStretch.setX(xCoord+100f-2f);
			centerStretch.setY(yCoord);
			centerStretch.setScaleCenterX(0);
			float scale = width - 100 - 105;
			//centerStretch.setScaleX(scale/2);
			centerStretch.setWidth(scale+4f);
			cap2.setX(xCoord+width-cap2.getWidth());
			cap2.setY(yCoord);
			
			specialCap1.setX(xCoord);
			specialCap1.setY(yCoord);
			
			specialCenterStretch.setX(xCoord-2+specialCap1.getWidth());
			specialCenterStretch.setY(yCoord);
			specialCenterStretch.setScaleCenterX(0);
			specialCenterStretch.setScaleX(scale+4f);
			
			specialCap2.setX(xCoord+width-specialCap2.getWidth());
			specialCap2.setY(yCoord);
			specialCenter.setX(xCoord+width/2-specialCenter.getWidth()/2);
			specialCenter.setY(yCoord);
			
			specialIcon1.setX(xCoord);
			specialIcon1.setY(yCoord);
			specialIcon2.setX(xCoord+width-specialCap2.getWidth());
			specialIcon2.setY(yCoord);
			specialIconCenter.setX(xCoord+width/2-specialCenter.getWidth()/2);
			specialIconCenter.setY(yCoord);
		}
		else{
			cap1.setX(xCoord);
			cap2.setX(xCoord);
			centerStretch.setX(xCoord);
			cap1.setY(yCoord);
			centerStretch.setY(yCoord+cap1.getHeight()-2f);
			centerStretch.setScaleCenterY(0);
			float scale = height -cap1.getHeight() - cap2.getHeight();
			//centerStretch.setScaleY(scale);
			centerStretch.setHeight(scale+4);
			cap2.setY(yCoord+height-cap2.getHeight());
			
			specialCap1.setX(xCoord);
			specialCap2.setX(xCoord);
			specialCenterStretch.setX(xCoord);
			specialCap1.setY(yCoord);
			specialCenterStretch.setY(yCoord-2+specialCap1.getHeight());
			specialCenterStretch.setScaleCenterY(0);
			specialCenterStretch.setScaleY(scale+4);
			specialCap2.setY(yCoord+height-specialCap2.getHeight());
			specialCenter.setX(xCoord);
			specialCenter.setY(yCoord+height/2-specialCenter.getHeight()/2);
			
			
			specialIcon1.setX(xCoord);
			specialIcon2.setX(xCoord);
			specialIcon1.setY(yCoord);
			specialIcon2.setY(yCoord+height-specialCap2.getHeight());
			specialIconCenter.setX(xCoord);
			specialIconCenter.setY(yCoord+height/2-specialCenter.getHeight()/2);
		}
	}
	public void setZindex(int index, Boolean zoom){
		zIndex = index;
		line.setZIndex(zIndex);
		if(zoom){
			float coeff = 0.25f/TOTAL_LINES;
			final LoopEntityModifier scale = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.15f, 0.75f + (zIndex-1)*coeff,0.75f + zIndex*coeff,0.75f + (zIndex-1)*coeff, 0.75f + zIndex*coeff)),1);
			line.registerEntityModifier(scale);
		}
		
	}
	public void setZindexNoAnim(int index, Boolean zoom){
		zIndex = index;
		line.setZIndex(zIndex);
		float coeff = 0.25f/TOTAL_LINES;
		//line.clearEntityModifiers();
		if(zoom){
			line.setScaleX(0.75f + zIndex*coeff);
			line.setScaleY(0.75f + zIndex*coeff);
		}
		
	}
	public int getX(){
		return xCoord;
	}
	public int getY(){
		return yCoord;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int getType(){
		return type;
	}
	public int getZindex(){
		return zIndex;
	}
	public Boolean isHorizontal(){
		return horizontal;
	}
	private void setSpecialVisibility(Boolean visible){
		specialCap1.setVisible(visible);
		specialCap2.setVisible(visible);
		specialCenterStretch.setVisible(visible);
		specialCenter.setVisible(visible);
		specialIcon1.setVisible(visible);
		specialIcon2.setVisible(visible);
		specialIconCenter.setVisible(visible);
	}
	private void animateInSpecial(){
		
		setSpecialVisibility(true);
		
		
		specialCap1.setAlpha(0);
		specialCap2.setAlpha(0);
		specialCenterStretch.setAlpha(0);
		specialCenter.setAlpha(0);
		specialIcon1.setAlpha(0);
		specialIcon2.setAlpha(0);
		specialIconCenter.setAlpha(0);
		final LoopEntityModifier alpha0 = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.2f, 0, 1)),1);
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.5f, 0, 1)),1);
		alpha.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				blinkSpecialIcons();
			}
		});
		specialCap1.registerEntityModifier(alpha0.deepCopy());
		specialCap2.registerEntityModifier(alpha0.deepCopy());
		specialCenterStretch.registerEntityModifier(alpha0.deepCopy());
		specialCenter.registerEntityModifier(alpha0.deepCopy());
		
		specialIcon1.registerEntityModifier(alpha);
		specialIcon2.registerEntityModifier(alpha.deepCopy());
		specialIconCenter.registerEntityModifier(alpha.deepCopy());
	}
	private void animateOutSpecial(){
		specialIcon1.clearEntityModifiers();
		specialIcon2.clearEntityModifiers();
		specialIconCenter.clearEntityModifiers();
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.15f, 1, 0)),1);
		if(horizontal){
			final LoopEntityModifier alphaDelay = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.05f),new ScaleModifier(0.1f, specialCenterStretch.getScaleX(), specialCenterStretch.getScaleX(),1,0,EaseBackInOut.getInstance())),1);
			alphaDelay.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					setSpecialVisibility(false);
				}
			});
			specialCenterStretch.registerEntityModifier(alphaDelay);
		}
		else{
			final LoopEntityModifier alphaDelay = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(0.05f),new ScaleModifier(0.1f, 1, 0,specialCenterStretch.getScaleY(),specialCenterStretch.getScaleY(),EaseBackInOut.getInstance())),1);
			alphaDelay.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					setSpecialVisibility(false);
				}
			});
			specialCenterStretch.registerEntityModifier(alphaDelay);
		}
		//specialCenterStretch.registerEntityModifier(alpha.deepCopy());
		specialCap1.registerEntityModifier(alpha.deepCopy());
		specialCap2.registerEntityModifier(alpha.deepCopy());
		
		specialCenter.registerEntityModifier(alpha.deepCopy());
		specialIcon1.registerEntityModifier(alpha.deepCopy());
		specialIcon2.registerEntityModifier(alpha.deepCopy());
		specialIconCenter.registerEntityModifier(alpha.deepCopy());
	}
	private void blinkSpecialIcons(){
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new AlphaModifier(0.8f, 1, 0.65f),new AlphaModifier(0.8f, 0.65f, 1f)),-1);
		specialIcon1.registerEntityModifier(alpha.deepCopy());
		specialIcon2.registerEntityModifier(alpha.deepCopy());
		specialIconCenter.registerEntityModifier(alpha.deepCopy());
	}
	public void removeLine(){
		cap1.detachSelf();
		centerStretch.detachSelf();
		cap2.detachSelf();
		specialCap1.detachSelf();
		specialCenterStretch.detachSelf();
		specialCap2.detachSelf();
		specialCenter.detachSelf();
		specialIcon1.detachSelf();
		specialIconCenter.detachSelf();
		specialIcon2.detachSelf();
		cap1.dispose();
		centerStretch.dispose();
		cap2.dispose();
		specialCap1.dispose();
		specialCenterStretch.dispose();
		specialCap2.dispose();
		specialCenter.dispose();
		specialIcon1.dispose();
		specialIconCenter.dispose();
		specialIcon2.dispose();
		line.detachSelf();
		line.dispose();
		line = null;
		
	}

	public Boolean spawnSpecial(Boolean[] spawnedSpecials){
		Boolean spawned = false;
		int spawn = (int) Math.round(Math.random()*2);
		if(spawn == 0 && spawnedSpecials[type-1] == false){
			spawned = true;
			specialSpawned = true;
			animateInSpecial();
		}
		return spawned;
	}
	public void restoreSpawnSpecial(Boolean value){
		specialSpawned = value;
		if(value){
			animateInSpecial();
		}
		
	}
 	public void spawnOutSpecial(){
		animateOutSpecial();
		specialSpawned = false;
	}
	public Boolean isSpecialSpawned(){
		return specialSpawned;
	}
	public void reduceLife(){
		lineLife--;
	}
	public void setLineLife(int life){
		lineLife = life;
	}
	public Boolean okToRemoveLine(){
		Boolean remove = false;
		if(lineLife == 0){
			remove = true;
		}
		return remove;
	}
	public int getLineLife(){
		return lineLife;
	}
	public void setColor(int c){
		lineColor = c;
	}
	public int getColor(){
		return lineColor;
	}
	public void highlightLine(Boolean highlight){
		if(highlight){
			Color initialColor = centerStretch.getColor();
			Color toColor = new Color(0.898f,0.11f,0.137f);
			final LoopEntityModifier move = new LoopEntityModifier(new SequenceEntityModifier(new ColorModifier(0.5f, initialColor, toColor),new ColorModifier(0.3f, toColor, initialColor)),-1);
			centerStretch.registerEntityModifier(move.deepCopy());
			cap1.registerEntityModifier(move.deepCopy());
			cap2.registerEntityModifier(move.deepCopy());

		}
	}

	
}
