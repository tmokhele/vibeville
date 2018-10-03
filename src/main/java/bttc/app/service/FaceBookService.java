package bttc.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FaceBookService {

    @Value("${spring.social.facebook.appId}")
    String facebookAppId;
    @Value("${spring.social.facebook.appSecret}")
    String facebookSecret;
    String accessToken;

    public String createFacebookAuthorizationURL(){
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri("http://localhost:8086/facebook");
        params.setScope("public_profile,email,user_birthday,user_posts,user_status,publish_pages," +
                "user_friends,pages_messaging,read_page_mailboxes" +
                ",user_photos,manage_pages,user_likes");
        return oauthOperations.buildAuthorizeUrl(params);
    }

    public void createFacebookAccessToken(String code) {
        FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookAppId, facebookSecret);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(code, "http://localhost:8086/facebook", null);
        accessToken = accessGrant.getAccessToken();
    }

    public String getName() {
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"id", "name","posts{comments{message,user_likes,from,reactions}}"};
        String me = facebook.fetchObject("me", String.class, fields);
        return me;
    }

    public String getPostById(String postId)
    {
        return "curl -i -X GET \\\n" +
                " \"https://graph.facebook.com/v3.1/10156503989831564_10156573167721564?access_token=EAACxz3IjjDMBAAj0D6bmCXQuOZBtr74P2GoUR9uF6IgystYmQHpZBaDUFeYbOFmCqnmXFyUVjsKb6ZAeRDVKFirZC6g8qzZCBA4FNx8DXDUaxpNYQbJQaHynVMegJOSkxilRdcxsgmyyCtq5F43SVZAfxkK9ljJ4tiHSTYU6xKZCKZBM6qZAv6p4GFKEKiGNcZAfIpEYqcGGbJPAZDZD\"";
    }
}
