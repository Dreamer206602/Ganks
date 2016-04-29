package com.mx.ganks.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mx.ganks.R;
import com.mx.ganks.constant.Constants;
import com.mx.ganks.model.CommonDate;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sloop.adapter.utils.CommonAdapter;
import com.sloop.adapter.utils.ViewHolder;

import java.util.List;

/**
 * Created by boobooL on 2016/4/29 0029
 * Created 邮箱 ：boobooMX@163.com
 */
public class GankCommonAdapter extends CommonAdapter<CommonDate.ResultsEntity> {

    private Animation push_left_in, push_right_in;
    private DisplayImageOptions mOptions;

    public GankCommonAdapter(@NonNull Context context, List<CommonDate.ResultsEntity> datas) {
        super(context, datas, R.layout.item_common);
        //创建默认的ImageLoafer配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);

        push_left_in = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        push_right_in = AnimationUtils.loadAnimation(context, R.anim.push_right_in);
        push_right_in.setDuration(1000);
        push_left_in.setDuration(1000);

        //显示图片的配置
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @Override
    public void convert(int i, ViewHolder holder, CommonDate.ResultsEntity bean) {

        View convert = holder.getConvertView();
        ImageView img = holder.getView(R.id.common_img);
        TextView desc = holder.getView(R.id.common_desc);
        TextView via = holder.getView(R.id.common_via);
        TextView tag = holder.getView(R.id.common_tag);

        via.setText("via" + bean.getWho());
        tag.setText(bean.getType());

        if (bean.getType().equals(Constants.FLAG_MeiZHI)) {
            img.setVisibility(View.VISIBLE);
            desc.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(bean.getUrl(), img, mOptions);
        } else {
            desc.setVisibility(View.VISIBLE);
            desc.setText(bean.getDesc());
            img.setVisibility(View.GONE);
        }

        if (i % 2 == 0) {
            convert.setAnimation(push_right_in);
        } else {
            convert.setAnimation(push_left_in);
        }

    }
}
