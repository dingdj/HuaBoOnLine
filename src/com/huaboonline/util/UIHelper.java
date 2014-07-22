package com.huaboonline.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import com.android.huaboonline.R;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.huaboonline.AppManager;

/**
 * Created by dingdj on 2014/7/20.
 */
public class UIHelper {

    /**
     * 发送App异常崩溃报告
     *
     * @param mContext
     * @param crashReport
     */
    public static void sendAppCrashReport(final Context mContext,
                                          final String crashReport) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_error);
        builder.setMessage(R.string.app_error_message);
        builder.setPositiveButton(R.string.submit_report,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 发送异常报告
                        Intent i = new Intent(Intent.ACTION_SEND);
                        // i.setType("text/plain"); //模拟器
                        i.setType("message/rfc822"); // 真机
                        i.putExtra(Intent.EXTRA_EMAIL,
                                new String[] { "18950295115@163.com" });
                        i.putExtra(Intent.EXTRA_SUBJECT,
                                "开源中国Android客户端 - 错误报告");
                        i.putExtra(Intent.EXTRA_TEXT, crashReport);
                        mContext.startActivity(Intent.createChooser(i, "发送错误报告"));
                        // 退出
                        AppManager.getAppManager().AppExit(mContext);
                    }
                });
        builder.setNegativeButton(R.string.sure,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 退出
                        AppManager.getAppManager().AppExit(mContext);
                    }
                });
        builder.show();
    }


    /**
     * 显示错误对话框
     * @param context
     * @param error
     */
    public static void showErrorDialog(Context context, VolleyError error) {
        if(error instanceof NetworkError) {
            UIHelper.getNetWorkErrorDialog(context).show();
            return;
        } else if(error instanceof ServerError) {
            UIHelper.getServerErrorDialog(context).show();
            return;
        } else {
            UIHelper.getNetWorkErrorDialog(context).show();
        }
    }


    /**
     * 網絡錯誤的Dialog
     * @return
     */
    public static AlertDialog getNetWorkErrorDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.network_not_connected));
        AlertDialog dialog = builder.create();
        return dialog;
    }

    /**
     * 服务端异常的Dialog
     * @return
     */
    public static AlertDialog getServerErrorDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.server_error));
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
