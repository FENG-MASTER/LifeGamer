package com.lifegamer.fengmaster.lifegamer.manager.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 默认头像管理器
 */

public class DefaultAvatarManager implements IAvatarManager{

    private AssetManager assetManager;

    private final String DIR="icon";

    public DefaultAvatarManager(){
        assetManager= App.getContext().getResources().getAssets();
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public Drawable getDrawable(String name) {
        Drawable drawable=null;
        if(name==null){

        }else {
            try {
                drawable= Drawable.createFromStream(assetManager.open(DIR+"/" + name, AssetManager.ACCESS_BUFFER), getName() + "#" + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return drawable;
    }

    @Override
    public List<String> getAllDrawableName() {
        try {
            return Arrays.asList(assetManager.list(DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Avatar getAvatar(String name) {
        return new Avatar(getName(),name);
    }
}
