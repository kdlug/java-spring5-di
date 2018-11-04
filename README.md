## SOLID principles of OOP

- proper dependency management leads to quality code that is easy to maintain

### Single responsibility principles

- Every vlass should have a single responsibility
- Classees should be small (not more than a screen full of code)
- Split classes into smaller classes

### Open closed principle

- Classes should be open for extentions
- but closed for modifications
- You should be able to extend a classes behavior without modyfing it
- Use private variables with getters and setters only when you need them
- Use abstract base classes11

### Liskov Substitution Principle

- Objects in a program would be replaceable with instances of their subtypes without altering the correctness of the program

### Interface segregation principle

- Make fine grained interfaces that are client specific
- Keep your components focused and minimize dependencies between them

### Dependency Inversion Principle

- Abstractions should not depend upon details
- Details should not depend upon abstractions
- This is not the same as Dependency Injection

### Open closed principle example

#### Code closed for extensions (BAD)

```java
public class HealthInsuranceSurveyor{
    public boolean isValidClaim(){
        System.out.println("HealthInsuranceSurveyor: Validating health insurance claim...");
        /*Logic to validate health insurance claims*/
        return true;
    }
}

public class ClaimApprovalManager {
    public void processHealthClaim (HealthInsuranceSurveyor surveyor)
    {
        if (surveyor.isValidClaim()){
            System.out.println("ClaimApprovalManager: Valid claim. Currently processing claim for approval....");
        }
    }
}
```

We now need to include a new VehicleInsuranceSurveyor class.

Modified ClaimApprovalManager.java

```java
public class ClaimApprovalManager {
    public void processHealthClaim (HealthInsuranceSurveyor surveyor)
    {
        if (surveyor.isValidClaim()){
            System.out.println("ClaimApprovalManager: Valid claim. Currently processing claim for approval....");
        }
    }
    public void processVehicleClaim (VehicleInsuranceSurveyor surveyor)
    {
        if (surveyor.isValidClaim()){
            System.out.println("ClaimApprovalManager: Valid claim. Currently processing claim for approval....");
        }
    }
}
```

#### Code open for extensions and closed for modifications (GOOD)

InsuranceSurveyor.java

```java
public abstract class InsuranceSurveyor {
    public abstract boolean isValidClaim();
}
```

HealthInsuranceSurveyor.java

```java
public class HealthInsuranceSurveyor extends InsuranceSurveyor{
    public boolean isValidClaim(){
        System.out.println("HealthInsuranceSurveyor: Validating health insurance claim...");
        /*Logic to validate health insurance claims*/
        return true;
    }
}
```

VehicleInsuranceSurveyor.java

```java
public class VehicleInsuranceSurveyor extends InsuranceSurveyor{
    public boolean isValidClaim(){
       System.out.println("VehicleInsuranceSurveyor: Validating vehicle insurance claim...");
        /*Logic to validate vehicle insurance claims*/
        return true;
    }
}
```

ClaimApprovalManager.java

```java
public class ClaimApprovalManager {
    public void processClaim(InsuranceSurveyor surveyor){
        if (surveyor.isValidClaim()){
            System.out.println("ClaimApprovalManager: Valid claim. Currently processing claim for approval....");
        }
    }
}
```

In the example above, we wrote a processClaim( ) method to accept a InsuranceSurveyor type instead of specifying a concrete type. In this way,any further addition of InsuranceSurveyor implementations will not affect the ClaimApprovalManager class.

To test our example, we can write unit test.

ClaimApprovalManagerTest.java

```java
public class ClaimApprovalManagerTest {
    @Test
    public void testProcessClaim() throws Exception {
      HealthInsuranceSurveyor healthInsuranceSurveyor=new HealthInsuranceSurveyor();
      ClaimApprovalManager claim1=new ClaimApprovalManager();
      claim1.processClaim(healthInsuranceSurveyor);
        VehicleInsuranceSurveyor vehicleInsuranceSurveyor=new VehicleInsuranceSurveyor();
        ClaimApprovalManager claim2=new ClaimApprovalManager();
        claim2.processClaim(vehicleInsuranceSurveyor);
    }
}
```

### Liskov substitution principle

Liskov Substitution Principle states the following: “in a computer program, if S is a subtype of T, then objects of type T may be replaced with objects of type S (i.e., objects of type S may substitute objects of type T) without altering any of the desirable properties of that program (correctness, task performed, etc.)”.

Simply said, any object of some class in an object-oriented program can be replaced by an object of a child class.

#### BAD example

```java
class TrasportationDevice
{
   String name;
   String getName() { ... }
   void setName(String n) { ... }
 
   double speed;
   double getSpeed() { ... }
   void setSpeed(double d) { ... }
   
   Engine engine;
   Engine getEngine() { ... }
   void setEngine(Engine e) { ... }
   void startEngine() { ... }
}
class Car extends TransportationDevice
{
   @Override
   void startEngine() { ... }
}
```

Above code is OK.

Let’s add another transportation device:

```java
class Bicycle extends TransportationDevice
{
   @Override
   void startEngine() /*problem!*/
}
```

A bicycle is a transportation device, however, it does not have an engine and hence, the method startEngine cannot be implemented.

### GOOD example

The solution to these problems is a correct inheritance hierarchy, and in our case we would solve the problem by differentiating classes of transportation devices with and without engines.

We can refactor our TransportationDevice  class as follows:

```java
class TrasportationDevice
{
   String name;
   String getName() { ... }
   void setName(String n) { ... }
 
   double speed;
   double getSpeed() { ... }
   void setSpeed(double d) { ... }
}
```

Now we can extend TransportationDevice for non-motorized devices.

```java
class DevicesWithoutEngines extends TransportationDevice
{  
   void startMoving() { ... }
}
```

And extend TransportationDevice for motorized devices.

```java
class DevicesWithEngines extends TransportationDevice
{  
   Engine engine;
   Engine getEngine() { ... }
   void setEngine(Engine e) { ... } 
   void startEngine() { ... }
}
```

Thus our Car class becomes more specialized, while adhering to the Liskov Substitution Principle.

```java
class Car extends DevicesWithEngines
{
   @Override
   void startEngine() { ... }
}
```

And our Bicycle  class is also in compliance with the Liskov Substitution Principle.

```java
class Bicycle extends DevicesWithoutEngines
{
   @Override
   void startMoving() { ... }
}
```

### Interface segregation principle

You use the interface keyword to create an interface and declare methods in it. Other classes can use that interface with the implements keyword, and then provide implementations of the declared methods

#### BAD example

```java
public interface Toy {
    void setPrice(double price);
    void setColor(String color);
    void move();
    void fly();
}
```

A class that represents a toy plane can implement the Toy interface and provide implementations of all the interface methods. But, imagine a class that represents a toy house. This is how the ToyHouse class will look.

```java
public class ToyHouse implements Toy {
    double price;
    String color;
    @Override
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public void setColor(String color) {
        this.color=color;
    }
    @Override
    public void move(){}
    @Override
    public void fly(){}
}
```

ToyHouse needs to provide implementation of the move() and fly() methods, even though it does not require them. This is a violation of the Interface Segregation Principle.

#### GOOD example

```java
public interface Toy {
     void setPrice(double price);
     void setColor(String color);
}
```

```java
public interface Movable {
    void move();
}
```

```java
public interface Flyable {
    void fly();
}
```

 We wrote the Movable and Flyable interfaces to represent moving and flying behaviors in toys. Let’s write the implementation classes.

```java
public class ToyHouse implements Toy {
    double price;
    String color;
    @Override
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public void setColor(String color) {
        this.color=color;
    }
    @Override
    public String toString(){
        return "ToyHouse: Toy house- Price: "+price+" Color: "+color;
    }
}

public class ToyCar implements Toy, Movable {
    double price;
    String color;
    @Override
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public void setColor(String color) {
     this.color=color;
    }
    @Override
    public void move(){
        System.out.println("ToyCar: Start moving car.");
    }
    @Override
    public String toString(){
        return "ToyCar: Moveable Toy car- Price: "+price+" Color: "+color;
    }
}

public class ToyPlane implements Toy, Movable, Flyable {
    double price;
    String color;
    @Override
    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public void setColor(String color) {
        this.color=color;
    }
    @Override
    public void move(){
        System.out.println("ToyPlane: Start moving plane.");
    }
    @Override
    public void fly(){
        System.out.println("ToyPlane: Start flying plane.");
    }
    @Override
    public String toString(){
        return "ToyPlane: Moveable and flyable toy plane- Price: "+price+" Color: "+color;
    }
}
```

Next, let’s write a class to create objects of the implementation classes.

```java
public class ToyBuilder {
    public static ToyHouse buildToyHouse(){
        ToyHouse toyHouse=new ToyHouse();
        toyHouse.setPrice(15.00);
        toyHouse.setColor("green");
        return toyHouse;
        }
    public static ToyCar buildToyCar(){
        ToyCar toyCar=new ToyCar();
        toyCar.setPrice(25.00);
        toyCar.setColor("red");
        toyCar.move();
        return toyCar;
    }
    public static ToyPlane buildToyPlane(){
        ToyPlane toyPlane=new ToyPlane();
        toyPlane.setPrice(125.00);
        toyPlane.setColor("white");
        toyPlane.move();
        toyPlane.fly();
        return toyPlane;
    }
}
```

Finally, let’s write this unit test to test our example.

```java
public class ToyBuilderTest {
    @Test
    public void testBuildToyHouse() throws Exception {
    ToyHouse toyHouse=ToyBuilder.buildToyHouse();
    System.out.println(toyHouse);
    }
    @Test
    public void testBuildToyCar() throws Exception {
    ToyCar toyCar=ToyBuilder.buildToyCar();;
        System.out.println(toyCar);
    }
    @Test
    public void testBuildToyPlane() throws Exception {
    ToyPlane toyPlane=ToyBuilder.buildToyPlane();
        System.out.println(toyPlane);
    }
}
```

### Dependency Invertsion Pronciple

"A. High-level modules should not depend on low-level modules. Both should not depend on abstractions.
B. Abstractions should not depend on details. Details should not depend on abstractions.”

#### Dependency Inversion Principle Violation (BAD Example)

Consider the example of an electric switch that turns a light bulb on or off.

```java
public class LightBulb {
    public void turnOn() {
        System.out.println("LightBulb: Bulb turned on...");
    }
    public void turnOff() {
        System.out.println("LightBulb: Bulb turned off...");
    }
}
```

```java
public class ElectricPowerSwitch {
    public LightBulb lightBulb;
    public boolean on;
    public ElectricPowerSwitch(LightBulb lightBulb) {
        this.lightBulb = lightBulb;
        this.on = false;
    }
    public boolean isOn() {
        return this.on;
    }
    public void press(){
        boolean checkOn = isOn();
        if (checkOn) {
            lightBulb.turnOff();
            this.on = false;
        } else {
            lightBulb.turnOn();
            this.on = true;
        }
    }
}
```

In the example above, we wrote the ElectricPowerSwitch class with a field referencing LightBulb. In the constructor, we created a LightBulb object and assigned it to the field.

Our high-level ElectricPowerSwitch class is directly dependent on the low-level LightBulb class.

If you see in the code, the LightBulb class is hardcoded in ElectricPowerSwitch. But, a switch should not be tied to a bulb. It should be able to turn on and off other appliances and devices too.

#### GOOD example

```java
public interface Switch {
    boolean isOn();
    void press();
}

public interface Switchable {
    void turnOn();
    void turnOff();
}

public class ElectricPowerSwitch implements Switch {
    public Switchable client;
    public boolean on;
    public ElectricPowerSwitch(Switchable client) {
        this.client = client;
        this.on = false;
    }
    public boolean isOn() {
        return this.on;
    }
   public void press(){
       boolean checkOn = isOn();
       if (checkOn) {
           client.turnOff();
           this.on = false;
       } else {
           client.turnOn();
           this.on = true;
       }
   }
}

public class LightBulb implements Switchable {
    @Override
    public void turnOn() {
        System.out.println("LightBulb: Bulb turned on...");
    }
    @Override
    public void turnOff() {
        System.out.println("LightBulb: Bulb turned off...");
    }
}

public class Fan implements Switchable {
    @Override
    public void turnOn() {
        System.out.println("Fan: Fan turned on...");
    }
    @Override
    public void turnOff() {
        System.out.println("Fan: Fan turned off...");
    }
}
```

Unit Test.

```java
public class ElectricPowerSwitchTest {
    @Test
    public void testPress() throws Exception {
     Switchable switchableBulb=new LightBulb();
     Switch bulbPowerSwitch=new ElectricPowerSwitch(switchableBulb);
     bulbPowerSwitch.press();
     bulbPowerSwitch.press();
    Switchable switchableFan=new Fan();
    Switch fanPowerSwitch=new ElectricPowerSwitch(switchableFan);
    fanPowerSwitch.press();
    fanPowerSwitch.press();
    }
}
```

## Dependency Injection

- Dependency injection is where a needed dependency is injected by another object
- The class being injected has no responsibility in instantiating object being injected
- You don't have to use new operator

Types of DI:

- by class properties - last preferred (evil)
- by setters (area of much debate)
- by constructor (most preferred)

DI via interfaces is highly preferred

Inversion of Control

- allow dependencies to be injected at runtime

### Example of DI in spring

services/GreetingService.java

```java
package com.github.di.services;

public interface GreetingService {
    String sayGreeting();
}
```

services/GreetingServiceImpl.java

```java
package com.github.di.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    public static final String HELLO = "Hello world";

    @Override
    public String sayGreeting() {
        return HELLO;
    }
}
```

#### Property injected DI

controllers/PropertyInjectedController.java

```java
@Controller
public class PropertyInjectedController {
    // Dependency injection in property
    @Autowired
    public GreetingServiceImpl greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
```

#### Setter injected DI

controllers/SetterInjectedController.java

```java
@Controller
public class SetterInjectedController {
    // We are using interface
    @Autowired
    private GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }

    // DI
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
```

#### Constructor injected DI (recommended)

controllers/ConstructorInjectedController.java

```java
package com.github.di.controllers;

import com.github.di.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class ConstructorInjectedController {
    // We are using interface
    // We don't have to do autowire here
    // It's automatic if we use constructor based DI
    private GreetingService greetingService;

    // DI
    // @Autowired
    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
```

#### Usage

DIApplication.java

```java
@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
	    // when we start the app, we load the context
        ApplicationContext ctx = SpringApplication.run(DiApplication.class, args);

        // we can ask context (which stores references to beans) to get any bean by name
        // and call method on bean
       
        // No qualifying bean of type 'com.github.di.controllers.PropertyInjectedController' available
        // means that constroller isn't annotated as Bean
        // that's why we should use @Controller annotation
        System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
        System.out.println(ctx.getBean(SetterInjectedController.class).sayHello());
        System.out.println(ctx.getBean(PropertyInjectedController.class).sayHello());
	}
}
```

> Branch `spring-di`

## Qualifier Addnotation
If we have multiple beans we should use `@Qualifier` to identify the bean that should be consumed. There are a few methods:

- Parameter qualifier

controllers/ConstructorInjectedController.java

```java
@Controller
public class ConstructorInjectedController {
    private GreetingService greetingService;

    public ConstructorInjectedController(@Qualifier("constructorGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
```

- Method qualifier

controllers/SetterInjectedController.java

```java
@Controller
public class SetterInjectedController {
    // We are using interface
    private GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }

    // DI
    @Autowired
    @Qualifier("setterGreetingService")
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
```

- Property name (a shortcut, not reccomended)

controllers/PropertyInjectedController.java

```java
@Controller
public class PropertyInjectedController {
    // Dependency injection in property
    // Instead of using @Qualifier we use a Bean name greetingServiceImpl as a variable name
    @Autowired
    public GreetingService greetingServiceImpl;

    public String sayHello() {
        return greetingServiceImpl.sayGreeting();
    }
}
```

> Branch `qualifiers`

## Primary Addnotation

`@Primary` annotation is placed before bean class and it indicates that this bean is default. 

services/PrimaryGreetingService.java

```java
@Service
@Primary
public class PrimaryGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello - Primary Greeting Service";
    }
}
```

> Branch `primary`

## Profiles

Profiles is kind of a configuration which we can set at runtime. Not active profiles are ignored. Profiles can be enabled in `application.properties` file.

```txt
spring.profiles.active=es
```

We declare profile using @Profile annotation, above the class name:

services/PrimarySpanishGreetingService.java

```java
@Service
@Profile("es")
@Primary
public class PrimarySpanishGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hola!";
    }
}
```

> Branch `profiles`

### Default profile

When there is no profile active in application.properties file, we can use a `default` one. Default is active only there are no others active.
We can specify it as a value in `@Profile` annotation.

resources/application.properties

```txt
spring.profiles.active=
```

services/PrimaryGreetingService.java

```java
@Service
@Primary
@Profile({"en", "default"})
public class PrimaryGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Hello - Primary Greeting Service";
    }
}
```

## Spring Bean Lifecycle

### Callback interfaces

Spring has two interfaces you can implement for callback events

- `InitializingBean.afterPropertiesSet()` - called after properties are set
- `DisposableBean.destroy()` - called during bean destruction in shutdown

### Life cycle annotations

- `@PostConstruct` annotated methods will be called after the bean has been constructed, but before it's returned to the requesting object.

- `@PreDestroy` is called just before the bean is destroyed by the container

### Bean post processors

Gives you a means to tap into the Spring context life cycle and interact with beans as they are processed.

Implement interface `BeanPostProcessor`

- `postProcessBeforeInitialization` - called before bean initialization method
- `postProcessAfterInitialization` - called after bean initialization method

> Branch `bean-life-cycle`