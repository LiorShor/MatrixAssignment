package com.example.matrixassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.example.matrixassignment.R;
import com.example.matrixassignment.view.adapters.IStateSelectionObserver;
import com.example.matrixassignment.data.remote.StateListRepository;
import com.example.matrixassignment.data.remote.VolleySingleton;
import com.example.matrixassignment.view.fragments.StateDetailsFragment;
import com.example.matrixassignment.view.fragments.StateListFragment;

public class MainActivity extends AppCompatActivity implements IStateSelectionObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue mRequestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        StateListRepository.getInstance().init(mRequestQueue);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StateListFragment()).commit();
        }
    }

    @Override
    public void onStateSelected(String alpha3Code) {
        Bundle bundle = new Bundle();
        Fragment fragment = new StateDetailsFragment();
        bundle.putSerializable("Alpha3Code", alpha3Code);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}