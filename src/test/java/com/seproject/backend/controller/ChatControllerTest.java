package com.seproject.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seproject.backend.dto.ChatMessageDTO;
import com.seproject.backend.service.ChatService;
import com.seproject.backend.service.TeamspaceService;
import com.seproject.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import jakarta.servlet.http.Cookie;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Integration tests for ChatController
 * Tests the REST endpoints for chat functionality
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private TeamspaceService teamspaceService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private ChatMessageDTO testMessage;
    private final Integer TEST_TEAMSPACE_ID = 1;
    private final Integer TEST_USER_ID = 1;
    private final String TEST_TOKEN = "test-jwt-token";
    private Cookie jwtCookie;
    private RequestPostProcessor authPostProcessor;

    @BeforeEach
    void setUp() {
        // Setup test message
        testMessage = new ChatMessageDTO();
        testMessage.setTeamspaceId(TEST_TEAMSPACE_ID);
        testMessage.setMessage("Test message");
        testMessage.setSenderId(TEST_USER_ID);

        // Setup JWT cookie
        jwtCookie = new Cookie("access_token", TEST_TOKEN);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");

        // Mock JWT validation
        Claims claims = new DefaultClaims();
        claims.setSubject("testUser");
        claims.put("role", "user");
        claims.put("userId", TEST_USER_ID);
        when(jwtUtil.decodeJwtToken(TEST_TOKEN)).thenReturn(claims);

        // Create auth post processor
        authPostProcessor = request -> {
            request.setAttribute("username", "testUser");
            request.setAttribute("role", "user");
            request.setAttribute("userId", TEST_USER_ID);
            return request;
        };

        // Reset and setup teamspace service mock
        reset(teamspaceService);
    }

    /**
     * Test creating a new chat message
     * Verifies that a valid message can be created successfully
     */
    @Test
    void createMessage_ValidMessage_ReturnsCreatedMessage() throws Exception {
        // Mock service behavior
        when(teamspaceService.isUserMemberOfTeamspace(TEST_USER_ID, TEST_TEAMSPACE_ID))
            .thenReturn(true);
        when(chatService.createMessage(any(ChatMessageDTO.class), eq(TEST_USER_ID)))
            .thenReturn(testMessage);

        // Perform POST request and verify
        mockMvc.perform(post("/api/teamspaces/{teamspaceId}/chat", TEST_TEAMSPACE_ID)
                .with(authPostProcessor)
                .cookie(jwtCookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(testMessage.getMessage()))
                .andExpect(jsonPath("$.teamspaceId").value(TEST_TEAMSPACE_ID));
    }

    /**
     * Test retrieving chat messages
     * Verifies that messages can be retrieved with pagination
     */
    @Test
    void getMessages_ValidRequest_ReturnsMessages() throws Exception {
        // Mock service behavior
        Page<ChatMessageDTO> messagePage = new PageImpl<>(Arrays.asList(testMessage));
        when(teamspaceService.isUserMemberOfTeamspace(TEST_USER_ID, TEST_TEAMSPACE_ID))
            .thenReturn(true);
        when(chatService.getMessages(eq(TEST_TEAMSPACE_ID), eq(0), eq(20)))
            .thenReturn(messagePage);

        // Perform GET request and verify
        mockMvc.perform(get("/api/teamspaces/{teamspaceId}/chat", TEST_TEAMSPACE_ID)
                .with(authPostProcessor)
                .cookie(jwtCookie))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.chatMessageDTOList[0].message").value(testMessage.getMessage()));
    }

    /**
     * Test unauthorized access
     * Verifies that unauthorized users cannot access chat messages
     */
    @Test
    void getMessages_UnauthorizedUser_ReturnsUnauthorized() throws Exception {
        // Mock service behavior for unauthorized user
        when(teamspaceService.isUserMemberOfTeamspace(TEST_USER_ID, TEST_TEAMSPACE_ID))
            .thenReturn(false);

        // Perform GET request and verify unauthorized status
        mockMvc.perform(get("/api/teamspaces/{teamspaceId}/chat", TEST_TEAMSPACE_ID)
                .with(authPostProcessor)
                .cookie(jwtCookie))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User is not a member of this teamspace"));
    }

    @Test
    void createMessage_UnauthorizedUser_ReturnsUnauthorized() throws Exception {
        // Mock service behavior for unauthorized user
        when(teamspaceService.isUserMemberOfTeamspace(TEST_USER_ID, TEST_TEAMSPACE_ID))
            .thenReturn(false);

        // Perform POST request and verify unauthorized status
        mockMvc.perform(post("/api/teamspaces/{teamspaceId}/chat", TEST_TEAMSPACE_ID)
                .with(authPostProcessor)
                .cookie(jwtCookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMessage)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("User is not a member of this teamspace"));
    }
} 