package cs1193.admu.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;

import cs1193.admu.finalproject.model.Event;
import cs1193.admu.finalproject.model.EventDate;
import cs1193.admu.finalproject.model.Memo;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;

public class EventListAdapter extends RealmBaseAdapter<Event> implements ExpandableListAdapter {

    private RealmList<EventDate> eventDates;

    public EventListAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Event> data) {
        super(context, data);
    }

    @Override
    public int getGroupCount() {
        return eventDates.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return eventDates.get(groupPosition).getEvents().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return eventDates.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return eventDates.get(groupPosition).getEvents().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
