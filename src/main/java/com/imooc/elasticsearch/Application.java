package com.imooc.elasticsearch;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * Created by corning on 2018/3/5.
 */

@SpringBootApplication
@RestController
public class Application {

    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static final String BOOK_INDEX = "book";
    public static final String BOOK_TYPE_NOVEL = "novel";

    @Autowired
    private TransportClient client;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 查询接口
    @GetMapping("/get/book/novel")
    public ResponseEntity get(@RequestParam(name = "id", defaultValue = "") String id) {
        GetResponse response = client.prepareGet(BOOK_INDEX, BOOK_TYPE_NOVEL, id).get();
        if (!response.isExists()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(response.getSource(), HttpStatus.OK);
    }

    // 增加接口
    @PostMapping("/add/book/novel")
    public ResponseEntity add(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "word_count") int wordCount,
            @RequestParam(name = "publish_date")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishDate) {

        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("title", title)
                    .field("author", author)
                    .field("word_count", wordCount)
                    .field("publish_date", publishDate.getTime())
                    .endObject();

            IndexResponse response = client.prepareIndex(BOOK_INDEX, BOOK_TYPE_NOVEL)
                    .setSource(content)
                    .get();

            return new ResponseEntity(response.getId(), HttpStatus.OK);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // 删除接口
    @DeleteMapping("/delete/book/novel")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(name = "id") String id) {
        DeleteResponse response = client.prepareDelete(BOOK_INDEX, BOOK_TYPE_NOVEL, id).get();
        return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
    }


    // 更新接口
    @PutMapping("/update/book/novel")
    @ResponseBody
    public ResponseEntity update(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "word_count", required = false) Integer wordCount,
            @RequestParam(name = "publish_date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishDate) {

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();

            if (title != null) {
                builder.field("title", title);
            }
            if (author != null) {
                builder.field("author", author);
            }
            if (wordCount != null) {
                builder.field("word_count", wordCount);
            }
            if (publishDate != null) {
                builder.field("publish_date", publishDate.getTime());
            }
            builder.endObject();

            UpdateRequest update = new UpdateRequest(BOOK_INDEX, BOOK_TYPE_NOVEL, id);
            update.doc(builder);
            UpdateResponse response = client.update(update).get();
            return new ResponseEntity(response.getResult().toString(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
