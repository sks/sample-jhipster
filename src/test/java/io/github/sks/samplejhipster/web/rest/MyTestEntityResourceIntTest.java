package io.github.sks.samplejhipster.web.rest;

import io.github.sks.samplejhipster.JhipsterApp;

import io.github.sks.samplejhipster.domain.MyTestEntity;
import io.github.sks.samplejhipster.repository.MyTestEntityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MyTestEntityResource REST controller.
 *
 * @see MyTestEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class MyTestEntityResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAA";
    private static final String UPDATED_COUNTRY = "BBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Inject
    private MyTestEntityRepository myTestEntityRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyTestEntityMockMvc;

    private MyTestEntity myTestEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyTestEntityResource myTestEntityResource = new MyTestEntityResource();
        ReflectionTestUtils.setField(myTestEntityResource, "myTestEntityRepository", myTestEntityRepository);
        this.restMyTestEntityMockMvc = MockMvcBuilders.standaloneSetup(myTestEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyTestEntity createEntity(EntityManager em) {
        MyTestEntity myTestEntity = new MyTestEntity();
        myTestEntity.setFirstName(DEFAULT_FIRST_NAME);
        myTestEntity.setLastName(DEFAULT_LAST_NAME);
        myTestEntity.setAge(DEFAULT_AGE);
        myTestEntity.setEmail(DEFAULT_EMAIL);
        myTestEntity.setCompany(DEFAULT_COMPANY);
        myTestEntity.setCountry(DEFAULT_COUNTRY);
        myTestEntity.setTag(DEFAULT_TAG);
        return myTestEntity;
    }

    @Before
    public void initTest() {
        myTestEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyTestEntity() throws Exception {
        int databaseSizeBeforeCreate = myTestEntityRepository.findAll().size();

        // Create the MyTestEntity

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isCreated());

        // Validate the MyTestEntity in the database
        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeCreate + 1);
        MyTestEntity testMyTestEntity = myTestEntityList.get(myTestEntityList.size() - 1);
        assertThat(testMyTestEntity.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMyTestEntity.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMyTestEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMyTestEntity.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMyTestEntity.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testMyTestEntity.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMyTestEntity.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createMyTestEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myTestEntityRepository.findAll().size();

        // Create the MyTestEntity with an existing ID
        MyTestEntity existingMyTestEntity = new MyTestEntity();
        existingMyTestEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMyTestEntity)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setFirstName(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setLastName(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setAge(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setEmail(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setCompany(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = myTestEntityRepository.findAll().size();
        // set the field null
        myTestEntity.setCountry(null);

        // Create the MyTestEntity, which fails.

        restMyTestEntityMockMvc.perform(post("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isBadRequest());

        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyTestEntities() throws Exception {
        // Initialize the database
        myTestEntityRepository.saveAndFlush(myTestEntity);

        // Get all the myTestEntityList
        restMyTestEntityMockMvc.perform(get("/api/my-test-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myTestEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    @Transactional
    public void getMyTestEntity() throws Exception {
        // Initialize the database
        myTestEntityRepository.saveAndFlush(myTestEntity);

        // Get the myTestEntity
        restMyTestEntityMockMvc.perform(get("/api/my-test-entities/{id}", myTestEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myTestEntity.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyTestEntity() throws Exception {
        // Get the myTestEntity
        restMyTestEntityMockMvc.perform(get("/api/my-test-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyTestEntity() throws Exception {
        // Initialize the database
        myTestEntityRepository.saveAndFlush(myTestEntity);
        int databaseSizeBeforeUpdate = myTestEntityRepository.findAll().size();

        // Update the myTestEntity
        MyTestEntity updatedMyTestEntity = myTestEntityRepository.findOne(myTestEntity.getId());
        updatedMyTestEntity.setFirstName(UPDATED_FIRST_NAME);
        updatedMyTestEntity.setLastName(UPDATED_LAST_NAME);
        updatedMyTestEntity.setAge(UPDATED_AGE);
        updatedMyTestEntity.setEmail(UPDATED_EMAIL);
        updatedMyTestEntity.setCompany(UPDATED_COMPANY);
        updatedMyTestEntity.setCountry(UPDATED_COUNTRY);
        updatedMyTestEntity.setTag(UPDATED_TAG);

        restMyTestEntityMockMvc.perform(put("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyTestEntity)))
            .andExpect(status().isOk());

        // Validate the MyTestEntity in the database
        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeUpdate);
        MyTestEntity testMyTestEntity = myTestEntityList.get(myTestEntityList.size() - 1);
        assertThat(testMyTestEntity.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMyTestEntity.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMyTestEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMyTestEntity.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMyTestEntity.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testMyTestEntity.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMyTestEntity.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingMyTestEntity() throws Exception {
        int databaseSizeBeforeUpdate = myTestEntityRepository.findAll().size();

        // Create the MyTestEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyTestEntityMockMvc.perform(put("/api/my-test-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myTestEntity)))
            .andExpect(status().isCreated());

        // Validate the MyTestEntity in the database
        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyTestEntity() throws Exception {
        // Initialize the database
        myTestEntityRepository.saveAndFlush(myTestEntity);
        int databaseSizeBeforeDelete = myTestEntityRepository.findAll().size();

        // Get the myTestEntity
        restMyTestEntityMockMvc.perform(delete("/api/my-test-entities/{id}", myTestEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MyTestEntity> myTestEntityList = myTestEntityRepository.findAll();
        assertThat(myTestEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
