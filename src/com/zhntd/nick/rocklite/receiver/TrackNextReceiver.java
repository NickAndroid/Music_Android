package com.zhntd.nick.rocklite.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhntd.nick.rocklite.service.CoreService;

/**
 * @author nick
 * @date Jul 29, 2014
 * @time 2:10:05 PM TODO
 */
public class TrackNextReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent newIntent = new Intent();
        newIntent.setAction(CoreService.ACTION_NEXT_TRACK);
        context.sendBroadcast(newIntent);
    }

}
