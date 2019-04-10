package com.jidouauto.ui.oushang.sideBar;

public class Menu {
    private int position;
    private int drawableRes;
    private int pressedDrawableRes;
    private String text;
    private boolean isSelected;

    public Menu(int position, int drawableRes, int pressedDrawableRes, String text) {
        this.position = position;
        this.drawableRes = drawableRes;
        this.pressedDrawableRes = pressedDrawableRes;
        this.text = text;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getPressedDrawableRes() {
        return pressedDrawableRes;
    }

    public String getTextRes() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
