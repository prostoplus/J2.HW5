public class MyThread extends Thread {

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 10; i++) {

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }

            System.out.println(i);
        }

    }
}