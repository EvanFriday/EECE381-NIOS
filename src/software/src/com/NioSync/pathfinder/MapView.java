package com.NioSync.pathfinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

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
		//this.setBackgroundResource(R.drawable.mcld1);
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
	
	public void onDraw(Canvas canvas, Map map ){
		canvas.drawBitmap(this.scaled_bitmap, 0, 0, null);
		//TODO: call:
		//canvas.drawLines(points_to_connect, paint);
		//where points to connect is the x,y coordinates from shortest path algorithm.
	
	}
	

}
