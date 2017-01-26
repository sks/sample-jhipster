package io.github.sks.samplejhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.sks.samplejhipster.domain.MyTestEntity;

import io.github.sks.samplejhipster.repository.MyTestEntityRepository;
import io.github.sks.samplejhipster.web.rest.util.HeaderUtil;
import io.github.sks.samplejhipster.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MyTestEntity.
 */
@RestController
@RequestMapping("/api")
public class MyTestEntityResource {

    private final Logger log = LoggerFactory.getLogger(MyTestEntityResource.class);
        
    @Inject
    private MyTestEntityRepository myTestEntityRepository;

    /**
     * POST  /my-test-entities : Create a new myTestEntity.
     *
     * @param myTestEntity the myTestEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myTestEntity, or with status 400 (Bad Request) if the myTestEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-test-entities")
    @Timed
    public ResponseEntity<MyTestEntity> createMyTestEntity(@Valid @RequestBody MyTestEntity myTestEntity) throws URISyntaxException {
        log.debug("REST request to save MyTestEntity : {}", myTestEntity);
        if (myTestEntity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myTestEntity", "idexists", "A new myTestEntity cannot already have an ID")).body(null);
        }
        MyTestEntity result = myTestEntityRepository.save(myTestEntity);
        return ResponseEntity.created(new URI("/api/my-test-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myTestEntity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-test-entities : Updates an existing myTestEntity.
     *
     * @param myTestEntity the myTestEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myTestEntity,
     * or with status 400 (Bad Request) if the myTestEntity is not valid,
     * or with status 500 (Internal Server Error) if the myTestEntity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-test-entities")
    @Timed
    public ResponseEntity<MyTestEntity> updateMyTestEntity(@Valid @RequestBody MyTestEntity myTestEntity) throws URISyntaxException {
        log.debug("REST request to update MyTestEntity : {}", myTestEntity);
        if (myTestEntity.getId() == null) {
            return createMyTestEntity(myTestEntity);
        }
        MyTestEntity result = myTestEntityRepository.save(myTestEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myTestEntity", myTestEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-test-entities : get all the myTestEntities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of myTestEntities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/my-test-entities")
    @Timed
    public ResponseEntity<List<MyTestEntity>> getAllMyTestEntities(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MyTestEntities");
        Page<MyTestEntity> page = myTestEntityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/my-test-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /my-test-entities/:id : get the "id" myTestEntity.
     *
     * @param id the id of the myTestEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myTestEntity, or with status 404 (Not Found)
     */
    @GetMapping("/my-test-entities/{id}")
    @Timed
    public ResponseEntity<MyTestEntity> getMyTestEntity(@PathVariable Long id) {
        log.debug("REST request to get MyTestEntity : {}", id);
        MyTestEntity myTestEntity = myTestEntityRepository.findOne(id);
        return Optional.ofNullable(myTestEntity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-test-entities/:id : delete the "id" myTestEntity.
     *
     * @param id the id of the myTestEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-test-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyTestEntity(@PathVariable Long id) {
        log.debug("REST request to delete MyTestEntity : {}", id);
        myTestEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myTestEntity", id.toString())).build();
    }

}
