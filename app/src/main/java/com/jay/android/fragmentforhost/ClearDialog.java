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

    // ���?��

    // ���Ӧ���ڲ�����(/data/data/com.xxx.xxx/cache) * * @param context

    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    //���Ӧ��������ݿ�(/data/data/com.xxx.xxx/databases) * * @param context

    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    // ���Ӧ��SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    // ���������Ӧ����ݿ�
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    //���/data/data/com.xxx.xxx/files�µ�����
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    //����ⲿcache�µ�����(/mnt/sdcard/android/data/com.xxx.xxx/cache)
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    //����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ��
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    //���Ӧ�����е����
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

    // ɾ�� ����ֻ��ɾ��ĳ���ļ����µ��ļ���������directory�Ǹ��ļ�������������
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}
