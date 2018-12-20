package com.lifegamer.fengmaster.lifegamer.manager.base.itf;

import android.graphics.drawable.Drawable;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.base.IName;

import java.util.List;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 头像管理器接口
 */

public interface IAvatarManager extends IName{

    /**
     * 根据名字获得相应的Drawable
     * @param name 名字
     * @return Drawable
     */
    Drawable getDrawable(String name);

    /**
     * 返回所有Drawable的名字列表
     * @return 列表
     */
    List<String> getAllDrawableName();

    IAvatarManager.Avatar getAvatar(String name);


    class Avatar{
        private String belong;
        private String name;
        private Drawable icon;

        public Avatar(String belong, String name) {
            this.belong = belong;
            this.name = name;
        }

        public String getBelong() {
            return belong;
        }

        public String getName() {
            return name;
        }

        public Drawable getIcon() {
            if (icon==null){
                icon= Game.getInstance().getAvatarManager().getDrawable(toString());
            }
            return icon;
        }

        @Override
        public String toString() {
            return belong+"#"+name;
        }
    }

}
