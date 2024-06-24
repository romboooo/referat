public class Main {
    public static void main(String[] args){
        ArrayBlockingQueueExampleClass arrayBlockingQueueExampleClass = new ArrayBlockingQueueExampleClass();
        ConcurrentLinkedQueueExampleClass concurrentLinkedQueueExampleClass = new ConcurrentLinkedQueueExampleClass();
        DelayQueueExampleClass delayQueueExampleClass = new DelayQueueExampleClass();
        LinkedBlockingQueueExampleClass linkedBlockingQueueExampleClass = new LinkedBlockingQueueExampleClass();
        LinkedTransferQueueExampleClass linkedTransferQueueExampleClass = new LinkedTransferQueueExampleClass();
        PriorityBlockingQueueExampleClass priorityBlockingQueueExampleClass = new PriorityBlockingQueueExampleClass();
        SynchronousQueueExampleClass synchronousQueueExampleClass = new SynchronousQueueExampleClass();


        arrayBlockingQueueExampleClass.go();
        concurrentLinkedQueueExampleClass.go();
        delayQueueExampleClass.go();
        linkedBlockingQueueExampleClass.go();
        linkedTransferQueueExampleClass.go();
        priorityBlockingQueueExampleClass.go();
        synchronousQueueExampleClass.go();

    }
}
