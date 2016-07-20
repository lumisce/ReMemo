package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class MemoListAdapter extends RealmBaseAdapter<Memo> implements ListAdapter {

    private OrderedRealmCollection<Memo> memos;

    public MemoListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Memo> data) {
        super(context, data);
        memos = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.fragment_event_row, parent, false);

        TextView title = (TextView) v.findViewById(R.id.memo_title);
        TextView date = (TextView) v.findViewById(R.id.memo_date);

        title.setText(memos.get(position).getTitle());
        date.setText(memos.get(position).getDate()+"");

        return v;
    }
}
