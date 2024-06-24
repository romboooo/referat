import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExampleClass {
    void go(){
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                String item = "Item " + i;
                queue.offer(item);
                System.out.println("засунул " + item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                String item = queue.poll();
                if (item != null) {
                    System.out.println("достал " + item);
                } else {
                    // Если очередь пуста, ждём некоторое время
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
