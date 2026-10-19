package com.revision.springboot2026.practice;

import java.lang.ref.WeakReference;

/** Small, safe-to-run demo of object reachability and JVM garbage collection. */
public final class GarbageCollectionDemo {
    private GarbageCollectionDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();

        printHeap("start", runtime);

        // This local strong reference keeps the byte array reachable, so GC must not reclaim it.
        byte[] data = new byte[8 * 1024 * 1024];
        WeakReference<byte[]> observer = new WeakReference<>(data);
        System.out.println("While strong reference exists: object reachable = "
                + (observer.get() != null));
        printHeap("after 8 MiB allocation", runtime);

        // Removing the only strong reference makes the array eligible, but does not force collection.
        data = null;
        System.out.println("After strong reference is removed: weak reference still sees object = "
                + (observer.get() != null));

        // System.gc() is only a request. The bounded loop makes the demo finish even if ignored.
        System.out.println("Requesting GC (the JVM may ignore this request)...");
        System.gc();
        long deadline = System.nanoTime() + 2_000_000_000L;
        while (observer.get() != null && System.nanoTime() < deadline) {
            Thread.sleep(50);
        }
        System.out.println("Weak reference cleared within 2 seconds = " + (observer.get() == null));
        printHeap("after GC request", runtime);

        // Short-lived arrays create allocation activity that can trigger young-generation collections.
        createAllocationChurn();
        printHeap("after short-lived allocation churn", runtime);

        System.out.println("Done. Use -Xlog:gc* to see collector events and the collector's own terminology.");
    }

    private static void createAllocationChurn() {
        // Keep the total modest; each array becomes unreachable at the end of its loop iteration.
        for (int i = 0; i < 32; i++) {
            byte[] shortLived = new byte[512 * 1024];
            shortLived[0] = (byte) i; // Touch the array so the allocation is observable to the JVM.
        }
    }

    private static void printHeap(String phase, Runtime runtime) {
        long usedBytes = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("Heap snapshot (%s): used=%d MiB, committed=%d MiB, max=%d MiB%n",
                phase,
                toMiB(usedBytes),
                toMiB(runtime.totalMemory()),
                toMiB(runtime.maxMemory()));
    }

    private static long toMiB(long bytes) {
        return bytes / (1024 * 1024);
    }
}
