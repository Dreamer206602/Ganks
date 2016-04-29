package com.mx.ganks.utils;

import android.graphics.Matrix;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
//数学计算工具类
public class MathUtils {

    //获取两点间距离
    public static float getDistance(float x1, float y1, float x2, float y2) {
        float x = x1 - x2;
        float y = y1 - y2;
        return (float) Math.sqrt(x * x + y * y);
    }

    //获取两点间中间点
    public static float[] getCenterPoint(float x1, float y1, float x2, float y2) {
        return new float[]{(x1 + x2) / 2f, (y1 + y2) / 2f};
    }

    //获取矩阵的缩放值
    public static float[] getMatrixScale(Matrix matrix) {
        if (matrix != null) {
            float[] value = new float[9];
            matrix.getValues(value);
            return new float[]{value[0], value[4]};
        } else {
            return new float[2];
        }
    }

    //计算点除以矩阵之后的值
    public static float[] inverseMatrixPoint(float[] point,Matrix matrix){
        if(point!=null&&matrix!=null){
            float[] dst=new float[2];
            Matrix inverse=new Matrix();
            matrix.invert(inverse);
            inverse.mapPoints(dst,point);
            return dst;
        }else{
            return new float[2];
        }

    }
}
