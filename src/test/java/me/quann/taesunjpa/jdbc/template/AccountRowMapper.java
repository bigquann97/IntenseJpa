package me.quann.taesunjpa.jdbc.template;

import me.quann.taesunjpa.jdbc.vo.AccountVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<AccountVO> {

    @Override
    public AccountVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        // ResultSet을 받아서 매핑해서 리턴해주는 방식 - jdbcTemplatedㅔ서 사용
        AccountVO vo = new AccountVO();
        vo.setId(rs.getInt("ID"));
        vo.setUsername(rs.getString("USERNAME"));
        vo.setPassword(rs.getString("PASSWORD"));
        return vo;
    }

}
