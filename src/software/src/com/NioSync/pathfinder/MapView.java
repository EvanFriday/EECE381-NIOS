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
	public MapView(Context context) {
		//super(context);
		super(context,null);
		
	}
	
	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);	
		
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

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(this.scaled_bitmap, 0, 0, null);
	}
	
	public void onDraw(Canvas canvas, Map map, String startID, String endID){
		canvas.drawBitmap(this.scaled_bitmap, 0, 0, null);
		
		int imgWidth = this.window_width;
		int imgHeight = this.window_height;
		
		float scaleX = imgWidth / map.getMapWidth();
		float scaleY = imgHeight / map.getMapHeight();
		
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(1);
		
		Node startNode = map.getNodeFromID(startID);
		
		Node endNode = map.getNodeFromID(endID);
		
		Vector<Coord> Path= map.getShortestPathCoords(startNode, endNode, false);
		
		for(int i=1;i< Path.size();i++){
			int startx = (int)(Path.elementAt(i-1).getX() * scaleX);
			int starty = (int)(Path.elementAt(i-1).getY() * scaleY);
			int endx = (int)(Path.elementAt(i).getX() * scaleX);
			int endy = (int)(Path.elementAt(i).getY() * scaleY);
			
			canvas.drawLine(startx, starty, endx, endy, paint);
		}
		
	}
	

}
