package com.github.di;

import com.github.di.controllers.ConstructorInjectedController;
import com.github.di.controllers.MyController;
import com.github.di.controllers.PropertyInjectedController;
import com.github.di.controllers.SetterInjectedController;
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

        // No qualifying bean of type 'com.github.di.controllers.PropertyInjectedController' available
        // means that constroller isn't annotated as Bean
        // that's why we should use @Controller annotation
        System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
        System.out.println(ctx.getBean(SetterInjectedController.class).sayHello());
        System.out.println(ctx.getBean(ConstructorInjectedController.class).sayHello());
	}
}
