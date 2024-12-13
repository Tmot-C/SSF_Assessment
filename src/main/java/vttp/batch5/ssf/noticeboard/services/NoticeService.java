package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.utils.Constants;

@Service
public class NoticeService {

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	
	@Autowired
	NoticeRepository noticeRepository;

	public ResponseEntity<String>  postToNoticeServer(JsonObject postedNotice, String url) {
		
		RestTemplate restTemplate = new RestTemplate();
            
		RequestEntity<String> requestEntity = 
		RequestEntity.post(url).contentType(MediaType.APPLICATION_JSON).body(postedNotice.toString());
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		
		return responseEntity;
	}

	public void saveResponseRedis(String id, Object object){
		noticeRepository.insertNotices(Constants.redisKey, id, object);
	}


	
}
