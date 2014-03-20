package com.NioSync.pathfinder;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class PathFinder extends Activity {

	private EditText start_loc,dest_loc;
	private ImageView map;
	private ImageButton pulldown;
	private LinearLayout pulldown_container;
	private RelativeLayout pathfinder_rel;
	private Map map_object;
	private Spinner start_loc_spin,dest_loc_spin;
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);
        map_object = new Map();
       pulldown = (ImageButton) findViewById(R.id.pulldown);
       start_loc_spin = (Spinner) findViewById(R.id.startLoc);
       
       
       
       
       
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
    public void spindownPopulate(Spinner spin){
    	int count = map_object.getNodeNames().size();
        String[] node_names = new String[count];
        map_object.getNodeNames().copyInto(node_names); 
        for(int i=0;i<count;i++){
        Log.e("ARRAY CONTAINS",node_names[i]);
        }
        
        ArrayAdapter<String> spinner_adapter_start = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, node_names);
        start_loc_spin.setAdapter(spinner_adapter_start);
    }
    
}
