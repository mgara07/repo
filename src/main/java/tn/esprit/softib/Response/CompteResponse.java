package tn.esprit.softib.Response;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompteResponse {
	private String message;
    private HashMap<String, Long> response;

}
