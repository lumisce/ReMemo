package cs1193.admu.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs1193.admu.finalproject.model.Event;
import io.realm.Realm;

public class EventViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        String id = getIntent().getStringExtra("EVENT_ID");
        Realm realm = Realm.getDefaultInstance();
        Event e = realm.where(Event.class)
                .equalTo("id", id)
                .findFirst();
        // TODO: 7/20/16 setEvent fields
        // TODO: 7/20/16 change ids of layout file
    }
}
