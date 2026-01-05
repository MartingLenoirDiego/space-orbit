package com.space.orbit.service;

import com.space.orbit.dto.TleDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class TleService {

    private final WebClient webClient;
    private Mono<List<TleDTO>> cachedActiveSatellites;

    public TleService(WebClient.Builder builder) {
        // Configurer WebClient pour supporter les gros fichiers (max 10 Mo)
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMinutes(1));

        this.webClient = builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // 10 Mo
                .baseUrl("https://celestrak.org")
                .build();

        // Initialisation du cache
        refreshCache();
    }

    private void refreshCache() {
        cachedActiveSatellites = Mono.defer(() ->
                webClient.get()
                        .uri("/NORAD/elements/gp.php?GROUP=active&FORMAT=tle")
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(this::parseTLE) // parse le texte TLE en objets
        ).cache(Duration.ofMinutes(30));
    }

    public Mono<List<TleDTO>> fecthActiveSatellites() {
        return cachedActiveSatellites;
    }

    /**
     * Parse le texte TLE Celestrak en une liste de TleDTO
     */
    private List<TleDTO> parseTLE(String tleText) {
        List<TleDTO> list = new ArrayList<>();
        String[] lines = tleText.split("\n");

        for (int i = 0; i < lines.length; i += 3) {
            if (i + 2 >= lines.length) break; // sécurité fin de fichier

            String name = lines[i].trim();
            String line1 = lines[i + 1].trim();
            String line2 = lines[i + 2].trim();

            list.add(new TleDTO(0, name, line1, line2));
        }

        return list;
    }
}
