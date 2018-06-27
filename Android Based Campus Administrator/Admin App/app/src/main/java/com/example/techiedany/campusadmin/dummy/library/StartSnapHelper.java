package com.example.techiedany.campusadmin.dummy.library;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class StartSnapHelper extends LinearSnapHelper {

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        int[] out = new int[2];
        out[0] = 0;
        out[1] = ((com.example.techiedany.campusadmin.dummy.library.VegaLayoutManager) layoutManager).getSnapHeight();
        return out;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        com.example.techiedany.campusadmin.dummy.library.VegaLayoutManager custLayoutManager = (com.example.techiedany.campusadmin.dummy.library.VegaLayoutManager) layoutManager;
        return custLayoutManager.findSnapView();
    }
}