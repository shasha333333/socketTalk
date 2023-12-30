package test;

import java.awt.*;

public class FinalData {
    public static final Dimension SCREEN_SIZE;                              //屏幕尺寸
    public static final Toolkit TK = Toolkit.getDefaultToolkit();           //默认工具

    //静态初始化块
    static {
        SCREEN_SIZE = TK.getScreenSize();
    }
}
