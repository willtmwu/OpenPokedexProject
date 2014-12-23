package com.projects.garage.experimental.pokedex;

import java.util.ArrayList;

import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;

import android.content.res.Configuration;
import android.content.res.TypedArray;

import android.os.Bundle;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

import android.text.method.ScrollingMovementMethod;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import com.projects.garage.experimental.pokedex.nav_drawer.adapter.*;
import com.projects.garage.experimental.pokedex.nav_drawer.model.*;
import com.projects.garage.experimental.pokedex.database.PokemonTypeChartTable;
import com.projects.garage.experimental.pokedex.network.PokemonNetworkConnection;

public class MainActivity extends Activity {
    private CharSequence mDrawerTitle;  // nav drawer title
    private CharSequence mTitle;        // used to store app title

    private DrawerLayout mDrawerLayout;     // To reference the overall container
    private FrameLayout  mFrameLayout;      // fragment container within drawer layout
    private ListView     mDrawerList;       // For the navigation drawer, within drawer layout

    private ActionBarDrawerToggle mDrawerToggle;

    // Create the navigation drawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter navAdapter;

    //Maintain a reference to the type chart
    PokemonTypeChartTable pokemonTable = new PokemonTypeChartTable();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_nav);

        mTitle = mDrawerTitle = getTitle();

        // Obtain references
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFrameLayout  = (FrameLayout) findViewById(R.id.frame_container);
        mDrawerList   = (ListView) findViewById(R.id.list_slidermenu);

        initNavDrawer();
        initActionBar();

        if (savedInstanceState == null) {
            displayFragment(0);
        }
    }

    private void initNavDrawer(){
        // Create the navigation drawer
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons  = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        navDrawerItems = new ArrayList<NavDrawerItem>();

        for(int i=0; i<navMenuTitles.length; i++){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }

        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayFragment(position);
            }
        });

        // Setting the nav drawer list adapter
        navAdapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(navAdapter); // Set the list adapter
    }

    private void initActionBar(){
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,   // nav drawer open - description for accessibility
                R.string.drawer_close   // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    //Check whether fragements really need to be recreated
    private void displayFragment(int position){
        Fragment fragment = null;
        switch (position) {
            case 1:
                fragment = new WeaknessAnalysisFragment();
                break;

            case 5:
                fragment = new SettingsFragment();
                break;

            default:
                fragment = new EmptyFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);

            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

        //Can add logic here to chosse which items to setVisible
        //menu.findIten(R.id).setVisible
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                displayFragment(5);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
