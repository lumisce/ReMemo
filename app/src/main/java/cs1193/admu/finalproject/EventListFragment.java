package cs1193.admu.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Date;
import java.util.UUID;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;

public class EventListFragment extends ListFragment {

    private OnEventFragmentInteractionListener mListener;
    private OrderedRealmCollection<Event> events = new RealmList<>();
    private EventListAdapter adapter;

    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        // TODO: 7/16/16 where event is by user
        realm.beginTransaction();
        Event e = realm.createObject(Event.class, UUID.randomUUID().toString());
        e.setTitle("New Event");
        e.setLocation("Quezon City");
        e.setDate(new Date());;
        realm.commitTransaction();
        events = realm.where(Event.class)
                .findAll();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // TODO: 7/16/16 start new Activity? ask acti to call new act
        Intent i = new Intent(getActivity(), EventViewActivity.class);
        i.putExtra("EVENT_ID", (String) v.getTag());
        startActivity(i);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        if (view instanceof ListView) {
            Context context = view.getContext();
            ListView listView = (ListView) view;
//            listView.setOnLongClickListener();
            listView.setAdapter(new EventListAdapter(context, events));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventFragmentInteractionListener) {
            mListener = (OnEventFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnEventFragmentInteractionListener {

        void onEventFragmentInteraction(Memo memo);
    }
}