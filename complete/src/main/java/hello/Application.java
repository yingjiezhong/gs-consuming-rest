package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

//	public static void main(String args[]) {
//		SpringApplication.run(Application.class);
//	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
			log.info(quote.toString());
		};
	}

	public static void main(String args[]) {
		RestTemplate restTemplate = new RestTemplate();
//		getObservableJobs(restTemplate);
		postJob(restTemplate);
	}

	static void getGreeting(RestTemplate restTemplate) {
		Greeting greeting = restTemplate.getForObject("http://localhost:8080/greeting", Greeting.class);
		log.info(greeting.toString());

	}

	static void getJob(RestTemplate restTemplate) {
		Job job = restTemplate.getForObject("http://localhost:8080/job", Job.class);
		log.info(job.toString());

	}

	static void getObservableJobs(RestTemplate restTemplate) {
//		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/job/osse", String.class);
//		assertNotNull(response);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertEquals("data:message 1\n\ndata:message 2\n\ndata:message 3\n\n", response.getBody());

		SseEmitter response = restTemplate.getForObject("http://localhost:8080/job/sse", SseEmitter.class);
//		response.toString();
//		log.info(response.getBody());
		log.info(response.toString());

//		response = restTemplate.getForEntity("http://localhost:8080/job/rbe", String.class);
//		log.info(response.getBody());
//		log.info(response.toString());
	}

	static void postJob(RestTemplate restTemplate) {
		Job job = new Job(100, "demo");
		ResponseEntity<Job> entity = restTemplate.postForEntity("http://localhost:8080/job", job, Job.class);
		nextStep(entity, restTemplate);
	}

	private static void nextStep(ResponseEntity<Job> entity, RestTemplate restTemplate) {
		HttpStatus s = entity.getStatusCode();
		HttpHeaders headers = entity.getHeaders();
		Job job = entity.getBody();
		log.info("received response: " + job);
	    if (s.equals(HttpStatus.ACCEPTED)) {
	    	getJob(restTemplate, job.getId());
		}
	}

	private static void getJob(RestTemplate restTemplate, long id) {

		ResponseEntity<Job> entity = restTemplate.getForEntity(getrui(id), Job.class);
		HttpStatus s = entity.getStatusCode();
		if (s.equals(HttpStatus.OK)) {
		    Job body = entity.getBody();
		    if (body == null) {
		    	log.error("recieve null object");
			} else {
				log.info(body.toString());
				if (body.getStatus() < 100) {
					getJob(restTemplate, id);
				}
			}
		}
	}

	private static String getrui(long id) {
		String s = "http://localhost:8080/job/running" + "?id=" + id;
		return s;
	}

	private static URI getURI(long id) {
		Map<String, String> params = new HashMap<>();
		params.put("id", String.valueOf(id));
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:8080/job/running")
				.buildAndExpand(params)


				// build and expand to add param does not work

				.toUri();
		return uri;
	}
}