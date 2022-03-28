package com.example;

import com.example.dal.DogDao;
import com.example.domain.Dog;
import com.example.services.DogService;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {

    private static void createSchema(DataSource ds, String ddlScript) {
        try {
            DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
            InputStream is = Main.class.getClassLoader().getResourceAsStream(ddlScript);
            if (is == null)
                throw new IllegalArgumentException(String.format("File %s not found in classpath.", ddlScript));
            scriptRunner.runScript(new InputStreamReader(is));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext("com/example/AppConfig.xml")) {
            createSchema(factory.getBean("dataSource", DataSource.class),
                    "com/example/table.sql");
            DogDao dogDao = factory.getBean(DogDao.class);
            DogService dogService = factory.getBean(DogService.class);
            //jdbcTemplateTest(dogDao);
            jpaTest(dogService);
        }
    }

    private static void jpaTest(DogService dogService) {
        Dog dog = new Dog("Franz");
        dogService.create(dog);
        dogService.findAll().forEach(System.out::println);
    }

    private static void jdbcTemplateTest(DogDao dogDao) {
        dogDao.findById(1L).ifPresentOrElse(System.out::println, () -> System.out.println("EMPTY!"));
        Dog dog = new Dog("Franz");
        dogDao.insert(dog);
        dogDao.findById(1L).ifPresentOrElse(System.out::println, () -> System.out.println("EMPTY!"));
        dogDao.findAll().forEach(System.out::println);
    }
}
