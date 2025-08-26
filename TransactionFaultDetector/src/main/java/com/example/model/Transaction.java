package com.example.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@Document(indexName = "transactions")
public class Transaction {
    @Field(type = FieldType.Text)
    private String userId;

    @Field(type = FieldType.Double)
    private double amount;

    @Field(type = FieldType.Date)
    private Instant timestamp;
}
