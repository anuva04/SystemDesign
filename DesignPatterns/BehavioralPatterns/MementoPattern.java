/**
 * This pattern is used when we want to store snapshot of an object's state in different times.
 * Instead of having a separate class manage this externally, it is better to have an utility within the concerned class itself to create a snapshot.
 * This promotes seperation of concerns.
 * Also, we need a caretaker class which maintains all mementos/snapshots of the object.
 * In this example, we are taking snapshots of the TextBox class using Memento class.
 * Editor class is storing a list of all mementos and asks the TextBox class to restore to a certain memento based on user input (undo/redo).
 */

import java.util.*;
class MementoPattern {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.write("Hi");
        editor.save();
        editor.write(" I am");
        editor.save();
        editor.write(" Anuva");
        editor.save();
        editor.undo();
        editor.undo();
        editor.undo();
        editor.redo();
        editor.redo();
        editor.redo();
        editor.redo();
    }
}

class Editor {
    private List<Memento> mementoList;
    private int pointer;
    private TextBox textBox;

    public Editor() {
        mementoList = new ArrayList<>();
        mementoList.add(new Memento(""));
        pointer = 0;
        textBox = new TextBox();
    }

    public void write(String s) {
        textBox.addText(s);
        System.out.println(textBox.showText());
    }

    public void save() {
        mementoList.add(textBox.save());
        pointer++;
        System.out.println("Saved");
    }

    public void undo() {
        if(pointer > 0) pointer--;
        textBox.restore(mementoList.get(pointer));
        System.out.println(textBox.showText());
    }

    public void redo() {
        if(pointer < mementoList.size() - 1) pointer++;
        textBox.restore(mementoList.get(pointer));
        System.out.println(textBox.showText());
    }
}

class TextBox {
    private StringBuilder text;

    public TextBox() {
        this.text = new StringBuilder();
    }

    public void addText(String s) {
        text.append(s);
    }

    public String showText() {
        return text.toString();
    }

    public Memento save() {
        return new Memento(text.toString());
    }

    public void restore(Memento m) {
        this.text = new StringBuilder(m.getText());
    }
}

class Memento {
    private String text;
    public Memento(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}