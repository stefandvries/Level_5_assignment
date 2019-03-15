package nl.stefandv.level_5_assignment_2.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nl.stefandv.level_5_assignment_2.model.Game;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    //List<Reminder> getAllReminders();
    public LiveData<List<Game>> getAllGames();

    @Insert
    void insertGame(Game game);

    @Delete
    void deleteGame(Game game);

    @Update
    void updateGame(Game game);
}

