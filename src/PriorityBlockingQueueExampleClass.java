import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueExampleClass {
    void go(){

         class Task implements Comparable<Task> {
            private final int priority;
            private final String name;

            public Task(int priority, String name) {
                this.priority = priority;
                this.name = name;
            }
            public int getPriority() {
                return priority;
            }
            public String getName() {
                return name;
            }
            @Override
            public int compareTo(Task o) {
                // Чем меньше значение, тем выше приоритет
                return Integer.compare(this.priority, o.priority);
            }
            @Override
            public String toString() {
                return "Task{" +
                        "priority=" + priority +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        PriorityBlockingQueue<Task> priorityBlockingQueue = new PriorityBlockingQueue();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int priority = ThreadLocalRandom.current().nextInt(1, 10);
                    Task task = new Task(priority, "Task " + i);
                    System.out.println("producer засунул: " + task);
                    priorityBlockingQueue.put(task);
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    Task task = priorityBlockingQueue.take();
                    System.out.println("сonsumer достал: " + task);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            TimeUnit.SECONDS.sleep(15);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
