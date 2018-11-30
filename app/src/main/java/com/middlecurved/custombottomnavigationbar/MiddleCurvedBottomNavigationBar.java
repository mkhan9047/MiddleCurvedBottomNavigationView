package com.middlecurved.custombottomnavigationbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MiddleCurvedBottomNavigationBar extends BottomNavigationView {

    private Path mPath;
    private Paint mPaint;

    Canvas temp;

    private SetMenuItemSelectedListener mSelectedListener;

    private int iconSize = 25;
    private int color = R.color.color_white;
    /**
     * the CURVE_CIRCLE_RADIUS represent the radius of the fab button
     */
    private int CURVE_CIRCLE_RADIUS;
    // the coordinates of the first curve
    private Point mFirstCurveStartPoint = new Point();
    private Point mFirstCurveEndPoint = new Point();
    private Point mFirstCurveControlPoint1 = new Point();
    private Point mFirstCurveControlPoint2 = new Point();

    //the coordinates of the second curve
    @SuppressWarnings("FieldCanBeLocal")
    private Point mSecondCurveStartPoint = new Point();
    private Point mSecondCurveEndPoint = new Point();
    private Point mSecondCurveControlPoint1 = new Point();
    private Point mSecondCurveControlPoint2 = new Point();

    private int mNavigationBarWidth;
    private int mNavigationBarHeight;
    Context context;

    public MiddleCurvedBottomNavigationBar(Context context) {
        super(context);
        setCubicCircleRadius(context);
        inflateMenu();
        removeShiftMode(this);
        init();
        this.context = context;
    }

    public MiddleCurvedBottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCubicCircleRadius(context);
        inflateMenu();
        removeShiftMode(this);
        init();
        this.context = context;
    }

    private void inflateMenu() {
        this.inflateMenu(R.menu.navigation_bar_menu_items);
    }

    public MiddleCurvedBottomNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCubicCircleRadius(context);
        inflateMenu();
        removeShiftMode(this);

        init();
        this.context = context;
    }


    public void setIconSize(int iconSize) {
        this.iconSize = iconSize;

        removeShiftMode(this);
    }

    public void setMenuIcons(int first, int second, int third, int fourth) {
        getMenu().getItem(0).setIcon(first);
        getMenu().getItem(1).setIcon(second);
        getMenu().getItem(2).setIcon(second);
        getMenu().getItem(3).setIcon(third);
        getMenu().getItem(4).setIcon(fourth);

    }

    public void setBarColor(int color) {
        this.color = color;
        init();
        if(temp != null){
            temp.drawPath(mPath, mPaint);
        }

        super.onDraw(temp);

    }

    public void setNewMenuItemSelectedListener(
            @Nullable SetMenuItemSelectedListener listener) {
        mSelectedListener = listener;
    }



    public interface SetMenuItemSelectedListener {
        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param item The selected item
         *
         * @return true to display the item as the selected item and false if the item should not
         *         be selected. Consider setting non-selectable items as disabled preemptively to
         *         make them appear non-interactive.
         */
        boolean onMenuItemSelected(@NonNull MenuItem item);

    }

    private void removeShiftMode(BottomNavigationView view) {

        /*get first bottom navigation menu view from bottom navigation  */
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            /*get field of bottom view from menu view */
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            /*set accessible true to Field*/
            shiftingMode.setAccessible(true);
            /*set boolean to field with menu b*/
            shiftingMode.setBoolean(menuView, false);
            /*then set accessible false to field */
            shiftingMode.setAccessible(false);
            /*for every menu view in the bottom navigation set shifting mode false and set checked the item data is checked*/
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                if (i == 2) {
                    item.setVisibility(View.INVISIBLE);
                }
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());

                /*center the menu items*/
                TextView smallText = (TextView) item.findViewById(R.id.smallLabel);
                smallText.setVisibility(View.INVISIBLE);
                //TextView largeText = (TextView) menuItemView.findViewById(R.id.largeLabel);
                ImageView icon = (ImageView) item.findViewById(R.id.icon);
                LayoutParams params = (LayoutParams) icon.getLayoutParams();
                params.gravity = Gravity.CENTER;
                item.setShiftingMode(true);

//                /*add badge to item views*/





                /*make icon little big*/

                final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
                final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                // set your height here
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iconSize, displayMetrics);
                // set your width here
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, iconSize, displayMetrics);
                iconView.setLayoutParams(layoutParams);


                if(i == 3 || i == 4){

                  /*  View badge = LayoutInflater.from(getContext())
                            .inflate(R.layout.badge_layout, menuView, false);*/
                  View badge = makeBadgeView();
                    FrameLayout.LayoutParams f = new FrameLayout.LayoutParams(badge.getLayoutParams());
                    f.topMargin = 7;
                    badge.setLayoutParams(f);
                    item.addView(badge);
                }
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

    private View makeBadgeView(){
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        TextView textView = new TextView(getContext());
       // frameLayout.addView(textView);
        textView.setBackground(getResources().getDrawable(R.drawable.notification_badge));


        textView.setLayoutParams(new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));


        textView.setGravity(Gravity.CENTER);
        textView.setPadding(3,3,3,3);
        textView.setText("+9");
        textView.setTextColor(getResources().getColor(R.color.white));
        frameLayout.addView(textView);

        return frameLayout;
    }


    private void setCubicCircleRadius(Context context) {

        CURVE_CIRCLE_RADIUS = (60 * context.getResources().getDisplayMetrics().densityDpi) / 320;

    }


    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(getResources().getColor(color));
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();
        // the coordinates (x,y) of the start point before curve
        mFirstCurveStartPoint.set((mNavigationBarWidth / 2) - (CURVE_CIRCLE_RADIUS * 2) - (CURVE_CIRCLE_RADIUS / 3), 0);
        // the coordinates (x,y) of the end point after curve
        mFirstCurveEndPoint.set(mNavigationBarWidth / 2, CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4));
        // same thing for the second curve
        mSecondCurveStartPoint = mFirstCurveEndPoint;
        mSecondCurveEndPoint.set((mNavigationBarWidth / 2) + (CURVE_CIRCLE_RADIUS * 2) + (CURVE_CIRCLE_RADIUS / 3), 0);

        // the coordinates (x,y)  of the 1st control point on a cubic curve
        mFirstCurveControlPoint1.set(mFirstCurveStartPoint.x + CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4), mFirstCurveStartPoint.y);
        // the coordinates (x,y)  of the 2nd control point on a cubic curve
        mFirstCurveControlPoint2.set(mFirstCurveEndPoint.x - (CURVE_CIRCLE_RADIUS * 2) + CURVE_CIRCLE_RADIUS, mFirstCurveEndPoint.y);

        mSecondCurveControlPoint1.set(mSecondCurveStartPoint.x + (CURVE_CIRCLE_RADIUS * 2) - CURVE_CIRCLE_RADIUS, mSecondCurveStartPoint.y);
        mSecondCurveControlPoint2.set(mSecondCurveEndPoint.x - (CURVE_CIRCLE_RADIUS + (CURVE_CIRCLE_RADIUS / 4)), mSecondCurveEndPoint.y);

        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(mFirstCurveStartPoint.x, mFirstCurveStartPoint.y);

        mPath.cubicTo(mFirstCurveControlPoint1.x, mFirstCurveControlPoint1.y,
                mFirstCurveControlPoint2.x, mFirstCurveControlPoint2.y,
                mFirstCurveEndPoint.x, mFirstCurveEndPoint.y);

        mPath.cubicTo(mSecondCurveControlPoint1.x, mSecondCurveControlPoint1.y,
                mSecondCurveControlPoint2.x, mSecondCurveControlPoint2.y,
                mSecondCurveEndPoint.x, mSecondCurveEndPoint.y);

        mPath.lineTo(mNavigationBarWidth, 0);
        mPath.lineTo(mNavigationBarWidth, mNavigationBarHeight);
        mPath.lineTo(0, mNavigationBarHeight);
        mPath.close();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        temp = canvas;
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }


}
