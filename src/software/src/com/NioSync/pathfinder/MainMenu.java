package com.NioSync.pathfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenu extends Activity {
	Button to_map, to_map_select, to_help;
	TextView map_selected;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        final ImageView imagebutton_gotomap = (ImageView) findViewById(R.id.goToMap_menu);
        imagebutton_gotomap.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN ) {
					imagebutton_gotomap.setImageResource(R.raw.gotomap_press);
					return true;
		        }
				else if(event.getAction() == MotionEvent.ACTION_UP ){
					imagebutton_gotomap.setImageResource(R.raw.gotomap);
					startActivity(new Intent(MainMenu.this,PathFinder.class));
					return true;
				}
				else{
					imagebutton_gotomap.setImageResource(R.raw.gotomap);
					return false;
				}
				
			}
		});
        imagebutton_gotomap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startActivity(new Intent(MainMenu.this,PathFinder.class));
            }
        });
        
        ListView menuList = (ListView) findViewById(R.id.listview_menu);
        String [] menu_items = {
        		//getResources().getString(R.string.to_map),
        		getResources().getString(R.string.download_map),
        		getResources().getString(R.string.to_map_select),
        		getResources().getString(R.string.to_help)
        };
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, menu_items);
        menuList.setAdapter(adapt);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id){
        		TextView textView = (TextView) itemClicked;
        		String strText = textView.getText().toString();
        		if(strText.equalsIgnoreCase(getResources().getString(R.string.to_map))){
        			//Launch pathfinder Activity
        			Log.e("activity swap", "swapping to pathfinder");
        			startActivity(new Intent(MainMenu.this,PathFinder.class));
        			
        		}
        		else if(strText.equalsIgnoreCase(getResources().getString(R.string.to_help))){
        			
        			startActivity(new Intent(MainMenu.this,Help.class));
        		}
        		else if(strText.equalsIgnoreCase(getResources().getString(R.string.download_map))){
        			
        			startActivity(new Intent(MainMenu.this,Wifi.class));
        		}
        		else if(strText.equalsIgnoreCase(getResources().getString(R.string.download_map))){
        			//TODO: Start activity for help
        			startActivity(new Intent(MainMenu.this,DE2_Link.class));
        			
        		}
        	}
		});
	}
	
	
}
