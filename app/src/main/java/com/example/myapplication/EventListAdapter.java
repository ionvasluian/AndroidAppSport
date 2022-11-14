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
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<EventListElement> eventListElements;

    public EventListAdapter(Context context, ArrayList<EventListElement> eventListElements, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.eventListElements = eventListElements;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public EventListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_view_event_element,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view,recyclerViewInterface);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, int position) {
        holder.name_event.setText(eventListElements.get(position).getEvent_name());
        if(eventListElements.get(position).getCategory().equals("volleyball")){
            holder.volley_ball.setImageResource(R.drawable.volleyball_imageview);
        }else if(eventListElements.get(position).getCategory().equals("basketball")) {
            holder.volley_ball.setImageResource(R.drawable.basketball_imageview);
        }else if(eventListElements.get(position).getCategory().equals("football")){
            holder.volley_ball.setImageResource(R.drawable.football_imageview);
        }else if(eventListElements.get(position).getCategory().equals("soccer")) {
            holder.volley_ball.setImageResource(R.drawable.soccer_imageview);
        }else if(eventListElements.get(position).getCategory().equals("golf")){
            holder.volley_ball.setImageResource(R.drawable.golf_imageview);
        }else if(eventListElements.get(position).getCategory().equals("cycling")){
            holder.volley_ball.setImageResource(R.drawable.cycling_imageview);
        }else if(eventListElements.get(position).getCategory().equals("running")){
            holder.volley_ball.setImageResource(R.drawable.running_imageview);
        }else if(eventListElements.get(position).getCategory().equals("tennis")){
            holder.volley_ball.setImageResource(R.drawable.tennis_imageview);
        }else if(eventListElements.get(position).getCategory().equals("table tennis")){
            holder.volley_ball.setImageResource(R.drawable.tennis_table_imageview);
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
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

             volley_ball = itemView.findViewById(R.id.img_volley);
             name_event = itemView.findViewById(R.id.name_adapter);
             people_event = itemView.findViewById(R.id.number_people);

             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(recyclerViewInterface != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                     }
                 }
             });
        }
    }

}
