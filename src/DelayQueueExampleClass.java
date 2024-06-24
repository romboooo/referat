import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExampleClass {


    /*
    есть два потока, один кладёт элементы в очередь, другой забирает их.
    поток consumer берет элементы не сразу, а с определённой задержкой
     */
    class DelayObject implements Delayed {
        private final long expireTime;
        private final String name;
        private final long delay;

        public DelayObject(String name, long delay, TimeUnit unit) {
            this.name = name;
            this.delay = TimeUnit.MILLISECONDS.convert(delay, unit);
            this.expireTime = System.currentTimeMillis() + this.delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long remainingTime = expireTime - System.currentTimeMillis();
            return unit.convert(remainingTime, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.expireTime < ((DelayObject) o).expireTime) {
                return -1;
            }
            if (this.expireTime > ((DelayObject) o).expireTime) {
                return 1;
            }
            return 0;
        }

        public String getName() {
            return name;
        }
    }

    void go() {
        DelayQueue<DelayObject> delayQueue = new DelayQueue<>();

        Thread producer = new Thread(() -> {
            try {
                System.out.println("положил 1");
                delayQueue.offer(new DelayObject("1 элемент", 1, TimeUnit.SECONDS));
                System.out.println("положил 2");
                delayQueue.offer(new DelayObject("2 элемент", 3, TimeUnit.SECONDS));
                System.out.println("положил 3");
                delayQueue.offer(new DelayObject("3 элемент", 4, TimeUnit.SECONDS));
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    DelayObject element = delayQueue.take();
                    System.out.println("достал: " + element.getName());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        try {
            producer.join();
            // Задержка для завершения потребителя через некоторое время после завершения производителя
            Thread.sleep(10000);
            consumer.interrupt();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
