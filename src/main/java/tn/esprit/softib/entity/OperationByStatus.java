package tn.esprit.softib.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationByStatus {

	private int id;
	private int executed;
	private int to_be_executed;
	private int cancelled;}