package br.com.restdocs.controller;

import br.com.restdocs.SpringRestdocsApplication;
import br.com.restdocs.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SpringRestdocsApplication.class)
@WebAppConfiguration
public class PersonControllerTest {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation).uris().withScheme("http").withHost("localhost").withPort(8080))
				.build();
	}

	@Test
	public void testFindAll() throws Exception {
		this.mockMvc.perform(get("/people").accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("{method-name}",
						responseFields(
								fieldWithPath("[].id").description("Person's identifier"),
								fieldWithPath("[].firstName").description("Person's first name"),
								fieldWithPath("[].lastName").description("Person's last name").optional().type(STRING)),
						responseHeaders(headerWithName("Content-Type").description("Content type of the result entity"))
				));
	}

	@Test
	public void testFindOne() throws Exception {
		this.mockMvc.perform(get("/people/{id}", 1L).accept(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("{method-name}",
						pathParameters(parameterWithName("id").description("Person's identifier")),
						responseFields(
								fieldWithPath("id").description("Person's identifier"),
								fieldWithPath("firstName").description("Person's first name"),
								fieldWithPath("lastName").description("Person's last name").optional().type(STRING)),
						responseHeaders(headerWithName("Content-Type").description("Content type of the result entity"))
				));
	}

	@Test
	public void testSave() throws Exception {
		final Map<String, Object> person = new HashMap<>();
		person.put("firstName", "Some User");
		person.put("lastName", "Some last name");

		final ConstraintDescriptions desc = new ConstraintDescriptions(Person.class);

		this.mockMvc.perform(post("/people").contentType(APPLICATION_JSON).accept(APPLICATION_JSON).content(this.mapper.writeValueAsString(person)))
				.andExpect(status().isCreated())
				.andDo(document("{method-name}",
						requestHeaders(headerWithName("Content-Type").description("Content type of input entity")),
						requestFields(
								attributes(key("title").value("Fields for Person creation")),
								fieldWithPath("firstName")
										.attributes(key("constraints").value(StringUtils.collectionToCommaDelimitedString(desc.descriptionsForProperty("firstName"))))
										.description("Person's first name").optional().type(STRING),
								fieldWithPath("lastName")
										.attributes(key("constraints").value(StringUtils.collectionToCommaDelimitedString(desc.descriptionsForProperty("lastName"))))
										.description("Person's last name").optional().type(STRING)),
						responseFields(
								fieldWithPath("id").description("Person's identifier"),
								fieldWithPath("firstName").description("Person's first name"),
								fieldWithPath("lastName").description("Person's last name").optional().type(STRING)),
						responseHeaders(headerWithName("Content-Type").description("Content type of the result entity"))
				));
	}

	@Test
	public void testUpdate() throws Exception {
		final Map<String, Object> person = new HashMap<>();
		person.put("id", "1");
		person.put("firstName", "User 1");
		person.put("lastName", "Last name");

		final ConstraintDescriptions desc = new ConstraintDescriptions(Person.class);

		this.mockMvc.perform(put("/people").contentType(APPLICATION_JSON).accept(APPLICATION_JSON).content(this.mapper.writeValueAsString(person)))
				.andExpect(status().isOk())
				.andDo(document("{method-name}",
						requestHeaders(headerWithName("Content-Type").description("Content type of input entity")),
						requestFields(
								attributes(key("title").value("Fields for Person update")),
								fieldWithPath("id")
										.attributes(key("constraints").value(StringUtils.collectionToCommaDelimitedString(desc.descriptionsForProperty("id"))))
										.description("Person's identifier").optional().type(NUMBER),
								fieldWithPath("firstName")
										.attributes(key("constraints").value(StringUtils.collectionToCommaDelimitedString(desc.descriptionsForProperty("firstName"))))
										.description("Person's first name").optional().type(STRING),
								fieldWithPath("lastName")
										.attributes(key("constraints").value(StringUtils.collectionToCommaDelimitedString(desc.descriptionsForProperty("lastName"))))
										.description("Person's last name").optional().type(STRING)),
						responseFields(
								fieldWithPath("id").description("Person's identifier"),
								fieldWithPath("firstName").description("Person's first name"),
								fieldWithPath("lastName").description("Person's last name").optional().type(STRING)),
						responseHeaders(headerWithName("Content-Type").description("Content type of the result entity"))
				));
	}

	@Test
	public void testDelete() throws Exception {
		this.mockMvc.perform(delete("/people/{id}", 1L))
				.andExpect(status().isOk())
				.andDo(document("{method-name}",
						pathParameters(parameterWithName("id").description("Person's identifier"))
				));
	}

}