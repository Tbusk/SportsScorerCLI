package edu.ferris.seng350.midtermproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
@EnableAspectJAutoProxy
public class MidtermProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidtermProjectApplication.class, args);
    }

}
