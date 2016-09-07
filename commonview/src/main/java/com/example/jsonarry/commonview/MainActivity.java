package com.example.jsonarry.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton button = (ToggleButton) findViewById(R.id.toggle_button);
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.countries_list);
        textView.setAdapter(adapter);*/
    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
}

