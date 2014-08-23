package com.zhntd.nick.rocklite.receiver;

import com.zhntd.nick.rocklite.service.CoreService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author nick
 * @date Jul 29, 2014
 * @time 2:10:05 PM TODO
 */
public class TrackPlayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent newIntent = new Intent();
        newIntent.setAction(CoreService.ACTION_PLAY_TRACK);
        context.sendBroadcast(newIntent);
    }

}
