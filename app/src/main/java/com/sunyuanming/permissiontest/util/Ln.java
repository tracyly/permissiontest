package com.sunyuanming.permissiontest.util;

import android.util.Log;

import com.sunyuanming.permissiontest.BuildConfig;

/**
 * @author sunyuanming
 * 日志打印
 */

public final class Ln {
    /**
     * print is initially set to Print(), then replaced by guice during static injection
     * pass. This allows overriding where the log message is delivered to.
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;
    private static Print print = new Print();

    private Ln() {
    }

    public static void v(Throwable t) {
        print.println(Log.VERBOSE, Log.getStackTraceString(t));
    }

    public static void v(String s1, Object... args) {
        final String message = args.length > 0 ? String.format(s1, args) : s1;
        print.println(Log.VERBOSE, message);
    }

    public static void d(Throwable t) {
        print.println(Log.DEBUG, Log.getStackTraceString(t));
    }

    public static void d(String s1, Object... args) {
        final String message = args.length > 0 ? String.format(s1, args) : s1;
        print.println(Log.DEBUG, message);
    }

    public static void i(Throwable t) {
        print.println(Log.INFO, Log.getStackTraceString(t));
    }

    public static void i(String s1, Object... args) {
        final String message = args.length > 0 ? String.format(s1, args) : s1;
        print.println(Log.INFO, message);
    }

    public static void w(Throwable t) {
        print.println(Log.WARN, Log.getStackTraceString(t));
    }

    public static void w(String s1, Object... args) {
        final String message = args.length > 0 ? String.format(s1, args) : s1;
        print.println(Log.WARN, message);
    }

    public static void e(Throwable t) {
        print.println(Log.ERROR, Log.getStackTraceString(t));
    }

    public static void e(String s1, Object... args) {
        final String message = args.length > 0 ? String.format(s1, args) : s1;
        print.println(Log.ERROR, message);
    }

    /**
     * Default implementation logs to android.util.Log
     */
    public static class Print {
        private static final int DEFAULT_STACK_TRACE_LINE_COUNT = 5;

        protected static String getScope() {
            final StackTraceElement trace = Thread.currentThread().getStackTrace()[DEFAULT_STACK_TRACE_LINE_COUNT];
            String score = "(" + trace.getFileName() + ":" + trace.getLineNumber() + ")";

            return score;
        }

        public void println(int priority, String msg) {
            if (DEBUG)
                Log.println(priority, getScope(), processMessage(msg));
        }

        protected String processMessage(String msg) {
            msg = String.format("%s %s", Thread.currentThread().getName(), msg);
            return msg;
        }
    }

}
