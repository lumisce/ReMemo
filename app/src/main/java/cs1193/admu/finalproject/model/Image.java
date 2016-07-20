package cs1193.admu.finalproject.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by candy on 7/16/16.
 */
public class Image extends RealmObject {
    @PrimaryKey
    private String id;
    private String filename;
    private String eventId;
    private String memoId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMemoId() {
        return memoId;
    }

    public void setMemoId(String memoId) {
        this.memoId = memoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
