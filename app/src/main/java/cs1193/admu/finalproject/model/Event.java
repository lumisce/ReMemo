package cs1193.admu.finalproject.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by candy on 7/16/16.
 */
public class Event extends RealmObject {
    @PrimaryKey
    private String id;
    private String title, location, comment;
    private String userId;
    private Date date;
    private RealmList<Tag> tags;
    private String startTime,endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user) {
        this.userId = user;
    }

    public RealmList<Tag> getTags() {
        return tags;
    }

    public void setTags(RealmList<Tag> tags) {
        this.tags = tags;
    }
}
