package com.feicui.edu.pie_chart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    public void draw(View view) {
        //找到控件的对象
        PieChartView pieChartView = (PieChartView) findViewById(R.id.pie);
        pieChartView.setAngle(-90,-180);
    }

}
