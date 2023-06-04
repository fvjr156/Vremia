package com.ablsv.vremia;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.kredibel.picker.Picker;
import io.kredibel.picker.PickerListener;
import yuku.ambilwarna.AmbilWarnaDialog;

public class AddTask extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ImageView add_CIV_ImageSelector, add_toolbar_img_cancel, add_toolbar_img_save;
    EditText add_edt_TitleInput, add_edt_DescrInput, add_edt_ColorPickerHexadecimalInput;
    Button add_btn_ColorPicker, add_btn_DateTimeInput;
    TextView add_txv_DateTimeInputPreview, add_txv_ColorInputText, add_txv_ColorPickerPreview;
    Picker imagepick;

    //primitive data
    int DefaultColor = 000000;
    int newColor = 000000;
    int userInput_color = 000000;
    String add_StringBase64_imageData;
    byte[] add_byteArray_imageData;
    Bitmap imageBitmap;

    //date variables
    int DatePickerYear, DatePickerMonth, DatePickerDayofmonth, TimePickerHour, TimePickerMinute;

    @Override
    protected void onCreate(Bundle sis)
    {
        super.onCreate(sis);
        setContentView(R.layout.add);

        //Image Selector
        add_CIV_ImageSelector = findViewById(R.id.add_CIV_ImageSelector);
        add_CIV_ImageSelector.setOnClickListener(this);
        //Save and Cancel Buttons in toolbar
        add_toolbar_img_cancel = findViewById(R.id.add_toolbar_img_cancel);
        add_toolbar_img_cancel.setOnClickListener(this);
        add_toolbar_img_save = findViewById(R.id.add_toolbar_img_save);
        add_toolbar_img_save.setOnClickListener(this);
        //EditText for Title, Description, and Hexadecimal Color Input
        add_edt_TitleInput = findViewById(R.id.add_edt_TitleInput);
        add_edt_TitleInput.setOnClickListener(this);
        add_edt_DescrInput = findViewById(R.id.add_edt_DescrInput);
        add_edt_DescrInput.setOnClickListener(this);
        add_edt_ColorPickerHexadecimalInput = findViewById(R.id.add_edt_ColorPickerHexadecimalInput);
        add_edt_ColorPickerHexadecimalInput.setOnClickListener(this);
        //Button for Color Picker and Date-Time Input
        add_btn_ColorPicker = findViewById(R.id.add_btn_ColorPicker);
        add_btn_ColorPicker.setOnClickListener(this);
        add_btn_DateTimeInput = findViewById(R.id.add_btn_DateTimeInput);
        add_btn_DateTimeInput.setOnClickListener(this);
        //TextView's
        add_txv_ColorInputText = findViewById(R.id.add_txv_ColorInputText);
        add_txv_ColorInputText.setOnClickListener(this);
        add_txv_ColorPickerPreview = findViewById(R.id.add_txv_ColorPreview);
        add_txv_ColorPickerPreview.setOnClickListener(this);
        add_txv_DateTimeInputPreview = findViewById(R.id.add_txv_DateTimeInputPreview);
        add_txv_DateTimeInputPreview.setOnClickListener(this);

        imagepick = new Picker(AddTask.this);

        add_edt_ColorPickerHexadecimalInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            String edt_userInputColor; int edt_userInputColortoInt;
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    edt_userInputColor = textView.getText().toString();
                    if(edt_userInputColor == null || edt_userInputColor.length() != 6 ||
                            !edt_userInputColor.matches("-?[0-9a-fA-F]+"))
                    {
                            edt_userInputColor = "000000";
                    }
                    userInput_color = Integer.parseInt(edt_userInputColor, 16);
                    edt_userInputColortoInt = Color.parseColor("#FF"+edt_userInputColor);
                    add_txv_ColorPickerPreview.setBackgroundColor(edt_userInputColortoInt);
                    DefaultColor = edt_userInputColortoInt;
                    add_txv_ColorInputText.setText(edt_userInputColor);
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        add_edt_ColorPickerHexadecimalInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            String edt_userInputColor; int edt_userInputColortoInt;
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b)
                {
                    add_edt_ColorPickerHexadecimalInput = (EditText) view;
                    edt_userInputColor = add_edt_ColorPickerHexadecimalInput.getText().toString();

                    if(edt_userInputColor == null || edt_userInputColor.length() != 6
                            || !edt_userInputColor.matches("-?[0-9a-fA-F]+"))
                    {
                        edt_userInputColor = "000000";
                    }
                    userInput_color = Integer.parseInt(edt_userInputColor, 16);
                    edt_userInputColortoInt = Color.parseColor("#FF"+edt_userInputColor);
                    add_txv_ColorPickerPreview.setBackgroundColor(edt_userInputColortoInt);
                    DefaultColor = edt_userInputColortoInt;
                    add_txv_ColorInputText.setText(edt_userInputColor);
                }
            }
        });

        //end of onCreate method
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
        Log.d("year", String.valueOf(year));
        Log.d("month", String.valueOf(month));
        Log.d("dayofmonth", String.valueOf(dayofmonth));

        DatePickerYear = year;
        DatePickerMonth = month;
        DatePickerDayofmonth = dayofmonth;

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Log.d("hour", String.valueOf(hour));
        Log.d("minute", String.valueOf(minute));

        TimePickerHour = hour;
        TimePickerMinute = minute;

        String datetimepreview = (DatePickerMonth+1)+"/"+DatePickerDayofmonth+"/"+DatePickerYear+" - "+TimePickerHour+":"+TimePickerMinute;
//debug. please add feature that accepts user's date preference or phone regional date settings
        add_txv_DateTimeInputPreview.setText(datetimepreview);
    }


    //Handle clicks here
    @Override
    public void onClick(View view) {
        int v = view.getId();

        if(v == R.id.add_toolbar_img_save) {
            //Database: Add task to SQL

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            add_byteArray_imageData = stream.toByteArray();
            add_StringBase64_imageData = Base64.encodeToString(add_byteArray_imageData, Base64.DEFAULT);


            int output_COLOR = ((ColorDrawable) add_txv_ColorPickerPreview.getBackground()).getColor();
            int output_YEAR, output_MONTH, output_DAYOFMONTH, output_HOUR, output_MINUTE;

            String output_IMAGE = add_StringBase64_imageData;
            String output_TITLE = add_edt_TitleInput.getText().toString();
            String output_DESCRIPTION = add_edt_DescrInput.getText().toString();

            output_YEAR = DatePickerYear;
            output_MONTH = DatePickerMonth;
            output_DAYOFMONTH = DatePickerDayofmonth;
            output_HOUR = TimePickerHour;
            output_MINUTE = TimePickerMinute;

            DatabaseHelper sqlitedatabase = new DatabaseHelper(AddTask.this);
            sqlitedatabase.addTask(output_TITLE, output_DESCRIPTION, output_COLOR, output_YEAR, output_MONTH, output_DAYOFMONTH, output_HOUR, output_MINUTE, output_IMAGE);

            Toast.makeText(AddTask.this, "Your task is created.", Toast.LENGTH_SHORT).show();

            Intent intentToMain = new Intent(AddTask.this, MainActivity.class);
            startActivity(intentToMain);

            finish();

        }
        if(v == R.id.add_toolbar_img_cancel)
        {
            AlertDialog.Builder alert2 = new AlertDialog.Builder(AddTask.this);
            alert2.setTitle("Confirm");
            alert2.setIcon(R.drawable.baseline_warning_amber_24);
            alert2.setMessage("You have an unsaved task. Are you sure you want to exit?");
            alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert2.setPositiveButton("Yes (Discard Task)", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    dialogInterface.dismiss();

                    Intent intentToMain = new Intent(AddTask.this, MainActivity.class);
                    startActivity(intentToMain);
                    finish();
                }
            });
            alert2.show();
        }

        if(v == R.id.add_CIV_ImageSelector)
        {
            imagepick.pickGallery(new PickerListener() {
                @Override
                public void onPicked(Uri uri, File file, Bitmap bitmap) {
                    loadingtext();
                    //get image from image selector, convert to bitmap and set it as CIV's image, replacing placeholder
                    String imagepath = file.getAbsolutePath();
                    imageBitmap = BitmapFactory.decodeFile(imagepath);
                    add_CIV_ImageSelector.setImageBitmap(imageBitmap);

                    Toast.makeText(AddTask.this, "Image successfully added", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(v == R.id.add_btn_DateTimeInput)
        {
            DialogFragment timep = new com.ablsv.vremia.TimePicker();
            timep.show(getSupportFragmentManager(), "Time Picker");
            DialogFragment datep = new com.ablsv.vremia.DatePicker();
            datep.show(getSupportFragmentManager(), "Date Picker");
        }

        if(v == R.id.add_btn_ColorPicker)
        {
            AmbilWarnaDialog colorPickerDialog = new AmbilWarnaDialog(this, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {

                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    userInput_color = color;
                    String userInput_color_hexadecimal = toHex(userInput_color);
                    newColor = Color.parseColor("#"+userInput_color_hexadecimal);
                    add_txv_ColorPickerPreview.setBackgroundColor(newColor);
                    DefaultColor = newColor;
                    add_txv_ColorInputText.setText(userInput_color_hexadecimal);
                }
            });
            colorPickerDialog.show();
        }
    }
    public static String toHex(int i) {
        long unsignedDecimal = i & 0xFFFFFFFFL;
        String newcolor_hex = Long.toHexString(unsignedDecimal);
        newcolor_hex.length();
        return newcolor_hex.toUpperCase();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddTask.this);
        alert.setTitle("Confirm");
        alert.setIcon(R.drawable.baseline_warning_amber_24);
        alert.setMessage("You have an unsaved task. Are you sure you want to exit?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setPositiveButton("Yes (Discard Task)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                Intent intentToMain = new Intent(AddTask.this, MainActivity.class);
                startActivity(intentToMain);
                finish();
            }
        });
        alert.show();
    }
    
    private void loadingtext()
    {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
    }
}
