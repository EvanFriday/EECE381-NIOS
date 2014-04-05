package com.NioSync.pathfinder;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MapView extends View {
	Bitmap bitmaps;
	Bitmap scaled_bitmap;
	int window_height;
	int window_width;
	//map data
	Map map;
	String startID;
	String endID;
	boolean line;
	//Line data
	int startx=0;
	int starty=0;
	int endx=0;
	int endy=0;
	//Find Path
	Vector<Coord> Path;
	
	
	public MapView(Context context) {
		//super(context);
		super(context,null);
		
	}
	
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);	
		
		line= false;
		this.map=null;
		
		this.bitmaps = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.mcld1));	
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//scaling to fill the screen width, and adjusting height to keep image aspect ratio
		
		//get height and width of original image
		float b_h = this.bitmaps.getHeight();
		float b_w = this.bitmaps.getWidth();
		//calculate aspect ratio
		float b_r = b_h / b_w;
		//multiply window width by aspect ratio to determine new height
		float new_height = w * b_r;
		this.window_width=w;
		this.window_height= (int) new_height;
		
		//create the scaled bitmap for screen size
		this.scaled_bitmap = Bitmap.createScaledBitmap(bitmaps, this.window_width, this.window_width, false);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	public void setData(Map mapObject,String sID,String eID,boolean drawLine){
		map= mapObject;
		startID= sID;
		endID = eID;
		line= drawLine;
	}
	
	public void setLine(int x0,int y0, int x1, int y1){
		startx= x0;
		starty= y0;
		endx=x1;
		endy=y1;
	}
	
	public void setPath(Vector<Coord> newPath, boolean drawLine){
		Path=newPath;
		line=drawLine;
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(this.bitmaps, 0, 0, null);
		//canvas.drawBitmap(this.scaled_bitmap, 0, 0, null);
		
		//int imgWidth = this.window_width;
		//int imgHeight = this.window_height;
		
		//float scaleX = imgWidth / map.getMapWidth();
		//float scaleY = imgHeight / map.getMapHeight();
		
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(2);
		
		if(line){
//			Node startNode = map.getNodeFromID(startID);
//			Node endNode = map.getNodeFromID(endID);			
//			Vector<Coord> Path= map.getShortestPathCoords(startNode, endNode, false);
			

			
			if(Path.size()>=2){
	/*			for(int i=1;i< Path.size();i++){
					int startx = (int)(Path.elementAt(i-1).getX() * scaleX);
					int starty = (int)(Path.elementAt(i-1).getY() * scaleY);
					int endx = (int)(Path.elementAt(i).getX() * scaleX);
					int endy = (int)(Path.elementAt(i).getY() * scaleY);
					
					canvas.drawLine(startx, starty, endx, endy, paint);
				}*/
				
				
				
				for(int i=1;i< Path.size();i++){
					startx = (int)(Path.elementAt(i-1).getX());
					starty = (int)(Path.elementAt(i-1).getY());
					endx = (int)(Path.elementAt(i).getX());
					endy = (int)(Path.elementAt(i).getY());
					
					canvas.drawLine(startx, starty, endx, endy, paint);
				}
			}
		}

	}
	

}
