package cs1193.admu.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.Image;
import io.realm.Realm;
import io.realm.RealmResults;

public class EventViewActivity extends AppCompatActivity {

    private String id;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        id = getIntent().getStringExtra(EventListFragment.EVENT_ID);
        realm = Realm.getDefaultInstance();
        //RealmResults<Event> e = realm.where(Event.class).findAll();

        GridView grid = (GridView) findViewById(R.id.gridView);
        grid.setAdapter(new ImageGridAdapter(this,realm.where(Image.class).equalTo("eventId",id).findAll()));

       updateViews();

    }

    public void updateViews(){

        Event e = realm.where(Event.class).equalTo("id",id).findFirst();

        EditText title = (EditText) findViewById(R.id.et_view_event_title);
        EditText date = (EditText) findViewById(R.id.et_view_event_date);
        EditText timeFrom = (EditText) findViewById(R.id.et_view_event_time);
        EditText timeTo = (EditText) findViewById(R.id.et_view_event_time_to);
        EditText location = (EditText) findViewById(R.id.et_view_event_location);
        EditText comments = (EditText) findViewById(R.id.et_view_event_comments);


        title.setText(e.getTitle());
        title.setEnabled(false);
        date.setText(e.getDate());
        date.setEnabled(false);
        timeFrom.setText(e.getStartTime());
        timeFrom.setEnabled(false);
        timeTo.setText(e.getEndTime());
        timeTo.setEnabled(false);
        location.setText(e.getLocation());
        location.setEnabled(false);
        comments.setText(e.getComment());
        comments.setEnabled(false);

    }

    public void editEvent(View v){

        Intent i = new Intent(this,cs1193.admu.finalproject.EventInputActivity.class);
        i.putExtra(EventListFragment.EVENT_ID,id);
        i.putExtra(MainActivity.INPUT_TYPE,EventInputActivity.EDIT);
        startActivityForResult(i,0);

    }

    public void delete(View v){

        realm.beginTransaction();
        Event.deleteFromRealm(realm.where(Event.class).equalTo("id",getIntent().getStringExtra(EventListFragment.EVENT_ID)).findFirst());
        RealmResults<Image> imgs = realm.where(Image.class).equalTo("eventid",getIntent().getStringExtra(EventListFragment.EVENT_ID)).findAll();
        for(Image img: imgs){

            Image.deleteFromRealm(img);

        }
        realm.commitTransaction();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateViews();
    }
}
