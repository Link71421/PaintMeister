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
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * This Activity is how the custom view where the user paints
 *
 * @author Dillon Ramsey
 * @author Zach Garner
 */
public class PaintScreen extends AppCompatActivity implements OnTouchListener {

    /**Request code for startActivityForResult*/
    private final static int REQUEST_CODE = 7;

    /**A custom view for the paint area*/
    CustomView touchArea;

    /**The color of the brush*/
    int brushColor;

    /**The color of the background*/
    int backgroundColor;

    /**The radius of the brush*/
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
                                    "No", (dialog, id) -> {});
                AlertDialog dialog = builder.create();
                dialog.show();
                break;

            case R.id.load_painting:
                Intent files = new Intent(this, loading_screen.class);
                this.startActivityForResult(files, REQUEST_CODE);
                break;

            case R.id.save_painting:
                String str = toXML();
                i = new Intent(this, save_screen.class);
                i.putExtra("Painting",str);
                break;

            case R.id.undo_painting:
                touchArea.undo();
                break;

            case R.id.redo_painting:
                touchArea.redo();
                break;
        }
        if (i != null){
            this.startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Converts the painting to XML and returns the XML string
     */
    private String toXML(){
        return "<paint>\n"+"\t<background_color>" + this.backgroundColor +
                "</background_color>\n" + touchArea.toXML() + "</paint>\n";
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
     * Loads a file from internal storage given a filename
     * @param filename - The file the user wishes to load
     */
    public void load(String filename){
        //Create a file if its not already on disk
        File extDir = new File(this.getFilesDir(),  filename);

        //Read text from file
        StringBuilder text = new StringBuilder();


        //Needs lots of try and catch blocks because so much can go wrong
        try{

            BufferedReader br = new BufferedReader(new FileReader(extDir));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }//end while

            br.close();//Close the buffer
        }//end try
        catch (FileNotFoundException e){//If file not found on disk here.
            Toast.makeText(this, "There was no data to load", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        catch (IOException e)//If io Exception here
        {
            Toast.makeText(this, "Error loading file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }//end catch


        //Set the data from the file content and conver it to a String
        String data = new String(text);

        //Safety first Parse data if available.
        if (data.length() > 0) {
            parseXML(data);
        }
        else
            Toast.makeText(this, "There is no data to display", Toast.LENGTH_LONG).show();
    }

    /**
     * Parses the input XML file
     * @param rawXML - The raw XML string to be parsed
     */
    private void parseXML(String rawXML){
        Log.w("AndroidParseXMLActivity", "Start Parsing");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {

            //Use the sax parser to parse the raw XML.
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlreader = saxParser.getXMLReader();

            //Use the handler to parse the XML to text
            PaintingXMLHandler handler = new PaintingXMLHandler();
            xmlreader.setContentHandler(handler);

            //Objects to read the stream.
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(rawXML));

            //Parse the input stream
            xmlreader.parse(inStream);

            //Get the map markers from the handler.
            if (handler.getPaintLines() != null) {
                touchArea.loadPainting(handler.getPaintLines());
            }
            setBackgroundColor(handler.getBackground());


            Toast.makeText(this, "Loaded", Toast.LENGTH_LONG).show();
        } catch (ParserConfigurationException e) {
            Toast.makeText(this, "Error 1 reading xml file.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (SAXException e) {
            Toast.makeText(this, "Error 2 reading xml file.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }catch(IOException e){
            Toast.makeText(this, "Error 3 reading xml file.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Sets the background color from the loaded file
     * @param backColor - The color to set the background as an int
     */
    private void setBackgroundColor(int backColor){
        backgroundColor = backColor;
        touchArea.setBackgroundColor(backgroundColor);
    }

    /**
     * This method gets the file name from the load screen
     *
     * @param requestCode - The request code sent to the activity
     * @param resultCode  - The result code returned to this activity
     * @param data        - The data returned (File Name)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Log.d("test", extras.getString("File_name"));
                    load(extras.getString("File_name"));
                }
            }
        }
    }
}