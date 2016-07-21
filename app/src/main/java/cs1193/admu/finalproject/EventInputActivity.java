package cs1193.admu.finalproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import cs1193.admu.finalproject.model.Event;
import io.realm.Realm;

public class EventInputActivity extends AppCompatActivity {

    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int type;
    private Event curEvent;
    private Realm realm;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_input);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        realm = Realm.getDefaultInstance();
        type = getIntent().getIntExtra(MainActivity.INPUT_TYPE,EventInputActivity.ADD);

        if(type == EventInputActivity.EDIT){


            //There should only be one event in the list so .first() should be fine
            curEvent = realm.where(Event.class).equalTo("id",getIntent().getStringExtra(EventListFragment.EVENT_ID)).findAll().first();

            EditText title = (EditText) findViewById(R.id.et_edit_event_title);
            EditText date = (EditText) findViewById(R.id.et_edit_event_date);
            EditText timeFrom = (EditText) findViewById(R.id.et_edit_event_time);
            EditText timeTo = (EditText) findViewById(R.id.et_edit_event_time_to);
            EditText location = (EditText) findViewById(R.id.et_edit_event_location);
            EditText comments = (EditText) findViewById(R.id.et_edit_event_comments);
            Button createEvent = (Button) findViewById(R.id.btn_event_create);



            title.setText(curEvent.getTitle());
            date.setText(curEvent.getDate());
            timeFrom.setText(curEvent.getStartTime());
            timeTo.setText(curEvent.getEndTime());
            location.setText(curEvent.getLocation());
            comments.setText(curEvent.getComment());
            createEvent.setText("Save");

        }
        else{

            Button img = (Button) findViewById(R.id.btn_event_image);
            img.setEnabled(false);
            Button tags = (Button) findViewById(R.id.btn_event_tags);
            tags.setEnabled(false);

        }
    }

    public void createEvent(View v){

        EditText title = (EditText) findViewById(R.id.et_edit_event_title);
        EditText date = (EditText) findViewById(R.id.et_edit_event_date);
        EditText timeFrom = (EditText) findViewById(R.id.et_edit_event_time);
        EditText timeTo = (EditText) findViewById(R.id.et_edit_event_time_to);
        EditText location = (EditText) findViewById(R.id.et_edit_event_location);
        EditText comments = (EditText) findViewById(R.id.et_edit_event_comments);

        Realm realm = Realm.getDefaultInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(type == EventInputActivity.ADD){

            realm.beginTransaction();
            Event e = realm.createObject(Event.class);

            e.setId(UUID.randomUUID().toString());
            e.setUserId(prefs.getString("id",""));
            e.setTitle(title.getText().toString());
            e.setDate(date.getText().toString());
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
            curEvent.setDate(date.getText().toString());
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
                    EditText e = (EditText) findViewById(R.id.et_edit_event_time);
                    e.setText(hour + ":" + min);
                }
                else{
                    EditText e = (EditText) findViewById(R.id.et_edit_event_time_to);
                    e.setText(hour + ":" + min);
                }
            }
        },c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
        d.show();
    }

    public void setDate(View v){

        Calendar c = Calendar.getInstance();
        Dialog d = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                String output = day+"/"+(month+1)+"/"+year;
                EditText e = (EditText) findViewById(R.id.et_edit_event_date);
                e.setText(output);

            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        d.show();
    }

    public void setTags(View v) {
        Dialog d = new TagInputDialog(this);
        d.show();
    }

    public void newImage(View v){

        Intent i = new Intent(this,cs1193.admu.finalproject.ImageInputActivty.class);
        i.putExtra(EventListFragment.EVENT_ID,getIntent().getStringExtra(EventListFragment.EVENT_ID));
        i.putExtra(ImageInputActivty.TYPE,ImageInputActivty.EVENT);
        startActivity(i);

    }



}
