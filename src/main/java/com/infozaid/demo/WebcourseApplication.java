package com.infozaid.demo;

import com.infozaid.demo.customer.Customer;
import com.infozaid.demo.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScans;

import java.util.List;


@SpringBootApplication
public class WebcourseApplication {


	public static void main(String[] args) {
	         SpringApplication.run(WebcourseApplication.class, args);
		/*String [] beanDefinition=applicationContext.getBeanDefinitionNames();

		for (String beanDefinitionNames : beanDefinition) {
			System.out.println(beanDefinitionNames);
		}*/
	}

	@Bean
	CommandLineRunner runner(CustomerRepository customerRepository){
		return args -> {
			Customer alex = new Customer(
					"Alex",
					"alex@gmail.com",
					24
			);
			Customer usman = new Customer(
					"Usman",
					"usman@gmail.com",
					24
			);

			List<Customer> customers=List.of(alex,usman);
		//	customerRepository.saveAll(customers);
		};
	}

	/*@Bean
	public Foo getFoo(){
		return new Foo("bar");
	}
	record Foo(String name){}*/

    /*
	@GetMapping("/greet")
	public GreetResponse greet(){
		GreetResponse greetResponse= new GreetResponse("Hello",
				List.of("java","Golang","JavaScript"),
				new Person("Alex",30,30_0000)
				);
		return greetResponse;
	}






	record Person(String name, int age, double savings) {

	}

	record GreetResponse(
			String greet,
			List<String> favProgrammingLanguages,
			Person person
	){}
	*/

}
