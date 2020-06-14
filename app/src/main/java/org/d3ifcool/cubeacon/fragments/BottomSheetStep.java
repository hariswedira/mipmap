package org.d3ifcool.cubeacon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.d3ifcool.cubeacon.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BottomSheetStep extends BottomSheetDialogFragment {

    private ImageView arrived;

    public BottomSheetStep() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_sheet_step, container, false);

        arrived = v.findViewById(R.id.iv_arrived);
        Glide.with(getContext()).load("https://firebasestorage.googleapis.com/v0/b/mipmap-apps.appspot.com/o/arrived.png?alt=media&token=14a6fb52-751c-418e-8016-cbb39003c822")
                .placeholder(R.drawable.image_placeholder)
                .into(arrived);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}
