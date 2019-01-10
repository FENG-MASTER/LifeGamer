package com.lifegamer.fengmaster.lifegamer.manager.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.manager.base.itf.IAvatarManager;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.iconics.typeface.ITypeface;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by FengMaster on 18/12/20.
 *
 * 接入Iconics三方库的头像管理器
 *
 */
public class IconicsAvatarManager implements IAvatarManager {

    private Context context;

    private List<String> iconNames;

    private Drawable emptyDrawable;

    public IconicsAvatarManager() {
        context=App.getContext();
        emptyDrawable=context.getDrawable(R.drawable.ic_default);
        initIconNames();
    }


    /**
     * 初始化所有icon名称
     */
    private void initIconNames(){
        iconNames=new LinkedList<>();
        Collection<ITypeface> registeredFonts = Iconics.getRegisteredFonts(context);
        for (ITypeface registeredFont : registeredFonts) {
            Collection<String> icons = registeredFont.getIcons();
            for (String icon : icons) {
                iconNames.add(registeredFont.getMappingPrefix()+"#"+icon);
            }

        }


    }

    @Override
    public Drawable getDrawable(String name) {
        if (name==null||name.equals(context.getString(R.string.empty))){
            return emptyDrawable;
        }

        String[] avatarStrFormat = FormatUtil.avatarStrFormat(name);
        String fontName=avatarStrFormat[0];
        String iconName=avatarStrFormat[1];

        ITypeface font = Iconics.findFont(context, fontName);
        if (font!=null){
            //存在对应的font
            IIcon icon = font.getIcon(iconName);
            if (icon!=null){
                //存在对应的图标
                return new IconicsDrawable(context).color(Color.WHITE).icon(icon);
            }else {
                return emptyDrawable;
            }


        }else {
            return emptyDrawable;

        }

    }

    @Override
    public List<String> getAllDrawableName() {
        return iconNames;
    }

    @Override
    public Avatar getAvatar(String name) {
        if (name==null||name.equals(context.getString(R.string.empty))){
            Avatar avatar = new Avatar("", "default");
            return avatar;
        }
        String[] avatarStrFormat = FormatUtil.avatarStrFormat(name);
        String fontName=avatarStrFormat[0];
        String iconName=avatarStrFormat[1];

        ITypeface font = Iconics.findFont(context, fontName);
        if (font!=null){
            //存在对应的font
            IIcon icon = font.getIcon(iconName);
            if (icon!=null){
                //存在对应的图标
                Avatar avatar = new Avatar(font.getMappingPrefix(), icon.getName());
                return avatar;
            }else {
                Avatar avatar = new Avatar("", "default");
                return avatar;
            }


        }else {
            Avatar avatar = new Avatar("", "default");
            return avatar;
        }

    }

    @Override
    public String getName() {
        return "iconics";
    }
}
