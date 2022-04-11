package org.ezalori.morph.web.service;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.ezalori.morph.web.model.ExtractTable;
import org.ezalori.morph.web.repository.DatabaseInstanceRepository;
import org.ezalori.morph.web.repository.ExtractTableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExtractTableService {
  private final DatabaseInstanceRepository instanceRepo;
  private final ExtractTableRepository tableRepo;

  public List<String> getColumns(
      Integer sourceInstance, String sourceDatabase, String sourceTable) {

    var instanceOpt = instanceRepo.findById(sourceInstance);
    if (instanceOpt.isEmpty()) {
      return List.of();
    }

    var instance = instanceOpt.get();
    var url = String.format("jdbc:mysql://%s:%d/?useUnicode=true&characterEncoding=UTF-8",
        instance.getHost(), instance.getPort());

    var ds = new DriverManagerDataSource(url, instance.getUsername(), instance.getPassword());
    var jt = new JdbcTemplate(ds);

    return jt.queryForList(
        "SELECT column_name FROM information_schema.columns"
        + " WHERE table_schema = ? AND table_name = ?",
        String.class, sourceDatabase, sourceTable);
  }

  @Transactional
  public Integer saveTable(ExtractTable table) {
    ExtractTable row;
    if (table.getId() != null) {
      row = tableRepo.findById(table.getId()).orElseThrow();
    } else {
      row = new ExtractTable();
      row.setCreatedAt(new Date());
    }
    BeanUtils.copyProperties(table, row, "id", "createdAt", "updatedAt");
    tableRepo.save(row);
    return row.getId();
  }
}
