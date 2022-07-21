package tn.esprit.softib.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.softib.enums.Status;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmationMessage {
	private Status status;
	private String message;

}
