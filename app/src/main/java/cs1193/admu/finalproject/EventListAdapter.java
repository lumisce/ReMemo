package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import cs1193.admu.finalproject.model.Event;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;

public class EventListAdapter extends RealmBaseAdapter<Event> implements ListAdapter {

    private OrderedRealmCollection<Event> events;

    public EventListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Event> data) {
        super(context, data);
        events = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.fragment_event_row, parent, false);

        TextView title = (TextView) v.findViewById(R.id.event_title);
        TextView date = (TextView) v.findViewById(R.id.event_date);
        TextView location = (TextView) v.findViewById(R.id.event_location);

        title.setText(events.get(position).getTitle());
        date.setText(events.get(position).getDate()+"");
        location.setText(events.get(position).getLocation());
        v.setTag(events.get(position).getId());
        return v;
    }
}
