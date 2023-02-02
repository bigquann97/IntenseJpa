package me.quann.taesunjpa.jdbc;

import me.quann.taesunjpa.jdbc.dao.AccountDAO;
import me.quann.taesunjpa.jdbc.vo.AccountVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCTest {

    @Test
    @DisplayName("JDBC DB 연결 실습")
    void jdbcTest() throws SQLException {
        // given

        // docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=teasun -e POSTGRES_DB=messenger --name postgres_boot -d postgres
        // -p : port
        // -e : 환경변수
        // --name : 컨테이너의 이름
        // -d : deamon으로 백그라운드에 띄우겠다
        // 실제 postgres 이미지 - 해당 이미지가 있는 서버를 띄운다

        // docker exec -i -t postgres_boot bash
        // postgres_boot 이름의 컨테이너에 bash 명령어를 실행하겠다.

        // su - postgres
        // psql --username teasun --dbname messenger
        // \list (데이터 베이스 조회)
        // \dt (테이블 조회)

        // IntelliJ Database 에서도 조회

        String url = "jdbc:postgresql://localhost:5432/messenger";
        String username = "teasun";
        String password = "pass";

        // when
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try {
                String creatSql = "CREATE TABLE ACCOUNT (id SERIAL PRIMARY KEY, username varchar(255), password varchar(255))";
                try (PreparedStatement statement = connection.prepareStatement(creatSql)) {
                    statement.execute();
                }
            } catch (SQLException e) {
                if (e.getMessage().equals("ERROR: relation \"account\" already exists")) {
                    System.out.println("ACCOUNT 테이블이 이미 존재합니다.");
                } else {
                    throw new RuntimeException();
                }
            }
        }

        // then
        // DB 확인
    }

    @Test
    @DisplayName("JDBC 삽입/조회 실습")
    void jdbcInsertSelectTest() throws SQLException {
        // given
        String url = "jdbc:postgresql://localhost:5432/messenger";
        String username = "teasun";
        String password = "pass";

        // when
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection created: " + connection);

            String insertSql = "INSERT INTO ACCOUNT (id, username, password) VALUES ((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), 'user1', 'pass1')";
            try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                statement.execute();
            }

            // then
            String selectSql = "SELECT * FROM ACCOUNT";
            try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
                var rs = statement.executeQuery();
                while (rs.next()) {
                    System.out.printf("%d, %s, %s", rs.getInt("id"), rs.getString("username"),
                            rs.getString("password"));
                }
            }
        }
    }


    @Test
    @DisplayName("JDBC DAO 삽입/조회 실습")
    void jdbcDAOInsertSelectTest() throws SQLException {
        // given
        AccountDAO accountDAO = new AccountDAO();

        // when
        var id = accountDAO.insertAccount(new AccountVO("new user", "new password"));

        // then
        var account = accountDAO.selectAccount(id);
        assert account.getUsername().equals("new user");
    }
}