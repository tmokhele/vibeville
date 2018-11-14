package bttc.app.exception;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
           new AppException(String.format("We are currently having technical problems: %s",inputStringBuilder.toString()));
        }
        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {

            String o = (String) JSONObject.stringToValue(inputStringBuilder.toString());
            Gson gson = new Gson();
            NotFoundException appException = gson.fromJson(o, NotFoundException.class);
            throw appException.getError();
        } else if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {

            throw new NotFoundException(new AppException("User not found"));
        }
        else if (httpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED)
        {
            throw  new AppException(String.format("User unauthorized: %s",inputStringBuilder.toString()));
        }else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST){
            throw new AppException(inputStringBuilder.toString());
        }

    }
}
