package com.chs.commonfunction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chs.commonfunction.prutorefresh.PruToRefreshActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.pru_to_refresh})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.pru_to_refresh:
                intent = new Intent(this, PruToRefreshActivity.class);
                startActivity(intent);
                break;
        }
    }
}
