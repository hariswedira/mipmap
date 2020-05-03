package org.d3ifcool.cubeacon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.d3ifcool.cubeacon.R;
import org.d3ifcool.cubeacon.models.Schedule;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context context;
    private int type;
    private ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_schedule,parent,false);
        return new ScheduleAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder holder, int position) {
        final Schedule scheduleData = schedules.get(position);
        holder.title.setText(getSchedules().get(position).getTitle());
        holder.time.setText(getSchedules().get(position).getTime());
        holder.lecture.setText(getSchedules().get(position).getLecture());

        if (scheduleData.getIsGoing()==0){
            holder.itemList.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_deep_blue));
            holder.title.setTextColor(context.getResources().getColor(R.color.white));
            holder.time.setTextColor(context.getResources().getColor(R.color.white));
            holder.lecture.setTextColor(context.getResources().getColor(R.color.white));
            holder.txt.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.itemList.setBackground(context.getResources().getDrawable(R.drawable.bg_card_white));
        }
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, lecture, txt;
        ConstraintLayout itemList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_schedule_time);
            title = itemView.findViewById(R.id.tv_schedule_titile);
            lecture = itemView.findViewById(R.id.tv_schedule_lecture);
            txt = itemView.findViewById(R.id.lecture_txt);
            itemList = itemView.findViewById(R.id.item_schedule);
        }
    }
}
