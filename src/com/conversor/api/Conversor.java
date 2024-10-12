package com.conversor.api;

import com.conversor.model.Moneda;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conversor {
    private String base;
    private String target;
    private Double amount;
    private Integer eleccionTipoDeCambio;
    private Double resultadoDeConversion;

    public Conversor(int eleccion, Double valorParaConvertir) {
        this.amount = valorParaConvertir;
        this.eleccionTipoDeCambio = eleccion;
    }

    public Double getAmount() {
        return amount;
    }

    public void convertidorDeMoneda() {
        String apiKey = "f47204bb7a5238f1a67dd0ae";
        String dolar = "USD";
        String pesoArgentino = "ARS";
        String realBrasil = "BRL";
        String pesoColombiano = "COP";

        // necesito saber que estoy convirtiendo para el mensaje final
        switch (eleccionTipoDeCambio) {
            case 1:
                base = dolar;
                target = pesoArgentino;
                break;
            case 2:
                base = pesoArgentino;
                target = dolar;
                break;
            case 3:
                base = dolar;
                target = realBrasil;
                break;
            case 4:
                base = realBrasil;
                target = dolar;
                break;
            case 5:
                base = dolar;
                target = pesoColombiano;
                break;
            case 6:
                base = pesoColombiano;
                target = dolar;
                break;
        }

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + target + "/" + amount);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Moneda resultado = new Gson().fromJson(response.body(), Moneda.class);
            // solo el valor convertido
            resultadoDeConversion = resultado.conversion_result();

        } catch (IOException | InterruptedException  e) {
            throw new RuntimeException(e);
        }
    }

    public String mensajeResultadoConversion() {
        return "El valor " + amount + " [" + base + "] " +
                "corresponde al valor final de =>>> " + String.format("%.4f", resultadoDeConversion) + " [" + target + "]";
    }
}
