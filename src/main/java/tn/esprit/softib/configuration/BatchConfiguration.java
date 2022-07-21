package tn.esprit.softib.configuration;


import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.batch.mapper.CompteMapper;
import tn.esprit.softib.batch.processor.CompteItemProcessor;
import tn.esprit.softib.batch.writer.CompteWriter;
import tn.esprit.softib.entity.Compte;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Random;

@EnableBatchProcessing
@Slf4j
@Configuration
public class BatchConfiguration {
	
	  @Autowired
	    public JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    public StepBuilderFactory stepBuilderFactory;

	    @Autowired
	    JobLauncher jobLauncher;

	    @Autowired
	    DataSource dataSource;


	    private final String JOB_NAME = "emailSenderJob";
	    private final String STEP_NAME = "emailSenderStep";

	    Random random = new Random();
	    int randomWithNextInt = random.nextInt();


	    @Bean(name = "emailSenderJob")
	    public Job emailSenderJob() {
	        return this.jobBuilderFactory.get(JOB_NAME+randomWithNextInt)
	                .start(emailSenderStep())
	                .build();
	    }

	    @Bean
	    public Step emailSenderStep() {
	        return (Step) this.stepBuilderFactory
	                .get(STEP_NAME)
	                .<Compte, Compte>chunk(100)
	                .reader(activeCompteReader())   //read all the data for us
	                .processor(compteItemProcessor())   //send email for each of compte records
	                .writer(compteWriter())     //update the status of colonne email status
	                .build();
	    }

	    @Bean
	    public ItemProcessor<Compte, Compte> compteItemProcessor() {
	        return new CompteItemProcessor();
	    }

	    @Bean
	    public ItemWriter<Compte> compteWriter() {
	        return new CompteWriter();
	    }

	    @Bean
	    public ItemReader<Compte> activeCompteReader() {
	        String sql = "SELECT * FROM compte WHERE status=1 and emailsent=0";
	        return new JdbcCursorItemReaderBuilder<Compte>()
	                .name("activeCompteReader")
	                .sql(sql)
	                .dataSource(dataSource)
	                .rowMapper(new CompteMapper())
	                .build();
	    }

}
