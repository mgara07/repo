package tn.esprit.softib.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tn.esprit.softib.entity.Compte;
import tn.esprit.softib.enums.Nature;

public class CompteMapper implements RowMapper<Compte>{
    @Override
    public Compte mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Compte
                .builder()
                .id(rs.getLong("id"))
                .nomComplet(rs.getString("nom_complet"))
                .rib(rs.getString("rib"))
                .iban(rs.getString("iban"))
                .codeBic(rs.getString("code_bic"))
                .solde(rs.getBigDecimal("solde"))
                .email(rs.getString("email"))
                .emailsent(rs.getBoolean("emailsent"))
                .status(rs.getBoolean("status"))
                .build();
    }


}
