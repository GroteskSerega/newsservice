package com.news.newsservice;

import com.news.newsservice.configuration.LiquibaseAotHints;
import com.news.newsservice.entity.*;
import com.news.newsservice.validation.*;
import com.news.newsservice.web.dto.v1.*;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;

//@ImportRuntimeHints(LiquibaseAotHints.class)
@RegisterReflectionForBinding({
		UserUpsertRequest.class,
		UserFilter.class,
		UserResponse.class,
		UserListResponse.class,

		UserFilterValid.class,
		UserFilterValidValidator.class,

		CategoryUpsertRequest.class,
		CategoryFilter.class,
		CategoryResponse.class,
		CategoryListResponse.class,

		CategoryFilterValid.class,
		CategoryFilterValidValidator.class,

		NewsUpsertRequest.class,
		NewsFilter.class,
		NewsResponse.class,
		NewsListResponse.class,

		NewsFilterValid.class,
		NewsFilterValidValidator.class,

		CommentUpsertRequest.class,
		CommentFilter.class,
		CommentResponse.class,
		CommentListResponse.class,

		CommentFilterValid.class,
		CommentFilterValidValidator.class,

		RoleType.class,
		Role.class,
		User.class,
		Category.class,
		News.class,
		Comment.class,

//		liquibase.changelog.FastCheckService.class,
//		liquibase.changelog.DatabaseChangeLog.class,
//		liquibase.parser.core.yaml.YamlChangeLogParser.class,
//
//		liquibase.ui.LoggerUIService.class,
//		liquibase.license.LicenseServiceFactory.class,
//		liquibase.executor.ExecutorService.class,
//
//		liquibase.change.core.CreateTableChange.class,
//		liquibase.change.core.AddForeignKeyConstraintChange.class,
//		liquibase.database.core.PostgresDatabase.class,
//
		java.util.ArrayList.class,
		java.util.UUID.class,
		java.util.UUID[].class,
		java.time.Instant.class,
		java.time.Instant[].class
})
@SpringBootApplication(exclude = { LiquibaseAutoConfiguration.class })
public class NewsserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsserviceApplication.class, args);
	}

}
