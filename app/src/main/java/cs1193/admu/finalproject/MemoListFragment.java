package cs1193.admu.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;

public class MemoListFragment extends ListFragment {

    private OnMemoFragmentInteractionListener mListener;
    private OrderedRealmCollection<Memo> memos = new RealmList<>();
    private MemoListAdapter adapter;
    private String userId;

    public MemoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        memos = realm.where(Memo.class).equalTo("userId",userId)
                .findAll();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // TODO: 7/16/16 start new Activity? ask acti to call new act
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        if (view instanceof ListView) {
            Context context = view.getContext();
            ListView listView = (ListView) view;
            listView.setAdapter(new MemoListAdapter(context, memos));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMemoFragmentInteractionListener) {
            mListener = (OnMemoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEventFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnMemoFragmentInteractionListener {

        void onMemoFragmentInteraction(Memo memo);
    }

    public MemoListFragment setUserId(String id){
        userId = id;
        return this;

    }
}