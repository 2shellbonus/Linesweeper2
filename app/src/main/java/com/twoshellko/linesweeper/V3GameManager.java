package com.twoshellko.linesweeper;

import android.content.SharedPreferences;
import android.util.Log;

public class V3GameManager {
	/*
	 * Line Type 1 = Bomb
	 * Line Type 2 = Heart
	 * Line Type 3 = Lock
	 * Line Type 4 = Snowflake
	 * Line Type 5 = Bulb
	 * Line Type 6 = Multiplier
	 * Line Type 7 = Coin
	 * Line Type 8 = Diamond
	 * Line Type 9 = Lightning
	 */
	private int[] specialLifeBaseMax = {1500,1350,5,1200,1350,1050,900,1050,900};
	private int[] specialLifeMax = {1500,1350,5,1200,1350,1050,900,1050,900};
	private int[] specialLife = {1500,1350,5,1200,1350,1050,900,1050,900};
	private int lifeRate = 2;
	private Boolean showBombBg = false;
	
	private Boolean freezeActivated = false;
	private Boolean bulbActivated = false;
	private Boolean activateLightning = false;
	private Boolean[] specials = {false,false,false,
			false,false,false,false,
			false,false};
	
	private int freezeLife = 1050;
	private int freezeLifeMax = 1050;
	private int heartBonus = 1500;
	private int bulbLife = 1500;
	private int bulbLifeMax = 1500;
	private int addLife = 150;
	private int gameLifeMax = 20000;
	private int gameLife = 20000;
	private int gameLifeRate = 4;
	private Boolean gameover= false;
	
	private int score = 0;
	private int multiplier = 1;
	private int multiplierMax = 1;
	private int multiplierAddValue = 1;
	private int diamondAddValue = 5;
	private int linesRemoved = 0;
	private int noError = 0;
	private int maxCombo = 0;
	private int touches = 0;
	
	private int coinValue = 0;
	private int coinAdd = 10;
	
	private int lightningRemoved = 0;
	private int lightningMaxRemove = 10;
	
	private int gametype = 1;
	private int extraLives = 0;
	private int extraLivesMax = 0;
	private SharedPreferences settings;
	
	
	public void setSharedPreferences(SharedPreferences s){
		settings = s;
		coinValue = settings.getInt("coins", 0);
	}
	
	public void setGametype(int type){
		gametype = type;
	}
	public Boolean[] getSpawnedSpecials(){
		return specials;
	}
	public void setSpecialSpawnStatus(int type, Boolean status){
		specials[type-1] = status;
		specialLife[type-1] = specialLifeMax[type-1];
		
	}
	public void updateSpecialLife(){
		
		for(int i=0;i<specials.length;i++){
			if(specials[i] && i !=2){
				int currentLifeRate = lifeRate;
				if(freezeActivated){
					currentLifeRate = lifeRate/2;
				}
				specialLife[i] = specialLife[i] - currentLifeRate;
			}
		}
		if(specialLife[0] < specialLifeMax[0]/2){
			showBombBg = true;
		}
		else{
			showBombBg = false;
		}
		if(specialLife[0] <=0){
			gameover = true;
		}
	}
	public void updateLockLife(int life){
		specialLife[2] = life;
	}
	public int[] getSpecialLife(){
		return specialLife;
	}
	public int[] getSpecialLifeMax(){
		return specialLifeMax;
	}
	public void activateBonus(int type){
		if(type == 2){
			//HEART BONUS
			gameLife = gameLife + heartBonus;
			if(gameLife>gameLifeMax){
				gameLife = gameLifeMax;
			}
		}
		if(type == 4){
			freezeActivated = true;
			freezeLife = freezeLifeMax;
		}
		if(type == 5){
			bulbActivated = true;
			bulbLife = bulbLifeMax;
		}
		if(type == 6){
			multiplier = multiplier + multiplierAddValue;
		}
		if(type == 7){
			updateCoinValue(coinAdd);
		}
		if(type == 8){
			score = score + diamondAddValue*multiplier;
		}
		if(type == 9){
			activateLightning = true;
		}
	}
	public Boolean updateGameState(Boolean startGame){
		Boolean lifeConsumed = false;
		if(freezeActivated){
			freezeLife = freezeLife - lifeRate/2;
			if(freezeLife<=0){
				freezeActivated = false;
				freezeLife = freezeLifeMax;
			}
		}
		if(bulbActivated){
			if(freezeActivated){
				bulbLife = bulbLife-lifeRate/2;
			}
			else{
				bulbLife = bulbLife-lifeRate;
			}
			if(bulbLife<=0){
				bulbActivated = false;
				bulbLife = bulbLifeMax;
			}
		}
		if(gameover == false && startGame){
			if(freezeActivated){
				gameLife = gameLife - gameLifeRate/2;
			}
			else{
				gameLife = gameLife - gameLifeRate;
			}
			if(gametype == 1){
				if(gameLife <=0){
					gameover = true;
				}
			}
			if(gametype == 2 && gameLife <=0){
				if(extraLives >0){
					extraLives--;
					lifeConsumed = true;
					gameLife=gameLifeMax;
				}
				else{
					gameover = true;
				}
			}
			
			
		}
		updateGameLifeRate();
		return lifeConsumed;
	}
	public int getGameLife(){
		return gameLife;
	}
	public void setGameLifeMax(int life){
		gameLifeMax = life*67;
		gameLife = gameLifeMax;
	}
	public int getGameLifeMax(){
		return gameLifeMax;
	}
	private void updateGameLifeRate(){
		if(gametype == 1){
			gameLifeRate = 4;
			lifeRate = 2;
			if(linesRemoved > 10){
				gameLifeRate = 6;
			}
			if(linesRemoved > 20){
				gameLifeRate = 8;
				lifeRate = 4;
			}
			if(linesRemoved > 30){
				gameLifeRate = 10;
			}
			if(linesRemoved > 40){
				gameLifeRate = 12;
				lifeRate = 6;
			}
			if(linesRemoved > 60){
				gameLifeRate = 14;
			}
			if(linesRemoved > 80){
				gameLifeRate = 16;
			}
			if(linesRemoved > 100){
				gameLifeRate = 18;
				lifeRate = 8;
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
				lifeRate = 10;
			}
			if(linesRemoved > 260){
				gameLifeRate = 28;
			}
			if(linesRemoved > 300){
				gameLifeRate = 30;
				
			}
			if(linesRemoved > 350){
				gameLifeRate = 32;
				lifeRate = 12;
			}
		}
		if(gametype == 2){
			gameLifeRate = 300;
			lifeRate = 6;
		}
	}
	public Boolean isGameOver(){
		if(gameLife <=0){
			gameover = true;
		}
		return gameover;
	}
	
	public void activateWrongClick(){
		touches++;
		if(gametype == 1){
			gameLife = gameLife - addLife;
			multiplier = 1;
		}
		
		noError = 0;
		
		if(gametype == 2){
			if(extraLives >0){
				extraLives--;
			}
			else{
				gameover = true;
			}
		}
	}
	public void updateLinesRemoved(){
		linesRemoved++;
	}
	public void activateLineRemovedBonus(){
		touches++;
		if(gametype == 1){
			gameLife = gameLife + addLife;
			if(gameLife>gameLifeMax){
				gameLife=gameLifeMax;
			}
		}
		if(gametype == 2){
			
			gameLife=gameLifeMax;
		}
		noError++;
		if(maxCombo <= noError){
			maxCombo = noError;
		}
	}
	public int getLinesRemoved(){
		return linesRemoved;
	}
	public int getMaxCombo(){
		return maxCombo;
	}
	public void updateScore(){
		score = score + multiplier;
	}
	public int getScore(){
		return score;
	}
	public int getBestScore(){
		int bestscore = 0;
		bestscore = settings.getInt("arcadeBest", 0);
		if(score > bestscore){
			bestscore = score;
			SharedPreferences.Editor prefEditor = settings.edit();
			prefEditor.putInt("arcadeBest", bestscore);
			prefEditor.commit();
		}
		return bestscore;
	}
	public Boolean setMultiplier(){
		Boolean sound = false;
		if(gametype == 1){
			int multiplierOld = multiplier;
			
			if(3 + 2*(multiplier*multiplier) < noError){
				multiplier++;
			}
			if(noError == 0 ){
				multiplier = 1;
			}
			if(multiplierOld<multiplier){
				sound = true;
			}
			if(multiplier>=multiplierMax){
				multiplierMax = multiplier;
			}
		}
		if(gametype == 2){
			if(gameLife > 0){
				multiplier = 0;
			}
			if(gameLife > 67*100){
				multiplier = 1;
			}
			if(gameLife > 67*200){
				multiplier = 2;
			}
			if(gameLife > 67*300){
				multiplier = 3;
			}
			if(gameLife > 67*400){
				multiplier = 4;
			}
		}
		return sound;
	}
	public int getMultiplier(){
		return multiplier;
	}
	public int getMaxMultiplier(){
		return multiplierMax;
	}
	public Boolean getLightningState(){
		if(activateLightning){
			lightningRemoved++;
		}
		if(lightningRemoved>=lightningMaxRemove){
			resetLightningState();
			lightningRemoved = 0;
		}
		return activateLightning;
	}
	public void resetLightningState(){
		activateLightning = false;
	}
	public void updateCoinValue(int value){
		coinValue = coinValue + value;
		SharedPreferences.Editor prefEditor = settings.edit();
		prefEditor.putInt("coins", coinValue);
		prefEditor.commit();
	}
	public int getCoinValue(){
		return coinValue;
	}
	public void setCoinAddValue(int value){
		coinAdd = value;
	}
	public void setLightningRemoveNumber(int value){
		lightningMaxRemove = value;
	}
	public Boolean getFreezeState(){
		return freezeActivated;
	}
	public Boolean getBombState(){
		return showBombBg;
	}
	public Boolean getBulbState(){
		return bulbActivated;
	}
	public void setBulbLifeValue(int value){
		bulbLifeMax = value;
	}
	public void setFreezeLifeValue(int value){
		freezeLifeMax = value;
	}
	public int getFreezeLife(){
		return freezeLife;
	}
	public void setFreezeLife(int life){
		freezeLife = life;
	}
	public int getBulbLife(){
		return bulbLife;
	}
	public void setBulbLife(int life){
		bulbLife = life;
	}
	public void setSpecialLifeCoefficient(float coeff){
		for(int i=0;i<specialLifeMax.length;i++){
			if(i !=2){
				specialLifeMax[i] = (int) (specialLifeBaseMax[i]*coeff);
				specialLife[i]  = (int) (specialLifeBaseMax[i]*coeff);
			}
			
		}
		
	}
	public void setLockMaxLife(int maxLife){
		specialLifeMax[2] = maxLife;
		specialLifeBaseMax[2] = maxLife;
	}
	public void setMultiplierAddBonus(int value){
		multiplierAddValue = value;
	}
	public void setDiamondBonus(int value){
		diamondAddValue = value;
	}
	public void activateLifeBonus(){
		gameLife = gameLifeMax;
	}
	public void resetGameState(){
		for(int i=0;i<specials.length;i++){
			specials[i] = false;
			specialLife[i] = specialLifeMax[i];
		}
		freezeActivated = false;
		bulbActivated = false;
		activateLightning = false;
		showBombBg = false;
		gameLife = gameLifeMax;
		score = 0;
		multiplier = 1;
		linesRemoved = 0;
		noError = 0;
		gameLifeRate = 4;
		gameover= false;
		maxCombo = 0;
		multiplierMax=1;
		touches = 0;
		extraLives = extraLivesMax;
	}
	public void continueGameBonus(){
		gameover= false;
		gameLife = gameLifeMax;
	}
	public int getAccuracy(){
		int intAccuracy = 0;
		float accuracy = 0;
		accuracy = (float)linesRemoved/touches;
		accuracy = Math.round(accuracy*100);
		Log.d("ACCURACY LINES", String.valueOf(linesRemoved));
		Log.d("ACCURACY TOUCH", String.valueOf(touches));
		intAccuracy = (int) accuracy;
		return intAccuracy;
	}
	
	
	public void setExtraMineLives(int lives){
		extraLives = lives;
		extraLivesMax = lives;
	}
	public int getExtraMineLives(){
		return extraLives;
	}
	
	
	public void saveLevel(SharedPreferences s){
		SharedPreferences settingsGametype = s;
		SharedPreferences.Editor prefEditor = settingsGametype.edit();
		String specialLifeString = String.valueOf(specialLife[0]);
		String specialsString = String.valueOf(specials[0]);
		for(int i=1; i<specials.length;i++){
			specialLifeString = specialLifeString + ";" + String.valueOf(specialLife[i]);
			specialsString = specialsString + ";" + String.valueOf(specials[i]);
			
		}
		prefEditor.putString("specialLifeString", specialLifeString);
		prefEditor.putString("specialsString", specialsString);
		prefEditor.putInt("noError", noError);
		prefEditor.putInt("touches", touches);
		prefEditor.putInt("maxCombo", maxCombo);
		prefEditor.putInt("linesRemoved", linesRemoved);
		prefEditor.putInt("multiplier", multiplier);
		prefEditor.putInt("score", score);
		prefEditor.putInt("gameLife", gameLife);
		prefEditor.putInt("lifeRate", lifeRate);
		prefEditor.putBoolean("freezeActivated", freezeActivated);
		prefEditor.putInt("freezeLife", freezeLife);
		prefEditor.putBoolean("bulbActivated", bulbActivated);
		prefEditor.putInt("bulbLife", bulbLife);
		prefEditor.putBoolean("gameover", gameover);
		prefEditor.putInt("extraLives", extraLives);
		prefEditor.commit();
	}
	public void restoreLevel(SharedPreferences s){
		
		String specialLifeString = s.getString("specialLifeString","0");
		String specialsString = s.getString("specialsString","0");
		//Log.d("SPECIALS", specialsString);
		String[] separatedSpecialLife = specialLifeString.split(";");
		String[] separatedSpecials = specialsString.split(";");
		for(int i=0; i<specials.length;i++){
			specials[i] = Boolean.parseBoolean(separatedSpecials[i]);
			specialLife[i] = Integer.parseInt(separatedSpecialLife[i]);
		}
		noError = s.getInt("noError", 0);
		touches = s.getInt("touches", 0);
		maxCombo = s.getInt("maxCombo", 0);
		linesRemoved = s.getInt("linesRemoved", 0);
		multiplier = s.getInt("multiplier", 0);
		score = s.getInt("score", 0);
		gameLife = s.getInt("gameLife", gameLifeMax);
		lifeRate = s.getInt("lifeRate", 2);
		freezeActivated = s.getBoolean("freezeActivated", false);
		freezeLife = s.getInt("freezeLife", 0);
		bulbActivated = s.getBoolean("bulbActivated", false);
		bulbLife = s.getInt("bulbLife", 0);
		gameover = s.getBoolean("gameover", false);
		extraLives = s.getInt("extraLives", extraLives);
	}
}
