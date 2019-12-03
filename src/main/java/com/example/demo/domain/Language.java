package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "language")
@Data
public class Language {

    @Id
    @JsonIgnore // теперь ето поле игнорируется и не отображается на урле !!!!
    private String id;

    private String name;
    private String creator;
    private String feature;


}
