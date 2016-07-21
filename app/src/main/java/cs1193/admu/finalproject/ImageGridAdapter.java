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
public class ImageGridAdapter extends RealmBaseAdapter<Image>  implements ListAdapter {

    private OrderedRealmCollection<Image> images;

    public ImageGridAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Image> data) {
        super(context, data);
        images = data;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.image_grid_row,viewGroup,false);

        ImageView img = (ImageView) v.findViewById(R.id.imageView);

        Picasso.with(context).load(new File(images.get(position).getFilename())).fit().into(img);
        img.setTag(position);
        return v;
    }
}
