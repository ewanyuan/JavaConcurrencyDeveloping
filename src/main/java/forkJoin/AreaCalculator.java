package forkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计算给定函数 y=1/x 在定义域 [1,100]上围城与X轴围成的面积，计算步长0.01
 */
public class AreaCalculator {

    public static class AreaComputingTask extends RecursiveTask<Float> {

        //步长
        public static final float TinyPart = 0.01f;
        public static final int Num = 10;
        public static AtomicInteger counter = new AtomicInteger(0);
        private float startX = 0.0f;
        private float endX = 0.0f;


        public AreaComputingTask(float startX, float endX) {
            this.startX = startX;
            this.endX = endX;
        }

        @Override
        protected Float compute() {

            float sum = 0.0f;

            boolean canCompute = (endX - startX)<= TinyPart;

            if (canCompute) {
                sum = calc(startX, endX);
                counter.incrementAndGet();
                System.out.println(counter + "--> calc result between " + (startX) + " -- " + (endX));
            }
            else {
                float step = (endX - startX)/ Num;

                for (int i = 0; i< Num; i++) {
                    AreaComputingTask task = new AreaComputingTask(startX + i*step, startX + (i+1)*step);
                    task.fork();

                    sum += task.join();
                }
            }

            return sum;
        }

        private float calc(float startXLoc, float endXLoc) {
            float rectArea = 1/(endXLoc) * TinyPart;
            float triangleArea = 0.5f * (1/startXLoc - 1/endXLoc)*0.01f;

            return rectArea + triangleArea;
        }
    }

    public static Float Calculate(int startX, int endX) throws ExecutionException, InterruptedException {
        AreaComputingTask nodeA1 = new AreaComputingTask(startX, endX);
        ForkJoinPool pool = new ForkJoinPool();

        Future<Float> result = pool.submit(nodeA1);

            return result.get();
    }
}  