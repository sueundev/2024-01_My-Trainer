package com.example.mytrainer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    final String TAG = this.getClass().getSimpleName();

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;
    private String emailPrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Intent로부터 이메일의 앞부분을 가져옴
        emailPrefix = getIntent().getStringExtra("emailPrefix");

        init();
        SettingListener();

        // 맨 처음 시작할 탭 설정
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        // 선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.menu_home) {
                BlankFragment blankFragment = new BlankFragment();
                Bundle bundle = new Bundle();
                bundle.putString("emailPrefix", emailPrefix);
                blankFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, blankFragment)
                        .commit();
                return true;
            } else if (menuItem.getItemId() == R.id.menu_myPage) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new myPageFragment())
                        .commit();
                return true;
            } else if (menuItem.getItemId() == R.id.menu_record) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new recordFragment())
                        .commit();
                return true;
            } else return false;
        }
    }
}
