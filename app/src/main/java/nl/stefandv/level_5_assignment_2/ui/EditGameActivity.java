package nl.stefandv.level_5_assignment_2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.stefandv.level_5_assignment_2.R;
import nl.stefandv.level_5_assignment_2.model.Game;

public class EditGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.Edit_Game_Action_Bar);

        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner2);
        final EditText titleInput = (EditText) findViewById(R.id.titleInput);
        final EditText platformInput = (EditText) findViewById(R.id.platformInput);


        // here comes the dropdown
        Spinner dropdown = findViewById(R.id.spinner2);
        String[] items = new String[]{"", getString(R.string.dropdown_option_1), getString(R.string.dropdown_option_2), getString(R.string.dropdown_option_3), getString(R.string.dropdown_option_4)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        // Creating intent
        Intent intent = getIntent();
        final Game game = (Game) intent.getSerializableExtra("Game");

        // set all fields
        titleInput.setText(game.getMTitle());
        dropdown.setSelection(getSpinnerValue(game.getMStatus()));
        platformInput.setText(game.getMPlatform());

        // create floating button
        FloatingActionButton fab = findViewById(R.id.saveButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String spinnerChoice = mySpinner.getSelectedItem().toString();
                String title = titleInput.getText().toString();
                String platform = platformInput.getText().toString();

                // set date
                SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.dateFormat));
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());

                // update game object
                game.setMdate(date);
                game.setmTitle(title);
                game.setmPlatform(platform);
                game.setmStatus(spinnerChoice);

                // create return intent
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Game", game);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();


            }
        });
    }


    // function to retrieve spinner value
    private int getSpinnerValue(String value) {
        int pos = 9;
        switch (value) {
            case "":
                pos = 0;
                break;
            case "Want to play":
                pos = 1;
                break;
            case "Playing":
                pos = 2;
                break;
            case "Stalled":
                pos = 3;
                break;
            case "Dropped":
                pos = 4;
                break;

        }
        return pos;
    }


}
