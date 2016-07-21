package cs1193.admu.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;
import java.util.UUID;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.Image;
import cs1193.admu.finalproject.model.Memo;
import io.realm.Realm;
import io.realm.RealmResults;

public class MemoInputActivity extends AppCompatActivity {

    public static final int ADD = 0;
    public static final int EDIT = 1;

    private int type;
    private Realm realm;
    private SharedPreferences prefs;
    private Memo curMemo;

    public MemoInputActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_input);

        realm = Realm.getDefaultInstance();
        type = getIntent().getIntExtra(MainActivity.INPUT_TYPE,ADD);

        if(type == EDIT){

            System.out.println(getIntent().getStringExtra(MemoListFragment.MEMO_ID));

            curMemo = realm.where(Memo.class).equalTo("id",getIntent().getStringExtra(MemoListFragment.MEMO_ID)).findFirst();

            EditText title = (EditText) findViewById(R.id.et_memo_title);
            EditText content = (EditText) findViewById(R.id.et_textarea);

            title.setText(curMemo.getTitle());
            content.setText(curMemo.getContent());

        }

    }

    public void newImage(View v){

        Intent i = new Intent(this,cs1193.admu.finalproject.ImageInputActivty.class);
        i.putExtra(MemoListFragment.MEMO_ID,getIntent().getStringExtra(MemoListFragment.MEMO_ID));
        i.putExtra(ImageInputActivty.TYPE,ImageInputActivty.MEMO);
        startActivity(i);

    }

    public void deleteMemo(View v){

        realm.beginTransaction();
        Memo.deleteFromRealm(realm.where(Memo.class).equalTo("id",getIntent().getStringExtra(MemoListFragment.MEMO_ID)).findFirst());
        RealmResults<Image> imgs = realm.where(Image.class).equalTo("memoId",getIntent().getStringExtra(MemoListFragment.MEMO_ID)).findAll();
        for(Image img: imgs){

            Image.deleteFromRealm(img);

        }
        realm.commitTransaction();
        finish();

    }

    public void newMemo(View v){

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EditText title = (EditText) findViewById(R.id.et_memo_title);
        EditText content = (EditText) findViewById(R.id.et_textarea);

        Calendar c = Calendar.getInstance();
        String date = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR);

        if(type == ADD){

            realm.beginTransaction();
            Memo m = realm.createObject(Memo.class);
            m.setId(UUID.randomUUID().toString());
            m.setUser(prefs.getString("id",""));
            m.setTitle(title.getText().toString());
            m.setContent(content.getText().toString());
            m.setDate(date);
            realm.commitTransaction();
            finish();

        }
        else{

            realm.beginTransaction();
            curMemo.setTitle(title.getText().toString());
            curMemo.setContent(content.getText().toString());
            curMemo.setDate(date);
            realm.commitTransaction();
            finish();

        }
    }
}
