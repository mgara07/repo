package tn.esprit.softib.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduledTasks {
	

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//	@Scheduled(fixedRate = 5000)
	@Scheduled(cron = "0 12 0 * * THU")
	public void reportCurrentTime() {
		
		log.info("The time is now {}", dateFormat.format(new Date()));
	}


}
