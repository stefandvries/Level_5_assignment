package nl.stefandv.level_5_assignment_2.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nl.stefandv.level_5_assignment_2.R;
import nl.stefandv.level_5_assignment_2.model.Game;
import nl.stefandv.level_5_assignment_2.model.RecyclerTouchListener;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener, Serializable {

    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private MainViewModel mMainViewModel;
    private List<Game> mgames;
    private List<Game> tmpList;
    View.OnClickListener mGetAllMyGamesBack;
    View.OnClickListener mGetMyGameBack;
    private Game tmpGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.mainactivity_action_bar);

        // Create Floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, AddGameActivity.class);
                startActivityForResult(i, 1);

            }
        });


        // Create recylerView
        recyclerView = findViewById(R.id.recyclerv_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create arrayLists for storing objects
        mgames = new ArrayList<>();
        tmpList = new ArrayList<>();

        // Create MainModelView
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getReminders().observe(this, new Observer<List<Game>>() {

            @Override
            public void onChanged(@Nullable List<Game> reminders) {
                mgames = reminders;
                updateUI();
            }
        });

        // Add on touchlisteners to recyclerview
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, EditGameActivity.class);
                Game gameToEdit = mgames.get(position);
                intent.putExtra("Game", gameToEdit);
                startActivityForResult(intent, 2);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        // Create on Swipe listener
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());

                        tmpGame = mgames.get(position);
                        mMainViewModel.delete(mgames.get(position));
                        mgames.remove(position);
                        adapter.notifyItemRemoved(position);

                        Snackbar.make(findViewById(android.R.id.content), tmpGame.getMTitle() + " deleted", Snackbar.LENGTH_LONG)
                                .setAction(R.string.snackbar_message, mGetMyGameBack)
                                .setActionTextColor(Color.RED)
                                .show();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnItemTouchListener(this);

        // create snackbar listeners

        mGetMyGameBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.insert(tmpGame);
                updateUI();
            }

        };

        mGetAllMyGamesBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int id = 0; id < tmpList.size(); id++) {
                    mMainViewModel.insert(tmpList.get((id)));
                }
                updateUI();
            }
        };

    }


    private void updateUI() {
        if (adapter == null) {
            adapter = new RecyclerViewAdapter(mgames);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.swapList(mgames);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_item) {

            tmpList.addAll(mgames);

            for (int i = 0; i < mgames.size(); ) {

                mMainViewModel.delete(mgames.get(i));
                mgames.remove(i);
            }

            updateUI();

            Snackbar.make(findViewById(android.R.id.content), R.string.snackbar_text, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_message, mGetAllMyGamesBack)
                    .setActionTextColor(Color.RED)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                String mTitle = data.getStringExtra("title");
                String mStatus = data.getStringExtra("status");
                String mPlatform = data.getStringExtra("platform");
                String mdate = data.getStringExtra("date");

                Game newGame = new Game(mTitle, mStatus, mPlatform, mdate);
                // New timestamp: timestamp of update
                mMainViewModel.insert(newGame);
                updateUI();

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                final Game editedGame = (Game) data.getSerializableExtra("Game");

                // New timestamp: timestamp of update
                mMainViewModel.update(editedGame);
                updateUI();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


}



