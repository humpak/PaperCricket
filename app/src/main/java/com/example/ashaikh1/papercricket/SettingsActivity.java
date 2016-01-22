package com.example.ashaikh1.papercricket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;


public class SettingsActivity extends ActionBarActivity {

    private EditText ballsNumber;
    private EditText wicketsNumber;
    private Switch aswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ballsNumber = (EditText)findViewById(R.id.ballsNumber);
        wicketsNumber = (EditText)findViewById(R.id.wicketsNumber);
        aswitch = (Switch)findViewById(R.id.switch1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void letsPlay(View view){
        PlayActivity.updateSettings(Integer.parseInt(ballsNumber.getText().toString()),
                Integer.parseInt(wicketsNumber.getText().toString()),aswitch.isChecked());
        //PlayActivity.updateSettings(5,2,false);
        finish();
        Intent intent = new Intent(this,PlayActivity.class);
        startActivity(intent);
    }




}
