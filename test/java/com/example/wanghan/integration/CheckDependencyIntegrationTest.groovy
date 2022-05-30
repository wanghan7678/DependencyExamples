package com.example.wanghan.integration

import com.example.wanghan.WanghanApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext

import spock.lang.Specification

import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CheckDependencyIntegrationTest extends Specification
{
    private ConfigurableApplicationContext ctx;

    private String url = "http://localhost:8080/checkdependency/";

    def setup()
    {
        ctx =  new SpringApplicationBuilder(WanghanApplication.class)
                .run()
    }

    def cleanup()
    {
        ctx.close()
    }

    def "check dependency integration test"()
    {
        setup:
        HttpClient client = HttpClient.newBuilder()
                .build();

        when:
        String inputFile = "src/main/resources/input/input009.txt";
        HttpResponse<String> response = sendGet(client, inputFile)

        then:
        response != null
        response.statusCode() == 200
        response.body() == "false"

        and:
        String inputFile1 = "src/main/resources/input/input010.txt";
        HttpResponse<String> response1 = sendGet(client, inputFile1)

        then:
        response1 != null
        response1.statusCode() == 200
        response1.body() == "true"


    }

    private HttpResponse<String> sendGet(HttpClient client, String inputFilePath)
    {
        String filePathEncoded = new String(Base64.getEncoder().encode(inputFilePath.getBytes()));
        String url = url + filePathEncoded + "/"
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .GET()
                .build()
        return client.send(request, HttpResponse.BodyHandlers.ofString())
    }
}
