package swt6.spring;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CliApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CliApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
