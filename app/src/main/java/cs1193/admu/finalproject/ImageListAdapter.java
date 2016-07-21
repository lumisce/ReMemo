package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.io.File;

import cs1193.admu.finalproject.model.Image;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by Dion Velasco on 7/21/2016.
 */
public class ImageListAdapter extends RealmBaseAdapter<Image> implements ListAdapter {

    private OrderedRealmCollection<Image> images;

    public ImageListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Image> data) {
        super(context, data);
        images = data;
    }

    @Override
    public int getCount(){

        //Extra row trick
        return adapterData.size()+1;

    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;

        if(i < adapterData.size()){

            v = inflater.inflate(R.layout.image_list_row,viewGroup,false);

            ImageView img = (ImageView) v.findViewById(R.id.list_imageView);
            Image image = images.get(i);

            Picasso.with(context).load(new File(image.getFilename())).fit().into(img);
            img.setTag(image.getId());

        }
        else{

            v = inflater.inflate(R.layout.image_list_top_row,viewGroup,false);

        }

        return v;
    }
}
