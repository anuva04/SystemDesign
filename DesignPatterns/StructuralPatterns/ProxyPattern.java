/**
 * This pattern is used when we need a proxy for an object to do it's work because operations on the real object are expensive.
 * Operations on the real object are carried out only when absolutely necessary.
 * There are many use-cases for this pattern such as caching, image/video operations, lazy initialization etc.
 * In this example, we are creating a cache in memory of a table which is stored on a remote server.
 * All operations are carried out on the proxy table.
 * The real table is updated only when update method is called in this example.
 */

import java.util.HashMap;
import java.util.Map;

class ProxyPattern {
    public static void main(String[] args) {
        TableProxy table = new TableProxy();
        table.add(1, "user1");
        table.add(2, "user2");
        table.add(3, "user3");
        table.add(2, "user4");

        System.out.println("After addition");
        table.backup();
        table.printTable();

        table.update(2, "user4");

        System.out.println("After udpation");
        table.backup();
        table.printTable();

        table.remove(1);
        table.remove(2);

        System.out.println("After deletion");
        table.backup();
        table.printTable();
    }
}

interface Table {
    void add(int id, String name);
    void remove(int id);
    void update(int id, String name);
    void printTable();
}

// This table is present in database on remote server
class RealTable implements Table {
    public HashMap<Integer, String> employeeIdName = new HashMap<>();

    @Override
    public void add(int id, String name) {
        if(employeeIdName.containsKey(id)) {
            System.out.println("EmployeeId exists already");
            return;
        }
        employeeIdName.put(id, name);
    }

    @Override
    public void remove(int id) {
        if(!employeeIdName.containsKey(id)) {
            System.out.println("EmployeeId doesn't exist");
            return;
        }
        employeeIdName.remove(id);
    }

    @Override
    public void update(int id, String name) {
        if(!employeeIdName.containsKey(id)) {
            System.out.println("EmployeeId doesn't exist");
            return;
        }
        employeeIdName.put(id, name);
    }

    @Override
    public void printTable() {
        for (Map.Entry<Integer, String> entry : employeeIdName.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}

// Cached table in memory
class TableProxy implements Table {
    private RealTable realTable = new RealTable();
    private String del = "DELETED";

    private HashMap<Integer, String> employeeIdName = new HashMap<>();

    @Override
    public void add(int id, String name) {
        if(employeeIdName.containsKey(id)) {
            System.out.println("EmployeeId exists already");
            return;
        }
        employeeIdName.put(id, name);
    }

    @Override
    public void remove(int id) {
        if(!employeeIdName.containsKey(id) || employeeIdName.get(id).equals(del)) {
            System.out.println("EmployeeId doesn't exist");
            return;
        }
        employeeIdName.put(id, del);
    }

    @Override
    public void update(int id, String name) {
        if(!employeeIdName.containsKey(id) || employeeIdName.get(id).equals(del)) {
            System.out.println("EmployeeId doesn't exist");
            return;
        }
        employeeIdName.put(id, name);
    }

    public void backup() {
        // Updating real table only from this method
        for(Map.Entry<Integer, String> entry : employeeIdName.entrySet()) {
            if(entry.getValue().equals(del)) {
                realTable.remove(entry.getKey());
            } else {
                if(realTable.employeeIdName.containsKey(entry.getKey())) {
                    realTable.update(entry.getKey(), entry.getValue());
                } else {
                    realTable.add(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Override
    public void printTable() {
        realTable.printTable();
    }
}