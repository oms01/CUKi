package project.univAlarm.batch.sender.sender;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import project.univAlarm.batch.sender.PushNotificationReport;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DiscordReportSender {

    @Value("${discord.webhook}")
    private String webhook;

    public Mono<Boolean> send(PushNotificationReport pushNotificationReport) {
        DiscordWebhookRequest discordRequest = new DiscordWebhookRequest(pushNotificationReport.toString());

        return WebClient.create().post()
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
