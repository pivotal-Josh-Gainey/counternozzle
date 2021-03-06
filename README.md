# counternozzle

//create a uaa user for this nozzle
```bash
uaac target --skip-ssl-validation uaa.<systemFQDN>
uaac token client get admin -s <admin-client-secret>
uaac user add CounterNozzle \
    --password ,<password> \
    --emails CounterNozzle@nozzle.com \
    && uaac member add doppler.firehose CounterNozzle
```

// to reset all counters in the nozzles.
```bash
for i in {0..<NUMBER INSTANCES OF COUNTERNOZZLE - 1>}
do
curl <COUNTERNOZZLE.APPS-DOMAIN>/reset -H "X-Cf-App-Instance":"<CounterNozzle GUID>:$i"
done
```

Example:
```bash
for i in {0..2}
do
curl counternozzle-sleepy-chipmunk.apps.joshbot.pas/reset -H "X-Cf-App-Instance":"afa78ee5-b952-4d7d-a553-fc48fe470245:$i"
done
```

//To sum all counternozzle counters
```bash
export CURRENT_COUNTERNOZZLE_TOTAL=0
for i in {0..<NUMBER INSTANCES OF COUNTERNOZZLE - 1>}
do
INSTANCE_TOTAL=`curl -s COUNTERNOZZLE.APPS-DOMAIN/read -H "X-Cf-App-Instance":"<CounterNozzle GUID>:$i"`
CURRENT_COUNTERNOZZLE_TOTAL=`echo "${INSTANCE_TOTAL} + $CURRENT_COUNTERNOZZLE_TOTAL" | bc`
done
echo $CURRENT_COUNTERNOZZLE_TOTAL
```

Example:
```bash
export CURRENT_COUNTERNOZZLE_TOTAL=0
for i in {0..2}
do
INSTANCE_TOTAL=`curl -s counternozzle-sleepy-chipmunk.apps.joshbot.pas/read -H "X-Cf-App-Instance":"afa78ee5-b952-4d7d-a553-fc48fe470245:$i"`
CURRENT_COUNTERNOZZLE_TOTAL=`echo "${INSTANCE_TOTAL} + $CURRENT_COUNTERNOZZLE_TOTAL" | bc`
done
echo $CURRENT_COUNTERNOZZLE_TOTAL
```
