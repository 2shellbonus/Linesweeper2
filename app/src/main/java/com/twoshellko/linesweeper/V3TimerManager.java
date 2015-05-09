package com.twoshellko.linesweeper;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;

public class V3TimerManager {
	private ArrayList<V3Timer> timerArray = new ArrayList<V3Timer>();
	private VertexBufferObjectManager buffer;
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	private ITextureRegion myDarkCenterRegion;
	private ITextureRegion myDarkCapRegion;
	private Scene scene;
	private ArrayList<ITextureRegion> lineLeftArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> lineStretchHorizontalArray = new ArrayList<ITextureRegion>();
	private ArrayList<ITextureRegion> iconArray = new ArrayList<ITextureRegion>();
	private int numberOfTimers = 0;
	private int[] typeLifeMax = new int[9];
	private int[] typeLife = new int[9];
	
	public void setCameraSize(int width, int height){
		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;
	}
	public void setBuffer(VertexBufferObjectManager buf){
		buffer = buf;
	}
	public void setCapArray(ArrayList<ITextureRegion> array){
		lineLeftArray = array;
	}
	public void setStretchArray(ArrayList<ITextureRegion> array){
		lineStretchHorizontalArray = array;
	}
	public void setIconArray(ArrayList<ITextureRegion> array){
		iconArray = array;
	}
	public void setDarkCap(ITextureRegion region){
		myDarkCapRegion = region;
	}
	public void setDarkCenter(ITextureRegion region){
		myDarkCenterRegion = region;
	}
	
	public void setScene(Scene sc){
		scene = sc;
	}
	
	public void createTimer(int color, int type){
		numberOfTimers++;
		V3Timer timer = new V3Timer();
		timer.setBuffer(buffer);
		timer.setTimerType(type);
		timer.setLineCap(lineLeftArray.get(color));
		timer.setLineRegion(lineStretchHorizontalArray.get(color));
		timer.setDarkLineCap(myDarkCapRegion);
		timer.setDarkLineCenter(myDarkCenterRegion);
		timer.setCameraSize(CAMERA_WIDTH, CAMERA_HEIGHT);
		timer.setIcon(iconArray.get(type-1));
		timer.createTimer(scene, numberOfTimers);
		timer.setTimerLocation(numberOfTimers);
		timerArray.add(timer);
	}
	public void spawnTimer(int index){
		timerArray.get(index).animateInTimer();
	}
	public void setMaxLife(int[] array){
		typeLifeMax = array;
	}
	public void setLife(int[] array){
		typeLife = array;
		for(int i=0;i<timerArray.size();i++){
			if(timerArray.get(i).updateLife(typeLifeMax[timerArray.get(i).getTimerType()-1], typeLife[timerArray.get(i).getTimerType()-1])){
				removeTimer(timerArray.get(i).getTimerType());
			}
		}
	}
	public void removeTimer(int type){
		//REMOVE TIMER
		//MOVE TIMERS UP
		for(int i=0;i<timerArray.size();i++){
			if(timerArray.get(i).getTimerType() == type && timerArray.get(i).isAnimatingOut()==false){
				timerArray.get(i).animateOutTimer();
				if(numberOfTimers>0){
					numberOfTimers--;
				}
				for(int j=0;j<timerArray.size();j++){
					if(j != i && timerArray.get(j).isAnimatingOut() == false && timerArray.get(j).getTimerLocation() > timerArray.get(i).getTimerLocation()){
						timerArray.get(j).moveUp();
					}
				}
				//remove this timer
				//move those with location higher up
			}
		}
	}
	public void cleanUpTimers(){
		for(int i=0;i<timerArray.size();i++){
			if(timerArray.get(i).isRemovable()){
				timerArray.get(i).removeTimer();
				timerArray.remove(i);
			}
		}
	}
	public void forceRemoveTimers(){
		for(int i=0;i<timerArray.size();i++){
			timerArray.get(i).removeTimer();
		}
		timerArray.clear();
		numberOfTimers = 0;
	}
}
