package vttp.batch5.ssf.noticeboard.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.models.PostedNotice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;
import vttp.batch5.ssf.noticeboard.utils.Constants;

// Use this class to write your request handlers

@Controller
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @GetMapping("/")
    public String homePage (Model model){

        Notice notice = new Notice();

        model.addAttribute("notice", notice);

        return "notice";
    }

    @PostMapping("/notice")
    public String postNotice(@Valid @ModelAttribute(value="notice") Notice n, BindingResult bindingResult, 
                            Model model,
                            @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "poster", required = false) String poster,
                            @RequestParam(value = "postDate", required = false) String postDate,
                            @RequestParam(value = "categories", required = false) List<String> categories,
                            @RequestParam(value = "text", required = false) String text) throws IllegalArgumentException, IllegalAccessException, IOException, ParseException{
        if (bindingResult.hasErrors()){
            return "notice";
        }
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(postDate);
        Long epochDate = date.getTime();

        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (String s : categories){
            jab.add(s);
        }
        
        JsonArray categoryJsonArray = jab.build();

        JsonObject json = Json.createObjectBuilder()
        .add("title", title)
        .add("poster", poster)
        .add("postDate", epochDate)
        .add("categories", categoryJsonArray)
        .add("text", text).build();

        String postUrl = Constants.url+"notice";
        ResponseEntity<String> responseEntity = noticeService.postToNoticeServer(json, postUrl);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String body = responseEntity.getBody();
        
        JsonReader jsonReader = Json.createReader(new StringReader(body));
        JsonObject jsonObject = jsonReader.readObject();

        if (!statusCode.is2xxSuccessful()){ 

            String message = jsonObject.getString("message");

            model.addAttribute("message", message);
            return "view3";

        }

        String id = jsonObject.getString("id");

        System.out.println(id);
        noticeService.saveResponseRedis(id, jsonObject.toString());
        
        model.addAttribute("id", id);
        return "view2";

    }

}
