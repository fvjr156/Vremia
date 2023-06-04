package com.ablsv.vremia;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList task_id,
            task_title,
            task_description,
            task_color,
            task_year,
            task_month,
            task_dayofmonth,
            task_hour,
            task_minute,
            task_imagedata;
    private android.text.format.DateFormat df;

    CustomAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList description, ArrayList color, ArrayList year, ArrayList month, ArrayList dayofmonth, ArrayList hour, ArrayList minute, ArrayList imagedata)
    {
        this.activity = activity;
        this.task_id = id;
        this.context = context;
        this.task_title = title;
        this.task_description = description;
        this.task_color = color;
        this.task_year = year;
        this.task_month = month;
        this.task_dayofmonth = dayofmonth;
        this.task_hour = hour;
        this.task_minute = minute;
        this.task_imagedata = imagedata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String base64String = String.valueOf(task_imagedata.get(position));
        byte[] byteArray = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        holder.image_task.setImageBitmap(bitmap);

        holder.title_task.setText((CharSequence) task_title.get(position));
        holder.color_task.setBackgroundColor(Integer.parseInt(String.valueOf(task_color.get(position))));

        int taskColor = Integer.parseInt(String.valueOf(task_color.get(position)));
        int taskColorR = Color.red(taskColor);
        int taskColorG = Color.green(taskColor);
        int taskColorB = Color.blue(taskColor);

        double taskColorBrightness = (taskColorR * 0.299 + taskColorG * 0.587 + taskColorB * 0.114) / 255;

        if(taskColorBrightness > 0.5)
        {
            holder.title_task.setTextColor(Color.BLACK);
            holder.date_task.setTextColor(Color.BLACK);
        }
        else {
            holder.title_task.setTextColor(Color.WHITE);
            holder.date_task.setTextColor(Color.WHITE);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(String.valueOf(task_dayofmonth.get(position))));
        calendar.set(Calendar.MONTH, Integer.parseInt(String.valueOf(task_month.get(position))));
        calendar.set(Calendar.YEAR, Integer.parseInt(String.valueOf(task_year.get(position))));

        Locale locale = context.getResources().getConfiguration().locale;
        String dateFormat = DateFormat.getBestDateTimePattern(locale, "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);

        String task_time = task_time = String.format("%02d:%02d", Integer.parseInt(String.valueOf(task_hour.get(position))), Integer.parseInt(String.valueOf(task_minute.get(position))));

        SimpleDateFormat militaryFormat, twelveHourFormat;
        String twelveHourTime = "";



        boolean is24Hour = DateFormat.is24HourFormat(activity.getApplicationContext());
        if (is24Hour) {
            task_time = String.format("%02d:%02d", Integer.parseInt(String.valueOf(task_hour.get(position))), Integer.parseInt(String.valueOf(task_minute.get(position))));
        } else {
             militaryFormat = new SimpleDateFormat("HH:mm");
             twelveHourFormat = new SimpleDateFormat("hh:mm a");

             try {
                 Date militaryDate = militaryFormat.parse(task_time);
                 twelveHourTime = twelveHourFormat.format(militaryDate);
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }

                task_time = twelveHourTime;
        }

        String task_date = sdf.format(calendar.getTime());
        holder.date_task.setText((CharSequence) task_date +", "+(CharSequence) task_time);

    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }
    //complete the code and implement needed tasks

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_task, date_task;
        CircleImageView image_task;
        RelativeLayout color_task;
        LinearLayout mainlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainlayout = itemView.findViewById(R.id.mainLayout);
            color_task = itemView.findViewById(R.id.task_display_rll_taskContainer);
            image_task = itemView.findViewById(R.id.task_display_CIV_ImageView);
            title_task = itemView.findViewById(R.id.task_display_txv_TitleView);
            date_task = itemView.findViewById(R.id.task_display_txv_DateTimeDisplay);

        }
    }
}
//TODO: CODE EDITTASKS
