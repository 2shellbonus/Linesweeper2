package com.twoshellko.linesweeper;

import java.util.concurrent.TimeUnit;

import android.content.SharedPreferences;
import android.util.Log;

public class GameManager {
	private Boolean gameover= false;
	private int gametype = 1;
	private int score = 0;
	private int gameLifeMax = 20000;
	private int gameLife = 20000;
	private int gameLifeRate = 300;
	private int linesRemoved = 0;
	private Boolean showBombBg = false;
	private SharedPreferences settings;

	private int speedUpdateTicks = 0;
	private int speedInitialLineCount = 0;
	private int speedMessage = 0;
	private int[] speedArrayTicks = {0,0,0,0,0,0,0};
	private int[] speedArrayLinesRemoved = {0,0,0,0,0,0,0};
	private int speedTicks = 0;
	private float speed = 0;
	private Boolean shownMessageGood = false;
	private Boolean shownMessageAmazing = false;
	private Boolean shownMessageExcellent = false;
	private Boolean aboveGood = false;
	private Boolean aboveAmazing = false;
	private Boolean aboveExcellent = false;

	//TIMEFRENZY
	private int gameTimeMs = 15000;
	private int gameTimeStart = 15000;

	public void setSharedPreferences(SharedPreferences s){
		settings = s;
	}
	public void setGametype(int type){
		gametype = type;
	}
	public int getGameLife(){
		return gameLife;
	}
	public int getGameLifeMax(){
		return gameLifeMax;
	}
	public void updateGameState(Boolean startGame){
		if(gameover == false && startGame && gametype == 1){
			gameLife = gameLife - gameLifeRate;

			if(gametype == 1){
				if(gameLife <=0){
					gameover = true;
				}
			}
		}
		if(gameover == false && startGame && gametype == 2){
			gameTimeMs = gameTimeMs-33;
			if(gameTimeMs <=0){
				gameover = true;
			}
		}
		updateGameLifeRate();
		//updateSpeedStats();
		updateSpeedTicks();
		calculateSpeed();
	}
	private void updateGameLifeRate(){
		/*if(gametype == 1){
			gameLifeRate = 4;
			if(linesRemoved > 10){
				gameLifeRate = 6;
			}
			if(linesRemoved > 20){
				gameLifeRate = 8;
			}
			if(linesRemoved > 30){
				gameLifeRate = 10;
			}
			if(linesRemoved > 40){
				gameLifeRate = 12;
			}
			if(linesRemoved > 60){
				gameLifeRate = 14;
			}
			if(linesRemoved > 80){
				gameLifeRate = 16;
			}
			if(linesRemoved > 100){
				gameLifeRate = 18;
			}
			if(linesRemoved > 130){
				gameLifeRate = 20;
			}
			if(linesRemoved > 160){
				gameLifeRate = 22;
			}
			if(linesRemoved > 190){
				gameLifeRate = 24;
			}
			if(linesRemoved > 220){
				gameLifeRate = 26;
			}
			if(linesRemoved > 260){
				gameLifeRate = 28;
			}
			if(linesRemoved > 300){
				gameLifeRate = 30;
				
			}
			if(linesRemoved > 350){
				gameLifeRate = 32;
			}
		}*/
	}
	public Boolean isGameOver(){
		if(gameLife <=0){
			gameover = true;
		}
		return gameover;
	}
	public void updateLinesRemoved(){
		linesRemoved++;
		updateSpeed();
	}
	public void activateLineRemovedBonus(){
		if(gametype == 1){
			gameLife=gameLifeMax;
		}
	}
	public void updateScore(){
		if(gametype == 1){
			score = score + 1;
			gameLifeRate = gameLifeRate +4;
		}
		if(gametype == 2){
			score = score + 1;
		}

	}
	public int getScore(){
		return score;
	}
	public void setScore(int s){
		score = s;
	}
	public int getBestScore(int gametype){
		//Log.d("GAMETYPE", String.valueOf(value))
		int bestscore = 0;
		if(gametype == 1){
			bestscore = settings.getInt("arcadeBest", 0);
			if(score > bestscore){
				bestscore = score;
				SharedPreferences.Editor prefEditor = settings.edit();
				prefEditor.putInt("arcadeBest", bestscore);
				prefEditor.commit();
			}
		}
		if(gametype == 2){
			bestscore = settings.getInt("frenzyBest", 0);
			if(score > bestscore){
				bestscore = score;
				SharedPreferences.Editor prefEditor = settings.edit();
				prefEditor.putInt("frenzyBest", bestscore);
				prefEditor.commit();
			}
		}

		return bestscore;
	}
	public void setGameOver(){
		gameover = true;
	}
	public void resetGameState(){
		showBombBg = false;
		gameLife = gameLifeMax;
		score = 0;
		linesRemoved = 0;
		gameLifeRate = 300;
		gameover= false;
		speedTicks = 0;
		speed = 0;
		shownMessageGood = false;
		shownMessageAmazing = false;
		shownMessageExcellent = false;
		aboveGood = false;
		aboveAmazing = false;
		aboveExcellent = false;
		for(int i = 0; i< 7; i++){
			speedArrayTicks[i] = 0;
			speedArrayLinesRemoved[i] = 0;
		}
		gameTimeMs = gameTimeStart;
	}
	public Boolean getBombState(){
		return showBombBg;
	}
	public void setWrongTap(){
		if(gametype == 1){
			gameover = true;
		}
		if(gametype == 2){
			gameTimeMs = gameTimeMs - 1000;
			int earliestMember = getMinInRemovedArray();
			speedArrayLinesRemoved[earliestMember] = linesRemoved;
			speedArrayTicks[earliestMember] = 99;
		}
	}
	public int getLinesRemoved(){
		return linesRemoved;
	}

	private void updateSpeedStats(){
		if(speedUpdateTicks == 0){
			speedInitialLineCount = linesRemoved;
			speedMessage = 0;
		}
		speedUpdateTicks++;
		if(speedUpdateTicks == 30){
			if(linesRemoved>=speedInitialLineCount+2 && linesRemoved<speedInitialLineCount+5 && linesRemoved >7){
				speedMessage = 1;
			}
			if(linesRemoved>=speedInitialLineCount+5 && linesRemoved<speedInitialLineCount+8){
				speedMessage = 2;
			}
			if(linesRemoved>=speedInitialLineCount+8){
				speedMessage = 3;
			}
			speedUpdateTicks = 0;
		}
	}
	public int getSpeedMessage(){
		speedMessage = 0;
		if(aboveGood && !shownMessageGood){
			speedMessage = 1;
			if(gametype == 2){
				gameTimeMs = gameTimeMs + 500;
			}
			if(gametype == 1){
				gameLifeRate = gameLifeRate - 3*4;
			}
			shownMessageGood = true;
		}
		if(aboveExcellent && !shownMessageExcellent){
			speedMessage = 2;
			if(gametype == 2){
				gameTimeMs = gameTimeMs + 1000;
			}
			if(gametype == 1){
				gameLifeRate = gameLifeRate - 7*4;
			}
			shownMessageExcellent = true;
		}
		if(aboveAmazing && !shownMessageAmazing){
			speedMessage = 3;
			if(gametype == 2){
				gameTimeMs = gameTimeMs + 2000;
			}
			if(gametype == 1){
				gameLifeRate = gameLifeRate - 15*4;
			}
			shownMessageAmazing = true;
		}
		return speedMessage;
	}
	private void updateSpeed(){
		int earliestMember = getMinInRemovedArray();
		speedArrayLinesRemoved[earliestMember] = linesRemoved;
		speedArrayTicks[earliestMember] = speedTicks;
		speedTicks = 0;
	}
	private void updateSpeedTicks(){
		speedTicks++;
	}
	private void calculateSpeed(){
		Boolean canCalculate = true;
		for(int i = 0;i<7;i++){
			if(speedArrayLinesRemoved[i] == 0){
				canCalculate = false;
			}
		}
		if(canCalculate){
			float average = 0;
			for(int i = 0;i<7;i++){
				average = average +speedArrayTicks[i];
			}
			average = average/7;
			speed = average;
			if(speed < 9){
				aboveAmazing = true;
			}
			if(speed > 17){
				aboveAmazing = false;
				shownMessageAmazing= false;
			}
			if(speed < 15){
				aboveExcellent = true;
			}
			if(speed > 25){
				aboveExcellent = false;
				shownMessageExcellent= false;
			}
			if(speed < 20){
				aboveGood = true;
			}
			if(speed > 30){
				aboveGood = false;
				shownMessageGood= false;
			}
			//Log.d("AVERAGE TICKS", String.valueOf(average));
		}
	}
	public float getSpeed(){
		return speed;
	}
	private int getMinInRemovedArray(){
		int j = 0;
		int smallestValue = speedArrayLinesRemoved[0];
		for(int i = 0;i<7;i++){
			if(speedArrayLinesRemoved[i] < smallestValue){
				j = i;
			}
		}
		return j;
	}

	public int getFrenzyGameTime(){
		return gameTimeMs;
	}
	public String getTime(){
		int tempTime = 0;
		if(gameTimeMs >=0){
			tempTime = gameTimeMs;
		}
		else{
			tempTime = 0;
		}
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tempTime);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(tempTime);// - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(gameTimeMs));
		long ms = tempTime - TimeUnit.MILLISECONDS.toSeconds(tempTime)*1000;
		//long ms = gameTimeMs - seconds;
		//int seconds1 = (int) (gameTimeMs / 1000) % 60 ;
		//ms = gameTimeMs - seconds1;
		//String.valueOf(minutes)+":"+
		String time = String.valueOf(seconds)+"."+String.valueOf(ms/10);
		return time;
	}
	/*public String getBestTime(){
		//bestscore = settings.getInt("frenzyBest", 999999999);
		//Log.d("RESULT", String.valueOf(gameTimeMs));
		//Log.d("BEST", String.valueOf(bestscore));
		
		
		int bestscore = settings.getInt("frenzyBest", 999999999);
		if(gameTimeMs < bestscore && gameTimeMs != 0 && gameover){
			bestscore = gameTimeMs;
			SharedPreferences.Editor prefEditor = settings.edit();
			prefEditor.putInt("frenzyBest", gameTimeMs);
			prefEditor.commit();
		}
		if(bestscore == 999999999){
			bestscore = 99999;
		}
		long minutes = TimeUnit.MILLISECONDS.toMinutes(bestscore);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(bestscore);// - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(bestscore));
		long ms = bestscore - TimeUnit.MILLISECONDS.toSeconds(bestscore)*1000;
		//long ms = gameTimeMs - seconds;
		//int seconds1 = (int) (gameTimeMs / 1000) % 60 ;
		//ms = gameTimeMs - seconds1;
		//String.valueOf(minutes)+":"+
		String time = String.valueOf(seconds)+"."+String.valueOf(ms/10);
		return time;
	}*/
	public int getGameTime(){
		return gameTimeMs;
	}
}
