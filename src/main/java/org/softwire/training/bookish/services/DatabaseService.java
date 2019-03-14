package org.softwire.training.bookish.services;

import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private String hostname = "localhost";
    private String database = "bookish";
    private String user = "bookish";
    private String password = "bookish";
    private String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

    private Jdbi jdbi = Jdbi.create(connectionString);

    public List<Technology> getAllTechnologies() {
        List<Technology> technologies = jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM technologies")
                        .mapToBean(Technology.class)
                        .list()
        );

        return technologies;
    }

    public void addTechnology(Technology technology) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO technologies (name, logoUrl) VALUES (:name, :logoUrl)")
                        .bind("name", technology.getName())
                        .bind("logoUrl", technology.getLogoUrl())
                        .execute()
        );
    }

    public void deleteTechnology(int technologyId) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM technologies WHERE id = :id")
                        .bind("id", technologyId)
                        .execute()
        );
    }
}
