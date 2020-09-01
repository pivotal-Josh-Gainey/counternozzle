package com.jgainey.counternozzle.controllers;


import com.jgainey.counternozzle.objects.Nozzle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

    Runtime runtime = Runtime.getRuntime();

    @ResponseBody
    @GetMapping("read")
    public ResponseEntity<Long> getTotalConsumed(){
        long totalConsumed = Nozzle.getInstance().getTotalConsumed();
        return new ResponseEntity<>(totalConsumed, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("reset")
    public ResponseEntity<String> resetTotal() {
        Nozzle.getInstance().setTotalConsumed(0);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("memory")
    public ResponseEntity<String> memory() {
        return new ResponseEntity<>("Free Memory=" + runtime.freeMemory(), HttpStatus.OK);
    }

}
