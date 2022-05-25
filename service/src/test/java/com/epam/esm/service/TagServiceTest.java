package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagConverter;
import com.epam.esm.repository.impl.TagDaoImpl;
import com.epam.esm.response.DaoException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);


    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(1, "tag_1");
    private static final Tag TAG_2 = new Tag(2, "tag_2");
    private static final Tag TAG_3 = new Tag(3, "tag_3");
    private static final Tag TAG_4 = new Tag(4, "tag_4");

    @Test
    public void getById_test() throws DaoException {
        when(tagDao.getById(TAG_1.getId())).thenReturn(TAG_1);
        Tag actual = TagConverter.toEntity(tagService.getById(TAG_1.getId()));
        assertEquals(TAG_1, actual);
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Tag> expected = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4);
        when(tagDao.list()).thenReturn(expected);

        List<Tag> actual = tagService.getAll()
                .stream()
                .map(TagConverter::toEntity)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    public void insert_test() throws DaoException {
        Tag newTag = new Tag("tag_99");
        when(tagDao.insert(new Tag("tag_99"))).thenReturn(5L);

        long id = tagService.insert(TagConverter.toDto(newTag));

        newTag.setId(id);
        Tag expected = new Tag(5L, "tag_99");

        assertEquals(expected, newTag);
        assertEquals(5L, id);

    }

    @Test
    void delete_test() throws DaoException {
        when(tagDao.removeById(1L)).thenReturn(true);

        boolean success = tagService.deleteById(1L);
        assertTrue(success);
    }
}
