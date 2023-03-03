package by.itstep.stpnbelko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItStepFinalAppApplication {

    public static void main(String[] args) {
//        Helper deamon = new Helper();
//        deamon.start();

        SpringApplication.run(ItStepFinalAppApplication.class, args);
    }

}


//class Helper extends Thread {
//
//    public Helper() {
//        this.setDaemon(true);
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            System.out.println("job");
//        }
//
//    }
//}
