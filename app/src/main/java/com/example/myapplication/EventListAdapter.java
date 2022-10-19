package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder> {
    Context context;
    ArrayList<EventListElement> eventListElements;

    public EventListAdapter(Context context, ArrayList<EventListElement> eventListElements){
        this.context = context;
        this.eventListElements = eventListElements;
    }

    @NonNull
    @Override
    public EventListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_view_event_element,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, int position) {
        holder.name_event.setText(eventListElements.get(position).getEvent_name());
        if(eventListElements.get(position).getCategory().equals("volleyball")){
            holder.volley_ball.setImageResource(R.drawable.volleyball_imageview);
        }
        holder.people_event.setText(eventListElements.get(position).getNumber_of_people());
    }

    @Override
    public int getItemCount() {
        return eventListElements.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView volley_ball;
        TextView name_event, people_event;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

             volley_ball = itemView.findViewById(R.id.img_volley);
             name_event = itemView.findViewById(R.id.name_adapter);
             people_event = itemView.findViewById(R.id.number_people);
        }
    }
}
