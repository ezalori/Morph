package org.ezalori.morph.web.controller;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.ezalori.morph.web.model.ExtractTable;
import org.ezalori.morph.web.repository.ExtractTableRepository;
import org.ezalori.morph.web.service.ExtractTableService;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableController {
  private final ExtractTableRepository tableRepo;
  private final ExtractTableService tableService;

  @GetMapping("/list")
  public ApiResponse list() {
    var data = tableRepo.findAll(Sort.by("createdAt").descending());
    return new ApiResponse(Map.of("tables", data));
  }

  @GetMapping("/get")
  public ApiResponse get(@RequestParam("id") Integer id) {
    var table = tableRepo.findById(id).orElseThrow(() -> new ApiException("id not found"));
    var columns = tableService.getColumns(
        table.getSourceInstance(), table.getSourceDatabase(), table.getSourceTable());
    return new ApiResponse(Map.of("table", table, "columns", columns));
  }

  @PostMapping("/save")
  public ApiResponse save(@ModelAttribute ExtractTable table) {
    if (table.getId() != null) {
      tableRepo.findById(table.getId()).orElseThrow(() -> new ApiException("id not found"));
    }
    var tableId = tableService.saveTable(table);
    return new ApiResponse(Map.of("id", tableId));
  }

  @GetMapping("/columns")
  public ApiResponse columns(@RequestParam("sourceInstance") Integer sourceInstance,
      @RequestParam("sourceDatabase") String sourceDatabase,
      @RequestParam("sourceTable") String sourceTable) {
    var columns = tableService.getColumns(sourceInstance, sourceDatabase, sourceTable);
    return new ApiResponse(Map.of("columns", columns));
  }
}
