package br.com.restdocs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @see //docs.spring.io/spring-restdocs/docs/1.0.1.RELEASE/reference/html5/
 * @see //www.tothepoint.company/blog/spring-rest-doc/
 */
@SpringBootApplication
@EnableJpaRepositories
public class SpringRestdocsApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SpringRestdocsApplication.class, args);
	}
}
