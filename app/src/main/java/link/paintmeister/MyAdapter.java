package link.paintmeister;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] mDataset;

    private ListItemClickListener listener;

    public MyAdapter(String[] myDataset, ListItemClickListener l){
        mDataset = myDataset;
        this.listener = l;
    }

    /**
     * Creats new views
     * @param parent the parent for that this view is apart of
     * @param viewType and int representing the viewType
     * @return An instance of the innenr  class MyViewHolder
     */
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_text,parent,false);

        MyViewHolder vh = new MyViewHolder(v,listener);

        return vh;
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mDataset[position]);


    }

    /**
     * Return the size of the dataSet
     * @return the data set Size
     */
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public boolean selected = false;
        private ListItemClickListener licl;
        private TextView prev = null;
        public MyViewHolder(TextView v, ListItemClickListener licl){
            super(v);
            textView = v;
            textView.setOnClickListener(this);
            this.licl = licl;

        }

        public void onClick(View v){

            licl.onListItemClick(textView.getText().toString());
        }


    }
    public interface ListItemClickListener{
        public void onListItemClick(String text);
    }
}
