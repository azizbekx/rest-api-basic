package com.epam.esm.api;

import com.epam.esm.dto.TagDto;
import com.epam.esm.response.DaoException;
import com.epam.esm.response.SuccessResponse;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code TagApiController} controller api.
 */

@RestController
@RequestMapping("/tags")
public class TagApiController {
    @Autowired
    private TagService tagService;

    /**
     * Method for getting all object from db
     *
     * @return List<TagDto> entity is found entities in db
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @GetMapping
    public List<TagDto> getList() throws DaoException{
        return tagService.getAll();
    }
    /**
     * Method for getting object with id from db
     * @param id it is id of object which is getting
     * @return TagDto entity is found
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @GetMapping("/{id}")
    public TagDto getById(@PathVariable long id) throws DaoException{
        return tagService.getById(id);
    }

    @GetMapping("/filter")
    public TagDto getByName(@RequestParam(value = "tag_name") String tag_name) throws DaoException{
        return tagService.getByName(tag_name);
    }

    /**
     * Method for inserting object to db
     *
     * @param tagDto TagDto is object which should get from request
     * @return id of new object which is created in db
     * @throws DaoException an exception thrown in case of saving error
     */
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody SuccessResponse insert(@RequestBody TagDto tagDto) throws DaoException{
        long id = tagService.insert(tagDto);
        return new SuccessResponse(true,"Object was successfully updated (id = "+id+" )");
    }

    /**
     * Method for deleting object with id
     *
     * @param id Id of object is being deleted
     * @return int is id of object
     * @throws DaoException an exception thrown in case of not found object in db
     */
    @DeleteMapping ("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody SuccessResponse deleteById(@PathVariable long id) throws DaoException{
        boolean success = tagService.deleteById(id);
        return new SuccessResponse(success,"Object was successfully updated (id = "+id+" )");
    }
}
