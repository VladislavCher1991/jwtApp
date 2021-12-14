package com.example.jwtapp.repos;

import com.example.jwtapp.models.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageRepo extends PagingAndSortingRepository<Message, Long> {

}
