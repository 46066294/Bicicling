package mysupercompany.bicicling;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ParkingListFragment extends Fragment {

    private FirebaseListAdapter<Park> adapter;

    public ParkingListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView lvParkings = (ListView) view.findViewById(R.id.lvParkings);

        MyApp app = (MyApp) getActivity().getApplication();
        Firebase ref = app.getRef();

        final Firebase parkings = ref.child("stations");

        Log.d("STATIONS", parkings.toString());

        adapter = new FirebaseListAdapter<Park>(getActivity(), Park.class, R.layout.row, parkings) {
            @Override
            protected void populateView(View view, Park parking, int position) {

                TextView tvName = (TextView) view.findViewById(R.id.tvName);
                tvName.setText(parking.getStreetName());
                //tvName.setText(parking.toString());
            }
        };

        lvParkings.setAdapter(adapter);
        return view;
    }

}

