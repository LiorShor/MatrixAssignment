package com.example.matrixassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new StateListFragment()).commit();
        }
    }

    public void loadStateDetails(State state) {

        Bundle bundle = new Bundle();
        Fragment fragment = new StateDetailsFragment();
        bundle.putSerializable("State", state);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}