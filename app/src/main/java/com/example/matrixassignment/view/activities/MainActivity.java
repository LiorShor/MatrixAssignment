package com.example.matrixassignment.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.matrixassignment.R;
import com.example.matrixassignment.view.fragments.StateDetailsFragment;
import com.example.matrixassignment.view.fragments.StateListFragment;
import com.example.matrixassignment.data.models.State;

import java.io.Serializable;
import java.util.List;

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

    public void loadStateDetails(State state, List<State> statesList) {
        Bundle bundle = new Bundle();
        Fragment fragment = new StateDetailsFragment();
        bundle.putSerializable("State", state);
        bundle.putSerializable("StatesList", (Serializable) statesList);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
    }
}