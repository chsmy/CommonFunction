package com.chs.commonfunction.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chs.commonfunction.R;

public class CoordinatorlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout);
    }
    public void first(View view) {
        Intent intent = new Intent(this,FirstActivity.class);
        startActivity(intent);
    }

    public void second(View view) {
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

    public void third(View view) {
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }

    public void four(View view) {
        Intent intent = new Intent(this,FourActivity.class);
        startActivity(intent);
    }
}
