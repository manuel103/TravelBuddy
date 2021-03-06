package com.example.travelbuddy.activities;

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

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;

//import org.w3c.dom.Text;

//import java.util.ArrayList;

import com.example.travelbuddy.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class home2_adapter extends RecyclerView.Adapter<home2_adapter.Viewholder> {

    int [] MimageUrls={R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,R.drawable.d5,R.drawable.d6,R.drawable.d7};
    String[]mNames={"Maasaimara","MtKenya","Mombasa","Coast","RiftValley","Amboseli","Naivasha"};

    //private ArrayList<String> mNames;
    // private ArrayList<String>MimageUrls;
    private Context mcontext;
    public home2_adapter(  Context context)// ArrayList<String> mNames, ArrayList<String> mimageUrls)
     {
        //this.mNames = mNames;
       // MimageUrls = mimageUrls;
        this.mcontext = context;
    }


    @Override
    public home2_adapter.Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home2_layout, parent, false);
        Viewholder holder = new Viewholder(view);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home2_adapter.Viewholder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");


        // RequestOptions requestOptions = new RequestOptions()
        //       .placeholder(R.drawable.d7);
        //  Glide.with(mcontext)
        // .load(MimageUrls.get(position))
        //  .placeholder(R.drawable.d7)
        //   .apply(requestOptions)
        //   .into(holder.imageView);
        holder.imageView.setImageResource(MimageUrls[position]);
        holder.textView.setText(mNames[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view){
                Log.d(TAG, "onClick: Clicked on" + mNames[position]);
                Toast.makeText(mcontext, mNames[position], Toast.LENGTH_SHORT).show();
                                                }

                                            }


        );

    }

    @Override
    public int getItemCount() {
        return MimageUrls.length;
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
