package com.fisk.woven;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DocumentStoragePropertiesRepo extends JpaRepository<DocumentProperties, Integer> {

  @Query("Select a from DocumentProperties a where user_id = ?1 and document_type = ?2")
  DocumentProperties checkDocumentByUserId(Integer userId, String docType);

  @Query("Select fileName from DocumentProperties a where user_id = ?1 and document_type = ?2")
  String getUploadPath(Integer userId, String docType);

  @Transactional
  @Modifying
  @Query("delete from DocumentProperties  a where user_id = ?1 and document_type = ?2")
  void deleteFile(Integer userId, String docType);

  @Query("select a from DocumentProperties a")
  List<DocumentProperties> getFiles();
}
