package com.twoshellko.linesweeper;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

public class V3ColorManager {
	private ArrayList<Color> colorArray = new ArrayList<Color>();
	
	private Color color0 = new Color(0.941f, 0.384f, 0.573f);
	private Color color1 = new Color(0.729f, 0.408f, 0.784f);
	private Color color2 = new Color(0.569f, 0.655f, 1f);
	private Color color3 = new Color(0.584f, 0.459f, 0.804f);
	private Color color4 = new Color(0.302f, 0.816f, 0.882f);
	private Color color5 = new Color(0.302f, 0.714f, 0.675f);
	private Color color6 = new Color(0.259f, 0.741f, 0.255f);
	private Color color7 = new Color(1f, 0.718f, 0.302f);
	private Color color8 = new Color(1f, 0.541f, 0.396f);
	private Color color9 = new Color(0.631f, 0.533f, 0.498f);
	private Color color10 = new Color(0.565f, 0.643f, 0.682f);
	private Color color11 = new Color(1f, 0.792f, 0.157f);
	private Color color12 = new Color(1f, 0.341f, 0.133f);
	private Color color13 = new Color(0.898f, 0.11f, 0.137f);
	private Color color14 = new Color(0.953f, 0.424f, 0.376f);
	private Color color15 = new Color(0.902f, 0.902f, 0.902f);
	private Color color16 = new Color(0.802f, 0.802f, 0.802f);
	
	public Color getColor(int color){
		colorArray.add(color0);
		colorArray.add(color1);
		colorArray.add(color2);
		colorArray.add(color3);
		colorArray.add(color4);
		colorArray.add(color5);
		colorArray.add(color6);
		colorArray.add(color7);
		colorArray.add(color8);
		colorArray.add(color9);
		colorArray.add(color10);
		colorArray.add(color11);
		colorArray.add(color12);
		colorArray.add(color13);
		colorArray.add(color14);
		colorArray.add(color15);
		colorArray.add(color16);
		return colorArray.get(color);
	}
}
