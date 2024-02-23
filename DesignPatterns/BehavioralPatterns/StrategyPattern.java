/**
 * Strategy pattern is used when we need to change the behavior of an algorithm at runtime.
 * We create an interface with a method which is implemented by all concrete classes.
 * This method contains the logic of the algorithm and can be implemented differently in each class.
 * The caller class stores an instance of this interface which can be set to required class at runtime.
 * In this example, LoadBalancer class contains an instance of Strategy which is implemented by RoundRobin and Random strategy.
 * setStrategy method of the LoadBalancer class is used to set/modify the required concrete class at runtime.
 */

import java.util.*;
class StrategyPattern {
    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer(100);
        lb.setStrategy(new RoundRobinStrategy());

        lb.assignTask(1);
        lb.assignTask(2);
        lb.assignTask(3);
        lb.assignTask(4);

        lb.setStrategy(new RandomStrategy());

        lb.assignTask(5);
        lb.assignTask(6);
        lb.assignTask(7);
        lb.assignTask(8);
    }
}

class LoadBalancer {
    private Strategy strategy;
    public List<List<Integer>> instances;

    public LoadBalancer(int numInstances) {
        instances = new ArrayList<>();
        for(int i=0; i<numInstances; i++) {
            instances.add(new ArrayList<>());
        }
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void assignTask(Integer task) {
        strategy.assignTask(this, task);
    }
}

interface Strategy {
    public void assignTask(LoadBalancer lb, Integer task);
}

class RoundRobinStrategy implements Strategy {
    int index = 0;
    @Override
    public void assignTask(LoadBalancer lb, Integer task) {
        lb.instances.get(index).add(task);
        System.out.println("Task " + task + " assigned to instance " + index + " using round robin strategy");
        index = (index + 1)%lb.instances.size();
    }
}

class RandomStrategy implements Strategy {
    Random random = new Random();
    @Override
    public void assignTask(LoadBalancer lb, Integer task) {
        int index = random.nextInt(lb.instances.size());
        System.out.println("Task " + task + " assigned to instance " + index + " using round robin strategy");
    }
}