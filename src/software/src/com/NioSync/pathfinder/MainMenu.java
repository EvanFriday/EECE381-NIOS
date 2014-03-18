package com.NioSync.pathfinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenu extends Activity {
	Button to_map,to_map_select,to_help;
	TextView map_selected;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ListView menuList = (ListView) findViewById(R.id.listview_menu);
        String [] menu_items = {
        		getResources().getString(R.string.to_map),
        		getResources().getString(R.string.to_map_select),
        		getResources().getString(R.string.to_help)
        };
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, R.layout.menu_item, menu_items);
        menuList.setAdapter(adapt);
        

		
       
    }

}
