package com.javachinna.model;

public class DataPoint {

	private String label;

	private float y;

	public DataPoint(String label, float y) {
		super();
		this.label = label;
		this.y = y;
	}

	public DataPoint() {
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
	
	
}
