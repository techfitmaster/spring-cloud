package com.huzhengxing;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDAO extends MongoRepository<User, String> {

}
