package com.codingdojo.belta.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codingdojo.belta.models.PackageModel;
import com.codingdojo.belta.repositories.PackageRepository;

@Service
public class PackageService {
	
	private PackageRepository packageRepository;
	public PackageService(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}
	
	public void setNewPackage(PackageModel pack) {
		packageRepository.save(pack);
	}
	public List<PackageModel> findAll(){
		return (List<PackageModel>) packageRepository.findAll();
	}
	public PackageModel findOne(Long id) {
		return packageRepository.findOne(id);
	}
	public void activatePack(Long id) {
		PackageModel pack =  packageRepository.findOne(id);
		pack.setAvailable(true);
		packageRepository.save(pack);
	}
	public void deactivatePack(Long id) {
		PackageModel pack =  packageRepository.findOne(id);
		pack.setAvailable(false);
		packageRepository.save(pack);
	}
	
	public void deletePack(Long id) {
		packageRepository.delete(id);
	}
}
