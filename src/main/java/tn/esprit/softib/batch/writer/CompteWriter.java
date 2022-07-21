package tn.esprit.softib.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.repository.CompteRepository;

@Slf4j
public class CompteWriter implements ItemWriter<Compte> {
	  @Autowired
	    CompteRepository compteRepository;

	    @Override
	    public void write(List<? extends Compte> list) throws Exception {
	      log.debug("item writer: {}", list.get(0));
	      compteRepository.saveAllAndFlush(list);
	    }

}
