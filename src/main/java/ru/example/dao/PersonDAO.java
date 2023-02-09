package ru.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.example.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class PersonDAO {
    //private static int PEOPLE_COUNT;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/Users";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1412";
    private  static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    private List<Person> people;
//
//    {
//        people = new ArrayList<>();
//        people.add(new Person(++PEOPLE_COUNT, "Nick", 20));
//        people.add(new Person(++PEOPLE_COUNT, "Helma", 36));
//        people.add(new Person(++PEOPLE_COUNT, "Ivanushka", 10));
//        people.add(new Person(++PEOPLE_COUNT, "Annushka", 60));
//    }

    public List<Person> index() throws SQLException {
        // jdbc
//        List<Person> personList = new ArrayList<>();
//        Statement statement = connection.createStatement();
//        String SQL = "Select * from Person";
//        ResultSet resultSet = statement.executeQuery(SQL);
//
//        while(resultSet.next()) {
//            Person person = new Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setAge(resultSet.getInt("age"));
//            personList.add(person);
//        }
//        return personList;

      //  return jdbcTemplate.query("Select * from Person", new PersonMapper()); // второй аргумент - объект, который отображает строки из таблицы в сущность
        return jdbcTemplate.query("Select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) throws SQLException {
        // jdbc
//        PreparedStatement statement = connection.prepareStatement("Select * from Person where id = ?");
//        statement.setInt(1, id);
//        ResultSet resultSet = statement.executeQuery();
//        resultSet.next();
//
//        Person person = new Person();
//        person.setId(resultSet.getInt("id"));
//        person.setName(resultSet.getString("name"));
//        person.setAge(resultSet.getInt("age"));
//        return person;

        //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

        return jdbcTemplate.query("Select * from Person where id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void added(String name, int age) throws SQLException {
        //people.add(new Person(++PEOPLE_COUNT, name, age));

        Statement statement = connection.createStatement();

        String SQLCount = "Select * from Person";
        ResultSet resultSet = statement.executeQuery(SQLCount);
        int id = 0;
        while(resultSet.next()) {
            id = resultSet.getInt("id");
        }
        //        String SQL = "insert into Person values(" + ++id + ",'" + name + "','" + age + "')";
        //        statement.executeUpdate(SQL);

        jdbcTemplate.update("insert into Person values(?, ?, ?)", ++id, name, age);
    }
    public void patchPerson(int id, String name) throws SQLException {
        jdbcTemplate.update("update Person set name = ? where id = ?", name, id);

//        PreparedStatement statement = connection.prepareStatement("update Person set name = ? where id = ?");
//        statement.setString(1, name);
//        statement.setInt(2, id);
//        statement.executeUpdate();

//        Person p = show(id);
//        p.setName(name);
    }

    public void delete(int id) throws SQLException{
        jdbcTemplate.update("delete from Person where id = ?", id);

//        PreparedStatement statement = connection.prepareStatement("delete from Person where id = ?");
//        statement.setInt(1, id);
//        statement.executeUpdate();


        //people.removeIf(p -> p.getId() == id);
    }
}
