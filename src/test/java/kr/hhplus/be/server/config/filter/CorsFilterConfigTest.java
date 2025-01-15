package kr.hhplus.be.server.config.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CorsFilterConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("CORS 필터가 올바른 헤더를 반환하는지 테스트")
    void corsFilterShouldReturnHeaders() throws Exception {
        mockMvc.perform(options("/api/test")
                        .header("Origin", "http://example.com")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().exists("Access-Control-Allow-Credentials"))
                .andExpect(header().exists("Access-Control-Expose-Headers")); // 노출된 헤더 검증
    }

}