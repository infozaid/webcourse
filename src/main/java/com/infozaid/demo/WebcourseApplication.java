package com.infozaid.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebcourseApplication {


	public static void main(String[] args) {
	         SpringApplication.run(WebcourseApplication.class, args);
		/*String [] beanDefinition=applicationContext.getBeanDefinitionNames();

		for (String beanDefinitionNames : beanDefinition) {
			System.out.println(beanDefinitionNames);
		}*/
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
