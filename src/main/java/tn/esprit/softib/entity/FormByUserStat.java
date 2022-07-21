package tn.esprit.softib.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormByUserStat {

	private String username;
	private int pending;
	private int confirmed;
	private int rejected;
	
}
