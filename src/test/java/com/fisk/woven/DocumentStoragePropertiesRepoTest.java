package com.fisk.woven;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DocumentStoragePropertiesRepoTest {

  @Autowired private DataSource dataSource;
  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private DocumentStoragePropertiesRepo documentStoragePropertiesRepo;

  public void loadData() {

    DocumentProperties properties = new DocumentProperties();
    properties.setSize(String.valueOf(234234));
    properties.setDocumentFormat("png");
    properties.setDocumentType("docType");
    properties.setUserId(3);
    properties.setFileName("fileName");
    properties.setUploadDir("/test/repo/fileName.png");
    documentStoragePropertiesRepo.save(properties);
    DocumentProperties properties2 = new DocumentProperties();
    properties2.setSize(String.valueOf(234234));
    properties2.setDocumentFormat("MIME");
    properties2.setDocumentType("docType2");
    properties2.setUserId(3);
    properties2.setFileName("fileName2");
    properties2.setUploadDir("/test/repo/fileName2.png");
    properties2.setSize("12");
    documentStoragePropertiesRepo.save(properties2);

  }

  @Test
  void checkDocumentByUserId() {
    loadData();
    DocumentProperties newDocuProp =
        documentStoragePropertiesRepo.checkDocumentByUserId(3, "docType");
    assertEquals(newDocuProp.getSize(), String.valueOf(234234));
  }

  @Test
  void getUploadPath() {
    loadData();
    String newDocuProp = documentStoragePropertiesRepo.getUploadPath(3, "docType");
    assertEquals(newDocuProp, "fileName");
  }

  @Test
  void deleteFile()
  {
    loadData();
    documentStoragePropertiesRepo.deleteFile(3, "docType");
    List<DocumentProperties> test = documentStoragePropertiesRepo.getFiles();
    assertEquals(test.size(), 1);
  }

  @Test
  void getFiles() {
    loadData();
    List<DocumentProperties> test = documentStoragePropertiesRepo.getFiles();
    assertEquals(test.size(), 2);
  }
}
