package mysupercompany.bicicling;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.metalev.multitouch.controller.MultiTouchController;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.MapTile;
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.DirectedLocationOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private MapView map;
    private MyLocationNewOverlay myLocationOverlay;
    private MinimapOverlay mMinimapOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    private CompassOverlay mCompassOverlay;
    private IMapController mapController;
    private RadiusMarkerClusterer parkingMarkers;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.map);
        map.setPositionAndScale(getActivity(),
                new MultiTouchController.PositionAndScale(),
                new MultiTouchController.PointInfo());
        Log.d("MAP ", map.toString());


        initializeMap();
        setZoom();
        setOverlays();
        putMarkers();

        map.invalidate();
        //map.getTileProvider().clearTileCache();

        return view;
    }

    private void initializeMap() {
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    private void setZoom() {
        //  Setteamos el zoom al mismo nivel y ajustamos la posición a un geopunto
        mapController = map.getController();
        mapController.setZoom(14);
    }

    private void setOverlays() {
        final DisplayMetrics dm = getResources().getDisplayMetrics();

        myLocationOverlay = new MyLocationNewOverlay(
                getContext(),
                new GpsMyLocationProvider(getContext()),
                map
        );
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo( myLocationOverlay
                        .getMyLocation());
            }
        });

/*
        mMinimapOverlay = new MinimapOverlay(getContext(), map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
*/

        mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);

        mCompassOverlay = new CompassOverlay(
                getContext(),
                new InternalCompassOrientationProvider(getContext()),
                map
        );
        mCompassOverlay.enableCompass();

        map.getOverlays().add(myLocationOverlay);
        //map.getOverlays().add(this.mMinimapOverlay);
        map.getOverlays().add(this.mScaleBarOverlay);
        map.getOverlays().add(this.mCompassOverlay);
    }

    private void putMarkers() {
        setupMarkerOverlay();

        MyApp app = (MyApp) getActivity().getApplication();
        Firebase ref = app.getRef();

        final Firebase parkings = ref.child("stations");
        Log.d("XXXX", parkings.toString());


        parkings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Park parking = postSnapshot.getValue(Park.class);
                    Log.d("XXXX", parking.getStreetName());

                    Marker marker = new Marker(map);

                    GeoPoint point = new GeoPoint(
                            Double.parseDouble(parking.getLatitude()),
                            Double.parseDouble(parking.getLongitude())
                    );

                    marker.setPosition(point);

                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setIcon(getResources().getDrawable(R.drawable.parking));
                    marker.setTitle(parking.getStreetName());
                    marker.setAlpha(0.6f);

                    parkingMarkers.add(marker);
                }
                parkingMarkers.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void setupMarkerOverlay() {
        parkingMarkers = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(parkingMarkers);

        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_default);
        Bitmap clusterIcon = ((BitmapDrawable)clusterIconD).getBitmap();

        parkingMarkers.setIcon(clusterIcon);
        parkingMarkers.setRadius(100);
    }

}
