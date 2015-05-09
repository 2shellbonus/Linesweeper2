package com.twoshellko.linesweeper;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
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
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicInOut;

public class V3Touch {
	private Sprite touchicon;
	private VertexBufferObjectManager buffer;
	private Scene scene;
	private Boolean removeMe = false;
	//private Text tapText;
	//private Text tapTextSh;
	private V3ColorManager colorManager = new V3ColorManager();
	public void createIcon(Scene sc, ITextureRegion icon, float x, float y){
		scene = sc;
		touchicon = new Sprite(x, y, icon, buffer);
		touchicon.setX(x-15);
		touchicon.setY(y-15);
		scene.attachChild(touchicon);
		touchicon.setAlpha(0.75f);
		touchicon.setScale(1f);
		touchicon.setScaleCenter(15, 15);
		touchicon.setColor(0.95f,0.95f,0.95f);
		touchicon.setZIndex(100000);
		/*scene.attachChild(tapTextSh);
		
		scene.attachChild(tapText);
		tapText.setZIndex(100001);
		tapText.setColor(colorManager.getColor(14));
		tapText.setX(touchicon.getX()+touchicon.getWidth()/2-tapText.getWidth()/2);
		tapText.setY(touchicon.getY()+touchicon.getHeight());
		tapTextSh.setZIndex(100000);
		tapTextSh.setColor(0f,0f,0f);
		tapTextSh.setX(tapText.getX()+2);
		tapTextSh.setY(tapText.getY()+2);*/
		//animateTouchIcon();
		pulseTouchIcon();
	}
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	/*public void setTapText(String text, Font font){
		tapText = new Text(140, 3, font, text, 50, buffer);
		tapTextSh = new Text(140, 3, font, text, 50, buffer);
	}*/
	private void animateTouchIcon(){
		
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.25f, 0.5f, 1.3f,EaseCubicIn.getInstance()), new AlphaModifier(0.25f,1,0))),1);
		alpha.addModifierListener(new IEntityModifierListener() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				
			}
			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				touchicon.setVisible(false);
				removeMe = true;
			}
		});
		touchicon.registerEntityModifier(alpha);
	}
	private void pulseTouchIcon(){
		final LoopEntityModifier alpha = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new ScaleModifier(0.4f, 1f, 0.85f,EaseCubicInOut.getInstance()), new AlphaModifier(0.4f,0.75f,0.65f)),new ParallelEntityModifier(new ScaleModifier(0.4f, 0.85f, 1f,EaseCubicInOut.getInstance()), new AlphaModifier(0.4f,0.65f,0.75f))),-1);
		touchicon.registerEntityModifier(alpha);
	}
	public Boolean removeTouchIcon(){
		if(removeMe){
			touchicon.clearEntityModifiers();
			touchicon.detachSelf();
			touchicon.dispose();
			touchicon = null;
		}
		return removeMe;
	}
	public void hideTouchIcon(){
		touchicon.setVisible(false);
	}
	public void showTouchIcon(){
		touchicon.setVisible(true);
	}
	public void moveTouchIcon(float x, float y){
		touchicon.setX(x-15);
		touchicon.setY(y-15);
	}
}
