package com.codingdojo.belta.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.belta.models.PackageModel;

public interface PackageRepository extends CrudRepository<PackageModel, Long> {

}
