package com.chs.commonfunction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chs.commonfunction.camera.CameraActivity;
import com.chs.commonfunction.camera.WebCameraActivity;
import com.chs.commonfunction.coordinatorlayout.CoordinatorlayoutActivity;
import com.chs.commonfunction.popupwindow.PopActivity;
import com.chs.commonfunction.prutorefresh.PruToRefreshActivity;
import com.chs.commonfunction.recyclerview.RecycleActivity;
import com.chs.commonfunction.tabselector.top.TopSelectorActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.pru_to_refresh,R.id.tab_select,R.id.camera,R.id.web_camera,R.id.pop,R.id.coordinatorlayout,R.id.recycle_view})
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.pru_to_refresh:
                intent = new Intent(this, PruToRefreshActivity.class);
                startActivity(intent);
                break;
            case R.id.tab_select:
                intent = new Intent(this, TopSelectorActivity.class);
                startActivity(intent);
                break;
            case R.id.camera:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.web_camera:
                intent = new Intent(this, WebCameraActivity.class);
                startActivity(intent);
                break;
            case R.id.pop:
                intent = new Intent(this, PopActivity.class);
                startActivity(intent);
                break;
            case R.id.coordinatorlayout:
                intent = new Intent(this, CoordinatorlayoutActivity.class);
                startActivity(intent);
                break;
            case R.id.recycle_view:
                intent = new Intent(this, RecycleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
