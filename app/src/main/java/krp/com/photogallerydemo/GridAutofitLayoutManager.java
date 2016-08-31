package krp.com.photogallerydemo;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class GridAutofitLayoutManager extends GridLayoutManager {
    private boolean mColumnWidthChanged = true;

    public GridAutofitLayoutManager(Context context) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1);
        mColumnWidthChanged = true;
    }

    public GridAutofitLayoutManager(Context context, int orientation, boolean reverseLayout) {
        /* Initially set spanCount to 1, will be changed automatically later. */
        super(context, 1, orientation, reverseLayout);
        mColumnWidthChanged = true;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(mColumnWidthChanged) {
            int[] measuredDimension = new int[2];
            View view = recycler.getViewForPosition(0);
            if (view != null) {
                RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
                int childWidthSpec = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        getPaddingLeft() + getPaddingRight(), p.width);
                int childHeightSpec = ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        getPaddingTop() + getPaddingBottom(), p.height);
                view.measure(childWidthSpec, childHeightSpec);
                measuredDimension[0] = view.getMeasuredWidth();
                measuredDimension[1] = view.getMeasuredHeight();
                recycler.recycleView(view);
            }


            int width = getWidth();
            int height = getHeight();
            if (measuredDimension[0] > 0 && measuredDimension[1] > 0 && width > 0 && height > 0) {
                int totalSpace;
                if (getOrientation() == VERTICAL) {
                    totalSpace = width - getPaddingRight() - getPaddingLeft();
                } else {
                    totalSpace = height - getPaddingTop() - getPaddingBottom();
                }
                int spanCount = Math.max(1, totalSpace / measuredDimension[0]);
                setSpanCount(spanCount);
                mColumnWidthChanged = false;
            }
        }
        super.onLayoutChildren(recycler, state);
    }
}
