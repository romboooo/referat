import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueExampleClass {
    void go(){
        ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
        /*
        разыграем следующую сценку - пусть у нас есть два потока. один кладёт элементы в очередь, второй достаёт из очереди.
        producer мечтает положить туда 10 элементов, хотя капасити очереди всего 5. поток будет блокироваться до
        момента, пока в очереди не окажется место.
        поток consumer же будет блокироваться Если очередь пуста.
         */
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    arrayBlockingQueue.put(i);
                    System.out.println("засунул: " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Integer item = arrayBlockingQueue.take();
                    System.out.println("достал: " + item);
                    Thread.sleep(200); // Имитация задержки потребления
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
