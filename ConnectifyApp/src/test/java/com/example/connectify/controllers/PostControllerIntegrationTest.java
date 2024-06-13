package com.example.connectify.controllers;

import com.example.connectify.api.ConnectifyApplication;
import com.example.connectify.api.models.Post;
import com.example.connectify.api.models.PostDTO;
import com.example.connectify.api.models.User;
import com.example.connectify.api.services.PostService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ConnectifyApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    public void testCreatePost() throws Exception {
        // Mocking the service method
        User author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        PostDTO postDTO = new PostDTO();
        postDTO.setAuthorId(author.getId());
        postDTO.setContent("Hello World!");

        Post mockPost = new Post();
        mockPost.setId(1L);
        mockPost.setAuthor(author);
        mockPost.setContent(postDTO.getContent());
        mockPost.setTimestamp(Instant.now());

        Mockito.when(postService.createPost(any(PostDTO.class))).thenReturn(mockPost);

        // Performing the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\": 1, \"content\": \"Hello World!\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.author.id").value(1L))
                .andExpect(jsonPath("$.author.username").value("testuser"))
                .andExpect(jsonPath("$.content").value("Hello World!"));
    }

    @Test
    public void testGetPostById() throws Exception {
        // Mocking the service method
        User author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        Post mockPost = new Post();
        mockPost.setId(1L);
        mockPost.setAuthor(author);
        mockPost.setContent("Hello World!");
        mockPost.setTimestamp(Instant.now());

        Mockito.when(postService.getPostById(1L)).thenReturn((mockPost));

        // Performing the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/getById/{postId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.author.id").value(1L))
                .andExpect(jsonPath("$.author.username").value("testuser"))
                .andExpect(jsonPath("$.content").value("Hello World!"));
    }

    @Test
    public void testGetAllPosts() throws Exception {
        // Mocking the service method
        User author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        Post post1 = new Post();
        post1.setId(1L);
        post1.setAuthor(author);
        post1.setContent("Post 1");
        post1.setTimestamp(Instant.now());

        Post post2 = new Post();
        post2.setId(2L);
        post2.setAuthor(author);
        post2.setContent("Post 2");
        post2.setTimestamp(Instant.now());

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        Mockito.when(postService.getAllPosts()).thenReturn(posts);

        // Performing the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].author.id").value(1L))
                .andExpect(jsonPath("$[0].author.username").value("testuser"))
                .andExpect(jsonPath("$[0].content").value("Post 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].author.id").value(1L))
                .andExpect(jsonPath("$[1].author.username").value("testuser"))
                .andExpect(jsonPath("$[1].content").value("Post 2"));
    }

    @Test
    public void testUpdatePostById() throws Exception {
        // Mocking the service method
        User author = new User();
        author.setId(1L);
        author.setUsername("testuser");

        PostDTO postDTO = new PostDTO();
        postDTO.setAuthorId(author.getId());
        postDTO.setContent("Updated content");

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setAuthor(author);
        updatedPost.setContent(postDTO.getContent());
        updatedPost.setTimestamp(Instant.now());

        Mockito.when(postService.updatePostById(eq(1L), any(PostDTO.class))).thenReturn(updatedPost);

        // Performing the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/updateById/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authorId\": 1, \"content\": \"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.author.id").value(1L))
                .andExpect(jsonPath("$.author.username").value("testuser"))
                .andExpect(jsonPath("$.content").value("Updated content"));
    }

    @Test
    public void testDeletePostById() throws Exception {
        // Performing the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/deleteById/{postId}", 1L))
                .andExpect(status().isNoContent());
    }
}
