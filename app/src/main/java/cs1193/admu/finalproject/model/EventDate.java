package cs1193.admu.finalproject.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by candy on 7/19/16.
 */
public class EventDate extends RealmObject {
    private RealmList<Event> events;

    public EventDate() {
    }

    public RealmList<Event> getEvents() {
        return events;
    }

    public void setEvents(RealmList<Event> events) {
        this.events = events;
    }
}
