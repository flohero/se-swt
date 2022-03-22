package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void testJdbc() {

        try (AbstractApplicationContext factory =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

            printTitle("create schema", 60, '-');
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

            //
            // get reference to implementation of EmployeeDao
            //
            EmployeeDao employeeDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

            printTitle("insert employee", 60, '-');
            Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
            employeeDao.insert(empl1);
            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

//            printTitle("update employee", 60, '-');
//            empl1.setFirstName("Jaquira");
//            empl1 = employeeDao.merge(empl1);
//            System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

            printTitle("find employee", 60, '-');
            Optional<Employee> empl = employeeDao.findById(1L);
            System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
            empl = employeeDao.findById(100L);
            System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));

            printTitle("find all employees", 60, '-');
            employeeDao.findAll().forEach(System.out::println);
        }
    }

    @SuppressWarnings("unused")
    private static void testJpa() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var emfactory = factory.getBean(EntityManagerFactory.class);
            EmployeeDao employeeDao = factory.getBean("employeeDaoJpa", EmployeeDao.class);

            //JpaUtil.beginTransaction(emfactory);

            printTitle("insert employee", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                employeeDao.insert(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));
            });
/*            JpaUtil.executeInTransaction(emfactory, () -> {

                printTitle("update employee", 60, '-');
                empl1.setFirstName("Jaquira");
                empl1 = employeeDao.merge(empl1);
                System.out.println("empl1 = " + (empl1 == null ? (null) : empl1.toString()));

            });*/
            printTitle("find employee", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                Optional<Employee> empl = employeeDao.findById(1L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
                empl = employeeDao.findById(100L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));

            });

            printTitle("find all employees", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                employeeDao.findAll().forEach(System.out::println);
            });

            //JpaUtil.commitTransaction(emfactory);
        }
    }

    @SuppressWarnings("unused")
    private static void testSpringData() {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

            var emfactory = factory.getBean(EntityManagerFactory.class);


            printTitle("insert employee", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                var employeeRepo = JpaUtil.getJpaRepository(emfactory, EmployeeRepository.class);

                Employee empl1 = new Employee("Josefine", "Feichtlbauer", LocalDate.of(1970, 10, 26));
                Employee empl2 = new Employee("Josefine", "Mandlbauer", LocalDate.of(1970, 10, 26));
                employeeRepo.saveAndFlush(empl1);
                employeeRepo.saveAndFlush(empl2);
                System.out.println("empl1 = " + empl1);
            });

            printTitle("find employee", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                var employeeRepo = JpaUtil.getJpaRepository(emfactory, EmployeeRepository.class);

                Optional<Employee> empl = employeeRepo.findById(1L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
                empl = employeeRepo.findById(100L);
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
            });
            printTitle("find all employees", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                var employeeRepo = JpaUtil.getJpaRepository(emfactory, EmployeeRepository.class);
                employeeRepo.findAll().forEach(System.out::println);
            });

            printTitle("find employee by lastname", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                var employeeRepo = JpaUtil.getJpaRepository(emfactory, EmployeeRepository.class);

                Optional<Employee> empl = employeeRepo.findByLastName("Feichtlbauer");
                System.out.println("empl=" + (empl.isPresent() ? empl.get() : "<not-found>"));
            });

            printTitle("find all employees with lastname", 60, '-');
            JpaUtil.executeInTransaction(emfactory, () -> {
                var employeeRepo = JpaUtil.getJpaRepository(emfactory, EmployeeRepository.class);
                employeeRepo.findByLastNameContaining("bauer").forEach(System.out::println);
            });
        }
    }

    public static void main(String[] args) {

        /*printSeparator(60);
        printTitle("testJDBC", 60);
        printSeparator(60);
        testJdbc();*/

        /*printSeparator(60);
        printTitle("testJpa", 60);
        printSeparator(60);
        testJpa();*/

        printSeparator(60);
        printTitle("testSpringData", 60);
        printSeparator(60);
        testSpringData();
    }
}
