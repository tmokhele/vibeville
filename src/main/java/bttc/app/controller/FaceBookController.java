package bttc.app.controller;

import bttc.app.service.FaceBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FaceBookController {

    @Autowired
    FaceBookService faceBookService;

    @GetMapping("/createFacebookAuthorization")
    public String createFacebookAuthorization(){
        return faceBookService.createFacebookAuthorizationURL();
    }

    @GetMapping("/facebook")
    public void createFacebookAccessToken(@RequestParam("code") String code){
        faceBookService.createFacebookAccessToken(code);
    }

    @GetMapping("/getName")
    public String getNameResponse() {
        return faceBookService.getName();
    }

    @GetMapping("/verifyRequest")
    public void
    verifyRequest(@RequestParam("hub.mode") String mode, @RequestParam("hub.challenge")String challenge, @RequestParam("hub.verify_token")String token)
    {

    }

    }
