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
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.AutoWrap;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackInOut;
import org.andengine.util.modifier.ease.EaseCubicInOut;

public class V3UpgradeBox {
	private VertexBufferObjectManager buffer;
	private ArrayList<ITextureRegion> interfaceBoxShadow = new ArrayList<ITextureRegion>();
	private Rectangle headerBg;
	private V3InterfaceButton upgradeButton = new V3InterfaceButton();
	
	private V3InterfaceBox box = new V3InterfaceBox();
	private Text headerText;
	private Text description;
	private Rectangle levelBackground;
	private Rectangle levelSlider;
	private Text levelText;
	private Sprite currencyIcon;
	private Text currencyValue;
	private Text costText;
	private int[] costArray = new int[15];
	private int currentUpgradeLevel = 0;
	private int length = 0;
	
	private Entity boxAssembled = new Entity();
	private Scene scene;
	private V3ColorManager colorManager = new V3ColorManager();
	
	
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
		box.setBuffer(buffer);
		upgradeButton.setBuffer(buffer);
	}
	public void setBoxShadows(ArrayList<ITextureRegion> array){
		interfaceBoxShadow = array;
	}
	public void setDescription(String string, Font font){
		description = new Text(45, 190, font, string, 1000, new TextOptions(AutoWrap.WORDS, 550f, HorizontalAlign.LEFT), buffer);
	}
	public void setHeader(String string, Font font){
		headerText = new Text(0, 0, font, string, 50, buffer);
	}
	public void setButtonRegions(ITextureRegion bgRegion, ITextureRegion colorRegion, ITextureRegion upgradeRegion){
		upgradeButton.setButtonBgRegion(bgRegion);
		upgradeButton.setButtonColorRegion(colorRegion);
		upgradeButton.setButtonIconRegion(upgradeRegion);
	}
	public void setButtonText(Text upgradeText){
		upgradeButton.setButtonText(upgradeText);
		
	}
	
	public void setCostText(String string, Font font){
		costText = new Text(0, 0, font, string, 50, buffer);
	}
	public void setCurrencyText(String string, Font font){
		currencyValue = new Text(0, 0, font, string, 50, buffer);
	}
	public void setCurrencyIcon(ITextureRegion region){
		currencyIcon = new Sprite(0, 0, region, buffer);
	}
	public void setCostArray(String[] cost){
		for(int i=0;i<costArray.length;i++){
			costArray[i] = 0;
		}
		for(int i=0;i<cost.length;i++){
			costArray[i] = Integer.parseInt(cost[i]);
		}
	}
	
	public void setLevelText(String string, Font font){
		levelText = new Text(0, 0, font, string, 10, buffer);
	}
	public void createUpgradeBox(Scene s, int x, int y, int colorUpgrade, int colorRectangle){
		scene = s;
		box.setShadowSprites(interfaceBoxShadow);
		box.buildBox(x, y-20, 790, 480);
		box.createBox(boxAssembled);
		box.setColor(0.902f,0.902f,0.902f);
		
		upgradeButton.buildButton(x-76, y+295, colorUpgrade, 0, -1);
		upgradeButton.createButton(boxAssembled, 0);
		
		
		headerBg = new Rectangle(x-365, y-230, 730, 62, buffer);
		headerBg.setColor(colorManager.getColor(colorRectangle));
		boxAssembled.attachChild(headerBg);
		
		levelBackground = new Rectangle(0, 0, 150, 200, buffer);
		levelBackground.setX(headerBg.getX()+headerBg.getWidth()-levelBackground.getWidth());
		levelBackground.setY(headerBg.getY()+headerBg.getHeight()+20);
		levelBackground.setColor(0.3f,0.3f,0.3f);
		boxAssembled.attachChild(levelBackground);
		
		levelSlider = new Rectangle(0, 0, 150, 1, buffer);
		levelSlider.setColor(colorManager.getColor(14));
		levelSlider.setX(levelBackground.getX());
		levelSlider.setY(levelBackground.getY()+levelBackground.getHeight()-levelSlider.getHeight());
		levelSlider.setScaleCenterY(1);
		boxAssembled.attachChild(levelSlider);
		
		length = 0;
		for(int i=0; i<costArray.length;i++){
			if(costArray[i] != 0){
				length = i+1;
			}
		}
		levelSlider.setScaleY((200/length)*currentUpgradeLevel);
		levelText.setText(String.valueOf(currentUpgradeLevel)+"/"+String.valueOf(length));
		levelText.setX(levelBackground.getX()+levelBackground.getWidth()/2-levelText.getWidth()/2);
		levelText.setY(levelBackground.getY()+levelBackground.getHeight()/2-levelText.getHeight()/2);
		boxAssembled.attachChild(levelText);
		
		description.setX(headerBg.getX());
		description.setY(headerBg.getY()+70);
		description.setColor(0.3f,0.3f,0.3f);
		boxAssembled.attachChild(description);

		headerText.setX(headerBg.getX()+headerBg.getWidth()/2 - headerText.getWidth()/2);
		headerText.setY(headerBg.getY()+headerBg.getHeight()/2 - headerText.getHeight()/2);
		headerText.setColor(0f,0f,0f);
		boxAssembled.attachChild(headerText);
		
		currencyValue.setX(headerBg.getX()+50);
		currencyValue.setY(levelBackground.getY()+levelBackground.getHeight()+10);
		currencyValue.setColor(0.5f,0.5f,0.5f);
		currencyValue.setText(String.valueOf(costArray[currentUpgradeLevel]));
		boxAssembled.attachChild(currencyValue);
		
		currencyIcon.setX(headerBg.getX());
		currencyIcon.setY(levelBackground.getY()+levelBackground.getHeight()+33);
		currencyIcon.setColor(0.3f,0.3f,0.3f);
		boxAssembled.attachChild(currencyIcon);
		
		costText.setX(headerBg.getX());
		costText.setY(currencyValue.getY()-costText.getHeight()+15);
		costText.setColor(0.3f,0.3f,0.3f);
		
		boxAssembled.attachChild(costText);
		
		scene.attachChild(boxAssembled);
		boxAssembled.setScaleCenter(x, y);
		boxAssembled.setScale(0);
		hideBox();
		
		
	}
	public void animateIn(float delay){
		showBox();
		final LoopEntityModifier slide = new LoopEntityModifier(new SequenceEntityModifier(new DelayModifier(delay), new ScaleModifier(0.4f,0,1, EaseBackInOut.getInstance())),1);
		boxAssembled.registerEntityModifier(slide);
		upgradeButton.animateIn(delay+0.2f);
		
		
	}
	public void animateOut(float delay){
		upgradeButton.animateOut(delay);
		
		
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
	public void showBox(){
		boxAssembled.setVisible(true);
	}
	public void hideBox(){
		boxAssembled.setVisible(false);
	}
	public Boolean isVisible(){
		return boxAssembled.isVisible();
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
	
	
	
	public float getButtonX(){
		return upgradeButton.getX()+boxAssembled.getX();
	}
	public float getButtonY(){
		return upgradeButton.getY();
	}
	public void pressButton(){
		upgradeButton.onPress();
	}
	public void releaseButton(){
		upgradeButton.onRelease(true);
	}

	public void setCurrentUpgradeLevel(int lvl){
		currentUpgradeLevel = lvl;
		levelSlider.setScaleY((levelBackground.getHeight()/length)*currentUpgradeLevel);
		levelText.setText(String.valueOf(currentUpgradeLevel)+"/"+String.valueOf(length));
		levelText.setX(levelBackground.getX()+levelBackground.getWidth()/2-levelText.getWidth()/2);
		currencyValue.setText(String.valueOf(costArray[currentUpgradeLevel]));
	}
	public Boolean animateUpgradePurchase(){
		Boolean upgradePossible = true;
		if(currentUpgradeLevel == length){
			upgradePossible = false;
		}
		else{
			currentUpgradeLevel++;
			final LoopEntityModifier scale1 = new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.3f, 1,1,(levelBackground.getHeight()/length)*(currentUpgradeLevel-1),(levelBackground.getHeight()/length)*currentUpgradeLevel,EaseCubicInOut.getInstance())),1);
			levelSlider.registerEntityModifier(scale1.deepCopy());
			levelText.setText(String.valueOf(currentUpgradeLevel)+"/"+String.valueOf(length));
			levelText.setX(levelBackground.getX()+levelBackground.getWidth()/2-levelText.getWidth()/2);
			currencyValue.setText(String.valueOf(costArray[currentUpgradeLevel]));
		}
		
		return upgradePossible;
	}
	public int getCost(){
		return costArray[currentUpgradeLevel];
	}
	public int getCostBeforeUpgrade(){
		return costArray[currentUpgradeLevel-1];
	}
	public int getCurrentUpgradeLevel(){
		return currentUpgradeLevel;
	}
}
