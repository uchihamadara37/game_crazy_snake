package org.siegan.dojo;

import java.io.*;

import java.io.IOException;

public class Utomo {
    private static class Arahes{
        public Arah arah;
    }

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        final Arahes arahes = new Arahes();
        arahes.arah = Arah.RIGHT;

        System.out.println("Initial arah : "+arahes.arah);

        Thread thread2 = new Thread() {

            @Override
            public void run() {
//                while(true) {
//                    try {
//                        int input = System.in.read();
//                        if (input != -1) { // Periksa apakah ada input
//                            if (input == '\n') { // Jika input adalah newline, baca karakter berikutnya
//                                input = System.in.read();
//                            }
//                        }
//                        // Periksa input dan lakukan tindakan yang sesuai
//                        synchronized (lock) {
//                            System.out.println("input e " + input);
//                            switch (input) {
//                                case 'w':
//                                    System.out.println("atas pkpk");
//                                    arahes.arah = Arah.UP;
//                                    break;
//                                case 'a':
//                                    System.out.println("kiri pkpk");
//                                    arahes.arah = Arah.LEFT;
//                                    break;
//                                case 's':
//                                    System.out.println("bawah pkpk");
//                                    arahes.arah = Arah.DOWN;
//                                    break;
//                                case 'd':
//                                    System.out.println("kanan pkpk");
//                                    arahes.arah = Arah.RIGHT;
//                                    break;
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                for(;;) {
                    try {
                        int data = System.in.read();
                        if (data == 27) { // esc key
                            break;
                        }
                        System.out.print(data);
                    } catch (IOException e) {
                    }
                }
            }
        };

        thread2.start();

        synchronized (lock) {
//            while (true) {
                System.out.println("yonngalah : " + arahes.arah.toString());
                Thread.sleep(1000);
//            }
        }


//        thread2.join(); // waiting for thread to finish

//        System.out.println("Result arah : " + arahes.arah);

    }

}
