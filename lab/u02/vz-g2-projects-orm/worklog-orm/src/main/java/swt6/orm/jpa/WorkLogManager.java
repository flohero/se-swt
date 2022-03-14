package swt6.orm.jpa;

import swt6.orm.domain.*;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class WorkLogManager {

    private static <T> void insertEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.persist(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
        }
    }

    private static <T> T saveEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            entity = em.merge(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
        }
        return entity;
    }

    private static Employee addLogbookEntries(Employee employee, LogbookEntry... entries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            for (LogbookEntry entry : entries) {
                employee.addLogbookEntry(entry);
            }
            employee = em.merge(employee);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return employee;
    }

    private static void listEmployees() {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            var employeeList = em.createQuery("select e from Employee e", Employee.class).getResultList();
            employeeList.forEach(employee -> {
                System.out.println(employee);
                if (employee.getLogbookEntries().size() > 0) {
                    System.out.println("\tEntries");
                    employee.getLogbookEntries().forEach(entry -> System.out.println("\t" + entry));
                }
            });
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    private static void testFetchingStrategies() {
        // prepare
        Long entryId;
        Long employeeId;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            Optional<LogbookEntry> entry = em.createQuery("select le from LogbookEntry le", LogbookEntry.class)
                    .setMaxResults(1)
                    .getResultList()
                    .stream()
                    .findAny();

            if (entry.isEmpty()) {
                return;
            }
            entryId = entry.get().getId();
            Optional<Employee> employee =
                    em.createQuery("select e from Employee e", Employee.class)
                            .setMaxResults(1)
                            .getResultList()
                            .stream()
                            .findAny();
            if (employee.isEmpty()) {
                return;
            }
            employeeId = employee.get().getId();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        // LogbookEntry -> Employee
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("###> Fetching LogbookEntry ...");
            var entry = em.find(LogbookEntry.class, entryId);
            System.out.println("###> Fetched LogbookEntry ...");

            System.out.println("###> Fetching associated Employee ...");
            Employee emp = entry.getEmployee();
            System.out.println("###> Fetched associated Employee ...");

            System.out.println(emp);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        // Employee -> LogbookEntry
        System.out.println("################## Employee -> LogbookEntry ##################");
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            System.out.println("###> Fetching Employee ...");
            Employee emp = em.find(Employee.class, employeeId);
            System.out.println("###> Fetched Employee ...");

            emp.getLogbookEntries().forEach(System.out::println);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    public static void main(String[] args) {
        PermanentEmployee pe1 = new PermanentEmployee("flo", "flo", LocalDate.now());
        pe1.setAddress(new Address("4232", "Hagenberg", "Softwarepark 45"));
        pe1.setSalary(3000);

        TemporaryEmployee te1 = new TemporaryEmployee("flo2", "flo2", LocalDate.now());
        te1.setAddress(new Address("4232", "Hagenberg", "Softwarepark 32"));
        te1.setRenter("Microsoft");
        te1.setStartDate(LocalDate.of(2000, 1, 1));
        te1.setEndDate(LocalDate.of(2001, 1, 1));

        Employee emp1 = pe1;
        Employee emp2 = te1;
        LogbookEntry entry1 = new LogbookEntry(
                "run",
                LocalDateTime.of(2000, 1, 1, 10, 0),
                LocalDateTime.of(2000, 1, 1, 11, 0)
        );
        LogbookEntry entry2 = new LogbookEntry(
                "weights",
                LocalDateTime.of(2000, 1, 1, 12, 0),
                LocalDateTime.of(2000, 1, 1, 13, 0)
        );
        LogbookEntry entry3 = new LogbookEntry(
                "eat",
                LocalDateTime.of(2000, 1, 1, 13, 0),
                LocalDateTime.of(2000, 1, 1, 14, 0)
        );
        System.out.println("==== Create Schema ====");
        JpaUtil.getEntityManagerFactory();
        System.out.println("========================");
        try {
            System.out.println("=== Insert Employees ===");
            insertEntity(emp1);
            insertEntity(emp2);
            System.out.println("==== Insert Entries ====");
            addLogbookEntries(emp1, entry1, entry2, entry3);
            listEmployees();

            System.out.println("=== Test Fetching Strategies ===");
            testFetchingStrategies();
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }

    }
}
