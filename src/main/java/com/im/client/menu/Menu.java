package com.im.client.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 老顾
 * @title: Menu
 * @projectName chat-room
 * @email: 1437594522@qq.com
 * @date 2022/11/1 18:58
 */
public class Menu {

    /**
     * 序号
     */
    private int order;

    /**
     * 名称
     */
    private String name;

    /**
     * 菜单命令
     */
    private int command;

    /**
     * 子菜单列表
     */
    private List<Menu> children = new ArrayList<>();

    public Menu(int order, String name, int command) {
        this.order = order;
        this.name = name;
        this.command = command;
    }

    /**
     * 添加子菜单
     * @param child
     */
    public void addChild(Menu child){
        children.add(child);
    }

    public int getCommand() {
        return command;
    }

    public Menu[] getChildren(){
        return children.toArray(new Menu[children.size()]);
    }

    @Override
    public String toString() {
        return order + "." + name;
    }
}
