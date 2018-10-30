package com.github.di;

import com.github.di.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
	    // when we start the app, we load the context
        ApplicationContext ctx = SpringApplication.run(DiApplication.class, args);

        // and ask context (which stores references to beans) to get bean named myController
        MyController controller = (MyController) ctx.getBean("myController");

        // we can now call method on bean
        controller.hello();
	}
}
