package com.zhntd.nick.rocklite;

import com.dk.animation.SwitchAnimationUtil;
import com.dk.animation.SwitchAnimationUtil.AnimationType;
import com.zhntd.nick.rocklite.fonts.FontsFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Welcome extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(),
                AnimationType.ROTATE);
        
        FontsFactory.createRoboto(this);

        new CountDownTimer(1000, 1000) {

            @Override
            public void onTick(long arg0) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Welcome.this, RockLite.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Welcome.this.finish();
            }
        }.start();

    }

    private void init() {
        Typeface typeFace = Typeface.createFromAsset(getAssets(),
                "fonts/hero.ttf");
        TextView splash = (TextView) findViewById(R.id.splash_text);
        splash.setTypeface(typeFace);
        splash.setText("ROCK LITE");
    }

}
