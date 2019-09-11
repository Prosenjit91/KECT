package com.kp.prosenjit.kect.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.jsibbold.zoomage.ZoomageView;
import com.kp.prosenjit.kect.R;


public class ViewFullscreenImage extends Base  {

    public ZoomageView view_fullscreen_image_view;
    public static ProgressBar view_fullscreen_progressbar;
    String imageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_fullscreen_image);

        view_fullscreen_image_view = (ZoomageView) findViewById(R.id.zoomimageView);
        view_fullscreen_progressbar = (ProgressBar) findViewById(R.id.view_fullscreen_progressbar);
        Intent i = getIntent();
        imageUrl = i.getStringExtra("image_url");
        Log.e("imageUrl**",imageUrl);

        view_fullscreen_progressbar.setVisibility(View.VISIBLE);
        Glide.with(ViewFullscreenImage.this)
                .load(imageUrl)
                .into(target);


    }

    private BaseTarget target = new BaseTarget<BitmapDrawable>() {
        @Override
        public void onResourceReady(BitmapDrawable bitmap, Transition<? super BitmapDrawable> transition) {
            view_fullscreen_image_view.setImageDrawable(bitmap);
            view_fullscreen_progressbar.setVisibility(View.GONE);
        }

        @Override
        public void getSize(SizeReadyCallback cb) {
            cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL);
        }

        @Override
        public void removeCallback(SizeReadyCallback cb)
        {
            view_fullscreen_progressbar.setVisibility(View.GONE);
        }
    };


}