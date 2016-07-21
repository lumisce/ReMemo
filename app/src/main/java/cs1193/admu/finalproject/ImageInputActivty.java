package cs1193.admu.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import cs1193.admu.finalproject.model.Image;
import io.realm.Realm;

public class ImageInputActivty extends AppCompatActivity {

    public static final String TYPE = "typeKey";
    public static final int EVENT = 0;
    public static final int MEMO = 1;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_input_activty);

        realm = Realm.getDefaultInstance();
        ListView lv = (ListView) findViewById(R.id.img_list);
        if(getIntent().getIntExtra(TYPE,0)== EVENT) {
            lv.setAdapter(new ImageListAdapter(this, realm.where(Image.class)
                    .equalTo("eventId",getIntent().getStringExtra(EventListFragment.EVENT_ID))
                    .findAll()));
        }
        else{
            lv.setAdapter(new ImageListAdapter(this, realm.where(Image.class)
                    .equalTo("memoId",getIntent().getStringExtra(MemoListFragment.MEMO_ID))
                    .findAll()));
        }
    }

    public static File outputFile;
    public File thumbNailFile;

    public void takePicture(View view)
    {

        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/CameraTest/");
        directory.mkdirs();

        // unique filename based on the time
        String name = String.valueOf(System.currentTimeMillis());
        outputFile = new File(directory, name+".jpg");
        thumbNailFile = new File(directory, name+"_tn.jpg");

        Uri outputFileUri = Uri.fromFile(outputFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
        {
            processPicture();
        }
    }


    class PictureProcessThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                // this is potentially time consuming and should be done in a thread

                // rescale the picture from the full size output file which is assumed to now be
                // in outputFile

                // create rescale 640
                // create thumbnail rescale 50

                ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 100, thumbNailFile.getAbsolutePath());
                ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 200, outputFile.getAbsolutePath());



                // UI updates must be done via the UI thread NOT here, this will
                // cause the Runnable to occur in the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        updateImageView();
                    }
                });

            }
            catch(final Exception e)
            {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


    public void processPicture()
    {
        new Thread(new PictureProcessThread()).start();

    }

    public void updateImageView(){
        if(getIntent().getIntExtra(TYPE,0) == EVENT){

            realm.beginTransaction();
            Image img = realm.createObject(Image.class);
            img.setId(UUID.randomUUID().toString());
            img.setFilename(outputFile.getAbsolutePath());
            img.setEventId(getIntent().getStringExtra(EventListFragment.EVENT_ID));
            realm.commitTransaction();

        }
        else{

            realm.beginTransaction();
            Image img = realm.createObject(Image.class);
            img.setId(UUID.randomUUID().toString());
            img.setFilename(outputFile.getAbsolutePath());
            img.setMemoId(getIntent().getStringExtra(MemoListFragment.MEMO_ID));
            realm.commitTransaction();

        }
    }

    public void close(View v){

        finish();

    }

    public void selectImage(View v){

        Dialog d = new ImagePopupDialog(this)
                .setImageId((String)v.getTag());
        d.show();

    }
}
