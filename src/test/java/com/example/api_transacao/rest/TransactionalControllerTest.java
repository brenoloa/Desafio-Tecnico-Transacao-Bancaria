package com.example.api_transacao.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void postTransacao_quandoValida_retorna201SemCorpo() throws Exception {
        String body = "{" +
                "\"valor\": 123.45," +
                "\"dataHora\": \"2000-08-07T12:34:56.789-03:00\"" +
                "}";

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_quandoJsonInvalido_retorna400() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void postTransacao_quandoValorNegativo_retorna422() throws Exception {
        String body = "{" +
                "\"valor\": -1.00," +
                "\"dataHora\": \"2000-08-07T12:34:56.789-03:00\"" +
                "}";

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(""));
    }

    @Test
    void deleteTransacao_retorna200SemCorpo() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getEstatistica_quandoSemTransacoes_retornaZeros() throws Exception {
        mockMvc.perform(delete("/transacao"));

        mockMvc.perform(get("/estatistica").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0))
                .andExpect(jsonPath("$.avg").value(0))
                .andExpect(jsonPath("$.min").value(0))
                .andExpect(jsonPath("$.max").value(0));
    }
}

