package com.fisk.woven;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;

@ConfigurationProperties(prefix = "file")
@Entity
@Table(name = "documents")
public class DocumentProperties {

  public Integer getDocumentId() {
    return documentId;
  }

  public void setDocumentId(Integer documentId) {
    this.documentId = documentId;
  }

  public Integer getUserId() {
    return UserId;
  }

  public String getFileName() {
    return fileName;
  }

  public String getDocumentType() {
    return documentType;
  }

  public String getDocumentFormat() {
    return documentFormat;
  }

  public void setUploadDir(String uploadDir) {
    this.uploadDir = uploadDir;
  }

  // keep track of uploaded files and keep track of dupes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "document_id")
  private Integer documentId;

  @Column(name = "user_id")
  private Integer UserId;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "document_type")
  private String documentType;

  @Column(name = "document_format")
  private String documentFormat;

  @Column(name = "upload_dir")
  private String uploadDir;

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  @Column(name = "file_size")
  private String size;

  public DocumentProperties() {}

  public String getUploadDir() {
    return uploadDir;
  }

  public void setDocumentFormat(String contentType) {
    this.documentFormat = contentType;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setUserId(Integer userId) {
    this.UserId = userId;
  }

  public void setDocumentType(String docType) {
    this.documentType = docType;
  }
}
