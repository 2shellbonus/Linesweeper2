package com.twoshellko.linesweeper;

import java.util.ArrayList;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicInOut;

public class V3OtherAppBox {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private Rectangle headerBg;
	private V3InterfaceButton installButton = new V3InterfaceButton();
	private V3InterfaceButton playButton = new V3InterfaceButton();
	
	private V3InterfaceBox box = new V3InterfaceBox();
	private Text headerText;
	private Text description;
	private Sprite appImage;
	private Entity boxAssembled = new Entity();
	private Scene scene;
	private V3ColorManager colorManager = new V3ColorManager();
	
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
		box.setBuffer(buffer);
		installButton.setBuffer(buffer);
		playButton.setBuffer(buffer);
	}
	public void setBoxShadows(ArrayList<ITextureRegion> array){
		interfaceBoxShadow = array;
	}
	public void setDescription(String string, Font font){
		description = new Text(45, 190, font, string, 1000, new TextOptions(AutoWrap.WORDS, 770f, HorizontalAlign.LEFT), buffer);
	}
	public void setHeader(String string, Font font){
		headerText = new Text(0, 0, font, string, 50, buffer);
	}
	public void setButtonRegions(ITextureRegion bgRegion, ITextureRegion colorRegion, ITextureRegion install ){
		installButton.setButtonBgRegion(bgRegion);
		playButton.setButtonBgRegion(bgRegion);
		installButton.setButtonColorRegion(colorRegion);
		playButton.setButtonColorRegion(colorRegion);
		installButton.setButtonIconRegion(install);
		//playButton.setButtonIconRegion(play);
	}
	public void setButtonText(Text installText, Text playText){
		installButton.setButtonText(installText);
		playButton.setButtonText(playText);
	}
	public void setAppImage(ITextureRegion image){
		appImage = new Sprite(0, 0, image, buffer);
	}
	public void createGameTypeBox(Scene s, int x, int y, int colorInstall, int colorRectangle){
		scene = s;
		box.setShadowSprites(interfaceBoxShadow);
		box.buildBox(x, y-60, 830, 580);
		box.createBox(boxAssembled);
		box.setColor(0.902f,0.902f,0.902f);
		
		installButton.buildButton(x-76, y+305, colorInstall, 0, -1);
		installButton.createButton(boxAssembled, 0);
		//playButton.animateIn(0);
		
		//scoresButton.buildButton(x-336, y+295, colorScores, 0, 0);
		//scoresButton.createButton(boxAssembled, 0);
		//scoresButton.animateIn(0);
		
		//upgradeButton.buildButton(x+185, y+295, colorUpgrade, 0, -2);
		//upgradeButton.createButton(boxAssembled, 0);
		//upgradeButton.animateIn(0);
		
		headerBg = new Rectangle(x-385, y-320, 770, 62, buffer);
		headerBg.setColor(colorManager.getColor(colorRectangle));
		boxAssembled.attachChild(headerBg);
		appImage.setX(headerBg.getX()+headerBg.getWidth()/2-appImage.getWidth()/2);
		appImage.setY(headerBg.getY()+headerBg.getHeight()+5);
		boxAssembled.attachChild(appImage);
		description.setX(headerBg.getX());
		description.setY(appImage.getY()+appImage.getHeight()+5);
		description.setColor(0.3f,0.3f,0.3f);
		boxAssembled.attachChild(description);

		headerText.setX(headerBg.getX()+headerBg.getWidth()/2 - headerText.getWidth()/2);
		headerText.setY(headerBg.getY()+headerBg.getHeight()/2 - headerText.getHeight()/2);
		headerText.setColor(0f,0f,0f);
		boxAssembled.attachChild(headerText);
		scene.attachChild(boxAssembled);
		boxAssembled.setScaleCenter(x, y);
		boxAssembled.setScale(0);
		hideBox();
	}
	public void animateIn(float delay){
		showBox();
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,0,1, EaseBackInOut.getInstance())),1);
		boxAssembled.registerEntityModifier(slide);
		installButton.animateIn(delay+0.2f);
		
		
	}
	public void animateOut(float delay){
		installButton.animateOut(delay);
		
		
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay+0.1f), new ScaleModifier(0.4f,1,0, EaseBackInOut.getInstance())),1);
			slide.addModifierListener(new IEntityModifierListener() {
				@Override
				public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
					
				}
				@Override
				public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
					hideBox();
				}
			});
			boxAssembled.registerEntityModifier(slide);
		
		
	}
	private void showBox(){
		boxAssembled.setVisible(true);
	}
	private void hideBox(){
		boxAssembled.setVisible(false);
	}
	public void moveBox(float xPos){
		boxAssembled.setX(xPos);
	}
	public float getX(){
		return boxAssembled.getX();
	}
	public void moveBoxWithAnimation(float toX){
		final LoopEntityModifier moveEntity = new LoopEntityModifier(new SequenceEntityModifier(new MoveModifier(0.4f, boxAssembled.getX(), toX,boxAssembled.getY(),boxAssembled.getY(),EaseCubicInOut.getInstance())),1);
		boxAssembled.registerEntityModifier(moveEntity);
	}
	
	public float getButtonX(int button){
		return installButton.getX();
	}
	public float getButtonY(){
		return installButton.getY();
	}
	public void pressButton(int button){
		installButton.onPress();
	}
	public void releaseButton(int button){
		installButton.onRelease(false);
	}
}
