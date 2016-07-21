package cs1193.admu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import cs1193.admu.finalproject.model.Image;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Dion Velasco on 7/21/2016.
 */
public class ImagePopupDialog extends Dialog {

    private String imageId;
    private Realm realm;

    public ImagePopupDialog(Context context) {
        super(context);
    }

    public ImagePopupDialog setImageId(String id){

        imageId = id;
        return this;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_popup);

        realm = Realm.getDefaultInstance();

        ImageView img = (ImageView) findViewById(R.id.popup_image);
        final Image i = realm.where(Image.class).equalTo("id",imageId).findFirst();

        Picasso.with(getContext()).load(new File(i.getFilename())).fit().into(img);

        Button deleteButton = (Button) findViewById(R.id.dialog_btn_delete);

        deleteButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                realm.beginTransaction();
                Image.deleteFromRealm(i);
                realm.commitTransaction();
                dismiss();
            }
        });

        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                dismiss();

            }

        });
    }
}
