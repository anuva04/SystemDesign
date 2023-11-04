/**
 * Composite pattern is used in cases where we have a tree-like hierarchy of similar elements.
 * In this example, there are multiple instances of folders and files in a tree-like directory.
 * So we implement them from same interface and same methods are available to them.
 * Composite elements are elements with children, while leaf elements have no children.
 * We store a link of children/parent element in each element so that we are able to navigate through the tree.
 */

import java.util.*;
import java.lang.UnsupportedOperationException;

class CompositePattern {
    public static void main(String[] args) {
        Folder SystemDesign = new Folder("SystemDesign", null);

        Directory DesignPatterns = new Folder("DesignPatterns", SystemDesign);
        System.out.println("Path of DesignPatterns:");
        DesignPatterns.printPath();
        System.out.println("");

        Directory ElevatorSystem = new Folder("ElevatorSystem", SystemDesign);
        System.out.println("Path of ElevatorSystem:");
        ElevatorSystem.printPath();
        System.out.println("");

        Directory StructuralDesignPatterns = new Folder("StructuralDesignPatterns", DesignPatterns);
        System.out.println("Path of StructuralDesignPatterns:");
        StructuralDesignPatterns.printPath();
        System.out.println("");

        System.out.println("Subdirectories of DesignPatterns:");
        DesignPatterns.printSubDirectories();
        System.out.println("");

        Directory CompositePattern = new File("CompositePattern", StructuralDesignPatterns);
        System.out.println("Path of CompositePattern:");
        CompositePattern.printPath();
        System.out.println("");
        System.out.println("Subdirectories of CompositePattern:");
        CompositePattern.printSubDirectories();
        System.out.println("");

        System.out.println("Subdirectories of SystemDesign:");
        SystemDesign.printSubDirectories();
        System.out.println("");
    }
}

interface Directory {
    public void printPath();
    public void printSubDirectories();
    public void add(Directory directory);
    public void remove(Directory directory);
}

// Composite element
class Folder implements Directory {
    public String name;
    public Directory parent;
    public List<Directory> subFolders;

    public Folder(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
        this.subFolders = new ArrayList<>();
        if(parent != null) parent.add(this);
    }

    @Override
    public void printPath() {
        if(parent != null) parent.printPath();
        System.out.print("/" + this.name);
    }

    @Override
    public void printSubDirectories() {
        for(Directory child : this.subFolders) {
            if(child instanceof Folder) {
                Folder folder = (Folder) child;
                System.out.println(folder.name);
            } else {
                File file = (File) child;
                System.out.println(file.name);
            }
        }
    }

    @Override
    public void add(Directory child) {
        this.subFolders.add(child);
    }

    @Override
    public void remove(Directory child) {
        this.subFolders.remove(child);
    }
}

// Leaf element
class File implements Directory {
    public String name;
    public Directory parent;

    public File(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public void printPath() {
        if(parent != null) parent.printPath();
        System.out.print("/" + this.name);
    }

    @Override
    public void printSubDirectories() {
        System.out.print("This is a file.");
    }

    @Override
    public void add(Directory child) {
        throw new UnsupportedOperationException("Can't add in a file.");
    }

    @Override
    public void remove(Directory child) {
        throw new UnsupportedOperationException("Can't remove from a file.");
    }
}