# counternozzle

// to reset all counters in the nozzles.
```java
for i in {0..<NUMBER INSTANCES OF COUNTERNOZZLE - 1>}
do
curl <COUNTERNOZZLE.APPS-DOMAIN>/reset -H "X-Cf-App-Instance":"<CounterNozzle GUID>:$i"
done
```

Example:
```java
for i in {0..2}
do
curl counternozzle-sleepy-chipmunk.apps.joshbot.pas/reset -H "X-Cf-App-Instance":"afa78ee5-b952-4d7d-a553-fc48fe470245:$i"
done
```

//To sum all counternozzle counters
```java
export CURRENT_COUNTERNOZZLE_TOTAL=0
for i in {0..<NUMBER INSTANCES OF COUNTERNOZZLE - 1>}
do
INSTANCE_TOTAL=`curl -s COUNTERNOZZLE.APPS-DOMAIN/read -H "X-Cf-App-Instance":"<CounterNozzle GUID>:$i"`
CURRENT_COUNTERNOZZLE_TOTAL=`echo "${INSTANCE_TOTAL} + $CURRENT_COUNTERNOZZLE_TOTAL" | bc`
done
echo $CURRENT_COUNTERNOZZLE_TOTAL
```

Example:
```java
export CURRENT_COUNTERNOZZLE_TOTAL=0
for i in {0..2}
do
INSTANCE_TOTAL=`curl -s counternozzle-sleepy-chipmunk.apps.joshbot.pas/read -H "X-Cf-App-Instance":"afa78ee5-b952-4d7d-a553-fc48fe470245:$i"`
CURRENT_COUNTERNOZZLE_TOTAL=`echo "${INSTANCE_TOTAL} + $CURRENT_COUNTERNOZZLE_TOTAL" | bc`
done
echo $CURRENT_COUNTERNOZZLE_TOTAL
```
