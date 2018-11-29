package com.middlecurved.custombottomnavigationbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MiddleCurvedBottomNavigationBar curvedBottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curvedBottomNavigationBar = findViewById(R.id.bottombar);

        curvedBottomNavigationBar.setMenuIcons(R.drawable.ic_archive_black_24dp,R.drawable.ic_archive_black_24dp, R.drawable.ic_archive_black_24dp, R.drawable.ic_archive_black_24dp);



    }
}
