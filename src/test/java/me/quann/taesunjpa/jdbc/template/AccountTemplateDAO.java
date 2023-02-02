package me.quann.taesunjpa.jdbc.template;

import me.quann.taesunjpa.jdbc.vo.AccountVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTemplateDAO {

    private final JdbcTemplate jdbcTemplate; // application.yml 설정정보도 주입됨

    public AccountTemplateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private final String ACCOUNT_INSERT =
            "INSERT INTO account(ID, USERNAME, PASSWORD) "
                    + "VALUES((SELECT coalesce(MAX(ID), 0) + 1 FROM ACCOUNT A), ?, ?)";

    private final String ACCOUNT_SELECT =
            "SELECT * FROM account WHERE ID = ?";

    public Integer insertAccount(AccountVO vo) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            var ps = con.prepareStatement(ACCOUNT_INSERT, new String[]{"id"});
            ps.setString(1, vo.getUsername());
            ps.setString(2, vo.getPassword());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public AccountVO selectAccount(Integer id) {
        return jdbcTemplate.queryForObject(ACCOUNT_SELECT, new AccountRowMapper(), id);
    }

}
