package com.tiki.tikitimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tikitimer.R;
import com.shawnlin.numberpicker.NumberPicker;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity: ";
    Button startButton;
    com.shawnlin.numberpicker.NumberPicker numberPicker;
    boolean isRunning;
    String pickerValues[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRunning = false;

        numberPicker = findViewById(R.id.numberPicker);
        startButton = findViewById(R.id.StartTimerButton);
        pickerValues = fillPicker();
        setNumberPicker(numberPicker,pickerValues);
        numberPicker.setValue(19);
        numberPicker.setOnValueChangedListener((numberPicker, oldValue, newValue) -> {
            int time = Integer.parseInt(pickerValues[numberPicker.getValue()]);
            HelperSharedPreferences.putSharedPreferencesInt(getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.tikiTimeInMillis, time*60*1000);
        });

        AlertManager alertManager = new AlertManager(this);

        HelperSharedPreferences.putSharedPreferencesBoolean(getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.shuffledTime, true);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning){
                    alertManager.stopAlert();
                    Log.i(TAG, "cancelled service");
                    isRunning = false;
                    startButton.setText("Start Tiki");
                    HelperSharedPreferences.putSharedPreferencesBoolean(getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.shouldStop, true);
                }
                else if (!isRunning){
                    Log.i(TAG, "Started service");
                    HelperSharedPreferences.putSharedPreferencesBoolean(getApplicationContext(),HelperSharedPreferences.SharedPreferencesKeys.shouldStop, false);
                    alertManager.startAlert();
                    isRunning = true;
                    startButton.setText("End Tiki");
                }
            }
        });
    }

    private String[] fillPicker() {
        String[] array = new String[29];
        short valueToAdd = 1;
        for (int i = 0; i < array.length; i++){

            if (i < 20){
                array[i] = Short.toString(valueToAdd);
                if (i!=19)valueToAdd++;
            }
            else if (i < 28){
                valueToAdd+=5;
                array[i] = Short.toString(valueToAdd);
            }
            else if (i == 28){
                valueToAdd+=60;
                array[i] = Short.toString(valueToAdd);
            }
        }
        return array;
    }
    private void setNumberPicker(NumberPicker numberPicker, String [] numbers ){
        numberPicker.setMaxValue(numbers.length-1);
        numberPicker.setMinValue(0);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDisplayedValues(numbers);
    }
}

