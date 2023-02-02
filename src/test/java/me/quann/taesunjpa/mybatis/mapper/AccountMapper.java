package me.quann.taesunjpa.mybatis.mapper;

import me.quann.taesunjpa.mybatis.vo.AccountMyBatisVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
public interface AccountMapper {

    AccountMyBatisVO selectAccount(@Param("id") int id);

    void insertAccount(AccountMyBatisVO vo);

}
