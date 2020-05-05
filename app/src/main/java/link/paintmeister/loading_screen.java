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

public class loading_screen extends AppCompatActivity implements View.OnClickListener, MyAdapter.ListItemClickListener {
    RecyclerView recyclerView;
    Button back;
    Button load;
    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager layoutManager;
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

    public String[] getFileNames(){
        String[] extDir = this.getFilesDir().list();
        //data/user/O/link.paintmeister/files
        return extDir;
    }

    @Override
    public void onClick(View v) {
        if(v == back){
            finish();
        }
    }

    @Override
    public void onListItemClick(String text) {
        Intent results = new Intent();
        results.putExtra("File_name",text);
        setResult(RESULT_OK, results);
        finish();
    }
}
