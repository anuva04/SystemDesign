package org.anuva04.Models;

import java.util.Queue;
import java.util.LinkedList;

// A tray in each slot of Vending Machine which usually contains items of same type
public class Tray {
    private int price;
    private Queue<Item> items;

    private int traySize;

    public Tray(int price, int traySize) {
        this.price = price;
        this.items = new LinkedList<>();
        this.traySize = traySize;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public boolean addItem(Item item) {
        if(items.size() >= traySize) {
            return false;
        }
        this.items.add(item);
        return true;
    }

    public Item peekItem() {
        return this.items.peek();
    }

    public Item dispenseItem(){
        return this.items.poll();
    }

    public void removeAllItems() {
        this.items.clear();
    }

    public int getTraySize() {
        return traySize;
    }

    public int getItemsQuantity () {
        return this.items.size();
    }
}
