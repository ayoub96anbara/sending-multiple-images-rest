package com.anbara.sending_multiple_images_rest.web;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MyController {

    private final Path rootLocation;

    public MyController() {
        final String LOCATION = System.getProperty("user.home") + "/myFiles";
        this.rootLocation = Paths.get(LOCATION);
    }

    @GetMapping("/files/listAllFiles")
    public MyResponse listAllImages() throws Exception {
        MyResponse myResponse = new MyResponse();


        List<String> list_path =
                Files.list(this.rootLocation)
                        .map(path ->
                        {
                            return
                                    MvcUriComponentsBuilder
                                            .fromMethodName(MyController.class, "serveFile",
                                                    path.getFileName().toString())
                                            .build()
                                            .toString();

                        })
                        .collect(Collectors.toList());

        myResponse.setPath(list_path);


        return myResponse;

    }


    @GetMapping(value = "/files/{filename:.+}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    private ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {


        Path file = this.rootLocation.resolve(filename);

        Resource resource = new UrlResource(file.toUri());

        return ResponseEntity
                .ok()
                .body(resource);
    }
}
