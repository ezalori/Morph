package org.ezalori.morph.web.repository;

import org.ezalori.morph.web.model.ExtractTable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractTableRepository extends PagingAndSortingRepository<ExtractTable, Integer> {
  @Query("SELECT COUNT(*) FROM extract_table WHERE source_instance_id = :dbId OR target_instance_id = :dbId")
  int countByDbId(int dbId);
}
