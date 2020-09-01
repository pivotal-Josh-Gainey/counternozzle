package com.jgainey.counternozzle.objects;


import com.jgainey.counternozzle.configs.CFProperties;
import com.jgainey.counternozzle.configs.Configuration;
import com.jgainey.counternozzle.utils.Utils;
import org.cloudfoundry.doppler.Envelope;
import org.cloudfoundry.doppler.FirehoseRequest;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.core.publisher.Flux;


public class Nozzle {

    //private singleton instance of nozzle to ensure only one per application.
    private static Nozzle nozzle;

    //variables to track nozzle statistics/settings
    private long totalConsumed = 0;

    //classwide for multi method access.
    private Flux<Envelope> stream;

    private Nozzle(){
        initSelf();
    }

    public static Nozzle getInstance(){
        if(nozzle == null){
            synchronized (Nozzle.class){
                nozzle =  new Nozzle();
            }
        }
        return nozzle;
    }

    private void initSelf() {
        initStream();
        Utils.logInfo("Stream obtained, starting up consumer.");
        startConsuming();
        Utils.logInfo("Nozzle Initialization is complete and now consuming.");
    }

    private void initStream() {
        Utils.logInfo("Initializing Nozzle");
        //cf auth and access
        CFProperties props = new CFProperties();
        ApplicationContext context = new AnnotationConfigApplicationContext(Configuration.class);
        DefaultCloudFoundryOperations ops = (DefaultCloudFoundryOperations) context.getAutowireCapableBeanFactory().getBean("CFOperations");
        Utils.logInfo("Successfully obtained CF credentials and access...requesting firehose stream.");
        stream = ops.getDopplerClient().firehose(
                FirehoseRequest
                        .builder()
                        .subscriptionId(props.getSubscriptionID()).build())
                .doOnNext(envelope -> totalConsumed++)
                .doOnTerminate(() -> {
                    Utils.logError("Unexpected error, reconnecting...");
                    startConsuming();
                })
                .onBackpressureDrop(envelope -> Utils.logWarning("Backpressure hit!"));
    }

    private void startConsuming(){
        stream.subscribe();
    }

    public long getTotalConsumed() {
        return totalConsumed;
    }

    public void setTotalConsumed(long totalConsumed) {
        this.totalConsumed = totalConsumed;
    }
}
