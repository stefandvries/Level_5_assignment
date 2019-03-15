package nl.stefandv.level_5_assignment_2.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import nl.stefandv.level_5_assignment_2.database.GameRepository;
import nl.stefandv.level_5_assignment_2.model.Game;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private GameRepository mRepository;

    private LiveData<List<Game>> mReminders;


    public MainViewModel(@NonNull Application application) {

        super(application);

        mRepository = new GameRepository(application.getApplicationContext());

        mReminders = mRepository.getAllGames();

    }


    public LiveData<List<Game>> getReminders() {

        return mReminders;

    }


    public void insert(Game game) {

        mRepository.insert(game);

    }


    public void update(Game game) {

        mRepository.update(game);

    }


    public void delete(Game game) {

        mRepository.delete(game);

    }

}