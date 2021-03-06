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

    public static final String EVENT_ID = "eventIdKey";

    private OnEventFragmentInteractionListener mListener;
    private OrderedRealmCollection<Event> events = new RealmList<>();
    private EventListAdapter adapter;
    private String userId;

    public EventListFragment() {


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();

        events = realm.where(Event.class).equalTo("userId",userId).findAll();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), EventViewActivity.class);
        i.putExtra(EventListFragment.EVENT_ID, (String) v.getTag());
        startActivity(i);
    }

    public EventListFragment setUserId(String id){

        userId = id;
        return this;

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