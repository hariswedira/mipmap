package org.d3ifcool.cubeacon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.NavigateActivity;
import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Room;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private int userPos;
    private ArrayList<Room> rooms;

    public RoomAdapter(Context context, int userPos) {
        this.context = context;
        this.userPos = userPos;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_room,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Room roomData = rooms.get(position);
        holder.title.setText(getRooms().get(position).getName());
        holder.floor.setText("Applied Science Faculty Building, "+getRooms().get(position).getFloor());
        Glide.with(context)
                .load(getRooms().get(position).getPhoto())
                .into(holder.photo);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, getRooms().get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userPos==0){
                    Toast.makeText(context, "please enter the beacon area", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(context, NavigateActivity.class);
                    intent.putExtra("room",roomData);
                    intent.putExtra("posisi user",userPos);
                    context.startActivity(intent);
                }
//                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, floor;
        ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_room);
            floor = itemView.findViewById(R.id.tv_floor_room);
            photo = itemView.findViewById(R.id.iv_room);
        }
    }

    public void updateList(List<Room> newRoom){
        rooms = new ArrayList<>();
        rooms.addAll(newRoom);
        notifyDataSetChanged();
    }
}
