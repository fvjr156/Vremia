package com.ablsv.vremia;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton main_FAB_toAdd;
    RecyclerView main_rcv_taskView;
    TextView main_txv_noData;
    CustomAdapter custom_adapter;
    ImageView main_toolbar_img_delete;

    DatabaseHelper myDB;
    ArrayList<String> task_id,
            task_title,
            task_description,
            task_color,
            task_year,
            task_month,
            task_dayofmonth,
            task_hour,
            task_minute,
            task_imagedata;

    DrawerLayout main_DrawerLayout;
    ImageView main_toolbar_img_menu;
    @Override
    protected void onCreate(Bundle sis)
    {
        super.onCreate(sis);
        setContentView(R.layout.main);

        main_txv_noData = findViewById(R.id.main_txv_noData);
        main_rcv_taskView = findViewById(R.id.main_rcv_taskView);

        main_FAB_toAdd = findViewById(R.id.main_FAB_toAdd);
        main_toolbar_img_delete = findViewById(R.id.main_toolbar_img_delete);

        main_FAB_toAdd.setOnClickListener(this);
        main_toolbar_img_delete.setOnClickListener(this);

        myDB = new DatabaseHelper(MainActivity.this);
        task_title = new ArrayList<>();
        task_description = new ArrayList<>();
        task_color = new ArrayList<>();
        task_year = new ArrayList<>();
        task_month = new ArrayList<>();
        task_dayofmonth = new ArrayList<>();
        task_hour = new ArrayList<>();
        task_minute = new ArrayList<>();
        task_imagedata = new ArrayList<>();
        task_id = new ArrayList<>();

        storedatainarrays();

        custom_adapter = new CustomAdapter(MainActivity.this, this, task_id, task_title, task_description, task_color, task_year, task_month, task_dayofmonth, task_hour, task_minute, task_imagedata);
        main_rcv_taskView.setAdapter(custom_adapter);
        main_rcv_taskView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    void storedatainarrays()
    {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            main_txv_noData.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
                task_description.add(cursor.getString(2));
                task_color.add(cursor.getString(3));
                task_year.add(cursor.getString(4));
                task_month.add(cursor.getString(5));
                task_dayofmonth.add(cursor.getString(6));
                task_hour.add(cursor.getString(7));
                task_minute.add(cursor.getString(8));
                task_imagedata.add(cursor.getString(9));

            }
            main_txv_noData.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View view) {
        int v = view.getId();
        if(v == R.id.main_FAB_toAdd)
        {
            Intent toAdd = new Intent(MainActivity.this, AddTask.class);
            startActivity(toAdd);
            finish();
        }
        if(v == R.id.main_toolbar_img_delete)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete All Tasks?")
                    .setIcon(R.drawable.baseline_delete_forever_24)
                    .setMessage("Are you sure? This action cannot be undone.")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                            myDB.deleteAllData();
                            dialogInterface.dismiss();

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("NO", null);

            builder.show();

        }
    }

}
