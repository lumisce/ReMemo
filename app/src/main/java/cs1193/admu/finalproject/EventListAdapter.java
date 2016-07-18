package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class EventListAdapter extends RealmBaseAdapter<Event> implements ListAdapter {

    private OrderedRealmCollection<Event> events;

    public EventListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Event> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: 7/16/16 update eventlistadapter getview
        return null;
    }
}
