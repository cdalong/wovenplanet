package com.fisk.woven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {
  @Autowired private DocumentService documentService;
  @PostMapping("/uploadFile")
  public UploadFileResponse uploadFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam("userId") Integer UserId,
      @RequestParam("docType") String docType) {
    String fileName = documentService.storeFile(file, UserId, docType);
    String fileDownloadUri =
        UriComponentsBuilder.fromPath("/downloadFile/" + fileName).toUriString();
    return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
  }

  @GetMapping("/downloadFile")
  public ResponseEntity<Resource> downloadFile(
      @RequestParam("userId") Integer userId,
      @RequestParam("docType") String docType,
      HttpServletRequest request) {
    String fileName = documentService.getDocumentName(userId, docType);
    Resource resource = null;
    if (fileName != null && !fileName.isEmpty()) {
      try {
        resource = documentService.loadFileAsResource(fileName);
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Try to determine file's content type
      String contentType = null;

      try {

        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

      } catch (IOException ex) {

        // logger.info("Could not determine file type.");
      }
      // Fallback to the default content type if type could not be determined
      if (contentType == null) {
        contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
          .contentType(MediaType.parseMediaType(contentType))
          .header(
              HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=\"" + resource.getFilename() + "\"")
          .body(resource);

    } else {

      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/deleteFile")
  public ResponseEntity<Resource> deleteFile(
      @RequestParam("userId") String UserId,
      @RequestParam("docType") String docType,
      HttpServletRequest request) {
    String fileName = documentService.getDocumentName(Integer.valueOf(UserId), docType);

    if (fileName == null) {
      return ResponseEntity.notFound().build();
    }
    documentService.deleteFile(Integer.valueOf(UserId), docType);
    return ResponseEntity.ok().build();
  }

  @GetMapping("listFiles")
  public ListFilesResponse listFiles(HttpServletRequest request)
  {
    List<DocumentProperties> response = documentService.getFiles();

    if (response.isEmpty())
    {
      return new ListFilesResponse();
    }
    return new ListFilesResponse(response);

  }
}
