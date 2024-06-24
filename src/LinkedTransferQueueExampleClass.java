import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueExampleClass {
    void go(){
        LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();
        /*
        два потока используют очередь, поток продюссер блокируется, пока consumer не заберет элемент
         */
        Thread producer = new Thread(() -> {
            try {
                String[] items = {"элемент 1", "элемент 2", "элемент 3"};
                for (String item : items) {
                    linkedTransferQueue.transfer(item);
                    System.out.println("засунул: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Поток потребитель
        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    String item = linkedTransferQueue.take();
                    System.out.println("достал " + item);
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
            // Потребитель будет завершен через некоторое время после производителя, для демонстрации остановим его
            Thread.sleep(5000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
