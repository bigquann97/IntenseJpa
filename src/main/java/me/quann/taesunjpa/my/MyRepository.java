package me.quann.taesunjpa.my;

import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Setter
public class MyRepository {

    private HashMap<Long, String> dataTable; // DB 테이블을 의미

    public String find(Long id) {
        return dataTable.getOrDefault(id, "");
    }

    public Long save(String data) {
        var newId = Long.valueOf(dataTable.size());
        this.dataTable.put(newId, data);
        return newId;
    }
}