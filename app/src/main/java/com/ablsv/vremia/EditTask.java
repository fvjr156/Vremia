package com.ablsv.vremia;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import androidx.fragment.app.DialogFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import io.kredibel.picker.Picker;
import io.kredibel.picker.PickerListener;

public class EditTask extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
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

       Base64String b = new Base64String();
       edit_imagedata64 = b.getBase64string();

        edit_edt_TitleInput.setText(edit_taskTitle);
        edit_edt_DescrInput.setText(edit_taskDescr);

        if(edit_imagedata64 != null)
        {
            byte[] byteArray = Base64.decode(edit_imagedata64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            edit_CIV_ImageSelector.setImageBitmap(bitmap);
        }
        else {
            edit_CIV_ImageSelector.setImageResource(R.drawable.image_placeholder);
        }

        edit_txv_ColorPickerPreview.setBackgroundColor(edit_taskColor);

        //datepreview and add the date and time values as pickers' default value



    }
    int DatePickerMonth, DatePickerDayofmonth, DatePickerYear, TimePickerHour, TimePickerMinute;
    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayofmonth) {
        Log.d("year", String.valueOf(year));
        Log.d("month", String.valueOf(month));
        Log.d("dayofmonth", String.valueOf(dayofmonth));

        DatePickerYear = year;
        DatePickerMonth = month;
        DatePickerDayofmonth = dayofmonth;

    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hour, int minute) {
        Log.d("hour", String.valueOf(hour));
        Log.d("minute", String.valueOf(minute));

        TimePickerHour = hour;
        TimePickerMinute = minute;

        String datetimepreview = (DatePickerMonth+1)+"/"+DatePickerDayofmonth+"/"+DatePickerYear+" - "+TimePickerHour+":"+TimePickerMinute;
//debug. please add feature that accepts user's date preference or phone regional date settings
        edit_txv_DateTimeInputPreview.setText(datetimepreview);
    }

Bitmap imageBitmap; byte[] edit_byteArray_imageData; String edit_StringBase64_imageData;
    @Override
    public void onClick(View v)
    {
        if(v == edit_CIV_ImageSelector)
        {
            imagepick.pickGallery(new PickerListener() {
                @Override
                public void onPicked(Uri uri, File file, Bitmap bitmap) {
                    //get image from image selector, convert to bitmap and set it as CIV's image, replacing placeholder
                    String imagepath = file.getAbsolutePath();
                    imageBitmap = BitmapFactory.decodeFile(imagepath);
                    edit_CIV_ImageSelector.setImageBitmap(imageBitmap);

                    Toast.makeText(EditTask.this, "Image successfully added", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if(v == edit_btn_overwrite)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            edit_byteArray_imageData = stream.toByteArray();
            edit_StringBase64_imageData = Base64.encodeToString(edit_byteArray_imageData, Base64.DEFAULT);

            //TODO: READ COMMENTS LIKE THESE TO KNOW WHERE YOU LEFT
            DatabaseHelper dbh = new DatabaseHelper(EditTask.this);
            int ocolor = ((ColorDrawable) edit_txv_ColorPickerPreview.getBackground()).getColor();
            dbh.updateTask(edit_taskId, edit_edt_TitleInput.getText().toString(), edit_edt_DescrInput.getText().toString(), ocolor, DatePickerYear, DatePickerMonth, DatePickerDayofmonth, TimePickerHour, TimePickerMinute, edit_StringBase64_imageData);

            Toast.makeText(EditTask.this, "Your task is created.", Toast.LENGTH_SHORT).show();

            Intent intentToMain = new Intent(EditTask.this, MainActivity.class);
            startActivity(intentToMain);

            finish();
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
            DialogFragment timep = new com.ablsv.vremia.TimePicker();
            timep.show(getSupportFragmentManager(), "Time Picker");
            DialogFragment datep = new com.ablsv.vremia.DatePicker();
            datep.show(getSupportFragmentManager(), "Date Picker");

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
