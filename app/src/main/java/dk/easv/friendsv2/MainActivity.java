package dk.easv.friendsv2;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import dk.easv.friendsv2.Model.BEFriend;
import dk.easv.friendsv2.Model.Friends;

public class MainActivity extends ListActivity implements IGPSCallback {

    public static String TAG = "Friend2";
    public final static int DETAIL_ACTIVITY_REQUEST_CODE = 400;
    public final static int DETAIL_ACTIVITY_MODIFIED_RESULT_CODE = 401;

    Friends m_friends;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Friends v2");
        m_friends = new Friends();

        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, m_friends.getNames());

        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView parent, View v, int position, long id) {
        Intent x = new Intent(this, DetailActivity.class);
        Log.d(TAG, "Detail activity will be started");
        BEFriend friend = m_friends.getAll().get(position);
        addData(x, friend);
        startActivityForResult(x, DETAIL_ACTIVITY_REQUEST_CODE);
        Log.d(TAG, "Detail activity is started");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DETAIL_ACTIVITY_REQUEST_CODE && resultCode == DETAIL_ACTIVITY_MODIFIED_RESULT_CODE) {
            BEFriend friendModified = (BEFriend) data.getSerializableExtra("friendModified");
            BEFriend friendOriginal = (BEFriend) data.getSerializableExtra("friendOriginal");
            int pos = findFriend(friendOriginal);
            if (pos >= 0) {
                m_friends.updateFriend(pos, friendModified);
                ListAdapter adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, m_friends.getNames());
                setListAdapter(adapter);
            }
        }
    }

    @Override
    public void setCurrentLocation(Location location) {

    }

    private void addData(Intent x, BEFriend f) {
        x.putExtra("friend", f);
    }

    private int findFriend(BEFriend friend) {
        int index = -1;
        for (BEFriend f : m_friends.getAll()) {
            if (f.getName().equals(friend.getName()) && f.getPhone().equals(friend.getPhone())) {
                index = m_friends.getAll().indexOf(f);
            }
        }
        return index;
    }
}
