package com.ablsv.vremia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import io.kredibel.picker.Picker;

public class EditTask extends AppCompatActivity implements View.OnClickListener{
    ImageView edit_CIV_ImageSelector, edit_toolbar_img_cancel;
    EditText edit_edt_TitleInput, edit_edt_DescrInput, edit_edt_ColorPickerHexadecimalInput;
    Button edit_btn_ColorPicker, edit_btn_DateTimeInput;
    TextView edit_txv_DateTimeInputPreview, edit_txv_ColorInputText, edit_txv_ColorPickerPreview;
    Picker imagepick;

    private Activity activity;
    private Context context;
    private String edit_taskId;

    private String edit_imagedata64, edit_taskTitle, edit_taskDescr;
    private int edit_taskColor, year, month, dayofmonth, hour, minute;

    Button edit_btn_overwrite, edit_btn_delete;

    @Override
    protected void onCreate(Bundle sis)
    {
        super.onCreate(sis);
        setContentView(R.layout.edit);

        edit_btn_overwrite = findViewById(R.id.edit_btn_Overwrite);
        edit_btn_delete = findViewById(R.id.edit_btn_Delete);
        edit_btn_delete.setOnClickListener(this);
        edit_btn_overwrite.setOnClickListener(this);
        //TODO: Marami, basahin mo nalang,
        //code functions of edit and overwrite
        //implement nav drawer on main
        //code alarm and notification

        //Image Selector
        edit_CIV_ImageSelector = findViewById(R.id.edit_CIV_ImageSelector);
        edit_CIV_ImageSelector.setOnClickListener(this);
        //Save and Cancel Buttons in toolbar
        edit_toolbar_img_cancel = findViewById(R.id.edit_toolbar_img_cancel);
        edit_toolbar_img_cancel.setOnClickListener(this);
        //EditText for Title, Description, and Hexadecimal Color Input
        edit_edt_TitleInput = findViewById(R.id.edit_edt_TitleInput);
        edit_edt_TitleInput.setOnClickListener(this);
        edit_edt_DescrInput = findViewById(R.id.edit_edt_DescrInput);
        edit_edt_DescrInput.setOnClickListener(this);
        edit_edt_ColorPickerHexadecimalInput = findViewById(R.id.edit_edt_ColorPickerHexadecimalInput);
        edit_edt_ColorPickerHexadecimalInput.setOnClickListener(this);
        //Button for Color Picker and Date-Time Input
        edit_btn_ColorPicker = findViewById(R.id.edit_btn_ColorPicker);
        edit_btn_ColorPicker.setOnClickListener(this);
        edit_btn_DateTimeInput = findViewById(R.id.edit_btn_DateTimeInput);
        edit_btn_DateTimeInput.setOnClickListener(this);
        //TextView's
        edit_txv_ColorInputText = findViewById(R.id.edit_txv_ColorInputText);
        edit_txv_ColorInputText.setOnClickListener(this);
        edit_txv_ColorPickerPreview = findViewById(R.id.edit_txv_ColorPreview);
        edit_txv_ColorPickerPreview.setOnClickListener(this);
        edit_txv_DateTimeInputPreview = findViewById(R.id.edit_txv_DateTimeInputPreview);
        edit_txv_DateTimeInputPreview.setOnClickListener(this);

        imagepick = new Picker(EditTask.this);

        Intent intent = getIntent();
        edit_taskId = intent.getStringExtra("task_id");
        String edit_taskTitle = intent.getStringExtra("task_title");
        String edit_taskDescr = intent.getStringExtra("task_description");
        int edit_taskColor = intent.getIntExtra("task_color", 0);
        int edit_taskYear = intent.getIntExtra("task_year", 0);
        int edit_taskMonth = intent.getIntExtra("task_month", 0);
        int edit_taskDayOfMonth = intent.getIntExtra("task_dayofmonth", 0);
        int edit_taskHour = intent.getIntExtra("task_hour", 0);
        int edit_taskMinute = intent.getIntExtra("task_minute", 0);
        String edit_taskImagedata = intent.getStringExtra("task_imagedata");

        edit_edt_TitleInput.setText(edit_taskTitle);
        edit_edt_DescrInput.setText(edit_taskDescr);

        if(edit_imagedata64 != null)
        {
            String base64String = String.valueOf(edit_imagedata64);
            byte[] byteArray = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            edit_CIV_ImageSelector.setImageBitmap(bitmap);
        }
        else {
            edit_CIV_ImageSelector.setImageResource(R.drawable.image_placeholder);
        }

        edit_txv_ColorPickerPreview.setBackgroundColor(edit_taskColor);

        //datepreview and add the date and time values as pickers' default value



    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
            // Handle the selected date
            // The selected year, month, and day will be passed as arguments to this method
        }
    };
    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        // Handle the selected time
                        // hourOfDay: selected hour
                        // minute: selected minute
                    }
                },
                hour,
                minute,
                true // set to true for 24-hour format
        );

        timePickerDialog.show();
    }


    @Override
    public void onClick(View v)
    {
        if(v == edit_btn_overwrite)
        {
            //TODO: READ COMMENTS LIKE THESE TO KNOW WHERE YOU LEFT
            DatabaseHelper dbh = new DatabaseHelper(EditTask.this);

        }
        if(v == edit_btn_delete)
        {
            AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
            alert2.setTitle("Confirm");
            alert2.setIcon(R.drawable.baseline_warning_amber_24);
            alert2.setMessage("Are you sure you want to Delete the Task?");
            alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert2.setPositiveButton("Yes (Delete Task)", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                    DatabaseHelper dbh = new DatabaseHelper(EditTask.this);
                    dbh.deleteOneRow(edit_taskId);

                    Intent intent = new Intent(EditTask.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alert2.show();


        }
        if(v == edit_btn_DateTimeInput)
        {
            showTimePickerDialog();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayofmonth);
            datePickerDialog.show();
        }
        if(v == edit_toolbar_img_cancel)
        {
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setTitle("Confirm");
            alert1.setIcon(R.drawable.baseline_warning_amber_24);
            alert1.setMessage("You have an unsaved task. Are you sure you want to exit?");
            alert1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert1.setPositiveButton("Yes (Discard Task)", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                    Intent intentToMain = new Intent(EditTask.this, MainActivity.class);
                    startActivity(intentToMain);
                    finish();
                }
            });
            alert1.show();
        }

    }
}
