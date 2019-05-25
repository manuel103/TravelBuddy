package com.example.travelbuddy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Home_Adapter extends RecyclerView.Adapter<Home_Adapter.Viewholder> {

    private static final String TAG = "Home_Adapter";

    private ArrayList<String>mNames;
    private ArrayList<String>MimageUrls;
    private Context mcontext;

    public Home_Adapter(  Context context, ArrayList<String> mNames, ArrayList<String> mimageUrls) {
        this.mNames = mNames;
        MimageUrls = mimageUrls;
        this.mcontext = context;
    }


    @Override
    public Home_Adapter.Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_layout, parent, false);
        Viewholder holder = new Viewholder(view);
         return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_Adapter.Viewholder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        RequestOptions requestOptions = new RequestOptions()
        .placeholder(R.drawable.ic_launcher_background);
        Glide.with(mcontext)
                .load(MimageUrls.get(position))
                .apply(requestOptions)
                .into(holder.imageView);
        holder.textView.setText(mNames.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener(){
            
            @Override
            public void onClick(View view){
                Log.d(TAG, "onClick: Clicked on" + mNames.get(position));
                Toast.makeText(mcontext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }

                                            }


        );

    }

    @Override
    public int getItemCount() {
        return MimageUrls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            this.imageView =itemView.findViewById(R.id.image);
            this.textView =itemView.findViewById(R.id.name);

        }
    }
}
