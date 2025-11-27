package guni.guflex.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;

public final class MouseUtils {
    public static double getX() {
        Minecraft minecraft = Minecraft.getInstance();
        MouseHandler mouseHandler = minecraft.mouseHandler;
        double scale = (double) minecraft.getWindow().getGuiScaledWidth() / (double) minecraft.getWindow().getScreenWidth();
        return mouseHandler.xpos() * scale;
    }

    public static double getY() {
        Minecraft minecraft = Minecraft.getInstance();
        MouseHandler mouseHandler = minecraft.mouseHandler;
        double scale = (double) minecraft.getWindow().getGuiScaledHeight() / (double) minecraft.getWindow().getScreenHeight();
        return mouseHandler.ypos() * scale;
    }
}
