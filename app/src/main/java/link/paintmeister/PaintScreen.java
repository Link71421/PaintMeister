package link.paintmeister;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

//################################################################

/**
 *
 * @author Andrew
 *
 */
//################################################################
public class PaintScreen extends AppCompatActivity implements OnTouchListener {

    CustomView touchArea;
    TextView tv;
    String brushColor;
    String backgroundColor;
    float brushSize;

    private ShapeDrawable[] mDrawable = new ShapeDrawable[5];

    //===========================================================
    /**
     * Called when the view is created
     */
    //===========================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_screen);

        touchArea = (CustomView) this.findViewById(R.id.view1);
        //	touchArea.setOnTouchListener(this);

        tv = (TextView) this.findViewById(R.id.textView1);
        //touchArea.setTextView(tv);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(observer);
        brushSize = (float)pref.getInt("brushSize", 10);
        brushColor = pref.getString("brushColor", "#000000");
        backgroundColor = pref.getString("backColor", "#FFFFFF");
        setBrushSettings();
    }//==========================================================

    //===========================================================
    /**
     * Called when the view instance is touched by the user.
     * @param v The view instance that was touched.
     * @param event The motion event that carries the touch data.
     */
    //===========================================================
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float raw_x = event.getRawX();
        float raw_y = event.getRawY();

        float axis_x = event.getAxisValue(MotionEvent.AXIS_X);
        float axis_y = event.getAxisValue(MotionEvent.AXIS_Y);


        float x = event.getX();
        float y = event.getY();
        tv.setText("RAW X:" + raw_x + "Y: " + raw_y +
                "     AXIS X:" + axis_x + " Y:" + axis_y +
                "     GETX:" + x + "  GETY:" + y);

        return false;
    }//===========================================================

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.brush_settings:
                i = new Intent(this,SettingsActivity.class);
                break;
                /*
            case R.id.new_painting:
                i = new Intent(this, Camera.class);
                break;
            case R.id.load_painting:
                i = new Intent(this, Gallery.class);
                break;
            case R.id.save_painting:
                i = new Intent(this, Gallery.class);
                break;
                 */
        }//end switch
        if (i != null){
            this.startActivity(i);
            return true;
        }//end if
        return super.onOptionsItemSelected(item);
    }//end method

    SharedPreferences.OnSharedPreferenceChangeListener observer = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            brushSize = (float)sharedPreferences.getInt("brushSize", 10);
            brushColor = sharedPreferences.getString("brushColor", "#000000");
            backgroundColor = sharedPreferences.getString("backColor", "#FFFFFF");
            setBrushSettings();
        }
    };

    private void setBrushSettings(){
        touchArea.setRadius(brushSize);
    }

}//###############################################################
