package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Note;

/**
 * Created by qianzise on 2017/10/4.
 */

public interface INoteManager {

    int addNote(Note note);

    Note getNote(int nodeID);

    boolean removeNote(int nodeID);

    boolean updateNote(Note note);

}
