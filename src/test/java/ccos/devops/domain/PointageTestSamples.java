package ccos.devops.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PointageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pointage getPointageSample1() {
        return new Pointage().id(1L).heureArrive("heureArrive1").heureDepart("heureDepart1");
    }

    public static Pointage getPointageSample2() {
        return new Pointage().id(2L).heureArrive("heureArrive2").heureDepart("heureDepart2");
    }

    public static Pointage getPointageRandomSampleGenerator() {
        return new Pointage()
            .id(longCount.incrementAndGet())
            .heureArrive(UUID.randomUUID().toString())
            .heureDepart(UUID.randomUUID().toString());
    }
}
