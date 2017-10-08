package com.dvoiss.literallytoastdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.dvoiss.literallytoast.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.show_toast_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.create(MainActivity.this, R.string.toast_demo_text, Toast.LENGTH_SHORT)
                    .show();
            }
        });
    }
}
