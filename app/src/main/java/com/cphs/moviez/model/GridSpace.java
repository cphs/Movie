package com.cphs.moviez.model;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by cesario.siringoringo on 15/06/2017.
 */

public class GridSpace extends RecyclerView.ItemDecoration {

  private int mSpace;

  public GridSpace(int space) {
    mSpace = space;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.left = mSpace;
    outRect.right = mSpace;
    outRect.bottom = mSpace;

    if (parent.getChildLayoutPosition(view) == 0) {
      outRect.top = mSpace;
    } else {
      outRect.top = 0;
    }
  }
}
