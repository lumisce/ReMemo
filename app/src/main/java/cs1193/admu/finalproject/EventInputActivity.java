package cs1193.admu.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.Date;
import java.util.UUID;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.Image;
import io.realm.Realm;

public class EventInputActivity extends AppCompatActivity {

    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_input);

        type = getIntent().getIntExtra(MainActivity.INPUTTYPE,EventInputActivity.ADD);

        if(type == EventInputActivity.EDIT){

            //To-Do autofill data

        }
    }

    public void createEvent(View v){

        EditText title = (EditText) findViewById(R.id.et_event_title);
        EditText date = (EditText) findViewById(R.id.et_event_date);
        EditText timeFrom = (EditText) findViewById(R.id.et_event_time);
        EditText timeTo = (EditText) findViewById(R.id.et_event_time_to);
        EditText location = (EditText) findViewById(R.id.et_event_location);
        EditText comments = (EditText) findViewById(R.id.et_event_comments);

        Realm realm = Realm.getDefaultInstance();

        if(type == EventInputActivity.ADD){

            realm.beginTransaction();
            Event e = realm.createObject(Event.class);

            e.setId(UUID.randomUUID().toString());
            e.setUserId(getIntent().getStringExtra(MainActivity.USERID));
            e.setTitle(title.getText().toString());
            e.setDate(new java.util.Date());
            e.setStartTime(timeFrom.getText().toString());
            e.setEndTime(timeTo.getText().toString());
            e.setLocation(location.getText().toString());
            e.setComment(comments.getText().toString());

            realm.commitTransaction();
            finish();
        }
        else{



        }

    }


}
