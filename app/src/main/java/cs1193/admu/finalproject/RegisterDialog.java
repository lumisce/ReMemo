package cs1193.admu.finalproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import cs1193.admu.finalproject.model.User;
import io.realm.Realm;

public class RegisterDialog extends Dialog {

    private Intent i;
    private EditText etName, etEmail, etPassword, etBday;
    private Button btnBday, btnOk, btnCancel;
    private DatePickerDialog dp;
    private SharedPreferences prefs;
    private int mYear, mMonth, mDay;


    public RegisterDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        findViews();

        btnBday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlert();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    private void findViews() {
        etName = (EditText) findViewById(R.id.et_reg_name);
        etEmail = (EditText) findViewById(R.id.et_reg_email);
        etPassword = (EditText) findViewById(R.id.et_reg_password);
        etBday = (EditText) findViewById(R.id.et_reg_bday);
        btnBday = (Button) findViewById(R.id.btn_bday);
        btnOk = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    private void createDatePickerDialog() {
        dp = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        }, mYear, mMonth, mDay);
        dp.show();
    }

    private void updateDisplay() {
        String date = (mMonth+1) + "/" + mDay + "/" + mYear;
        etBday.setText(date);
    }

    private void createAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Close?").setMessage("Are you sure you don't want to register?").setCancelable(false)
                .setPositiveButton("Yes", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .setNegativeButton("No", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveUser() {

        final String name = etName.getText().toString();
        final String email = etEmail.getText().toString();
        final String pw = etPassword.getText().toString();
        final String bday = etBday.getText().toString();

        if (name.isEmpty() || email.isEmpty() || pw.isEmpty() || bday.isEmpty()) {
            Toast.makeText(getContext(), "All Fields Required", Toast.LENGTH_SHORT).show();
            return;
        }
        Realm realm = Realm.getDefaultInstance();

        User res = realm.where(User.class)
                .equalTo("email", email)
                .findFirst();
        if (res != null) {
            Toast.makeText(getContext(), "Email already used", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar c = Calendar.getInstance();
        Date date = new Date(c.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(bday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        realm.beginTransaction();
        User u = realm.createObject(User.class, UUID.randomUUID().toString());
        u.setName(name);
        u.setEmail(email);
        u.setPassword(pw);
        realm.commitTransaction();

        realm.close();

        dismiss();
    }


}
