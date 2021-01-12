package com.hfad.onlinemarket.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.onlinemarket.R;
import com.hfad.onlinemarket.databinding.FragmentAddAddressMapsBinding;
import com.hfad.onlinemarket.viewmodel.AddAddressMapViewModel;

import static com.hfad.onlinemarket.utils.SnakeBar.showAddSnakeBar;

public class AddAddressMapsFragment extends Fragment {
    public static final String TAG = "Map Fragment";
    public static final int REQUEST_CODE_PERMISSION = 1;
    private FragmentAddAddressMapsBinding mBinding;
    private AddAddressMapViewModel mMapViewModel;
    private FusedLocationProviderClient mClient;
    private SupportMapFragment mMapFragment;
    private NavController mNavController;


    private void getCurrentLocation() {
        //check permission
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = mClient.getLastLocation();
            Log.d(TAG, "getCurrentLocation: ");
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d(TAG, "onSuccess: " + location);
                    if (location != null) {
                        mMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                Log.d(TAG, "onMapReady: " + location.getLatitude());
                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions().position(currentLocation).title("شما اینجا هستید");
                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));

                                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                    @Override
                                    public void onMapLongClick(LatLng latLng) {
                                        markerOptions.position(latLng);
                                        mMapViewModel.setLatitude(latLng.latitude);
                                        mMapViewModel.setLongitude(latLng.longitude);
                                        mMapViewModel.setFullAddress();
                                        mBinding.setViewModel(mMapViewModel);
                                    }
                                });

                                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                    @Override
                                    public void onMapClick(LatLng latLng) {
                                        mMapViewModel.setLongitude(null);
                                        mMapViewModel.setLatitude(null);
                                        mBinding.setViewModel(mMapViewModel);
                                    }
                                });
                            }
                        });
                    }
                }
            });
        } else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_PERMISSION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_address_maps, container, false);
        //Initialize fused location
        mClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mMapViewModel = new ViewModelProvider(this).get(AddAddressMapViewModel.class);
        mBinding.setViewModel(mMapViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mMapFragment != null) {
            getCurrentLocation();
        }
        mNavController = Navigation.findNavController(view);
        setListeners();
    }

    private void setListeners() {
        mBinding.registerAddressButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (mBinding.fullAddress.getText() == null || mBinding.fullAddress.getText().length() < 5
                        || mBinding.addressName.getText() == null || mBinding.addressName.length() < 2) {
                    Snackbar snackbar = Snackbar.make(mBinding.getRoot(), R.string.please_get_full_info, BaseTransientBottomBar.LENGTH_LONG);
                    showAddSnakeBar(snackbar, getActivity());
                } else {
                    if (mMapViewModel.setAddress(mBinding.addressName.getText().toString(), mBinding.fullAddress.getText().toString())) {
                        mNavController.navigateUp();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: before check");
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d(TAG, "onRequestPermissionsResult: ");
            getCurrentLocation();
        }
    }
}