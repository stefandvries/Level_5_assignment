package nl.stefandv.level_5_assignment_2.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;


@Entity(tableName = "game")

public class Game implements Serializable{

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "status")
    private String mStatus;

    @ColumnInfo(name = "platform")
    private String mPlatform;

    @ColumnInfo(name = "date")
    private String mdate;


    public Game(String mTitle, String mStatus, String mPlatform, String mdate) {
        this.mTitle = mTitle;
        this.mStatus = mStatus;
        this.mPlatform = mPlatform;
        this.mdate = mdate;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getMTitle() {
        return mTitle;
    }

    public String getMStatus() {
        return mStatus;
    }

    public String getMPlatform() {
        return mPlatform;
    }

    public String getMdate() {
        return mdate;
    }
    public Long getId() {
        return id;
    }

}
