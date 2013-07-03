package com.example.somegame;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Rect extends Rectangle{
	int rowID;
	int colID;
	
	public Rect(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager pVertexBufferObjectManager,int rowID,int colID) {
		super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
		this.rowID = rowID;
		this.colID = colID;
	}
	void setRowID(int n){
		rowID = n; 
	}
	
	void setColID(int n){
		colID = n; 
	}
}
