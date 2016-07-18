package cs1193.admu.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import cs1193.admu.finalproject.model.User;
import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    private Toolbar tb;
    private EditText un, pw;
    private CheckBox cb;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        un = (EditText) findViewById(R.id.username);
        pw = (EditText) findViewById(R.id.password);
        cb = (CheckBox) findViewById(R.id.remember_me);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean rem = prefs.getBoolean("remember", false);

        if (rem) {
            Realm realm = Realm.getDefaultInstance();
            if (prefs.contains("id")) {
                String id = prefs.getString("id", "");
                User user = realm.where(User.class)
                        .equalTo("id", id)
                        .findFirst();
                if (user != null) {
                    un.setText(user.getEmail());
                    pw.setText(user.getPassword());
                    cb.setChecked(rem);
                }
            }
            realm.close();
        }
    }

    public void signIn(View v) {
        String login_email = un.getText().toString();
        String login_pw = pw.getText().toString();

        Realm realm = Realm.getDefaultInstance();
        User u = realm.where(User.class)
                .equalTo("email", login_email)
                .findFirst();
        if (u == null || u.getPassword().equals(login_pw)) {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean("remember", cb.isChecked());
        e.putString("id", u.getId());
        e.putBoolean("login", true);
        e.apply();

        realm.close();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void register(View v) {
        register();
    }

    public void register() {
        RegisterDialog rd = new RegisterDialog(this);
        rd.show();
    }
}
