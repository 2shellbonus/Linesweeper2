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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;
import org.andengine.util.modifier.ease.EaseCubicOut;

public class V3InterfaceBox {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> boxShadowRegion = new ArrayList<ITextureRegion>();
	private ArrayList<Sprite> boxShadow = new ArrayList<Sprite>();
	private Rectangle box;
	private Entity scene;
	private int width;
	private int height;
	private int xMiddleCoord;
	private int yMiddleCoord;
	private Entity boxEntity = new Entity();
	private V3ColorManager colorManager = new V3ColorManager();
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setShadowSprites(ArrayList<ITextureRegion> array){
		boxShadowRegion = array;
	}
	public void buildBox(int x, int y, int w, int h){
		xMiddleCoord = x;
		yMiddleCoord = y;
		width = w;
		height = h;
		box = new Rectangle(xMiddleCoord-(width)/2, yMiddleCoord-(height)/2, width, height, buffer);
		box.setColor(0.9f,0.9f,0.9f);
		boxShadow.add(new Sprite(0, 200, boxShadowRegion.get(0), buffer));
		boxShadow.get(0).setX(box.getX()-boxShadow.get(0).getWidth()+10);
		boxShadow.get(0).setY(box.getY()-boxShadow.get(0).getHeight()+10);
		
		boxShadow.add(new Sprite(box.getX()+width/2-0.5f, box.getY()-boxShadow.get(0).getHeight()+10, boxShadowRegion.get(1), buffer));
		boxShadow.get(1).setScaleX(width-20);
		
		boxShadow.add(new Sprite(0, 200, boxShadowRegion.get(2), buffer));
		boxShadow.get(2).setX(box.getX()+width-10);
		boxShadow.get(2).setY(box.getY()-boxShadow.get(0).getHeight()+10);
		
		boxShadow.add(new Sprite(box.getX()-boxShadow.get(0).getWidth()+10, box.getY()+height/2-0.5f, boxShadowRegion.get(3), buffer));
		boxShadow.get(3).setScaleY(height-20);
		
		boxShadow.add(new Sprite(box.getX()+width-10, box.getY()+height/2-0.5f, boxShadowRegion.get(4), buffer));
		boxShadow.get(4).setScaleY(height-20);
		
		boxShadow.add(new Sprite(0, 200, boxShadowRegion.get(5), buffer));
		boxShadow.get(5).setX(box.getX()-boxShadow.get(5).getWidth()+10);
		boxShadow.get(5).setY(box.getY()+height-10);
		
		boxShadow.add(new Sprite(box.getX()+width/2-0.5f, box.getY()+height-10, boxShadowRegion.get(6), buffer));
		boxShadow.get(6).setScaleX(width-20);
		
		boxShadow.add(new Sprite(box.getX()+width-10, box.getY()+height-10, boxShadowRegion.get(7), buffer));
	}
	public void rebuildBox(int x, int y, int w, int h){
		xMiddleCoord = x;
		yMiddleCoord = y;
		width = w;
		height = h;
		box.setX(xMiddleCoord-(width)/2);
		box.setY(yMiddleCoord-(height)/2);
		box.setWidth(width);
		box.setHeight(height);
		boxShadow.get(0).setX(box.getX()-boxShadow.get(0).getWidth()+10);
		boxShadow.get(0).setY(box.getY()-boxShadow.get(0).getHeight()+10);
		boxShadow.get(1).setScaleX(width-20);
		boxShadow.get(2).setX(box.getX()+width-10);
		boxShadow.get(2).setY(box.getY()-boxShadow.get(0).getHeight()+10);
		boxShadow.get(3).setScaleY(height-20);
		boxShadow.get(4).setScaleY(height-20);
		boxShadow.get(5).setX(box.getX()-boxShadow.get(5).getWidth()+10);
		boxShadow.get(5).setY(box.getY()+height-10);
		boxShadow.get(6).setScaleX(width-20);
		boxShadow.get(7).setX(box.getX()+width-10);
		boxShadow.get(7).setY(box.getY()+height-10);
	}
	public void createBox(Entity sc){
		scene = sc;
		boxEntity.attachChild(boxShadow.get(0));
		boxEntity.attachChild(boxShadow.get(1));
		boxEntity.attachChild(boxShadow.get(2));
		boxEntity.attachChild(boxShadow.get(3));
		boxEntity.attachChild(boxShadow.get(4));
		boxEntity.attachChild(boxShadow.get(5));
		boxEntity.attachChild(boxShadow.get(6));
		boxEntity.attachChild(boxShadow.get(7));
		boxEntity.attachChild(box);
		scene.attachChild(boxEntity);
		box.setAlpha(1);
	}
	public void setColor(float red, float green, float blue){
		box.setColor(red,green,blue);
	}

	public void morphBox(float delay, final int newWidth,final int newHeight, int newColor){
		//height = scaleY = 1;
		//newHeight/height = newScaleY
		box.setScaleCenter(box.getWidth()/2, box.getHeight()/2);
		float duration = 0.35f;
		
		float scaleXFrom = box.getScaleX();
		float scaleYFrom = box.getScaleY();
		float scaleXTo = (float)newWidth/box.getWidth();
		float scaleYTo = (float)newHeight/box.getHeight();
		final LoopEntityModifier morphBox = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new ScaleModifier(duration, scaleXFrom, scaleXTo,scaleYFrom, scaleYTo,EaseCubicInOut.getInstance()))),1);
		morphBox.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				box.setScale(1);
				box.setWidth(newWidth);
				box.setHeight(newHeight);
				box.setX(xMiddleCoord-box.getWidth()/2);
				box.setY(yMiddleCoord-box.getHeight()/2);
				width = newWidth;
				height = newHeight;
			}
		});
		box.registerEntityModifier(morphBox);
		
		float moveXFrom = box.getX()-boxShadow.get(0).getWidth()+10;
		float moveXTo = box.getX()+box.getWidth()/2-newWidth/2+10-boxShadow.get(0).getWidth();
		float moveYFrom = box.getY()+10-boxShadow.get(0).getHeight();
		float moveYTo = box.getY()+box.getHeight()/2-newHeight/2+10-boxShadow.get(0).getHeight();
		final LoopEntityModifier morph0 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(0).registerEntityModifier(morph0);
		
		scaleXFrom = box.getWidth()-20;
		scaleXTo = newWidth-20;
		scaleYFrom = 1;
		scaleYTo = 1;
		moveXFrom = boxShadow.get(1).getX();
		moveXTo = boxShadow.get(1).getX();
		moveYFrom = box.getY()+10-boxShadow.get(1).getHeight();
		moveYTo = box.getY()+box.getHeight()/2-newHeight/2+10-boxShadow.get(1).getHeight();
		final LoopEntityModifier morph1 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new ScaleModifier(duration, scaleXFrom, scaleXTo,scaleYFrom, scaleYTo,EaseCubicInOut.getInstance()), new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(1).registerEntityModifier(morph1);
		
		moveXFrom = box.getX()+width-10;
		moveXTo = box.getX()+box.getWidth()/2+newWidth/2-10;
		moveYFrom = box.getY()-10;
		moveYTo = box.getY()+box.getHeight()/2-newHeight/2+10-boxShadow.get(2).getHeight();
		final LoopEntityModifier morph2 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(2).registerEntityModifier(morph2);
		
		
		scaleXFrom = 1;
		scaleXTo = 1;
		scaleYFrom = box.getHeight()-20;
		scaleYTo = newHeight-20;
		moveXFrom = box.getX()+10-boxShadow.get(3).getWidth();
		moveXTo = box.getX()+box.getWidth()/2-newWidth/2+10-boxShadow.get(3).getWidth();
		moveYFrom = boxShadow.get(3).getY();
		moveYTo = boxShadow.get(3).getY();
		final LoopEntityModifier morph3 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new ScaleModifier(duration, scaleXFrom, scaleXTo,scaleYFrom,scaleYTo,EaseCubicInOut.getInstance()), new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(3).registerEntityModifier(morph3);
		
		scaleXFrom = 1;
		scaleXTo = 1;
		scaleYFrom = box.getHeight()-20;
		scaleYTo = newHeight-20;
		moveXFrom = box.getX()+box.getWidth()-10;
		moveXTo = box.getX()+box.getWidth()/2+newWidth/2-10;
		moveYFrom = boxShadow.get(4).getY();
		moveYTo = boxShadow.get(4).getY();
		final LoopEntityModifier morph4 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new ScaleModifier(duration, scaleXFrom, scaleXTo,scaleYFrom,scaleYTo,EaseCubicInOut.getInstance()), new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(4).registerEntityModifier(morph4);
		
		moveXFrom = box.getX()+10-boxShadow.get(5).getWidth();
		moveXTo = box.getX()+box.getWidth()/2-newWidth/2+10-boxShadow.get(5).getWidth();
		moveYFrom = box.getY()+box.getHeight()-10;
		moveYTo = box.getY()+box.getHeight()/2+newHeight/2-10;
		final LoopEntityModifier morph5 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(5).registerEntityModifier(morph5);
		
		scaleXFrom = box.getWidth()-20;
		scaleXTo = newWidth-20;
		scaleYFrom = 1;
		scaleYTo = 1;
		moveXFrom = boxShadow.get(6).getX();
		moveXTo = boxShadow.get(6).getX();
		moveYFrom = box.getY()+box.getHeight()-10;
		moveYTo = box.getY()+box.getHeight()/2+newHeight/2-10;
		final LoopEntityModifier morph6 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new ScaleModifier(duration, scaleXFrom, scaleXTo,scaleYFrom, scaleYTo,EaseCubicInOut.getInstance()), new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(6).registerEntityModifier(morph6);
		
		moveXFrom = box.getX()+width-10;
		moveXTo = box.getX()+box.getWidth()/2+newWidth/2-10;
		moveYFrom = box.getY()+box.getHeight()-10;
		moveYTo = box.getY()+box.getHeight()/2+newHeight/2-10;
		final LoopEntityModifier morph7 = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()))),1);
		boxShadow.get(7).registerEntityModifier(morph7);
	}
	public void moveEntity(float delay, int newX, int newY){
		float duration = 0.4f;
		final LoopEntityModifier moveEntity = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new MoveModifier(duration, boxEntity.getX(), newX,boxEntity.getY(),newY,EaseCubicInOut.getInstance())),1);
		boxEntity.registerEntityModifier(moveEntity);
	}
	public void changeColor(float delay, int colorV){
		float duration = 0.2f;
		//final LoopEntityModifier color = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ColorModifier(duration, box.getRed(),colorR,box.getGreen(),colorG,box.getBlue(),colorB,EaseCubicInOut.getInstance())),1);
		final LoopEntityModifier color = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay),new ColorModifier(duration, box.getColor(), colorManager.getColor(colorV),EaseCubicInOut.getInstance())),1);

		box.registerEntityModifier(color);
	}
	public void setEntityXY(int x, int y){
		boxEntity.setX(x);
		boxEntity.setY(y);
	}
	public void setColor(Color color){
		box.setColor(color);
	}
}
