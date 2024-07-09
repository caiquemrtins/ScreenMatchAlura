package br.com.alura.screenmatch.principal;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class PrincipalComBuscaAssincrona {
    public static void main(String[] args) {

        System.out.println("Inicio");

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.omdbapi.com/?t=Matrix&apikey=8b3868d7"))
                .timeout(Duration.ofMinutes(2))
                .build();

        CompletableFuture<HttpResponse<String>> response = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

//        response.thenApply(HttpResponse::body)
//                response.thenAccept(System.out::println);
//        response.thenApply(resp -> resp.body())
//                .thenAccept(body -> System.out.println(body));

        response.thenAccept(resp -> {
            System.out.println("Response Body: " + resp.body());
            System.out.println("Response Code: " + resp.statusCode());

        });

        response.join();


        System.out.println("Fim ");
    }
}
