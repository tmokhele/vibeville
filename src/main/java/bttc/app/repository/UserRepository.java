package bttc.app.repository;

import bttc.app.exception.RestTemplateResponseErrorHandler;
import bttc.app.model.*;
import bttc.app.util.UserSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@Repository
public class UserRepository {

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.identity.key}")
    private String apiUrl;

    @Value("${firebase.db.url}")
    private String dbUrl;

    private RestTemplate restTemplate;

    @Autowired
    public UserRepository(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public FirebaseAuthResponse existsByUsername(String usernameOrEmail, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setEmail(usernameOrEmail);
        userLogin.setPassword(password);
        String url = apiUrl + "verifyPassword?key=" + apiKey;
        ResponseEntity<FirebaseAuthResponse> responseEntity = restTemplate.postForEntity(url, userLogin, FirebaseAuthResponse.class);
        return responseEntity.getBody();
    }

    public AccountInformation existsByIdToken(String idToken)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("getAccountInfo?key={0}",apiKey));
        Token token = new Token();
        token.setIdToken(idToken);
        ResponseEntity<AccountInformation> responseEntity = restTemplate.postForEntity(stringBuilder.toString(),token, AccountInformation.class);
        return responseEntity.getBody();
    }

    public User getAccountInfo(String idToken, String uid) {
        StringBuilder stringBuilder = new StringBuilder();
        List<User> userList = new ArrayList<>();
        String token = bttc.app.util.Token.invoke();
        Gson g = new Gson();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}&orderBy=\"uid\"&equalTo=\"{1}\"", token, uid));
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) forEntity.getBody();
        map.entrySet().forEach(e -> userList.add(g.fromJson(JSONObject.valueToString(e.getValue()), User.class)));
        return userList.get(0);
    }

    public FirebaseUser saveLoginDetails(UserLogin user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("signupNewUser?key={0}", apiKey));
        ResponseEntity<FirebaseUser> createdUser = restTemplate.postForEntity(stringBuilder.toString(), user, FirebaseUser.class);
        return createdUser.getBody();
    }

    public User saveAdditionalUserDetails(User signUpRequest){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create();
        User user = gson.fromJson(gson.toJson(signUpRequest),User.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("profileInformation.json");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), user, User.class);
        return userResponseEntity.getBody();
    }

    public boolean deleteUser(String idToken) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("deleteAccount?key={0}", apiKey));
        restTemplate.postForEntity(stringBuilder.toString(),new UserDelete(idToken),UserDelete.class);
        return true;
    }

    public PasswordResetResponse resetPassword(String email)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("getOobConfirmationCode?key={0}",apiKey));
        ResponseEntity<PasswordResetResponse> passwordResetResponseResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), new PasswordReset(email), PasswordResetResponse.class);
        return passwordResetResponseResponseEntity.getBody();
    }

    public PasswordChangeResponse passwordChange(String idToken, String newPassword)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("setAccountInfo?key={0}",apiKey));
        ResponseEntity<PasswordChangeResponse> passwordResetResponseResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), new PasswordRequest(idToken,newPassword), PasswordChangeResponse.class);
        return passwordResetResponseResponseEntity.getBody();
    }

    public PasswordChangeResponse confirmPasswordRequest(PasswordConfirmation passwordConfirmation) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("resetPassword?key={0}",passwordConfirmation.getApiKey()));
        ResponseEntity<PasswordChangeResponse> passwordResetResponseResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), new PasswordConfirmation(passwordConfirmation.getOobCode(),passwordConfirmation.getNewPassword()), PasswordChangeResponse.class);
        return passwordResetResponseResponseEntity.getBody();
    }
}
