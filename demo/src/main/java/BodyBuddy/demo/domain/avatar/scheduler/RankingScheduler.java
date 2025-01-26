package BodyBuddy.demo.domain.avatar.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RankingScheduler {

    private final RestTemplate restTemplate;

    public RankingScheduler() {
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(cron = "0 0 0 * * MON") // 매주 월요일 자정 실행
    public void triggerRankingUpdate() {
        //TODO 프론트엔드와 협업할 시 Url 수정해야함
        String apiUrl = "http://localhost:8080/api/rankings/update"; // API 엔드포인트

        try {
            restTemplate.postForEntity(apiUrl, null, Void.class);
            System.out.println("✅ 랭킹 업데이트가 성공적으로 되었습니다.");
        } catch (Exception e) {
            System.err.println("❌ 랭킹 업데이트가 실패했습니다.: " + e.getMessage());
        }
    }
}
