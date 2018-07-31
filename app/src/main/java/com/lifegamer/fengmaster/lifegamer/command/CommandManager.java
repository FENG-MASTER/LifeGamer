package com.lifegamer.fengmaster.lifegamer.command;

import android.support.design.widget.Snackbar;

import com.lifegamer.fengmaster.lifegamer.command.command.ICommand;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

/**
 * Created by qianzise on 2017/10/15.
 */

public class CommandManager {

    public void executeCommand(ICommand command){
        command.execute();
        if (command.isShow()){
            if (command.isUndoable()){
                //可取消
                ViewUtil.showSnack(command.getName(), Snackbar.LENGTH_LONG, command.getUndoActionName(), view -> command.undo());
            }else {
                //不可取消
                ViewUtil.showSnack(command.getName(),Snackbar.LENGTH_LONG);
            }
        }

    }

}
