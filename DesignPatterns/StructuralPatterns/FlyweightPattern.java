/**
 * This pattern is used when we need to create a lot of objects of same class with very minimal differences in properties.
 * Each object requires memory, so it is efficient to create one object for a certain set of properties and reuse is wherever possible.
 * Ofcourse these objects should be immutable so as to avoid any unintentional behavior anywhere they get used.
 * In this example, say we are creating a terrain for a game. It can be viewed as a grid with different elements in each cell.
 * Most of these elements will be immutable.
 * So it is way more efficient to create 1 instance of each element and render it multiple times on the terrain.`
 */

import java.util.HashMap;
import java.util.ArrayList;

class FlyweightPattern {
    public static void main(String[] args) {
        Terrain terrain = new Terrain(2, 2);
        terrain.setElement(ElementType.TREE, 0, 0);
        terrain.setElement(ElementType.WATER, 0, 1);
        terrain.setElement(ElementType.WATER, 1, 1);
        terrain.setElement(ElementType.LAND, 1, 0);

        terrain.render();

        // Observe that object address for same element types are same.
        System.out.println("0,0 element: " + terrain.terrain[0][0].render() + ", address: " + terrain.terrain[0][0]);
        System.out.println("0,0 element: " + terrain.terrain[0][1].render() + ", address: " + terrain.terrain[0][1]);
        System.out.println("0,0 element: " + terrain.terrain[1][0].render() + ", address: " + terrain.terrain[1][0]);
        System.out.println("0,0 element: " + terrain.terrain[1][1].render() + ", address: " + terrain.terrain[1][1]);
    }
}

interface Element {
    char render();
}

class Tree implements Element {
    @Override
    public char render() {
        return 'T';
    }
}

class Water implements Element {
    @Override
    public char render() {
        return 'W';
    }
}

class Rock implements Element {
    @Override
    public char render() {
        return 'W';
    }
}

class Land implements Element {
    @Override
    public char render() {
        return 'L';
    }
}

class ElementFactory {
    private HashMap<ElementType, Element> elementMap = new HashMap<>();

    public Element getElement(ElementType elementType) {
        if(elementMap.containsKey(elementType)) {
            return elementMap.get(elementType);
        }

        Element element;
        switch(elementType) {
            case TREE:
                element = new Tree();
                break;
            case WATER:
                element = new Water();
                break;
            case ROCK:
                element = new Rock();
                break;
            case LAND:
                element = new Land();
                break;
            default:
                element = null;
        }

        if(element == null)
            System.out.println("Invalid element type " + elementType);

        elementMap.put(elementType, element);
        return element;
    }
}

enum ElementType {
    LAND, TREE, ROCK, WATER
}

class Terrain {
    public int length;
    public int width;
    Element[][] terrain;
    ElementFactory factory = new ElementFactory();

    public Terrain(int length, int width) {
        this.length = length;
        this.width = width;
        terrain = new Element[length][width];
    }

    public void setElement(ElementType elementType, int x, int y) {
        if(x >= this.length || y >= this.width) {
            throw new IndexOutOfBoundsException("Given coordinates are not within terrain dimensions");
        }

        terrain[x][y] = factory.getElement(elementType);
    }

    public void render() {
        for(int i=0; i<length; i++) {
            for(int j=0; j<width; j++) {
                System.out.print(terrain[i][j].render());
            }
            System.out.println();
        }
    }
}

