package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import cs1193.admu.finalproject.model.Tag;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by candy on 7/21/16.
 */
public class TagInputListAdapter extends RealmBaseAdapter<Tag> implements ListAdapter {

    private OrderedRealmCollection<Tag> tags;

    public TagInputListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Tag> data) {
        super(context, data);
        tags = data;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.dialog_tag_input_row, parent, false);
        CheckBox cb = (CheckBox) v.findViewById(R.id.cb_tag);
        cb.setText(tags.get(position).getName());
        // TODO: 7/21/16 if this tag contains event, check it
        return v;
    }
}
