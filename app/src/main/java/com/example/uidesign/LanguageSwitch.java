package com.example.uidesign;

import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.util.Locale;
public class LanguageSwitch {
    public static void set(boolean isEnglish) {
        Configuration configuration = MainActivity.instance.getResources().getConfiguration();
        DisplayMetrics displayMetrics = MainActivity.instance.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        MainActivity.instance.getResources().updateConfiguration(configuration, displayMetrics);
    }

}
