package com.kosta.KOSTA_3_final.service.product_package;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.KOSTA_3_final.model.product_package.Category;
import com.kosta.KOSTA_3_final.model.product_package.Company;
import com.kosta.KOSTA_3_final.model.product_package.PackageVO;
import com.kosta.KOSTA_3_final.model.product_package.Product;
import com.kosta.KOSTA_3_final.model.product_package.ProductListVO;
import com.kosta.KOSTA_3_final.model.product_package.Product_Package;
import com.kosta.KOSTA_3_final.persistance.product_package.CategoryRepository;
import com.kosta.KOSTA_3_final.persistance.product_package.CompanyRepository;
import com.kosta.KOSTA_3_final.persistance.product_package.PackageRepository;
import com.kosta.KOSTA_3_final.persistance.product_package.ProductPackageRepository;
import com.kosta.KOSTA_3_final.persistance.product_package.ProductRepository;
import com.querydsl.core.types.Predicate;



@Service
public class ProductPackageService {
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	PackageRepository packageRepo;
	
	@Autowired
	ProductPackageRepository productPackageRepo;

	@Autowired
	CategoryRepository categoryRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	InsertImg insertImg;
	
	int price_result = 0;
	
	public List<Product> selectProductAll() {
		return (List<Product>) productRepo.findAll();
	}
	
	public List<Company> selectCompanyAll() {
		return (List<Company>) companyRepo.findAll();
	}
	
	public List<Product> selectProductAll(String type, String keyword) {
		System.out.println(type);
		System.out.println(keyword);
		Predicate p = productRepo.makePredicate(type, keyword);
		return (List<Product>) productRepo.findAll(p);
	}
	
	public List<Category> selectCategoryAll() {
		return (List<Category>) categoryRepo.findAll();
	}
	
	public PackageVO insertPackage(ProductListVO productList) {
		
		String pack_img = "";
		
		if(productList.getPackageType() == 0) {
			// 사진저장 클래스
			insertImg.insertImg(productList);	
			pack_img = productList.getFiles()[0].getOriginalFilename();
		}
		
		// 따로 클래스로 만들자
		PackageVO packageVO = PackageVO.builder().
				packageName(productList.getPackageName()). // 이름도 login한 사람의 이름+"package" 로 사용
				price(productList.getPackagePrice()).
				packageType(productList.getPackageType()).
				img(pack_img).
				build();

		PackageVO newPackage = packageRepo.save(packageVO);
		System.out.println(newPackage+"newPackage!!!!!!!!!!!!!!!!!");
		// 따로 클래스로 만들자
		
		 
		IntStream.range(0, productList.getProductId().length).forEach(idx->{
			
			Product p = productRepo.findById(productList.getProductId()[idx]).get();
			PackageVO packageVO1 = packageRepo.findById(packageVO.getPackageId()).get();
			
			Product_Package productPackage = Product_Package.builder().
					product_qty(productList.getProductQty()[idx]).
					product(p).
					pack(packageVO1).
					build();
			
			productPackageRepo.save(productPackage);
			System.out.println("insert 성공" + idx);
		});
		return newPackage;
	}
	
	public void adminInsertProduct(ProductListVO productList) {
		// 사진저장 클래스
		insertImg.insertImg(productList);
	    
		IntStream.range(0, productList.getProductName().length).forEach(idx->{
						
			Product product = Product.builder().
					productName(productList.getProductName()[idx]).
					price(productList.getProductPrice()[idx]).
					categoryId(productList.getCategoryId()[idx]).
					companyId(productList.getCompanyId()[idx]).
					build();
			
			productRepo.save(product);
			System.out.println("insert 성공" + idx);
		});	
	}
	
	public void adminDeleteProduct(ProductListVO productList) {
		System.out.println("ddd");  
		System.out.println(productList.getProductId()[0]);  
		// 삭제할 제품의 product_id
		Product product = productRepo.findById(productList.getProductId()[0]).get();
		System.out.println("product : "+product);

		// 프로덕트_패키지 테이블에서 package_id를 조회할 row를 조회한 후 저장
		List<Product_Package> product_pack_list = productPackageRepo.findByProduct(product);
		System.out.println("product_pack_list : "+product_pack_list);
		// 프로덕트_패키지 테이블에서 package_id를 조회한 후 저장. 여러개면 set으로 저장
		HashSet<Long> package_id_list = new HashSet<>();
		
		for(Product_Package product_pack:product_pack_list) {
			// set에 package_id저장
			package_id_list.add(product_pack.getPack().getPackageId());
			
			// 프로덕트_패키지 테이블에서 삭제 선택한 제품 로우 삭제
			productPackageRepo.deleteById(product_pack.getPpId());
		}
		System.out.println("package_id_list : "+package_id_list);
		// 프로덕트 테이블에서 선택한 프로덕트를 삭제
		productRepo.delete(product);

		// 프로덕트_패키지 테이블에서 저장한 package_id를 조회한 후 패키지 타입과 price를 조인
		Iterator<Long> it = package_id_list.iterator();
		
		while(it.hasNext()){
			
			// package_id를 한 곳에 저장
			long package_id = it.next();
			
			for(Integer product_price:productPackageRepo.findbyPackageId(package_id)) {
				System.out.println(product_price);
				price_result += product_price;
				
			}
			packageRepo.findById(package_id).ifPresent(b->{
				// 패키지 타입별로 할인 가격을 적용시켜 계산한다.
				
				// 계산된 가격을 패키지에 저장
				b.setPrice(price_result);
				
				// 새로운 패키지 가격이 저장된 패키지를 테이블에 업데이트한다.
				packageRepo.save(b);
			});
			
			// 변수를 클래스 단에서 선언했기 때문에 다음 update를 위해 reset해준다.
			price_result = 0;
		}
	}
	
	public PackageVO selectById(Long packageId) {
		return packageRepo.findById(packageId).get();
	}
	   
	   
	public List<String> findProductbyPackageNo(Long pno){
		return productPackageRepo.findProductbyPackageNo(pno);
	}
}