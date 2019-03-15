package nl.stefandv.level_5_assignment_2.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nl.stefandv.level_5_assignment_2.R;

public class AddGameActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        // Set action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Game Activity");

        // Connect all Views
        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner1);
        final EditText titleInput = (EditText) findViewById(R.id.titleInput);
        final EditText platformInput = (EditText) findViewById(R.id.platformInput);

        // Create floating action button
        FloatingActionButton fab = findViewById(R.id.saveButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String spinnerChoice = mySpinner.getSelectedItem().toString();
                String title = titleInput.getText().toString();
                String platform = platformInput.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", title);
                returnIntent.putExtra("platform", platform);
                returnIntent.putExtra("status", spinnerChoice);
                returnIntent.putExtra("date" , date);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

// Create dropdown for choosing status
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"","Want to play", "Playing", "Stalled", "Dropped"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
