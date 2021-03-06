package helper;

import bean.SwPointBean;
import constant.ConstantScreen;
import utils.AwtUtil;
import utils.FileUtil;
import utils.ScreenUtil;
import utils.ThreadPoolUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SwTaskPointhelper {

    private int minR = 255;
    private int maxR = 0;
    private int minG = 255;
    private int maxG = 0;
    private int minB = 255;
    private int maxB = 0;

    private int mOldMinR = 255;
    private int mOldMaxR = 0;
    private int mOldMinG = 255;
    private int mOldMaxG = 0;
    private int mOldMinB = 255;
    private int mOldMaxB = 0;

    private long mOldSaveFileTime = System.currentTimeMillis();

    private List<String> mXyList = new CopyOnWriteArrayList<>();

    /**
     * 挑战
     *     public static final int ROOT_X = 764;
     *     public static final int ROOT_Y = 62;
     *     挑战：1620，253
     *     (maxR:246,minR:237)(maxG:248,minG:240)(maxB:0,minB:0)
     */


    /**
     * 鼠标跳到捕捉宠物处
     */
    public static void jumpToCatchPet() {
        moveMouse(ConstantScreen.TASK_CHALLENGE_1_X, ConstantScreen.TASK_CHALLENGE_1_Y);
    }

    public static void moveMouse(int x, int y) {
        AwtUtil.getRobot().mouseMove(x, y);
    }

    /**
     * 指定位置的像素值存入文件
     *
     * @param fileHead
     * @param x
     * @param y
     */
    public void inputMsgToFile(int x, int y, String fileHead, boolean addHead) {
        if (addHead) {
            mXyList.add(fileHead);
        }
//        Color pixelColor = AwtUtil.getRobot().getPixelColor(ConstantScreen.TASK_CHALLENGE_2_X, ConstantScreen.TASK_CHALLENGE_2_Y);
        Color pixelColor = AwtUtil.getRobot().getPixelColor(x, y);
        maxR = Math.max(maxR, pixelColor.getRed());
        minR = Math.min(minR, pixelColor.getRed());

        maxG = Math.max(maxG, pixelColor.getGreen());
        minG = Math.min(minG, pixelColor.getGreen());

        maxB = Math.max(maxB, pixelColor.getBlue());
        minB = Math.min(minB, pixelColor.getBlue());
        String xy = "(maxR:" + maxR + ",minR:" + minR + ")" + "(maxG:" + maxG + ",minG:" + minG + ")" + "(maxB:" + maxB + ",minB:" + minB + ")";
        if (maxR != mOldMaxR || minR != mOldMinR || maxG != mOldMaxG || minG != mOldMinG || maxB != mOldMaxB || minB != mOldMinB) {
            mXyList.add(xy);
        }
//        System.out.println(xy);
        if (System.currentTimeMillis() - mOldSaveFileTime > 3 * 1000 || mXyList.size() > 5) {
            FileUtil.appendContentToFile(mXyList, ScreenUtil.DIR_RES + "file/sm_" + fileHead + ".txt");
            System.out.println("appendContentToFile: " + fileHead + "...");
            mXyList.clear();
        }
        mOldMaxR = maxR;
        mOldMinR = minR;

        mOldMaxG = maxG;
        mOldMinG = minG;

        mOldMaxB = maxB;
        mOldMinB = minB;
    }
}
