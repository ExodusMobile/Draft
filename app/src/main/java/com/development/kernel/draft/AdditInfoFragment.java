package com.development.kernel.draft;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anton on 18.02.2017.
 */

public class AdditInfoFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addit_info, null);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
    public static Fragment newInstance() {
        return new AdditInfoFragment();
    }
}
