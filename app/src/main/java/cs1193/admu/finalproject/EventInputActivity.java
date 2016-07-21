package cs1193.admu.finalproject;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.UUID;

import cs1193.admu.finalproject.model.Event;
import io.realm.Realm;

public class EventInputActivity extends AppCompatActivity {

    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int type;
    private Event curEvent;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_input);

        realm = Realm.getDefaultInstance();
        type = getIntent().getIntExtra(MainActivity.INPUT_TYPE,EventInputActivity.ADD);

        if(type == EventInputActivity.EDIT){


            //There should only be one event in the list so .first() should be fine
            curEvent = realm.where(Event.class).equalTo("id",getIntent().getStringExtra(EventListFragment.EVENT_ID)).findAll().first();

            EditText title = (EditText) findViewById(R.id.et_view_event_title);
            EditText date = (EditText) findViewById(R.id.et_view_event_date);
            EditText timeFrom = (EditText) findViewById(R.id.et_view_event_time);
            EditText timeTo = (EditText) findViewById(R.id.et_view_event_time_to);
            EditText location = (EditText) findViewById(R.id.et_view_event_location);
            EditText comments = (EditText) findViewById(R.id.et_view_event_comments);
            Button createEvent = (Button) findViewById(R.id.btn_event_create);


            title.setText(curEvent.getTitle());
            date.setText(curEvent.getDate().toString());
            timeFrom.setText(curEvent.getStartTime());
            timeTo.setText(curEvent.getEndTime());
            location.setText(curEvent.getLocation());
            comments.setText(curEvent.getComment());
            createEvent.setText("Edit Event");

        }
    }

    public void createEvent(View v){

        EditText title = (EditText) findViewById(R.id.et_view_event_title);
        EditText date = (EditText) findViewById(R.id.et_view_event_date);
        EditText timeFrom = (EditText) findViewById(R.id.et_view_event_time);
        EditText timeTo = (EditText) findViewById(R.id.et_view_event_time_to);
        EditText location = (EditText) findViewById(R.id.et_view_event_location);
        EditText comments = (EditText) findViewById(R.id.et_view_event_comments);

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


            realm.beginTransaction();
            curEvent.setTitle(title.getText().toString());
            curEvent.setTitle(title.getText().toString());
            curEvent.setDate(new java.util.Date());
            curEvent.setStartTime(timeFrom.getText().toString());
            curEvent.setEndTime(timeTo.getText().toString());
            curEvent.setLocation(location.getText().toString());
            curEvent.setComment(comments.getText().toString());
            realm.commitTransaction();
            finish();
        }

    }

    public void setTime(View v){

        final View v1 = v;

        Calendar c = Calendar.getInstance();
        Dialog d = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                if(v1.getId() == R.id.btn_event_time_from) {
                    EditText e = (EditText) findViewById(R.id.et_view_event_time);
                    e.setText(hour + ":" + min);
                }
                else{
                    EditText e = (EditText) findViewById(R.id.et_view_event_time_to);
                    e.setText(hour + ":" + min);
                }
            }
        },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
        d.show();
    }

    public void setTags(View v) {
        // TODO: 7/21/16 new TagInputDialog
    }

}
