package tn.esprit.softib.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.service.ICardRequest;

@Component
@Slf4j
public class ScheduledTasks {
	@Autowired 
	ICardRequest icardRequest;

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	@Scheduled(fixedRate = 5000)
	//@Scheduled(cron = "0 12 0 * * THU")
	@Scheduled(cron = "0 */2 * ? * *")
	public void reportCurrentTime() {
		log.info("Job Spring Scheduler started");
		icardRequest.deleteAutoCard();
		log.info("Card rejected Deleted sucessfuly !");
		log.info("The time is now {}", dateFormat.format(new Date()));
	}


}
