package com.jay.android.fragmentforhost.Help;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Handler;
import android.widget.Toast;

import com.jay.android.fragmentforhost.Utils.HexUtils;
import com.jay.android.fragmentforhost.Utils.UIUtils;
import com.mt.ble.mtble.MTBLEMBLE;
import com.mt.help.LogText;
import com.sdk.ble.MTBLEManager;
import com.sdk.help.Helpful;

public class BLEHelp {

    private MTBLEManager mMTBLEManager;
    private MTBLEMBLE mBle;
    private Handler handl = new Handler();

    private Activity activity;

    private static String mac = "78:A5:04:8D:18:2A";

    public BLEHelp(Activity activity, MTBLEMBLE.CallBack blecallback, String mac) {
        this.activity = activity;
        this.mac = mac;
        initBLE(blecallback);
    }

    //    初始化BLE
//    private static final String mac = "F4:B8:5E:E6:98:AC"; // 冯明敏
//    private static final String mac = "F4:B8:5E:E6:8C:1F"; // 吴海滨
//    private static final String mac = "78:A5:04:8D:18:2A";

    private void initBLE(MTBLEMBLE.CallBack blecallback) {
        if (android.os.Build.VERSION.SDK_INT < 18) {
            Toast.makeText(activity, "你out了，系统尽然还没有到android 4.3", Toast.LENGTH_LONG).show();
            return;
        }
        mMTBLEManager = MTBLEManager.getInstance();
        mMTBLEManager.init(activity);
        mBle = new MTBLEMBLE(activity, mMTBLEManager.mBluetoothManager, mMTBLEManager.mBluetoothAdapter);

        mBle.setCallback(blecallback);
        new connectThread().start();
    }

//    // 显示接收数据和命令
//    private boolean disDatas(final BluetoothGattCharacteristic data_char) {
////        UIUtils.showToastSafe("正在接收数据");
//        handl.post(new Runnable() {
//            @Override
//            public void run() {
//                switch (1) {
//                    case 0: // String
////                        Toast.makeText(activity, data_char.getStringValue(0), Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1: // 16进制
//                        UIUtils.showToastSafe(Helpful.MYBytearrayToString(data_char.getValue()));
////                        Toast.makeText(activity, Helpful.MYBytearrayToString(data_char.getValue()), Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2: // 10进制
//                        int count = 0;
//                        byte[] tmp_byte = data_char.getValue();
//                        for (int i = 0; i < tmp_byte.length; i++) {
//                            count *= 256;
//                            count += (tmp_byte[tmp_byte.length - 1 - i] & 0xFF);
//                        }
////                        Toast.makeText(activity, "" + count, Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        return true;
//    }

    // 获取发送数据
    public byte[]  getSendDatas(String tmp_str, int type, boolean dis_flag) {
        byte[] tmp_byte = null;
        byte[] write_msg_byte = null;

        switch (type) {
            case 0: // 字符串类型
                if (0 == tmp_str.length())
                    return null;

                write_msg_byte = tmp_str.getBytes();
                break;

            case 1: // 十六进制类型
                if (0 == tmp_str.length())
                    return null;

                tmp_byte = tmp_str.getBytes();
                write_msg_byte = new byte[tmp_byte.length / 2 + tmp_byte.length % 2];
                for (int i = 0; i < tmp_byte.length; i++) {
                    if ((tmp_byte[i] <= '9') && (tmp_byte[i] >= '0')) {
                        if (0 == i % 2)
                            write_msg_byte[i / 2] = (byte) (((tmp_byte[i] - '0') * 16) & 0xFF);
                        else
                            write_msg_byte[i / 2] |= (byte) ((tmp_byte[i] - '0') & 0xFF);
                    } else {
                        if (0 == i % 2)
                            write_msg_byte[i / 2] = (byte) (((tmp_byte[i] - 'a' + 10) * 16) & 0xFF);
                        else
                            write_msg_byte[i / 2] |= (byte) ((tmp_byte[i] - 'a' + 10) & 0xFF);
                    }
                }
                break;

            case 2: // 十进制类型
                if (0 == tmp_str.length())
                    return null;

                int data_int = Integer.parseInt(tmp_str);
                int byte_size = 0;
                for (byte_size = 0; data_int != 0; byte_size++) { // 计算占用字节数
                    data_int /= 256;
                }
                write_msg_byte = new byte[byte_size];

                data_int = Integer.parseInt(tmp_str);
                for (int i = 0; i < byte_size; i++) { // 转换
                    write_msg_byte[i] = (byte) (0xFF & (data_int % 256));
                    data_int /= 256;
                }

                break;
        }

        if (0 == tmp_str.length())
            return null;
        // 显示
        if (dis_flag) {
            Toast.makeText(activity, "发送" + tmp_str + "成功", Toast.LENGTH_SHORT).show();
        }

        return write_msg_byte;
    }

    // 设置回调方法
//    private MTBLEMBLE.CallBack blecallback = new MTBLEMBLE.CallBack() {
//
//        @Override
//        public void onReviceDatas(final BluetoothGattCharacteristic data_char) {
//            disDatas(data_char);
//        }
//
//        @Override
//        public void onReviceCMD(BluetoothGattCharacteristic data_char) {
//            disDatas(data_char);
//        }
//
//        @Override
//        public void onDisconnect() {
//            handl.post(new Runnable() {
//
//                @Override
//                public void run() {
//                    if (!activity.isDestroyed()) {
//                        Toast.makeText(activity, "断开连接，正在自动重连", Toast.LENGTH_SHORT).show();
//                        if (mBle.isConnected()) {
//                            return;
//                        } else {
//                            new connectThread().start();
//                        }
//                    }
//                }
//            });
//        }
//    };


    // 建立连接线程
//    private ProgressDialog pd;
    private class connectThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                // 创建一个ProgressDialog框, 类似于loading作用
//                handl.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        pd = ProgressDialog.show(MainActivity.this, "", "正在连接", true, false);
//                    }
//
//                });

                // 开始连接
                if (!mBle.connect(mac, 10000, 2)) {
                    mBle.disConnect();
                    handl.post(new Runnable() {

                        @Override
                        public void run() {
//                            pd.dismiss();
                            Toast.makeText(activity, "连接失败", Toast.LENGTH_LONG).show();
                        }

                    });
                }
//                else{
//                    handl.post(new Runnable() {
//
//                        @Override
//                        public void run() {
////                            pd.dismiss();
//                            Toast.makeText(activity, "连接成功", Toast.LENGTH_LONG).show();
//                        }
//
//                    });
//                }

            } catch (Exception e) {
                LogText.writeStr("MTBeacon1Set AsyDataThread->" + e.toString());
            }

        }
    }

    // 发送数据
    public boolean sendDatas(String hint, byte[] value) {
        if (value == null) {
            return false;
        }
        mBle.sendData(value);
        UIUtils.showToastSafe(hint + HexUtils.Bytes2HexString(value));
        return true;
    }
}
