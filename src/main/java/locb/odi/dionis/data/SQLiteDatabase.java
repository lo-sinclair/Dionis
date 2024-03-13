package locb.odi.dionis.data;

import locb.odi.dionis.Dionis;
import locb.odi.dionis.model.Drinker;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class SQLiteDatabase implements Database {

    private final String url;

    public SQLiteDatabase(File file) throws SQLException {
        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try (Connection con = connect(); Statement st = con.createStatement()) {
            st.executeUpdate(String.format(
                    "CREATE TABLE IF NOT EXISTS `%s` (" +
                            "`uuid` VARCHAR(36)," +
                            "`name` TEXT NOT NULL, " +
                            "`count` INTEGER NOT NULL, " +
                            "`time` BIGINT NOT NULL, " +
                            "PRIMARY KEY (`uuid`));",
                    "dionis_drinkers"));
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url);
    }

    private void createDrinker(Drinker drinker) {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String q = "INSERT INTO dionis_drinkers (`uuid`, `name`, `count`, `time`) "
                    + "VALUES ('%s', '%s', %d, %d);";
            stmt.executeUpdate(String.format(Locale.ROOT, q,
                    drinker.getUuid(),
                    drinker.getName(),
                    drinker.getCount(),
                    drinker.getTime()));

        } catch (SQLException e) {
            Dionis.error("Failed to create drinker", e);
        }
    }

    private void updateDrinker(Drinker drinker) {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String q = "UPDATE dionis_drinkers SET " +
                    "`uuid` = '%s'," +
                    "`name` = '%s'," +
                    "`count` = %d," +
                    "`time` = %d," +
                    "WHERE `uuid` = %s;";

            stmt.executeUpdate(String.format(Locale.ROOT, q,
                    drinker.getName(),
                    drinker.getCount(),
                    drinker.getTime()));
                    drinker.getUuid();

        } catch (SQLException e) {
            Dionis.error("Failed to update drinker", e);
        }
    }



    public void saveDrinker(Drinker drinker) {
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String q = "INSERT INTO dionis_drinkers (`uuid`, `name`, `count`, `time`)"  +
                    " VALUES ('%s', '%s', %d, %d)" +
                    " ON CONFLICT DO UPDATE SET " +
                    " `uuid` = '%s'," +
                    " `name` = '%s'," +
                    " `count` = %d," +
                    " `time` = %d;";

            stmt.executeUpdate(String.format(Locale.ROOT, q,
                    drinker.getUuid().toString(),
                    drinker.getName(),
                    drinker.getCount(),
                    drinker.getTime(),
                    drinker.getUuid().toString(),
                    drinker.getName(),
                    drinker.getCount(),
                    drinker.getTime()
            ));

        } catch (SQLException e) {
            Dionis.error("Failed to save drinker", e);
        }
    }

    @Override
    public Drinker findDrinkerByUUD(UUID uuid) {
        Drinker drinker = null;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String q = "SELECT * FROM dionis_drinkers WHERE `uuid` = '%s'";

            ResultSet result = stmt.executeQuery(String.format(q, uuid.toString()));

            if(result.next()) {

                drinker = new Drinker(
                        UUID.fromString(result.getString("uuid")),
                        result.getString("name"),
                        result.getInt("time"),
                        result.getInt("count")
                );
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drinker;
    }


    public List<Drinker> findAlcoholics() {
        return null;
    }


    @Override
    public boolean deleteDrinker(UUID uuid) {
        boolean result = false;

        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();

            String q;
            q = "DELETE FROM dionis_drinkers WHERE `uuid`='%s';";
            result = stmt.execute(String.format(q, uuid.toString()));

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

       return result;
    }


    public void close() {


    }
}