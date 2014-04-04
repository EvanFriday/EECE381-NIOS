package com.NioSync.pathfinder;



import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PathFinder extends Activity {
	//test
	public  Map mapTest;
	public String startID="r101";
	public String endID="r105";
	public MapView mapView;
	
	
	private EditText start_loc,dest_loc;
	private ImageView map;
	private ImageButton pulldown;
	private LinearLayout pulldown_container;
	private RelativeLayout pathfinder_rel;
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);
        getActionBar().setDisplayHomeAsUpEnabled(true);
       pulldown = (ImageButton) findViewById(R.id.pulldown);
       
       //create map object
       mapTest= new Map();
       
       pulldown.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			showHideTools();
			
			
		}
	});
    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.path_finder, menu);
        return true;
    }
    
    public void showHideTools(){
        this.pulldown_container = (LinearLayout) findViewById(R.id.linearLayout_pulldowncontainer);
    	this.pathfinder_rel = (RelativeLayout) findViewById(R.id.relativelayout_pathfinder);
    	
    	//****************update map***********************
    	//create a new path
    	Coord position1= new Coord(350,400);
    	Coord position2= new Coord(350,340);
    	Coord position3= new Coord(350,285);
    	Vector<Coord> newPath = new Vector<Coord>();
    	newPath.add(position1);
    	newPath.add(position2);
    	newPath.add(position3);
    	//start updating
    	this.mapView = (MapView) findViewById(R.id.map_container);
    	//mapView.setData( mapTest.loadMapFromFile("d") , startID, endID, true);
    	//mapView.setLine(0, 0, 400, 400);
    	mapView.setPath(newPath, true);
    	mapView.invalidate();
    	//*************************************************
    	
        if(this.pulldown_container.getVisibility()==View.VISIBLE){
    		
        	this.pulldown_container.setVisibility(View.GONE);
    		for ( int i = 0; i < pulldown_container.getChildCount();  i++ ){
    		    View view = pulldown_container.getChildAt(i);
    		    view.setVisibility(View.GONE); // Or whatever you want to do with the view.
    		}
    		pathfinder_rel.invalidate();
    		this.pulldown.setImageResource(R.drawable.pulldown_bar);
    	}
    	else{
    		
    		this.pulldown_container.setVisibility(View.VISIBLE);
    		for ( int i = 0; i < pulldown_container.getChildCount();  i++ ){
    		    View view = pulldown_container.getChildAt(i);
    		    view.setVisibility(View.VISIBLE); // Or whatever you want to do with the view.
    		}
    		pathfinder_rel.invalidate();
    		this.pulldown.setImageResource(R.drawable.pulldown_bar_extended);
    		
    	}
    }
    
}
