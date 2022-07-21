package tn.esprit.softib.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.softib.enums.ReclamationStatus;

import javax.persistence.*;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reclamation")
public class Reclamation implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReclamationStatus status;

    @Column(name = "reclamation_title")
    private String reclamationTitle;

    @Column(name = "reclamation_description")
    private String reclamationDescription;

    @Column(name = "assignee_name")
    private String assigneeName;

    @Column(name = "user_id")
    private Long userId;

    public boolean isReclamationClosed() {
        return (ReclamationStatus.CLOSED.equals(this.status));
    }
}
