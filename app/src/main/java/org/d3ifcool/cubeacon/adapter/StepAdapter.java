package org.d3ifcool.cubeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.d3ifcool.cubeacon.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list;

    public StepAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_step,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sign.setText(getList().get(position));

        if (getList().get(position).equalsIgnoreCase("Arrive at destination")){
            Glide.with(context).load(R.drawable.strip).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("Move Forward")){
            Glide.with(context).load(R.drawable.up_arrow).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("Turn Right")){
            Glide.with(context).load(R.drawable.up_right_arrow).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("Turn Left")){
            Glide.with(context).load(R.drawable.up_left_arrow).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("Slight right")){
            Glide.with(context).load(R.drawable.slight_right).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("Slight left")){
            Glide.with(context).load(R.drawable.slight_left).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("on the right")){
            Glide.with(context).load(R.drawable.right_arrow).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }else if (getList().get(position).equalsIgnoreCase("on the left")){
            Glide.with(context).load(R.drawable.left_arrow).placeholder(R.drawable.image_placeholder).into(holder.photo);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sign;
        ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sign = itemView.findViewById(R.id.tv_steps);
            photo = itemView.findViewById(R.id.iv_steps);
        }
    }
}
