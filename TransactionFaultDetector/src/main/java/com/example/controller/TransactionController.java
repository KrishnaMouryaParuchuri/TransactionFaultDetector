package com.example.controller;

import com.example.model.Fault;
import com.example.model.Transaction;
import com.example.producer.TransactionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionProducer producer;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @PostMapping
    public void createTransaction(@RequestBody Transaction transaction) {
        transaction.setTimestamp(Instant.now());
        producer.sendTransaction(transaction);
    }

    @GetMapping("/faults")
    public List<Fault> getFaults(@RequestParam(required = false) String userId) {
        Criteria criteria = userId != null ? new Criteria("userId").is(userId) : new Criteria();
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Fault> searchHits = elasticsearchOperations.search(query, Fault.class);
        return searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }
}