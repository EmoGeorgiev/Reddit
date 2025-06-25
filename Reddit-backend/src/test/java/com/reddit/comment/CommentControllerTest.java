package com.reddit.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddit.comment.dto.CommentDto;
import com.reddit.content.Content;
import com.reddit.exception.comment.CommentNotFoundException;
import com.reddit.util.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {
    private static final String BASE_URL = "/api/comments";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CommentService commentService;
    private final Long invalidId = -1L;
    private final Long id = 1L;
    private final CommentDto commentDto = new CommentDto(id, id, null, "text", Content.INITIAL_SCORE, false, new ArrayList<>(), null, id);
    private final Pageable pageable = PageRequest.of(0, 200, Sort.by("score").descending());

    @Test
    public void shouldReturnNotFoundForInvalidId() throws Exception {
        when(commentService.getComment(invalidId))
                .thenThrow(new CommentNotFoundException(ErrorMessages.COMMENT_NOT_FOUND));

        mockMvc
                .perform(get(BASE_URL + "/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(ErrorMessages.COMMENT_NOT_FOUND));

        verify(commentService)
                .getComment(invalidId);
    }

    @Test
    public void shouldReturnCommentForValidId() throws Exception {
        when(commentService.getComment(id))
                .thenReturn(commentDto);

        mockMvc
                .perform(get(BASE_URL + "/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(commentDto.id()))
                .andExpect(jsonPath("userId").value(commentDto.userId()))
                .andExpect(jsonPath("created").value(commentDto.created()))
                .andExpect(jsonPath("text").value(commentDto.text()))
                .andExpect(jsonPath("score").value(commentDto.score()))
                .andExpect(jsonPath("isDeleted").value(commentDto.isDeleted()))
                .andExpect(jsonPath("replies").value(commentDto.replies()))
                .andExpect(jsonPath("parentId").value(commentDto.parentId()))
                .andExpect(jsonPath("postId").value(commentDto.postId()));

        verify(commentService)
                .getComment(id);
    }

    @Test
    public void shouldReturnEmptyPageWhenAPostDoesNotHaveComments() throws Exception {
        Page<CommentDto> emptyPage = Page.empty();

        when(commentService.getCommentsByPostId(id, pageable))
                .thenReturn(emptyPage);

        mockMvc
                .perform(get(BASE_URL + "/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));


        verify(commentService)
                .getCommentsByPostId(id, pageable);
    }

    @Test
    public void shouldReturnPageWithOneCommentWhenPostHasOneComment() throws Exception {
        List<CommentDto> comments = List.of(commentDto);
        Page<CommentDto> pageWithOneElement  = new PageImpl<>(comments, pageable, comments.size());

        when(commentService.getCommentsByPostId(id, pageable))
                .thenReturn(pageWithOneElement);

        mockMvc
                .perform(get(BASE_URL + "/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.content[0].id").value(commentDto.id()))
                .andExpect(jsonPath("$.content[0].userId").value(commentDto.userId()))
                .andExpect(jsonPath("$.content[0].created").value(commentDto.created()))
                .andExpect(jsonPath("$.content[0].text").value(commentDto.text()))
                .andExpect(jsonPath("$.content[0].score").value(commentDto.score()))
                .andExpect(jsonPath("$.content[0].isDeleted").value(commentDto.isDeleted()))
                .andExpect(jsonPath("$.content[0].replies").value(commentDto.replies()))
                .andExpect(jsonPath("$.content[0].parentId").value(commentDto.parentId()))
                .andExpect(jsonPath("$.content[0].postId").value(commentDto.postId()));

        verify(commentService)
                .getCommentsByPostId(id, pageable);
    }
}
