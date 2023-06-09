package com.ablsv.vremia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle sis)
    {
        super.onCreate(sis);
        setContentView(R.layout.about);

        TextView vremiabout3 = findViewById(R.id.vremiabout3);

        vremiabout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/juniorvillanueva156/Vremia.git");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
