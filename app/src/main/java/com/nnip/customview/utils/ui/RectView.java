package com.nnip.customview.utils.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.nnip.customview.R;

public class RectView extends View {

    /*
    定义变量来映射属性值
     */
    private int backgroundcolor;
    private int color;
    private float radius_lefttop;
    private float radius_righttop;
    private float radius_leftbuttom;
    private float radius_rightbuttom;
    private String text;
    private int textColor;
    private float textsize;

    private float density;
    private float scaledDensity;

    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialattrs(attrs);
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialattrs(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialattrs(attrs);
    }

    /*
    加载解释属性集
     */
    private void initialattrs(AttributeSet attrs){
        //获取分辨率信息
        DisplayMetrics metrics=getResources().getDisplayMetrics();
        density =metrics.density;
        scaledDensity=metrics.scaledDensity;
        //获取属性值
        if(attrs!=null) {
            TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs,
                    R.styleable.RectView);
            backgroundcolor=mTypedArray.getColor(R.styleable.RectView_backgroundColor,0);
            color=mTypedArray.getColor(R.styleable.RectView_color,Color.BLACK);
            radius_lefttop=mTypedArray.getFloat(R.styleable.RectView_radius_lefttop,0)* density;
            radius_righttop=mTypedArray.getFloat(R.styleable.RectView_radius_righttop,0)* density;
            radius_leftbuttom=mTypedArray.getFloat(R.styleable.RectView_radius_leftbuttom,0)* density;
            radius_rightbuttom=mTypedArray.getFloat(R.styleable.RectView_radius_rightbuttom,0)* density;
            text=mTypedArray.getString(R.styleable.RectView_text);
            textColor=mTypedArray.getColor(R.styleable.RectView_textColor,Color.GRAY);
            textsize=mTypedArray.getFloat(R.styleable.RectView_textsize,14)*scaledDensity;
            mTypedArray.recycle();
        }
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();
        canvas.drawColor(backgroundcolor);
        Path path=new Path();
        int l=getPaddingLeft();
        int t=getPaddingTop();
        int w=getWidth()-getPaddingRight();
        int h=getHeight()-getPaddingBottom();
        int cx=l-getPaddingRight();
        int cy=t-getPaddingBottom();
        /*
        绘制圆角矩形path
         */
        path.moveTo(l,radius_lefttop);
        RectF lefttop=new RectF(l,t,radius_lefttop+l,radius_lefttop+t);
        RectF righttop=new RectF(w-radius_righttop,t,w,radius_righttop+t);
        RectF rightbuttom=new RectF(w-radius_rightbuttom,h-radius_rightbuttom,w,h);
        RectF leftbuttom=new RectF(l,h-radius_leftbuttom,radius_leftbuttom+l,h);
        path.arcTo(lefttop,180,90,false);
        path.lineTo(w-radius_righttop,t);
        path.arcTo(righttop,270,90,false);
        path.lineTo(w,h-radius_rightbuttom);
        path.arcTo(rightbuttom,0,90,false);
        path.lineTo(radius_leftbuttom,h);
        path.arcTo(leftbuttom,90,90,false);
        path.close();
        paint.setColor(color);
        canvas.drawPath(path,paint);
        /*
        绘制文字
         */
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textsize);
        canvas.drawText(text,w/2+cx,w/2+cy,paint);
    }

    public void setRadius_lefttop(float radius_lefttop) {
        this.radius_lefttop = radius_lefttop*density;
    }

    public void setRadius_leftbuttom(float radius_leftbuttom) {
        this.radius_leftbuttom = radius_leftbuttom*density;
    }

    public void setRadius_righttop(float radius_righttop) {
        this.radius_righttop = radius_righttop*density;
    }

    public void setRadius_rightbuttom(float radius_rightbuttom) {
        this.radius_rightbuttom = radius_rightbuttom*density;
    }
}
