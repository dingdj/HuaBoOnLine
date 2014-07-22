package com.huaboonline.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dingdj on 2014/7/21.
 */
public class ThreadUtil {

    /**
     * 固定数量线程池
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);


    /**
     * <br>Author:dingdj
     */
    public static void execute(Runnable command) {
        executorService.execute(command);
    }


}
