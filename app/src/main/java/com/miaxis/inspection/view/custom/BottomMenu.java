package com.miaxis.inspection.view.custom;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.miaxis.inspection.R;

/**
 * Created by xu.nan on 2016/9/21.
 */
public class BottomMenu implements View.OnTouchListener{

    private Activity context;
    private View.OnClickListener listener;

    private View menuView;

    private Button btnMenu1;

    private Button btnMenu2;

    private Button btnMenu3;

    public PopupWindow popupWindow;

    private LinearLayout pop_layout;

    public BottomMenu(Activity context, View.OnClickListener listener) {

        this.context = context;
        this.listener = listener;

        LayoutInflater inflater = LayoutInflater.from(context);
        menuView = inflater.inflate(R.layout.menu_bottom, null);

        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setOutsideTouchable(true);

        menuView.setOnTouchListener(this);

    }

    public void show(){
        //得到当前activity的rootView
        View rootView=((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event) {

        int height = pop_layout.getTop();
        int y=(int) event.getY();
        if(event.getAction()== MotionEvent.ACTION_UP){
            if(y<height){
                popupWindow.dismiss();
            }
        }
        return true;
    }

    private void onBtnClicked(View view) {
        listener.onClick(view);
    }

}
