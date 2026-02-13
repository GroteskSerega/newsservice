package com.news.newsservice.configuration;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.util.ClassUtils;

import java.util.stream.Stream;


public class LiquibaseAotHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        // Чтобы Liquibase видел classpath
        hints.resources().registerPattern("db/changelog/**");
        hints.resources().registerPattern("liquibase/**");
        hints.resources().registerPattern("*.sql");

        Stream.of(
                // Группа 1: Ядро и Сервисы
                "liquibase.changelog.FastCheckService",
                "liquibase.changelog.StandardFastCheckService",
                "liquibase.database.LiquibaseTableNamesFactory",
                "liquibase.ui.LoggerUIService",
                "liquibase.license.LicenseServiceFactory",
                "liquibase.executor.ExecutorService",
                "liquibase.logging.core.LogServiceFactory",
                "liquibase.hub.HubServiceFactory",
                "liquibase.configuration.LiquibaseConfiguration",
                "liquibase.lockservice.StandardLockService",

                // Группа 2: Структура и Генерация
                "liquibase.report.InternalReportManager",
                "liquibase.command.CommandFactory",
                "liquibase.lockservice.LockServiceFactory",
                "liquibase.structure.core.Table",
                "liquibase.sqlgenerator.SqlGeneratorFactory",
                "liquibase.servicelocator.StandardServiceLocator",

                // Группа 3: Критические парсеры для YAML/SQL
                "liquibase.parser.core.yaml.YamlChangeLogParser",
                "liquibase.changelog.DatabaseChangeLog",
                "liquibase.database.core.PostgresDatabase",
                "liquibase.resource.ClassLoaderResourceAccessor",

                "liquibase.command.CommandScope",
                "liquibase.command.core.UpdateCommandStep",
                "liquibase.database.LiquibaseTableNames", //FastCheckService.<init>()
                "liquibase.database.StandardLiquibaseTableNames",
                "liquibase.logging.core.DefaultLogServiceFactory",
                "liquibase.report.StandardReportManager",
                "liquibase.command.core.helpers.DbclhTask",
                "liquibase.sqlgenerator.core.CreateDatabaseChangeLogTableGenerator"
        ).forEach(className -> {
            try {
                Class<?> clazz = ClassUtils.forName(className, classLoader);
                hints.reflection().registerType(clazz, builder ->
                        builder.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                                MemberCategory.INVOKE_PUBLIC_METHODS,
                                MemberCategory.DECLARED_FIELDS));
            } catch (Exception ignored) {

            }
        });

        try {
            hints.reflection().registerType(
                    ClassUtils.forName("liquibase.parser.core.yaml.YamlChangeLogParser", classLoader),
                    builder -> builder.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
            );
        } catch (Exception ignored) {

        }
   }
}
