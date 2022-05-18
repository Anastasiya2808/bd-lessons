package ru.ifmo.lessons.base;

import ru.ifmo.lessons.dao.Author;

import java.sql.*;
import java.util.HashSet;

public class JDBCLesson {
    // данные для подключения
    // 1. строка подключения
    static final String CONNECTION_STR =
            "jdbc:postgresql://localhost:5432/lessons";
    static final String LOGIN = "postgres"; // 2. логин
    static final String PWD = "anas22";  // 3. пароль

    private static void createTable()
            throws ClassNotFoundException, SQLException {
        String create = "CREATE TABLE IF NOT EXISTS tb_books(" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(50) NOT NULL" +
                ");";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(
                CONNECTION_STR, LOGIN, PWD
        )){
            // PreparedStatement - для выполения запроса (кешируютя, данные кронируются)
            try(Statement statement = connection.createStatement()){
               // Statement - не кешируются (не сохраняются в памяти)
               // если в запрос не передаются данные из вне
                // возвращает кол-во
                int res = statement.executeUpdate(create);
                System.out.println(res);
            }
        }
    }

    /* INSERT INTO author (name, age)
    VALUES ('qwerty', 28),*/

    private static void insertIntoAuthorTb(String name, int age) throws ClassNotFoundException, SQLException {
      /*  String insert = "INSERT INTO author (name, age)" + "VALUES (" + name + "," + age + "" + ")"; */
        String insert = "INSERT INTO author (name, age)" + "VALUES (?, ?)";
        Class.forName("org.postgresql.Driver"); // все зависти от данного драйвера
        try (Connection connection = DriverManager.getConnection(
                CONNECTION_STR, LOGIN, PWD
        )){
            try (PreparedStatement statement = connection.prepareStatement(insert)){
                // передача данных вместо вопросительных знаков
                statement.setString(1, name);
                statement.setInt(2, age);
                int res = statement.executeUpdate();
                System.out.println("результат добавления = " + res);
            }
        }
    }

    public static void getAllAuthors () throws ClassNotFoundException, SQLException{
        String select = "SELECT * FROM author";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(
                CONNECTION_STR, LOGIN, PWD
        )){
            try (Statement statement = connection.createStatement()){
                // выполнение select  запросов, метод executeQuery
                ResultSet resultSet = statement.executeQuery(select);
                while (resultSet.next()){
                    // author_id - int
                    // name - String
                    // age - int
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    int id = resultSet.getInt("id");
                    System.out.println("name" + name);
                    System.out.println("age" + age);
                    System.out.println("id" + id);
                }
            }
        }
    }

    private static HashSet<Author> getAuthorsByAge (int age) throws ClassNotFoundException, SQLException {
        HashSet<Author> authors = new HashSet<>();
        String select = "SELECT * FROM author WHERE age > ?";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(
                CONNECTION_STR, LOGIN, PWD
        )){
            try (PreparedStatement statement = connection.prepareStatement(select)){
                statement.setInt(1, age);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Author author = new Author();
                    author.setId(resultSet.getInt("author_id"));
                    author.setAge(resultSet.getInt("age"));
                    author.setName(resultSet.getString("name"));
                    authors.add(author);
                }
            }
        }
        return authors;
    }

    private static void articlesByData(String date) throws ClassNotFoundException, SQLException {
        String select = "SELECT title FROM article WHERE created_on > ?";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(
                CONNECTION_STR, LOGIN, PWD
        )) {
            try(PreparedStatement statement = connection.prepareStatement(select)) {
                statement.setTimestamp(1, Timestamp.valueOf(date));
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    System.out.println("title " + resultSet.getString("title "));
                }

            }
        }
    }

    public static void main(String[] args) {
            try {
                createTable();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                getAllAuthors();
                System.out.println(getAuthorsByAge(30));
                // getAuthorsByAge(30).forEach(System.out::println); - чтобы было с новой строчки
                articlesByData("2019-08-07 00:00:00");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
    }
}
// вывестив консоль название статей,
// дата создания которых больше "2019-08-07"
// WHERE created_on "2019-08-07"
/* Data data = new Data();
data.setMonth()...
        data.setYear()...
        data.setDay()... */

