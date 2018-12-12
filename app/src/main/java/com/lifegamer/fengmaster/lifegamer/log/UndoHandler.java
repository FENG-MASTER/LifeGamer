package com.lifegamer.fengmaster.lifegamer.log;

public @interface UndoHandler {

    String action();

    String type();

    String property();

}
