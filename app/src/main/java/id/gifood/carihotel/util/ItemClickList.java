package id.gifood.carihotel.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class ItemClickList implements RecyclerView.OnItemTouchListener {

    private GestureDetector gestureDetector;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public ItemClickList(Context context, OnItemClickListener listener){
        onItemClickListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View view = rv.findChildViewUnder(e.getX(), e.getY());

        if (view != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)){
            onItemClickListener.onItemClick(view, rv.getChildAdapterPosition(view));
        }

        return false;

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
