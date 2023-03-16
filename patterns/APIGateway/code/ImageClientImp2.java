package APIGateway;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * subclass of IImageClient that return the image path (semi-real example)
 */
public class ImageClientImp2 implements IImageClient{

  @Override
  public String getImagePath(){
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpGet = HttpRequest.newBuilder().GET().uri(URI.create("https://localhost:8080/images")).build();

    try {
      HttpResponse httpResponse = httpClient.send(httpGet, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
