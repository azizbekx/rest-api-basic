package com.epam.esm.api;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.response.DaoException;
import com.epam.esm.response.SuccessResponse;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Class {@code GiftCertificateApiController} controller api which operation of all gift certificate system.
 */

@RestController
@RequestMapping("/giftcertificates")
public class GiftCertificateApiController {
    @Autowired
    private GiftCertificateService giftCertificateService;

    /**
     * Method for getting all object from db
     * @return List<GiftCertificateDto> entity is found entities in db
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @GetMapping
    public List<GiftCertificateDto> getAll() throws DaoException {
        return giftCertificateService.getAll();
    }
    /**
     * Method for getting object with id from db
     * @param id it is id of object which is getting
     * @return GiftCertificateDto entity is found
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable int id) throws DaoException {
        return giftCertificateService.getById(id);
    }
    /**
     * Method for inserting object to db
     * @param  giftCertificateDto is id of object which is getting
     * @return GiftCertificateDto entity is found
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody SuccessResponse insert(@RequestBody GiftCertificateDto giftCertificateDto) throws DaoException {
        long id =  giftCertificateService.insert(giftCertificateDto);
        return new SuccessResponse(true, "Object was successfully created (id = "+id+" )");
    }

    /**
     * Method for updating object
     *
     * @param giftCertificateDto is object which is should updated
     * @return boolean if operation successfully updated return true, else return false
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @PatchMapping("/update")
    public @ResponseBody SuccessResponse update(@RequestBody GiftCertificateDto giftCertificateDto) throws DaoException{
        boolean success = giftCertificateService.update(giftCertificateDto);
        return new SuccessResponse(success,"Object was successfully updated (id = "+giftCertificateDto.getId()+" )");
    }

    /**
     * Method for deleting object with id from db
     *
     * @param id it is id of object which is getting
     * @return id of entity is found
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody SuccessResponse deleteById(@PathVariable int id) throws DaoException {
        boolean success = giftCertificateService.deleteById(id);
        return new SuccessResponse(success,"Object was successfully updated (id = "+id+" )");
    }

    /**
     * Method for filtering objects in db
     *
     * @param tag_name name of Tag for getting gift certificates
     * @param name name of gift certificate for searching pattern
     * @param description description of gift certificates for searching pattern
     * @param sortBy field of gift certificate for sorting objects
     * @param sortDir name of ordering asc or desc order
     * @return List of GiftCertificates
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @GetMapping("/filter")
    public List<GiftCertificateDto> getWithParameters(
            @RequestParam(value = "tag_name", required = false) String tag_name,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "sortDir", required = false) String sortDir
    ) throws DaoException{
        return giftCertificateService.doFilter(tag_name,name, description, sortBy, sortDir);
    }
}
