package ccos.devops.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Employee getEmployeeSample1() {
        return new Employee()
            .id(1L)
            .matricule("matricule1")
            .prenom("prenom1")
            .nom("nom1")
            .email("email1")
            .lieuNaissance("lieuNaissance1")
            .telephone("telephone1")
            .status("status1")
            .fonction("fonction1")
            .structure("structure1");
    }

    public static Employee getEmployeeSample2() {
        return new Employee()
            .id(2L)
            .matricule("matricule2")
            .prenom("prenom2")
            .nom("nom2")
            .email("email2")
            .lieuNaissance("lieuNaissance2")
            .telephone("telephone2")
            .status("status2")
            .fonction("fonction2")
            .structure("structure2");
    }

    public static Employee getEmployeeRandomSampleGenerator() {
        return new Employee()
            .id(longCount.incrementAndGet())
            .matricule(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .lieuNaissance(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .fonction(UUID.randomUUID().toString())
            .structure(UUID.randomUUID().toString());
    }
}
