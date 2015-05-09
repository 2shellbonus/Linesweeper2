package com.twoshellko.linesweeper;

import android.util.Log;

public class V3GameGenerator {
	private int xBoundLeft;
	private int xBoundRight;
	private int yBoundTop;
	private int yBoundBottom;
	
	public void setBounds(int xLeft, int xRight, int yTop, int yBottom){
		xBoundLeft = xLeft;
		xBoundRight = xRight;
		yBoundTop = yTop;
		yBoundBottom = yBottom;
	}
	
	public Boolean generateLineOrientation(Boolean prevHorizontal){
		Boolean horizontal = true;
		if(prevHorizontal){
			int lineOrientation = (int) Math.round (Math.random()*3);
			if(lineOrientation == 0 || lineOrientation == 1 || lineOrientation ==2){
				horizontal = false;
			}
			else{
				horizontal = true;
			}
			//horizontal = false;
		}
		else{
			int lineOrientation = (int) Math.round (Math.random()*3);
			if(lineOrientation == 0 || lineOrientation == 1 || lineOrientation ==2){
				horizontal = true;
			}
			else{
				horizontal = false;
			}
		}
		return horizontal;
	}
	public int generateLineColor(){
		int color = (int) Math.round(Math.random()*13);
		return color;
	}
	private int[] generateDimensions(int prevX1, int prevY1, int prevX2, int prevY2, Boolean prevOrientation, Boolean currentHorizontal ){
		int[] dimensions = {0,0,0,0,0};
		//int horizontal = 0;
		int minSize = 250;
		int xStart = xBoundLeft;
		int xEnd = xBoundRight;
		int yStart = yBoundTop;
		int yEnd = yBoundBottom; 
		int overlap = 60;
		int textureSize = 106;
		
		//horizontal = (int) Math.round (Math.random());
		//Get Line Color
		//Get Line orientation
		//Get line size and position
		if (currentHorizontal == true){
				
				if (prevOrientation == true){   			//If this is a horizontal line and the previous one was also horizontal
					
					//////////////////////////////////////////////////////////////////
					// Generate the Y position of the horizontal bar				//
					//////////////////////////////////////////////////////////////////
					double yPos = Math.random();
					dimensions[1] = (int) (prevY1 - overlap + Math.round(yPos*2*overlap)); 
					if(Math.round(yPos) == 1){
						dimensions[1] = prevY1 + overlap;
					}
					else{
						dimensions[1] = prevY1 - overlap;
					}
					if (dimensions[1] < yStart){
						dimensions[1] = prevY1 + overlap;
					}
					if (dimensions[1] > yEnd-textureSize){
						dimensions[1] = prevY1 - overlap;
					}
					dimensions[3] = dimensions[1] + textureSize;
					////////////////////////////////////////////////////////////////// 
					// Generate the X positions of the horizontal bar				// 
					//////////////////////////////////////////////////////////////////
					double x1Pos = Math.random();
					if((xEnd - minSize) >= prevX2){
						dimensions[0] = xStart + (int) Math.round(x1Pos*(prevX2 - xStart - overlap));
					}
					if((xEnd - minSize) < prevX2){
						//dimensions[0] = xStart + (int) Math.round(x1Pos*(xEnd - minSize - xStart));
						dimensions[0] = xStart + (int) Math.round(x1Pos*(xEnd - minSize - xStart - overlap));
					}
					/*if(dimensions[0] >= prevX2){
						dimensions[0] = prevX2 -30;
					}*/
					double xScale = Math.random();
					if (dimensions[0] + minSize < prevX1){
						//dimensions[2] = dimensions[0] + minSize + (prevX1 - dimensions[0] - minSize + overlap) + (int) Math.round((xEnd - prevX1 - overlap)*xScale);
						dimensions[2] = prevX1 + overlap + (int) Math.round((xEnd - prevX1 - overlap)*xScale);
					}
					if (dimensions[0] + minSize >= prevX1){
						//dimensions[2] = dimensions[0] + minSize + (int) Math.round((xEnd - dimensions[0] - minSize)*xScale);
						dimensions[2] = dimensions[0] + minSize + overlap + (int) Math.round((xEnd - dimensions[0] - minSize - overlap)*xScale);
					}
				}
				if (prevOrientation == false){
					//////////////////////////////////////////////////////////////////
					// Generate the Y position of the horizontal bar				//
					//////////////////////////////////////////////////////////////////
					double yPos = Math.random();
					dimensions[1] = prevY1 + (int) Math.round((prevY2 - prevY1 - textureSize)*yPos);
					dimensions[3] = dimensions[1] + textureSize;

					//////////////////////////////////////////////////////////////////
					// Generate the X positions of the horizontal bar				//
					////////////////////////////////////////////////////////////////// 
					double x1Pos = Math.random();
					if((xEnd - minSize) >= prevX2){
						dimensions[0] = xStart + (int) Math.round(x1Pos*(prevX2 - xStart - overlap));
					}
					if((xEnd - minSize) < prevX2){
						//dimensions[0] = xStart + (int) Math.round(x1Pos*(xEnd - minSize - xStart));
						dimensions[0] = xStart + (int) Math.round(x1Pos*(xEnd - minSize - xStart - overlap));
					}
					//Log.d("XCOORD", String.valueOf(dimensions[0]));
					/*if(dimensions[0] >= prevX2){
						dimensions[0] = prevX2 -10;
					}*/
					double xScale = Math.random();
					if (dimensions[0] + minSize < prevX1){
						//dimensions[2] = dimensions[0] + minSize + (prevX1 - dimensions[0] - minSize + overlap) + (int) Math.round((xEnd - prevX1 - overlap)*xScale);
						dimensions[2] = prevX1 + overlap + (int) Math.round((xEnd - prevX1 - overlap)*xScale);

					}
					if (dimensions[0] + minSize >= prevX1){
						//dimensions[2] = dimensions[0] + minSize + (int) Math.round((xEnd - dimensions[0] - minSize)*xScale); 
						dimensions[2] = dimensions[0] + minSize + overlap + (int) Math.round((xEnd - dimensions[0] - minSize - overlap)*xScale);

					}
				}
		}
		if (currentHorizontal == false){
				if (prevOrientation == false){
					//////////////////////////////////////////////////////////////////
					// Generate the X position of the vertical bar				//
					//////////////////////////////////////////////////////////////////
					double xPos = Math.random();
					//dimensions[0] = (int) (prevX1 - overlap + Math.round(xPos*2*overlap)); 
					if(Math.round(xPos)==1){
						dimensions[0] = prevX1 + overlap;
					}
					else{
						dimensions[0] = prevX1 - overlap;
					}
					
					if (dimensions[0] < xStart){
					dimensions[0] = prevX1 + overlap;
					}
					if (dimensions[0] > xEnd-textureSize){
						dimensions[0] = prevX1 - overlap;
					}
					
					
					dimensions[2] = dimensions[0] + textureSize;
					////////////////////////////////////////////////////////////////// 
					// Generate the Y positions of the vertical bar				// 
					//////////////////////////////////////////////////////////////////
					double y1Pos = Math.random();
					//y1Pos =0;
					if((yEnd - minSize) >= prevY2){
						dimensions[1] = yStart + (int) Math.round(y1Pos*(prevY2 - yStart - overlap));
					}
					if((yEnd - minSize) < prevY2){
						//dimensions[1] = yStart + (int) Math.round(y1Pos*(yEnd - minSize - yStart));
						dimensions[1] = yStart + (int) Math.round(y1Pos*(yEnd - minSize - yStart - overlap));
					}
					/*if(dimensions[1] >= prevY2){
						dimensions[1] = prevY2 -30;
					}*/
					double yScale = Math.random();
					if (dimensions[1] + minSize < prevY1){
						//dimensions[3] = dimensions[1] + minSize + (prevY1 - dimensions[1] - minSize + overlap) + (int) Math.round((yEnd - prevY1 - overlap)*yScale);
						dimensions[3] = prevY1 + overlap + (int) Math.round((yEnd - prevY1 - overlap)*yScale);
					}
					if (dimensions[1] + minSize >= prevY1){
						//dimensions[3] = dimensions[1] + minSize + (int) Math.round((yEnd - dimensions[1] - minSize)*yScale); 
						dimensions[3] = dimensions[1] + minSize + overlap + (int) Math.round((yEnd - dimensions[1] - minSize - overlap)*yScale);
					}
				}
				if (prevOrientation == true){
					//////////////////////////////////////////////////////////////////
					// Generate the X position of the horizontal bar				//
					//////////////////////////////////////////////////////////////////
					double xPos = Math.random();
					dimensions[0] = prevX1 + (int) Math.round((prevX2 - prevX1-textureSize)*xPos);
					dimensions[2] = dimensions[0] + textureSize;
					
					
					//////////////////////////////////////////////////////////////////
					// Generate the Y positions of the horizontal bar				//
					////////////////////////////////////////////////////////////////// 
					double y1Pos = Math.random();
					//y1Pos =0;
					if((yEnd - minSize) >= prevY2){
						dimensions[1] = yStart + (int) Math.round(y1Pos*(prevY2 - yStart - overlap));
					}
					if((yEnd - minSize) < prevY2){
						//dimensions[1] = yStart + (int) Math.round(y1Pos*(yEnd - minSize - yStart));
						dimensions[1] = yStart + (int) Math.round(y1Pos*(yEnd - minSize - yStart - overlap));

					}
					/*if(dimensions[1] >= prevY2){
					dimensions[1] = prevY2 -10;
					}*/
					double yScale = Math.random();
					if (dimensions[1] + minSize < prevY1){
						//dimensions[3] = dimensions[1] + minSize + (prevY1 - dimensions[1] - minSize + overlap) + (int) Math.round((yEnd - prevY1 - overlap)*yScale);
						dimensions[3] = prevY1 + overlap + (int) Math.round((yEnd - prevY1 - overlap)*yScale);

					}
					if (dimensions[1] + minSize >= prevY1){
						//dimensions[3] = dimensions[1] + minSize + (int) Math.round((yEnd - dimensions[1] - minSize)*yScale); 
						dimensions[3] = dimensions[1] + minSize + overlap + (int) Math.round((yEnd - dimensions[1] - minSize - overlap)*yScale);

					}
				}
			}
			
		
		return dimensions;
	}
	public int generateLineType(){
		/*
		 * Line Type 1 = Bomb
		 * Line Type 2 = Heart
		 * Line Type 3 = Lock
		 * Line Type 4 = Snowflake
		 * Line Type 5 = Bulb
		 * Line Type 6 = Multiplier
		 * Line Type 7 = Coin
		 * Line Type 8 = Diamond
		 * Line Type 9 = SuperArcadeLine
		 */
		int type = 0;
		int tempType = (int) Math.round(Math.random()*22);
		if(tempType == 0 || tempType == 1 || tempType ==2 || tempType == 3){
			type=1;
		}
		if(tempType == 4 || tempType == 5 || tempType == 6){
			type=2;
		}
		if(tempType == 7 || tempType == 8 || tempType == 9 || tempType == 10){
			type=3;
		}
		if(tempType == 11 || tempType == 12 || tempType == 13){
			type=4;
		}
		if(tempType == 14 || tempType == 15){
			type=5;
		}
		if(tempType == 16 || tempType == 17){
			type=6;
		}
		if(tempType == 18 || tempType == 19){
			type=7;
		}
		if(tempType == 20){
			type=8;
		}
		if(tempType == 21 || tempType == 22){
			type=9;
		}
		//type=5;
		return type;
	}
	public int[] generateSize(int previousX, int previousY, int pwidth, int pheight, Boolean cHorizontal, Boolean pHorizontal){
		int[] size = {0,0,0,0};
		int[] dimensions = generateDimensions(previousX, previousY,previousX+pwidth,previousY+pheight,pHorizontal,cHorizontal);
		size[0] = dimensions[0];
		size[1] = dimensions[1];
		size[2] = dimensions[2]-dimensions[0];
		size[3] = dimensions[3] - dimensions[1];
		return size;
	}
}
