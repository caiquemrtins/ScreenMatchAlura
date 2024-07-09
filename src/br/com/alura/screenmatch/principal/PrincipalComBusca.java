package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um filme para busca");

        var busca = scanner.nextLine();
        var chave = "8b3868d7";

        String endereco ="https://www.omdbapi.com/?t=" + busca+ "&apikey=" + chave;

        System.out.println("Inicio");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Corpo da resposta: "+ response.body());
        System.out.println("Código de retorno: " + response.statusCode());
        System.out.println("Uri: " + response.uri());
        System.out.println("Headers: "+response.headers());
        String json = response.body();

//   Sem o record TituloOMDB
//        Gson gson = new Gson();
//        Titulo meuTitulo = gson.fromJson(json, Titulo.class);
//        System.out.println("Titulo: " + meuTitulo.getNome());
//        System.out.println("Ano de lançamento: "+ meuTitulo.getAnoDeLancamento());

        // aqui ela fez o gsonbuilder para colocar o que ele recebe como CamelCase, se
        // não, ao eu tentar imprimir o meutituloomdb, vai estar com null, porque no record
        // está como title e nao Title.
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        TituloOMDB meuTituloOMDB = gson.fromJson(json, TituloOMDB.class);
        System.out.println("Titulo ainda como TituloOMDB");
        System.out.println(meuTituloOMDB);

        Titulo meuTitulo = new Titulo(meuTituloOMDB);
        System.out.println("Titulo Convertido para Titulo");
        System.out.println(meuTitulo);


        System.out.println("Fim ");
    }
}
