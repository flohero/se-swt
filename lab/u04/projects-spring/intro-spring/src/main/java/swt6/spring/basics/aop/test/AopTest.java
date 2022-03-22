package swt6.spring.basics.aop.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogService;
import swt6.util.PrintUtil;

public class AopTest {

    private static void testAOP(String configFileName) {
        try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(configFileName)) {
            WorkLogService workLogService = factory.getBean(WorkLogService.class);

            System.out.printf("workLogService %s\n", workLogService.getClass().getName());

            try {
                workLogService.findAllEmployees();
                workLogService.findEmployeeById(1L);
                workLogService.findEmployeeById(99L);
            } catch (EmployeeIdNotFoundException e) {
                System.out.println("Exception EmployeeIdNotFoundException");
            }
        }
    }

    public static void main(String[] args) {
        PrintUtil.printTitle("testAOP (config based)", 60);
        testAOP("swt6/spring/basics/aop/test/applicationContext-xml-config.xml");
        PrintUtil.printSeparator(60);

        PrintUtil.printTitle("testAOP (annotation based)", 60);
        testAOP("swt6/spring/basics/aop/test/applicationContext-annotation-config.xml");
        PrintUtil.printSeparator(60);
    }

}
