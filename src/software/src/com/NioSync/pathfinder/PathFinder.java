package com.NioSync.pathfinder;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class PathFinder extends Activity {

	private String start_loc=null;
	private String dest_loc=null;
	private ImageButton pulldown;
	private LinearLayout pulldown_container;
	private RelativeLayout pathfinder_rel;
	private Map map_object;
	private Spinner start_loc_spin,dest_loc_spin;
	private ArrayAdapter<String> start_adapt,dest_adapt;
	private EditText searchString;
	private Button searchButton;
    @Override	
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_finder);
        
        map_object=Map.loadMapFromFile(null);

        pulldown = (ImageButton) findViewById(R.id.pulldown);
        start_loc_spin = (Spinner) findViewById(R.id.startLoc);
        dest_loc_spin = (Spinner) findViewById(R.id.destLoc);
        start_adapt = spindownPopulate(start_loc_spin);
        dest_adapt = spindownPopulate(dest_loc_spin);
        searchButton = (Button) findViewById(R.id.searchButton);
        
     
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	searchClicked();
            }
        });
        
        
        
        start_loc_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View itemClicked, int position, long id){
        			TextView text = (TextView) itemClicked;
        			String name = text.getText().toString();
        			start_loc = name;
        	        Log.d("START LOCATION SET", start_loc);
        	}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				start_loc = null;
				
			}
        });
        dest_loc_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent, View itemClicked, int position, long id){
        			TextView text = (TextView) itemClicked;
        			String name = text.getText().toString();
        			dest_loc = name;
        			Log.d("DEST LOCATION SET", dest_loc);
        	}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				dest_loc = null;
				
			}
        });

       
        
        
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
    public ArrayAdapter<String> spindownPopulate(Spinner spin){

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.map_object.getNodeNames());
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(spinner_adapter);
		return spinner_adapter;
    }
    
    public void searchClicked() {
    	this.searchString = (EditText) findViewById(R.id.search_input);
    	
    }
  
    
}
