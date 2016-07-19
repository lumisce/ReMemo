package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.EventDate;
import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;

public class EventListAdapter extends RealmBaseAdapter<Event> implements ListAdapter {

    private RealmList<EventDate> eventDates;

    public EventListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Event> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
