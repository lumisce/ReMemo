package cs1193.admu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.UUID;

import cs1193.admu.finalproject.model.Tag;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;

/**
 * Created by candy on 7/18/16.
 */
public class  TagInputDialog extends Dialog {

    private OrderedRealmCollection<Tag> tags;
    SharedPreferences prefs;
    EditText etAdd;

    public TagInputDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tag_input);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        ListView lv = (ListView) findViewById(R.id.lv_tag_input);
        Realm realm = Realm.getDefaultInstance();
        tags = realm.where(Tag.class)
                .equalTo("userId", prefs.getString("id", ""))
                .findAll();
        //get event
        lv.setAdapter(new TagInputListAdapter(getContext(), tags));
        Button btnOk = (Button) findViewById(R.id.btn_tags_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 7/21/16 do sth?
                dismiss();
            }
        });
        Button btnCancel = (Button) findViewById(R.id.btn_tags_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        etAdd = (EditText) findViewById(R.id.et_tag_input);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btn_add_tag);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Tag t = realm.createObject(Tag.class, UUID.randomUUID().toString());
                t.setUserId(prefs.getString("id", ""));
                t.setName(etAdd.getText().toString());
                realm.commitTransaction();
                etAdd.setText("");
            }
        });

    }
}
