package link.paintmeister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This activity is where the user will enter the name they want to use to save
 * the painting
 *
 * @author Dillon Ramsey
 */
public class save_screen extends AppCompatActivity implements View.OnClickListener {

    private String paintingXml;

    /**
     * Valid player name charaters
     */
    private static final char[] valid = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    /**
     * Button used to confirm the user is done
     */
    private Button ok;
    /**
     * Button used to cancle the save
     */
    private Button cancle;
    /**
     * EditText box used to take in the name of the painting
     */
    private EditText painting;

    /**
     * Sets the view variables and onClick listener for the button
     *
     * @param savedInstanceState - Any saved instance state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_screen);

        ok = findViewById(R.id.button);
        cancle = findViewById(R.id.button2);
        painting = findViewById(R.id.editText);

        ok.setOnClickListener(this);
        cancle.setOnClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            paintingXml = bundle.getString("Painting");
        }
    }

    /**
     * Implementation of onClick. Checks that the player did enter a name into the box and sends
     * the name back to the calling activity
     *
     * @param v - The button that was clicked
     */
    @Override
    public void onClick(View v) {
        if (v == ok){
            boolean check = false;
            boolean invalid = false;
            String pName = painting.getText().toString();
            char[] pString = pName.toLowerCase().toCharArray();
            if (pName.equals("")) {
                invalidName("");
            } else {
                for (char n : pString) {
                    for (char r : valid) {
                        if (n == r) {
                            check = true;
                            break;
                        }
                    }
                    if (!check) {
                        invalid = true;
                        break;
                    }
                }
                if (!invalid) {
                    savePainting(pName);

                } else {
                    invalidName(pName);
                }
            }
        }
        else if(v == cancle){
            finish();
        }
    }

    /**
     * Saves the painting name so that it will persist after re-orientation
     *
     * @param savedInstanceState - Bundled saved instance state data containing the player name
     */
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("name", this.painting.getText().toString());
    }

    /**
     * Restores the painting name after a re-orientation
     *
     * @param savedInstanceState - Bundled saved instance state data containing the player's name
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        String pName = savedInstanceState.getString("name", "");
        painting.setText(pName);
    }

    /**
     * Creates a dialog box that tells the user there entered name is invalid
     *
     * @param paintName
     */
    private void invalidName(String paintName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid painting name");
        builder.setMessage("The name " + paintName + " is not valid\nOnly "
                + "characters [A-Z][a-z]").setPositiveButton("Ok", (dialog, id) -> {});
                builder.show();
    }
    public void savePainting(String fileName){

        String xml_data = this.paintingXml;

        //Create a file if its not already on disk
        File file = new File(this.getFilesDir(), fileName);

        //By default black text if no text can be generated from the notePad.
        //String string = "";

        FileOutputStream outputStream;//declare FOS

        try{  //to do this
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(xml_data.getBytes());
            outputStream.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            finish();
        }
        catch(FileNotFoundException e){
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch(IOException e){
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        catch (Exception e) {//else if failed trying do this
            Toast.makeText(this, "Error saving file", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
