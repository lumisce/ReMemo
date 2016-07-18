package cs1193.admu.finalproject.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by candy on 7/16/16.
 */
public class Tag extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private RealmList<Event> events;
    private RealmList<Memo> memos;

    public Tag() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Event> getEvents() {
        return events;
    }

    public void setEvents(RealmList<Event> events) {
        this.events = events;
    }

    public RealmList<Memo> getMemos() {
        return memos;
    }

    public void setMemos(RealmList<Memo> memos) {
        this.memos = memos;
    }
}
