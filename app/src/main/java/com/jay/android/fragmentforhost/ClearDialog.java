package com.jay.android.fragmentforhost;

import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.File;

public class ClearDialog {
    public Dialog showDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        dialog.setContentView(R.layout.dialog_clear);
        dialog.show();
        dialog.findViewById(R.id.dialog_clear_tv_sure).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cleanApplicationData(context);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_dlg_clear_cancel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        return dialog;
    }

    // 清理缓存

    // 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context

    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    //清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context

    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    // 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    // 按名字清除本应用数据库
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    //清除/data/data/com.xxx.xxx/files下的内容
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    //清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    //清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    //清除本应用所有的数据
    public static void cleanApplicationData(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        // for (String filePath : filepath) {
        // cleanCustomCache(filePath);
        // }

    }

    // 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
