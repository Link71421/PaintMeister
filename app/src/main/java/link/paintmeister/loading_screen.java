package link.paintmeister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Loading screen takes care of the loading screen
 *
 *  * @author Dillon Ramsey
 *  * @author Zach Garner
 */
public class loading_screen extends AppCompatActivity implements View.OnClickListener, MyAdapter.ListItemClickListener {
    /**
     * The recyclerView that is going to be used
     */
    RecyclerView recyclerView;
    /**
     * the back button that goes back to the previous screen
     */
    Button back;
    /**
     * The adapter that will be used in the recyclerView
     */
    RecyclerView.Adapter mAdapter;

    /**
     * the layoutManager that will be used
     */
    RecyclerView.LayoutManager layoutManager;

    /**
     * the method called whenever the screen is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);//performance boost yay
        String[] myDataset = getFileNames();
        MyAdapter myAdapter = new MyAdapter(myDataset, this);
        recyclerView.setAdapter(myAdapter);
        back = findViewById(R.id.back_button);


        back.setOnClickListener(this);

        //setting the layout Manager

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * gets the files that allow you to choose to load
     * @return
     */
    public String[] getFileNames(){
        String[] extDir = this.getFilesDir().list();
        return extDir;
    }

    /**
     * allows us to check buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(v == back){
            finish();
        }
    }

    /**
     * allows us to pass whichever file was clicked to be loaded
     * @param text
     */
    @Override
    public void onListItemClick(String text) {
        Intent results = new Intent();
        results.putExtra("File_name",text);
        setResult(RESULT_OK, results);
        finish();
    }
}
