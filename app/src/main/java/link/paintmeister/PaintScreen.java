package link.paintmeister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

/**
 * This Activity how the custom view where the user paints
 *
 * @author Dillon Ramsey
 */
public class PaintScreen extends AppCompatActivity implements OnTouchListener {

    CustomView touchArea;
    int brushColor;
    int backgroundColor;
    float brushSize;

    /**
     * Called when the view is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_screen);

        /*The painting area*/
        touchArea = (CustomView) this.findViewById(R.id.view1);

        /*Setting up a listener for when the user changes settings*/
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(observer);

        /*Getting the saved settings for the brush*/
        brushSize = (float)pref.getInt("brushSize", 10);
        brushColor = Color.parseColor(pref.getString("brushColor", "#000000"));
        backgroundColor = Color.parseColor(pref.getString("backColor", "#FFFFFF"));
        setBrushSettings();
    }

    /**
     * Inflates the action bar
     * @param menu - The menu to inflate
     * @return A boolean that the operation was complete
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Detects when an action bar button has been pressed
     * @param item - The button that was pressed
     * @return A boolean indicating the button press was handled
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.brush_settings:


                i = new Intent(this,SettingsActivity.class);
                break;

            case R.id.new_painting:

                //Display a dialog to ask if the user wants to start a new painting
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("New Painting");
                builder.setMessage("Are you sure you want a new painting?").setPositiveButton(
                            "Yes", (dialog, id) -> touchArea.newPainting()).setNegativeButton(
                                    "No", (dialog, id) -> noNewPainting());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
                /*
            case R.id.load_painting:
                //TODO Determine how we will load a painting into the app. A dialog to say the current painting will be cleared would be good as well
                i = new Intent(this, Load.class);
                break;
                */
            case R.id.save_painting:
                //TODO Choose a method of saving the paint. This will Preferably be XML.
                //TODO Determine how to allow the user to save to external location.
                String test = touchArea.toXML();
                Log.d("XML", test);
                break;
        }
        if (i != null){
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The listener for changes to the settings
     */
    SharedPreferences.OnSharedPreferenceChangeListener observer = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            brushSize = (float)sharedPreferences.getInt("brushSize", 10);
            brushColor = Color.parseColor(sharedPreferences.getString("brushColor", "#000000"));
            backgroundColor = Color.parseColor(sharedPreferences.getString("backColor", "#FFFFFF"));
            setBrushSettings();
        }
    };

    /**
     * Sets the settings for the brush and background
     */
    private void setBrushSettings(){
        touchArea.setRadius(brushSize);
        touchArea.setColor(brushColor);
        touchArea.setBackgroundColor(backgroundColor);
    }

    /**
     * Currently nothing needs to be done here
     * @param v - The view that was touched
     * @param event - The event that holds the motion data
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    /**
     * Method to run if the user decides not to make a new painting. Currently does nothing.
     */
    private void noNewPainting(){
        //Do Nothing Currently
    }

    //TODO Add any other features we deem needed to the app and make the interface look good
}