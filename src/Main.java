import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static float[] createArr(int size) {

        // 1. Создают одномерный длинный массив, например:
        float[] arr = new float[size];

        // 2. Заполняют этот массив единицами.
        /*for (int i = 0; i < arr.length; i++) {
            arr[0] = 1;
        }*/
        Arrays.fill(arr, 1.0f);

        return arr;
    }

    static long reSetArr(float[] arr) {

        // 3) Засекают время выполнения:

        long a = System.currentTimeMillis();

        // 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        /*
        5) Проверяется время окончания метода System.currentTimeMillis().
        6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a).*/
        return (System.currentTimeMillis() - a);
    }


    /**
     * reSetArr в n-потоков
     *
     * @param arr          исходный массив
     * @param threadsCount кол-во потоков
     * @return
     */
    static long reSetArrInMultiThread(float[] arr, int threadsCount) {

        int countElementForThread = (arr.length / threadsCount);

        long a = System.currentTimeMillis();
        float[][] m = new float[threadsCount][countElementForThread];
        Thread[] t = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            System.arraycopy(arr, countElementForThread * i, m[i], 0, countElementForThread);
            final int u = i; //обычную переменную нельзя передать в поток
            t[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0, z = u * countElementForThread; j < countElementForThread; j++, z++) {
                        m[u][j] = (float) (m[u][j] * Math.sin(0.2f + z / 5) * Math.cos(0.2f + z / 5) * Math.cos(0.4f + z / 2));
                    }
                }
            });

            t[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < threadsCount; i++) {
            System.arraycopy(m[i], 0, arr, i * countElementForThread, countElementForThread);
        }

        return (System.currentTimeMillis() - a);
    }


    public static void main(String[] args) {

        float[] arr = createArr(10_000_000);
        System.out.println(reSetArr(arr));

        /* Отличие первого метода от второго:
            ● Первый просто бежит по массиву и вычисляет значения.
            ● Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и
            потом склеивает эти массивы обратно в один. */

        float[] arrNew = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew, 2));

        float[] arrTmpNew = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrTmpNew, 4));

        float[] arrNew1 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew1, 6));

        float[] arrNew2 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew2, 8));

        float[] arrNew3 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew3, 12));

        float[] arrNew4 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew4, 32));

        float[] arrNew5 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew5, 64));

        float[] arrNew6 = createArr(10_000_000);
        System.out.println(reSetArrInMultiThread(arrNew6, 128));
    }
}