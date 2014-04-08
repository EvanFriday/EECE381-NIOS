package com.NioSync.pathfinder;


import java.util.Vector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class PathFinder extends Activity {

	private Animator mCurrentAnimator;
	private int mShortAnimationDuration;
	private ImageView map;
	public MapView mapView;
	private String start_loc = null;
	private String dest_loc = null;
	private ImageButton pulldown;
	private LinearLayout pulldown_container;
	private RelativeLayout pathfinder_rel;
	private Map map_object;
	private Spinner start_loc_spin, dest_loc_spin;
	private ArrayAdapter<String> start_adapt, dest_adapt;
	private Vector<Coord> newPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path_finder);

		map_object = Map.loadMapFromFile(null);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//************search action bar************
		// TODO
		
		//***************zoom************
		mapView = (MapView) findViewById(R.id.map_container);
		mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(mapView, R.raw.mcld1);
            }
        });
		//***********************************

		pulldown = (ImageButton) findViewById(R.id.pulldown);
		start_loc_spin = (Spinner) findViewById(R.id.startLoc);
		dest_loc_spin = (Spinner) findViewById(R.id.destLoc);
		start_adapt = spindownPopulate(start_loc_spin);
		dest_adapt = spindownPopulate(dest_loc_spin);

		start_loc_spin
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View itemClicked, int position, long id) {
						TextView text = (TextView) itemClicked;
						String name = text.getText().toString();
						start_loc = name;
						Log.d("START LOCATION SET", start_loc);
						
						//****************update map***********************
				    	Node startNode = map_object.getNodeFromName(start_loc);
				    	Node endNode = map_object.getNodeFromName(dest_loc);

				    	newPath=map_object.getShortestPathCoords(startNode, endNode, false);

				    	//start updating
				    	mapView = (MapView) findViewById(R.id.map_container);
				    	//mapView.setData( mapTest.loadMapFromFile("d") , startID, endID, true);
				    	//mapView.setLine(0, 0, 400, 400);
				    	mapView.setPath(newPath, true);
				    	mapView.invalidate();
				    	//*************************************************
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						start_loc = null;

					}
				});
		dest_loc_spin
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View itemClicked, int position, long id) {
						TextView text = (TextView) itemClicked;
						String name = text.getText().toString();
						dest_loc = name;
						Log.d("DEST LOCATION SET", dest_loc);
						
						//****************update map***********************
				    	Node startNode = map_object.getNodeFromName(start_loc);
				    	Node endNode = map_object.getNodeFromName(dest_loc);

				    	newPath=map_object.getShortestPathCoords(startNode, endNode, false);

				    	//start updating
				    	mapView = (MapView) findViewById(R.id.map_container);
				    	//mapView.setData( mapTest.loadMapFromFile("d") , startID, endID, true);
				    	//mapView.setLine(0, 0, 400, 400);
				    	mapView.setPath(newPath, true);
				    	mapView.invalidate();
				    	//*************************************************
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
	
	
	//****************search on navigation***************** 
	

	
	
	//**************set up menu*****************
	// TODO
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.path_finder, menu);
	    
	 // Associate searchable configuration with the SearchView
	    SearchManager searchManager =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =(SearchView) menu.findItem(R.id.action_search).getActionView();
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    
	    return super.onCreateOptionsMenu(menu);
	}

	
	
	//**********************************************
	//show action bar items
	//**********************************************
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            
	            return true;
	        case R.id.action_settings:
	            
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

	public void showHideTools() {
		this.pulldown_container = (LinearLayout) findViewById(R.id.linearLayout_pulldowncontainer);
		this.pathfinder_rel = (RelativeLayout) findViewById(R.id.relativelayout_pathfinder);
		
		//****************update map***********************
    	Node startNode = map_object.getNodeFromName(start_loc);
    	Node endNode = map_object.getNodeFromName(dest_loc);

    	newPath=map_object.getShortestPathCoords(startNode, endNode, false);

    	//start updating
    	this.mapView = (MapView) findViewById(R.id.map_container);
    	//mapView.setData( mapTest.loadMapFromFile("d") , startID, endID, true);
    	//mapView.setLine(0, 0, 400, 400);
    	mapView.setPath(newPath, true);
    	mapView.invalidate();
    	//*************************************************
    	
		if (this.pulldown_container.getVisibility() == View.VISIBLE) {

			this.pulldown_container.setVisibility(View.GONE);
			for (int i = 0; i < pulldown_container.getChildCount(); i++) {
				View view = pulldown_container.getChildAt(i);
				view.setVisibility(View.GONE); // Or whatever you want to do
												// with the view.
			}
			pathfinder_rel.invalidate();
			this.pulldown.setImageResource(R.drawable.pulldown_bar);
		} else {

			this.pulldown_container.setVisibility(View.VISIBLE);
			for (int i = 0; i < pulldown_container.getChildCount(); i++) {
				View view = pulldown_container.getChildAt(i);
				view.setVisibility(View.VISIBLE); // Or whatever you want to do
													// with the view.
			}
			pathfinder_rel.invalidate();
			this.pulldown.setImageResource(R.drawable.pulldown_bar_extended);

		}
	}

	public ArrayAdapter<String> spindownPopulate(Spinner spin) {

		ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				this.map_object.getNodeNames());
		spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(spinner_adapter);
		return spinner_adapter;
	}
	
	private void zoomImageFromThumb(final View thumbView, int imageResId) {
	    // If there's an animation in progress, cancel it
	    // immediately and proceed with this one.
	    if (mCurrentAnimator != null) {
	        mCurrentAnimator.cancel();
	    }

	    // Load the high-resolution "zoomed-in" image.
	    //final ImageView expandedImageView = (ImageView) findViewById(R.id.expanded_image);
	    final MapView expandedImageView = (MapView) findViewById(R.id.expanded_image);
	    expandedImageView.setPath(newPath, true);
	    //*******edit
	    
	    
	    
	    //**********************************
	    expandedImageView.invalidate();

	    // Calculate the starting and ending bounds for the zoomed-in image.
	    // This step involves lots of math. Yay, math.
	    final Rect startBounds = new Rect();
	    final Rect finalBounds = new Rect();
	    final Point globalOffset = new Point();

	    // The start bounds are the global visible rectangle of the thumbnail,
	    // and the final bounds are the global visible rectangle of the container
	    // view. Also set the container view's offset as the origin for the
	    // bounds, since that's the origin for the positioning animation
	    // properties (X, Y).
	    thumbView.getGlobalVisibleRect(startBounds);
	    findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
	    startBounds.offset(-globalOffset.x, -globalOffset.y);
	    finalBounds.offset(-globalOffset.x, -globalOffset.y);

	    // Adjust the start bounds to be the same aspect ratio as the final
	    // bounds using the "center crop" technique. This prevents undesirable
	    // stretching during the animation. Also calculate the start scaling
	    // factor (the end scaling factor is always 1.0).
	    float startScale;
	    if ((float) finalBounds.width() / finalBounds.height()
	            > (float) startBounds.width() / startBounds.height()) {
	        // Extend start bounds horizontally
	        startScale = (float) startBounds.height() / finalBounds.height();
	        float startWidth = startScale * finalBounds.width();
	        float deltaWidth = (startWidth - startBounds.width()) / 2;
	        startBounds.left -= deltaWidth;
	        startBounds.right += deltaWidth;
	    } else {
	        // Extend start bounds vertically
	        startScale = (float) startBounds.width() / finalBounds.width();
	        float startHeight = startScale * finalBounds.height();
	        float deltaHeight = (startHeight - startBounds.height()) / 2;
	        startBounds.top -= deltaHeight;
	        startBounds.bottom += deltaHeight;
	    }

	    // Hide the thumbnail and show the zoomed-in view. When the animation
	    // begins, it will position the zoomed-in view in the place of the
	    // thumbnail.
	    thumbView.setAlpha(0f);
	    thumbView.setVisibility(View.INVISIBLE);
	    expandedImageView.setVisibility(View.VISIBLE);

	    // Set the pivot point for SCALE_X and SCALE_Y transformations
	    // to the top-left corner of the zoomed-in view (the default
	    // is the center of the view).
	    expandedImageView.setPivotX(0f);
	    expandedImageView.setPivotY(0f);

	    // Construct and run the parallel animation of the four translation and
	    // scale properties (X, Y, SCALE_X, and SCALE_Y).
	    AnimatorSet set = new AnimatorSet();
	    
	    set
	            .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
	                    startBounds.left, finalBounds.left))
	            .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
	                    startBounds.top, finalBounds.top))
	            .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
	            .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
	    
	    set.setDuration(mShortAnimationDuration);
	    set.setInterpolator(new DecelerateInterpolator());
	    set.addListener(new AnimatorListenerAdapter() {
	        @Override
	        public void onAnimationEnd(Animator animation) {
	            mCurrentAnimator = null;
	        }

	        @Override
	        public void onAnimationCancel(Animator animation) {
	            mCurrentAnimator = null;
	        }
	    });
	    set.start();
	    mCurrentAnimator = set;
	    
	    // Upon clicking the zoomed-in image, it should zoom back down
	    // to the original bounds and show the thumbnail instead of
	    // the expanded image.
	    final float startScaleFinal = startScale;
	    expandedImageView.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View view) {
	            if (mCurrentAnimator != null) {
	                mCurrentAnimator.cancel();
	            }

	            // Animate the four positioning/sizing properties in parallel,
	            // back to their original values.
	            AnimatorSet set = new AnimatorSet();
	            set.play(ObjectAnimator
	                        .ofFloat(expandedImageView, View.X, startBounds.left))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedImageView, 
	                                        View.Y,startBounds.top))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedImageView, 
	                                        View.SCALE_X, startScaleFinal))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedImageView, 
	                                        View.SCALE_Y, startScaleFinal));
	            set.setDuration(mShortAnimationDuration);
	            set.setInterpolator(new DecelerateInterpolator());
	            set.addListener(new AnimatorListenerAdapter() {
	                @Override
	                public void onAnimationEnd(Animator animation) {
	                    thumbView.setAlpha(1f);
	                    thumbView.setVisibility(View.VISIBLE);
	                    expandedImageView.setVisibility(View.GONE);
	                    mCurrentAnimator = null;
	                }

	                @Override
	                public void onAnimationCancel(Animator animation) {
	                	thumbView.setVisibility(View.VISIBLE);
	                    thumbView.setAlpha(1f);
	                    expandedImageView.setVisibility(View.GONE);
	                    mCurrentAnimator = null;
	                }
	            });
	            set.start();
	            mCurrentAnimator = set;
	        }
	    });
	}
	
}
