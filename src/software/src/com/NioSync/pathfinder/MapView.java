package com.NioSync.pathfinder;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class MapView extends View {
	Bitmap bitmaps;
	
	public MapView(Context context) {
		//super(context);
		super(context,null);
		
	}

	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);	
		bitmaps = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.mcld1));
	}
	

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(bitmaps, 0, 0, null);
		
	}

}
