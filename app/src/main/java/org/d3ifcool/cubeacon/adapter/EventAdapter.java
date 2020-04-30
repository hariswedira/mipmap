package org.d3ifcool.cubeacon.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.DetailEventActivity;
import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Event;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Event> events;

    public EventAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_event,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        final Event eventData = events.get(position);
        holder.room.setText("By "+getEvents().get(position).getRoom());
        holder.title.setText(getEvents().get(position).getTitle());
//        Glide.with(context)
//                .load(getEvents().get(position).getPoster())
//                .into(holder.poster);
        Glide.with(context).load(getEvents().get(position).getPoster()).into(holder.poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailEventActivity.class);
                intent.putExtra("event",eventData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView room, title;
        ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room = itemView.findViewById(R.id.tv_room_llist);
            title = itemView.findViewById(R.id.tv_title_list);
            poster = itemView.findViewById(R.id.iv_poster_list);
        }
    }
}
