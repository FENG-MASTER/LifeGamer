package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Note;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 笔记管理器接口
 */

public interface INoteManager {

    /**
     * 新增笔记
     * @param note 笔记
     * @return 笔记ID
     */
    int addNote(Note note);

    /**
     * 根据笔记ID返回笔记
     * @param nodeID 笔记ID
     * @return 笔记
     */
    Note getNote(int nodeID);

    /**
     * 删除笔记
     * @param nodeID 笔记ID
     * @return 是否删除成功
     */
    boolean removeNote(int nodeID);

    /**
     * 更新笔记内容
     * @param note 笔记
     * @return 是否成功
     */
    boolean updateNote(Note note);

}
