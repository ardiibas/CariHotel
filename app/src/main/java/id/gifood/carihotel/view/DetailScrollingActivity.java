package id.gifood.carihotel.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import id.gifood.carihotel.R;

public class DetailScrollingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;

    private Double lat, lng;
    private String nama_hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.detail_list_map_hotel);
        mapView.onCreate(savedInstanceState);

        nama_hotel = getIntent().getStringExtra("nama");
        String alamat_hotel = getIntent().getStringExtra("alamat");
        String url_hotel = getIntent().getStringExtra("image");
        lat = getIntent().getDoubleExtra("lat", 0.0);
        lng = getIntent().getDoubleExtra("lng", 0.0);
        ArrayList<String> fasilitas = getIntent().getStringArrayListExtra("fasilitas");
        ArrayList<String> sekitar = getIntent().getStringArrayListExtra("sekitar");

        mapView.getMapAsync(this);

        Button btnFasilitas = findViewById(R.id.btn_fasilitas);
        btnFasilitas.setOnClickListener((v) -> {
            dialogPopUp(fasilitas);
        });

        Button btnSekitar = findViewById(R.id.btn_sekitar);
        btnSekitar.setOnClickListener((v) -> {
            dialogPopUp(sekitar);
        });

        TextView textView = findViewById(R.id.detail_alamat_hotel);
        textView.setText(alamat_hotel);


        ImageView imageView = findViewById(R.id.detail_image);
        Glide.with(getApplicationContext())
                .load(url_hotel).into(imageView);
    }

    private void dialogPopUp(ArrayList<String> fasilitas) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_fasilitas);

        ListView listView = dialog.findViewById(R.id.list_detail);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_list_item_1, fasilitas
        );

        listView.setAdapter(stringArrayAdapter);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setMyLocationButtonEnabled(false);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14);
        map.animateCamera(cameraUpdate);

        map.addMarker(
                new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(nama_hotel)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_hotel))
                        .draggable(false)
        );
    }
}
