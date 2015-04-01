package com.ecofactorqa.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WaitUtil is a utility class that provides helper methods to create pause in execution.
 * @author $Author: sgulhar $
 * @version $Rev: 32884 $ $Date: 2014-11-26 23:54:28 +0530 (Wed, 26 Nov 2014) $
 */
public class WaitUtil {

    private static Logger logger = LoggerFactory.getLogger(WaitUtil.class);
    public static final int ONE_SECS = 1000;
    public static final int FOUR_SECS = 4000;
    public static final int FIVE_SECS = 5000;
    public static final int TEN_SECS = 10000;
    public static final int TWENTY_SECS = 20000;
    public static final int THIRTY_SECS = 30000;
    public static final int FIFTY_SECS = 50000;
    public static final int ONE_MIN = 60000;
    public static final int FIVE_MINS = 300000;
    public static final int TWO_MINS = 120000;
    public static final int THREE_MINS = 180000;
    public static final int FOUR_MINS = 240000;
    public static final int EIGHT_MINS = 480000;
    public static final long TEN_MINS = 600000;

    /**
     * Wait until.
     * @param msecs the msecs
     */
    public static void waitUntil(long msecs) {

        try {
            int minutes = (int) (msecs / 1000 / 60);
            int seconds = (int) (msecs / 1000 % 60);
            //logger.info("Started Wait " + DateUtil.format(new Date(), DateUtil.DATE_FMT_FULL_TZ) + ", [" + minutes + " min :" + seconds + " sec]");
            Thread.sleep(msecs);
            //logger.info("Ended wait " + DateUtil.format(new Date(), DateUtil.DATE_FMT_FULL_TZ));
        } catch (InterruptedException e) {
            logger.error("Error Waiting ", e);
        }
    }

    /**
     * Tiny wait.
     */
    public static void oneSec() {

        waitUntil(ONE_SECS);
    }

    /**
     * Four sec.
     */
    public static void fourSec() {

        waitUntil(FOUR_SECS);
    }

    /**
     * Tiny wait.
     */
    public static void tinyWait() {

        waitUntil(FIVE_SECS);
    }

    /**
     * Small wait.
     */
    public static void smallWait() {

        waitUntil(TEN_SECS);
    }

    /**
     * Medium wait.
     */
    public static void mediumWait() {

        waitUntil(TWENTY_SECS);
    }

    /**
     * Large wait.
     */
    public static void largeWait() {

        waitUntil(THIRTY_SECS);
    }

    /**
     * Three minutes wait.
     */
    public static void threeMinutesWait() {

        waitUntil(THREE_MINS);
    }

    /**
     * Huge wait.
     */
    public static void hugeWait() {

        waitUntil(FIVE_MINS);
    }

    /**
     * Very huge wait.
     */
    public static void veryHugeWait() {

        waitUntil(EIGHT_MINS);
    }
}
