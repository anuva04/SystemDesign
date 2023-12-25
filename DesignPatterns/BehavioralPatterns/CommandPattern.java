/**
 * This pattern is used when we want to include all data in an object required to perform an action.
 * Hence, the executor need not know the details of how to perform the action. It can simply call the execute method of the object.
 * In this example, multiple ArithmeticOperation objects are created and each of them contains all details required to perform the action.
 * The executor simply picks up an object and execute it using the operate() method.
 * This is useful while building an architecture of message queues with multiple worker threads which can pick up tasks when possible and execute them without needing to know how to perform it.
 */

import java.util.concurrent.*;
class CommandPattern {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(3);

        BlockingQueue<ArithmeticOperation> queue = new LinkedBlockingQueue<>();
        queue.offer(new AdditionOperation(1, 2, 5));
        queue.offer(new AdditionOperation(2, 3, 6));
        queue.offer(new SubtractionOperation(3, 4, 1));
        queue.offer(new MultiplicationOperation(4, 4, 4));
        queue.offer(new MultiplicationOperation(5, 2, 2));
        queue.offer(new DivisionOperation(6, 6, 3));

        for(int i = 0; i < 3; i++) {
            ex.submit(new ArithmeticOperationExecutor(queue));
        }

        ex.shutdown();
    }
}

abstract class ArithmeticOperation {
    protected int operand1;
    protected int operand2;
    protected int timeTaken;
    protected int id;

    public ArithmeticOperation(int id, int operand1, int operand2, int timeTaken) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.timeTaken = timeTaken;
        this.id = id;
    }

    abstract void operate();
}

class AdditionOperation extends ArithmeticOperation {
    public AdditionOperation(int id, int operand1, int operand2) {
        super(id, operand1, operand2, 2);
    }

    @Override
    public void operate() {
        try {
            System.out.println("#" + this.id + " Sleeping for " + this.timeTaken + " seconds...");
            Thread.sleep(this.timeTaken);
            System.out.println("#" + this.id + " " + (this.operand1 + this.operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("#" + this.id + " " + Integer.MAX_VALUE);
        }
    }
}

class SubtractionOperation extends ArithmeticOperation {
    public SubtractionOperation(int id, int operand1, int operand2) {
        super(id, operand1, operand2, 3);
    }

    @Override
    public void operate() {
        try {
            System.out.println("#" + this.id + " Sleeping for " + this.timeTaken + " seconds...");
            Thread.sleep(this.timeTaken);
            System.out.println("#" + this.id + " " + (this.operand1 - this.operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("#" + this.id + " " + Integer.MAX_VALUE);
        }
    }
}

class MultiplicationOperation extends ArithmeticOperation {
    public MultiplicationOperation(int id, int operand1, int operand2) {
        super(id, operand1, operand2, 4);
    }

    @Override
    public void operate() {
        try {
            System.out.println("#" + this.id + " Sleeping for " + this.timeTaken + " seconds...");
            Thread.sleep(this.timeTaken);
            System.out.println("#" + this.id + " " + (this.operand1 * this.operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("#" + this.id + " " + Integer.MAX_VALUE);
        }
    }
}

class DivisionOperation extends ArithmeticOperation {
    public DivisionOperation(int id, int operand1, int operand2) {
        super(id, operand1, operand2, 5);
    }

    @Override
    public void operate() {
        try {
            System.out.println("#" + this.id + " Sleeping for " + this.timeTaken + " seconds...");
            Thread.sleep(this.timeTaken);
            System.out.println("#" + this.id + " " + (this.operand1 / this.operand2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("#" + this.id + " " + Integer.MAX_VALUE);
        }
    }
}

class ArithmeticOperationExecutor implements Runnable {
    private final BlockingQueue<ArithmeticOperation> queue;

    public ArithmeticOperationExecutor(BlockingQueue<ArithmeticOperation> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                ArithmeticOperation operation = queue.poll();
                if(operation == null) break;
                operation.operate();
            } catch(Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}