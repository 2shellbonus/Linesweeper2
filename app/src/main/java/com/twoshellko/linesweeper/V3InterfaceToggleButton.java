package com.twoshellko.linesweeper;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
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
import org.andengine.util.modifier.ease.EaseCubicInOut;

public class V3InterfaceToggleButton {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private Sprite toggleTop;
	private Sprite toggleColor;
	private Rectangle toggleSlider;
	private Text buttonText;
	private Entity button = new Entity();
	private Scene scene;
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
	public void setToggleTopRegion(ITextureRegion region){
		toggleTop = new Sprite(0, 200, region, buffer);
	}
	public void setToggleColorRegion(ITextureRegion region){
		toggleColor = new Sprite(0, 200, region, buffer);
	}
	public void setTextStringFont(String text, Font font){
		buttonText = new Text(140, 3, font, text, 50, buffer);
		buttonText.setColor(0,0,0);
	}
	public void setToggleRectangle(){
		toggleSlider = new Rectangle(0, 0, 74, 10, buffer);

	}
	public void createToggleButton(Scene s, int x, int y, Boolean toggle){
		scene = s;
		box.setBuffer(buffer);
		box.setShadowSprites(interfaceBoxShadow);
		box.buildBox(x, y, 700, 100);
		box.createBox(button);
		box.setColor(0.902f,0.902f,0.902f);
		buttonText.setY(y-20);
		buttonText.setX(x-300);
		button.attachChild(buttonText);
		showToggle = toggle;
		if(toggle){
			toggleSlider.setY(y-toggleSlider.getHeight()/2);
			toggleSlider.setX(x+210);
			toggleSlider.setColor(0.2f,0.2f,0.2f);
			button.attachChild(toggleSlider);
			toggleColor.setY(y-toggleColor.getHeight()/2);
			toggleColor.setX(toggleSlider.getX()+toggleSlider.getWidth()-toggleColor.getWidth()/2);
			toggleColor.setColor(colorOn);
			button.attachChild(toggleColor);
			toggleTop.setX(toggleColor.getX()-15);
			toggleTop.setY(toggleColor.getY()-13);
			button.attachChild(toggleTop);
		}
		scene.attachChild(button);
		button.setScaleCenter(x, y);
		button.setScale(0);
		hideButton();
	}
	public void deactivateButton(){
		float duration = 0.3f;
		float moveXFrom = toggleColor.getX();
		float moveXTo = toggleSlider.getX()-toggleColor.getWidth()/2;
		float moveYFrom = toggleColor.getY();
		float moveYTo = toggleColor.getY();
		final LoopEntityModifier toggle = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()), new ColorModifier(duration, toggleColor.getColor(), colorOff))),1);
		toggleColor.registerEntityModifier(toggle);
		
		moveXFrom = toggleColor.getX()-15;
		moveXTo = toggleSlider.getX()-toggleColor.getWidth()/2 -15;
		moveYFrom = toggleTop.getY();
		moveYTo = toggleTop.getY();
		final LoopEntityModifier toggle2 = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance())),1);
		toggleTop.registerEntityModifier(toggle2);
	}
	public void activateButton(){
		float duration = 0.3f;
		float moveXFrom = toggleColor.getX();
		float moveXTo = toggleSlider.getX()+toggleSlider.getWidth()-toggleColor.getWidth()/2;
		float moveYFrom = toggleColor.getY();
		float moveYTo = toggleColor.getY();
		final LoopEntityModifier toggle = new LoopEntityModifier(new SequenceEntityModifier(new ParallelEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance()), new ColorModifier(duration, toggleColor.getColor(), colorOn))),1);
		toggleColor.registerEntityModifier(toggle);
		
		moveXFrom = toggleColor.getX()-15;
		moveXTo = toggleSlider.getX()+toggleSlider.getWidth()-toggleColor.getWidth()/2 -15;
		moveYFrom = toggleTop.getY();
		moveYTo = toggleTop.getY();
		final LoopEntityModifier toggle2 = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(duration,moveXFrom,moveXTo,moveYFrom,moveYTo,EaseCubicInOut.getInstance())),1);
		toggleTop.registerEntityModifier(toggle2);
	}
	public void setText(String text){
		buttonText.setText(text);
	}
	public void onRelease(){
		Color color = new Color(0.902f,0.902f,0.902f);
		box.setColor(color);
	}
	public void onPress(){
		Color color = new Color(0.7802f,0.802f,0.802f);
		box.setColor(color);
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
}
