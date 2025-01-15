package kr.hhplus.be.server.common.interceptor;


import kr.hhplus.be.server.balance.domain.entity.User;
import kr.hhplus.be.server.balance.domain.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ExtendWith(OutputCaptureExtension.class) // 로그 캡처
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoggingInterceptorTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BalanceRepository balanceRepository;

    @BeforeEach
    void setUp() {
        User user = User.createUser("testname", 1000L);
        balanceRepository.saveUser(user); // 초기 잔액
    }

    @Test
    void testRequestBodyLogged(CapturedOutput output) {
        // given
        String url = "http://localhost:" + port + "/api/balances/1/charge"; // 예: 테스트용 컨트롤러
        String jsonBody = "{\"amount\": \"10000\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class);

        // then
        // 컨트롤러가 정상 응답을 하는지
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        // 로그가 제대로 찍혔는지 확인 (LoggingInterceptor에서 System.out.println 등으로 찍었다고 가정)
        // OutputCaptureExtension 덕분에, output에 콘솔에 찍힌 문자열이 다 들어온다
        assertThat(output.getOut())
                .contains("Request Body: " + jsonBody);
    }

}