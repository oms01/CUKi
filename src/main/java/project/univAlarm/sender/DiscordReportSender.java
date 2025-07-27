package project.univAlarm.sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import project.univAlarm.domain.Notification;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DiscordReportSender {

    @Value("${discord.webhook}")
    private String webhook;

    private final WebClient webClient;

    public Mono<Boolean> send(Notification notification) {
        DiscordWebhookRequest discordRequest = new DiscordWebhookRequest(notification.toString());

        return webClient.post()
                .uri(webhook)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(discordRequest)
                .retrieve()
                .toBodilessEntity()
                .map(response -> {
                    return response.getStatusCode().is2xxSuccessful();
                })
                .onErrorReturn(false);
    }

    @Getter @Setter
    private static class DiscordWebhookRequest{
        private String content;
        public DiscordWebhookRequest(String content) {this.content = content;}
    }

}
