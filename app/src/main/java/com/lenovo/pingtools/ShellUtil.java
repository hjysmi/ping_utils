package com.lenovo.pingtools;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * https://blog.csdn.net/wcqlwyt/article/details/80405405
 * https://blog.csdn.net/LJX646566715/article/details/112244168 飞行模式
 * http://www.javashuo.com/article/p-zkgpkavp-np.html 飞行模式
 * https://blog.csdn.net/weixin_34102807/article/details/91902453 root权限
 * https://blog.csdn.net/wszonline/article/details/73558255  root权限
 */
public class ShellUtil
{


//    boolean isEnabled = Settings.System.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
//                Log.e("xue", "isEnabled: "+isEnabled);
//                Settings.System.putInt(getContentResolver(),Settings.Global.AIRPLANE_MODE_ON, isEnabled?0:1);
//    Intent i=new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//                i.putExtra("state", !isEnabled);
//    sendBroadcast(i);
    public static String HigherAirplaneModePref1 = "settings put global airplane_mode_on ";
    public static String HigherAirplaneModePref2 = "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state ";
    private static String TAG = ShellUtil.class.getName();

    /**  * @ value =1 打开飞行模式  * @ value =2 关闭飞行模式  *  * */
    public static void setSettingsOnHigh(int value) {

        String commond = HigherAirplaneModePref1 + value + ";";
        if (value == 1)
            commond += HigherAirplaneModePref2 + "true";
        else
            commond += HigherAirplaneModePref2 + "false";
        String result = ShellUtil.runRootCmd(commond);
    }

    public static String runCommand(String command)
    {
        Process process = null;
        String result = "false";
        try
        {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            result = inputStreamToString(process.getInputStream());
            Log.e(TAG, result);
        } catch (Exception e)
        {
            return result;
        } finally
        {
            try
            {
                process.destroy();
            } catch (Exception e)
            {
            }
        }
        return result;
    }

    //http://blog.csdn.net/alexander_xfl/article/details/9150971
    //command can be some cmd, use ; to split

    public static String runRootCmd(String command)
    {
        return runRootCmd(command,  ";");
    }

    public static String runRootCmd(String command, String split)
    {
        Process process = null;
        DataOutputStream os = null;
        String result = "false";
        try
        {
            process = Runtime.getRuntime().exec("su");
            OutputStream outstream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outstream);
            String temp = "";
            String[] cmds = command.split(split);
            for(int i = 0; i < cmds.length; i++)
                temp += cmds[i] + "\n";
            dataOutputStream.writeBytes(temp);
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            result = inputStreamToString(process.getInputStream());
            Log.i(TAG, temp);

        } catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return result;
        } finally
        {
            Log.i(TAG, result);
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String inputStreamToString(InputStream in) throws IOException
    {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[1024];
        for (int n; (n = in.read(b)) != -1;)
        {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}