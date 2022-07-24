package com.adli.returnmanagementservice;

import com.adli.returnmanagementservice.entity.order.Items;
import com.adli.returnmanagementservice.entity.order.Orders;
import com.adli.returnmanagementservice.entity.product.Products;
import com.adli.returnmanagementservice.repository.order.ItemsRepository;
import com.adli.returnmanagementservice.repository.order.OrdersRepository;
import com.adli.returnmanagementservice.repository.product.ProductsRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class ReturnManagementServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReturnManagementServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReturnManagementServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			OrdersRepository ordersRepository,
			ItemsRepository itemsRepository,
			ProductsRepository productsRepository,
			@Value("classpath:orders.csv")
			Resource resource
	) {
		return (args -> {
			CsvMapper mapper = new CsvMapper();
			CsvSchema schema = CsvSchema.builder()
					.addColumn("orderId")
					.addColumn("emailAddress")
					.addColumn("sku")
					.addColumn("quantity")
					.addColumn("price")
					.addColumn("itemName")
					.build().withHeader();

			MappingIterator<Map<String, String>> csvIterator = mapper.readerForMapOf(String.class)
					.with(schema)
					.readValues(resource.getFile());
			while (csvIterator.hasNextValue()) {
				Map<String, String> row = csvIterator.nextValue();

				String orderId = row.get("orderId");
				if (!StringUtils.hasText(orderId)) {
					continue;
				}

				String email = row.get("emailAddress");

				Orders order = ordersRepository.findByIdAndEmail(orderId, email).orElse(
						ordersRepository.save(Orders.builder()
								.id(orderId)
								.email(email)
								.build())
				);

				String productId = row.get("sku");
				float price = Float.parseFloat(row.get("price"));
				Products product = productsRepository.findById(productId).orElse(
						// not found product with this sku. will create
						productsRepository.save(Products.builder()
								.sku(productId)
								.name(row.get("itemName"))
								.price(price)
								.build())
				);

				itemsRepository.save(Items.builder()
						.sku(productId)
						.price(price)
						.order(order)
						.quantity(Integer.parseInt(row.get("quantity")))
						.build());
			}
		});
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
