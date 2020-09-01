package com.jgainey.counternozzle;

import com.jgainey.counternozzle.objects.Nozzle;
import com.jgainey.counternozzle.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CounternozzleApplication {

    public static void main(String[] args) {
        Utils.initLogger();
        Nozzle.getInstance();
        SpringApplication.run(CounternozzleApplication.class, args);
    }

}
