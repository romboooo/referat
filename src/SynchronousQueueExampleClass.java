import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExampleClass {
    void go(){
        /*
        два потока - producer и consumer, продюссер кладёт элементы в очередь и блокируется пока consumer не
        заберет их.
         */

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        Thread producer = new Thread(() -> {
            try {
                String[] items = {"элемент 1", "элемент 2", "элемент 3"};
                for (String item : items) {
                    synchronousQueue.put(item);
                    System.out.println("положил: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String item = synchronousQueue.take();
                    System.out.println("достал: " + item);
                    Thread.sleep(1500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            Thread.sleep(5000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
