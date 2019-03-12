package org.softwire.training.bookish.dbinteractiontests;


import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.Main;

import java.util.List;

public class JDBITest {
    private static String rowFomat = "%20s : %-20s";

    public static List<String> printBooksOrderedByTitle(String connectionString) {
        Jdbi jdbi = Jdbi.create(connectionString);
        String format = "%20s : %-20s";
        List<String> outputRows = jdbi.withHandle(handle ->
             handle.createQuery(Main.bookListingQuery).map((resultSet, context) ->
                        String.format(format, resultSet.getString(1), resultSet.getString(2))
                    ).list()
        );
        outputRows.add(0, String.format(format, "BookCopy Title", "Author(s)"));
        return outputRows;
    }
}
