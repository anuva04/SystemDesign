/**
 Abstract factory pattern provides another level of abstraction to factory pattern.
 User provides required params to abstract factory which in turn creates a concrete factory which ultimately creates required objects.
 Type of factory to be created is determined at runtime based on user parameters (in this case, the OS on which this program runs).

 In this example, user provides length, width and mode for creating a GUI to an abstract factory.
 Abstract factory determines the OS on which this programs runs and creates a corresponding concrete factory.
 The concrete factory creates GUI using user provided arguments.
 **/

import java.util.*;
public class AbstractFactoryPattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter mode: ");
        String userMode = sc.next();
        Mode mode;
        if (userMode.equalsIgnoreCase("dark")) mode = Mode.DARK;
        else mode = Mode.LIGHT;
        System.out.println("Enter length: ");
        int length = sc.nextInt();
        System.out.println("Enter width: ");
        int width = sc.nextInt();

        System.out.println(GUIFactory.buildGUI(mode, length, width));
    }
}

abstract class GUI {
    int length;
    int width;
    Mode mode;
    OS os;

    GUI(int length, int width, Mode mode, OS os) {
        this.length = length;
        this.width = width;
        this.mode = mode;
        this.os = os;
    }

    @Override
    public String toString() {
        return "Length: " + length + " width: " + width + " mode: " + mode + " OS: " + os;
    }
}

class DarkGui extends GUI {
    DarkGui(OS os, int length, int width) {
        super(length, width, Mode.DARK, os);
    }
}

class LightGui extends GUI {
    LightGui(OS os, int length, int width) {
        super(length, width, Mode.LIGHT, os);
    }
}

class WindowsGuiFactory {
    public static GUI buildGUI (Mode mode, int length, int width) {
        GUI gui = null;
        switch (mode) {
            case DARK:
                gui = new DarkGui(OS.WINDOWS, length, width);
                break;
            case LIGHT:
                gui = new LightGui(OS.WINDOWS, length, width);
                break;
            default:
                break;
        }
        return gui;
    }
}

class MacGuiFactory {
    public static GUI buildGUI (Mode mode, int length, int width) {
        GUI gui = null;
        switch (mode) {
            case DARK:
                gui = new DarkGui(OS.MAC_OS, length, width);
                break;
            case LIGHT:
                gui = new LightGui(OS.MAC_OS, length, width);
                break;
            default:
                break;
        }
        return gui;
    }
}

class LinuxGuiFactory {
    public static GUI buildGUI (Mode mode, int length, int width) {
        GUI gui = null;
        switch (mode) {
            case DARK:
                gui = new DarkGui(OS.LINUX, length, width);
                break;
            case LIGHT:
                gui = new LightGui(OS.LINUX, length, width);
                break;
            default:
                break;
        }
        return gui;
    }
}

class GUIFactory {
    public static GUI buildGUI(Mode mode, int length, int width) {
        GUI gui = null;
        OS os = null;

        String systemOs = System.getProperty("os.name");
        if(systemOs.toLowerCase().contains("windows")) os = OS.WINDOWS;
        else if(systemOs.toLowerCase().contains("mac")) os = OS.MAC_OS;
        else if(systemOs.toLowerCase().contains("ubuntu")) os = OS.LINUX;


        switch (os) {
            case WINDOWS:
                gui = WindowsGuiFactory.buildGUI(mode, length, width);
                break;
            case MAC_OS:
                gui = MacGuiFactory.buildGUI(mode, length, width);
                break;
            case LINUX:
                gui = LinuxGuiFactory.buildGUI(mode, length, width);
                break;
            default:
                break;
        }
        return gui;
    }
}

enum Mode {
    DARK, LIGHT;
}

enum OS {
    WINDOWS, MAC_OS, LINUX
}